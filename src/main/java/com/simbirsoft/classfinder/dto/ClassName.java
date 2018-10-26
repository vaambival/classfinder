package com.simbirsoft.classfinder.dto;

import com.simbirsoft.classfinder.constants.PunctuationSymbols;

import java.util.Objects;

public class ClassName implements Comparable<ClassName> {
    private final String fullName;
    private final String simpleName;
    private final String packageName;

    public ClassName(String word) {
        this.fullName = word;

        this.simpleName = parseSimpleName(fullName);
        this.packageName = fullName.replace(simpleName, "");
    }

    public String getSimpleName() {
        return simpleName;
    }

    public String getPackageName() {
        return packageName;
    }

    @Override
    public int compareTo(ClassName other) {
        return simpleName.compareTo(other.simpleName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassName className = (ClassName) o;
        return Objects.equals(fullName, className.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }

    @Override
    public String toString() {
        return fullName;
    }

    private String parseSimpleName(String fullName) {
        String[] partsOfName = fullName.split(PunctuationSymbols.PACKAGE_DELIMITER_REGEX);
        return partsOfName[partsOfName.length - 1];
    }
}
