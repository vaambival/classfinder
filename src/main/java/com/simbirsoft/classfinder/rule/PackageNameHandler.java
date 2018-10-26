package com.simbirsoft.classfinder.rule;

import com.simbirsoft.classfinder.dto.ClassName;
import com.simbirsoft.classfinder.constants.PunctuationSymbols;

import java.util.Arrays;

public class PackageNameHandler implements SearchRule {
    private final SearchRule rule;
    private final char[] patternSymbols;

    public PackageNameHandler(SearchRule rule, String pattern) {
        this.rule = rule;
        this.patternSymbols = pattern.toCharArray();
    }

    @Override
    public boolean test(ClassName className) {
        int lastIndexOfPoint = -1;
        int countOfDots = 0;
        for (int i = 0; i < patternSymbols.length; i++) {
            if (patternSymbols[i] == PunctuationSymbols.DOT) {
                lastIndexOfPoint = i;
                countOfDots++;
            }
        }

        if (className.getPackageName().isEmpty()) {
            return false;
        }

        String[] packageNames = className.getPackageName()
                .split(PunctuationSymbols.PACKAGE_DELIMITER_REGEX);
        if (countOfDots != packageNames.length) {
            return false;
        }

        int index = 0;
        StringBuilder subSequence = new StringBuilder();
        for (int i = 0; i <= lastIndexOfPoint; i++) {
            if (patternSymbols[i] != PunctuationSymbols.DOT && patternSymbols[i] != PunctuationSymbols.ASTERISK) {
                subSequence.append(patternSymbols[i]);
            } else if (patternSymbols[i] == PunctuationSymbols.DOT) {
                if (!packageNames[index].contains(subSequence.toString().toLowerCase())) {
                    return false;
                }

                subSequence = new StringBuilder();
                index++;
            } else if (patternSymbols[i] == PunctuationSymbols.ASTERISK) {
                if (i != 0 && patternSymbols[i + 1] != PunctuationSymbols.DOT) {
                    StringBuilder wantedSubSequence = new StringBuilder();

                    ++i;
                    while (i < lastIndexOfPoint && patternSymbols[i] != PunctuationSymbols.DOT
                            && patternSymbols[i] != PunctuationSymbols.ASTERISK) {
                        wantedSubSequence.append(patternSymbols[i++]);
                    }
                    i--;

                    int from = packageNames[index].indexOf(subSequence.toString());
                    if (from == -1) {
                        return false;
                    }
                    from += subSequence.length();
                    int indexOfWantedSubSeq = packageNames[index].indexOf(wantedSubSequence.toString(), from);
                    if (indexOfWantedSubSeq == -1) {
                        return false;
                    }
                    int to = indexOfWantedSubSeq + wantedSubSequence.length();

                    subSequence.append(packageNames[index].substring(from, to));
                }
            }
        }

        rule.setPatternSymbols(Arrays.copyOfRange(patternSymbols, lastIndexOfPoint + 1, patternSymbols.length));

        return rule.test(className);
    }
}
