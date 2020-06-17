package com.study.generator.jdbc.java;

public enum JavaVisibility {
    PUBLIC("public "), //$NON-NLS-1$
    PRIVATE("private "), //$NON-NLS-1$
    PROTECTED("protected "), //$NON-NLS-1$
    DEFAULT(""); //$NON-NLS-1$

    private String value;

    JavaVisibility(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}