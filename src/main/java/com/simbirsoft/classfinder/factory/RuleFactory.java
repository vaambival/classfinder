package com.simbirsoft.classfinder.factory;

import com.simbirsoft.classfinder.constants.PunctuationSymbols;
import com.simbirsoft.classfinder.rule.*;

public class RuleFactory {

    public static SearchRule createRule(String pattern) {
        pattern = preparePattern(pattern);
        validatePattern(pattern);

        boolean caseInsensitive = isCaseInsensitive(pattern);
        boolean containsSpace = isContainingSpace(pattern);
        boolean containsPackages = isContainingPackages(pattern);

        SearchRule rule;
        if (caseInsensitive) {
            rule = new CaseInsensitiveRule(pattern);
        } else {
            rule = new CamelCaseRule(pattern);
        }

        if (containsPackages) {
            rule = new PackageNameHandler(rule, pattern);
        }
        if (containsSpace) {
            rule = new SpaceAtTheEndHandler(rule);
        }

        return rule;
    }

    private static void validatePattern(String pattern) {
        if (pattern.isEmpty()) {
            throw new RuntimeException("Pattern: " + pattern + " is not valid: it contains only spaces and dots\n" +
                    "You should use some letters or asterisks in your pattern.");
        }
        if (pattern.trim().endsWith(".")) {
            throw new RuntimeException("Pattern cannot end with dot (.) or dot (.) and several spaces");
        }
        if (pattern.trim().contains(" ")) {
            throw new RuntimeException("Pattern cannot contain space inside. It can start or end with space(s)");
        }
        int size = pattern.length();
        for (int i = 0; i < size; i++) {
            char ch = pattern.charAt(i);
            if (!Character.isLetter(ch)
                    && ch != PunctuationSymbols.ASTERISK
                    && ch != PunctuationSymbols.DOT
                    && ch != PunctuationSymbols.SPACE) {
                throw new RuntimeException("Not valid symbol: " + ch + ". You can use A-Z, a-z, <space>, '.', '*'.");
            }
        }
    }

    private static String preparePattern(String pattern) {
        StringBuilder sb = new StringBuilder(pattern);
        while (sb.length() > 0
                && (sb.charAt(0) == PunctuationSymbols.DOT || sb.charAt(0) == PunctuationSymbols.SPACE)) {
            sb.deleteCharAt(0);
        }

        int index = 0;
        while (sb.length() > 0 && sb.charAt(index) == PunctuationSymbols.ASTERISK) {
            index++;
        }
        if (index != 0) {
            sb.delete(0, index - 1);
        }

        int lastDotIndex = sb.toString().lastIndexOf(PunctuationSymbols.DOT);
        if (lastDotIndex != -1) {
            String lowerCasePackageName = sb.substring(0, lastDotIndex).toLowerCase();
            sb.replace(0, lastDotIndex, lowerCasePackageName);
        }

        return sb.toString();
    }

    private static boolean isContainingPackages(String pattern) {
        return pattern.indexOf(PunctuationSymbols.DOT) != -1;
    }

    private static boolean isContainingSpace(String pattern) {
        return pattern.charAt(pattern.length() - 1) == PunctuationSymbols.SPACE;
    }

    private static boolean isCaseInsensitive(String pattern) {
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) >= 'A' && pattern.charAt(i) <= 'Z') {
                return false;
            }
        }
        return true;
    }
}
