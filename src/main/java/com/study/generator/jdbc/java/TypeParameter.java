package com.study.generator.jdbc.java;

import java.util.ArrayList;
import java.util.List;

public class TypeParameter {

    private String name;

    private List<FullyQualifiedJavaType> extendsTypes = new ArrayList<>();

    public TypeParameter(String name) {
        super();
        this.name = name;
    }

    public TypeParameter(String name, List<FullyQualifiedJavaType> extendsTypes) {
        super();
        this.name = name;
        this.extendsTypes.addAll(extendsTypes);
    }

    public String getName() {
        return name;
    }

    public List<FullyQualifiedJavaType> getExtendsTypes() {
        return extendsTypes;
    }


}
