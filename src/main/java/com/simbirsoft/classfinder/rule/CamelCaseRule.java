package com.simbirsoft.classfinder.rule;

import com.simbirsoft.classfinder.dto.ClassName;
import com.simbirsoft.classfinder.constants.PunctuationSymbols;

public class CamelCaseRule implements SearchRule {
    private char[] patternSymbols;

    public CamelCaseRule(String pattern) {
        this.patternSymbols = pattern.toCharArray();
    }

    public CamelCaseRule() {
    }

    @Override
    public boolean test(ClassName className) {
        String simpleClassName = className.getSimpleName();

        int previousIndex = 0;
        StringBuilder subPattern = new StringBuilder();
        for (int i = 0; i < patternSymbols.length; i++) {
            if (patternSymbols[i] == PunctuationSymbols.ASTERISK) {
                String subPatternString = subPattern.toString();
                if (subPatternString.isEmpty()) {
                    continue;
                }

                int indexOfPatternString = simpleClassName.indexOf(subPatternString, previousIndex);
                if (indexOfPatternString == -1) {
                    return false;
                } else {
                    previousIndex = indexOfPatternString + subPattern.length();
                    subPattern = new StringBuilder();
                }
                continue;
            }

            subPattern.append(patternSymbols[i]);

            if (i == patternSymbols.length - 1 || Character.isUpperCase(patternSymbols[i + 1])) {
                int indexOfPatternString = simpleClassName.indexOf(subPattern.toString(), previousIndex);
                if (indexOfPatternString == -1) {
                    return false;
                } else {
                    previousIndex = indexOfPatternString + subPattern.length();
                    subPattern = new StringBuilder();
                }
            }
        }
        return true;
    }

    @Override
    public void setPatternSymbols(char[] patternSymbols) {
        this.patternSymbols = patternSymbols;
    }
}
