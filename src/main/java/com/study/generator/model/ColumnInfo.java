package com.study.generator.model;

import lombok.Data;

@Data
public class ColumnInfo {


    private  String JavaTypeFullName;

    private String javaTypeShortName;

    private String javaTypePackageName;

    private String jdbcType;

    private String propertyName;

    private String columnName;

    private String comment;

}
