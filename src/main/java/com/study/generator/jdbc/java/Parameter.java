package com.study.generator.jdbc.java;

import java.util.ArrayList;
import java.util.List;

public class Parameter {
    private String name;
    private FullyQualifiedJavaType type;
    private boolean isVarargs;

    private List<String> annotations = new ArrayList<>();

    public Parameter(FullyQualifiedJavaType type, String name, boolean isVarargs) {
        this.name = name;
        this.type = type;
        this.isVarargs = isVarargs;
    }

    public Parameter(FullyQualifiedJavaType type, String name) {
        this(type, name, false);
    }

    public Parameter(FullyQualifiedJavaType type, String name, String annotation) {
        this(type, name, false);
        addAnnotation(annotation);
    }

    public Parameter(FullyQualifiedJavaType type, String name, String annotation, boolean isVarargs) {
        this(type, name, isVarargs);
        addAnnotation(annotation);
    }

    public String getName() {
        return name;
    }

    public FullyQualifiedJavaType getType() {
        return type;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(String annotation) {
        annotations.add(annotation);
    }


    public boolean isVarargs() {
        return isVarargs;
    }
}