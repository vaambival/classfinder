package com.simbirsoft.classfinder.rule;

import com.simbirsoft.classfinder.dto.ClassName;
import com.simbirsoft.classfinder.constants.PunctuationSymbols;

import java.util.LinkedList;
import java.util.Queue;

public class CaseInsensitiveRule implements SearchRule {
    private char[] patternSymbols;

    public CaseInsensitiveRule(String pattern) {
        this.patternSymbols = pattern.toCharArray();
    }

    @Override
    public boolean test(ClassName className) {
        String simpleName = className.getSimpleName();
        String lowerCaseSimpleName = simpleName.toLowerCase();

        Queue<String> conformityQueue = new LinkedList<>();

        char firstSymbol = patternSymbols[0];
        if (firstSymbol == PunctuationSymbols.ASTERISK) {
            conformityQueue.add(PunctuationSymbols.ASTERISK + simpleName);
        } else {
            int index = -1;
            while ((index = lowerCaseSimpleName.indexOf(firstSymbol, index + 1)) != -1) {
                conformityQueue.add(simpleName.substring(index));
            }
        }
        if (conformityQueue.isEmpty()) {
            return false;
        }

        for (int i = 1; i < patternSymbols.length; i++) {
            char symbol = patternSymbols[i];

            int size = conformityQueue.size();
            for (int j = 0; j < size; j++) {
                String subSequence = conformityQueue.poll();
                String lowerCaseSubSequence = subSequence.toLowerCase();

                if (symbol == PunctuationSymbols.ASTERISK) {
                    conformityQueue.add(PunctuationSymbols.ASTERISK + subSequence.substring(1));
                } else {
                    int index = 0;
                    while ((index = lowerCaseSubSequence.indexOf(patternSymbols[i], index + 1)) != -1) {
                        if (subSequence.charAt(0) == PunctuationSymbols.ASTERISK || (
                                (patternSymbols[i] != PunctuationSymbols.SPACE
                                        && !Character.isLowerCase(subSequence.charAt(index))
                                ) || index == 1)) {

                            conformityQueue.add(subSequence.substring(index));
                        }
                    }
                }
            }

            if (conformityQueue.isEmpty()) {
                return false;
            }
        }

        return !conformityQueue.isEmpty();
    }

    @Override
    public void setPatternSymbols(char[] patternSymbols) {
        this.patternSymbols = patternSymbols;
    }
}
