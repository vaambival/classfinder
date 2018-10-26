package com.simbirsoft.classfinder.rule;

import com.simbirsoft.classfinder.dto.ClassName;

import java.util.function.Predicate;

@FunctionalInterface
public interface SearchRule extends Predicate<ClassName> {

    default void setPatternSymbols(char[] patternSymbols) {
        throw new UnsupportedOperationException();
    }
}
