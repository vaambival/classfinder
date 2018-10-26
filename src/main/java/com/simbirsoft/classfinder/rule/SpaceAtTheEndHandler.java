package com.simbirsoft.classfinder.rule;

import com.simbirsoft.classfinder.constants.PunctuationSymbols;
import com.simbirsoft.classfinder.dto.ClassName;

public class SpaceAtTheEndHandler implements SearchRule {
    private final SearchRule rule;

    public SpaceAtTheEndHandler(SearchRule rule) {
        this.rule = rule;
    }

    @Override
    public boolean test(ClassName className) {
        className = new ClassName(
                className.getPackageName() + className.getSimpleName() + PunctuationSymbols.SPACE);
        return rule.test(className);
    }
}
