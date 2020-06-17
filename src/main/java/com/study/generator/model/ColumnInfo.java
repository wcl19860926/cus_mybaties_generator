package com.study.generator.model;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class ColumnInfo  implements  Comparable<ColumnInfo> {


    private  String JavaTypeFullName;

    private String javaTypeShortName;

    private String javaTypePackageName;

    private String jdbcType;

    private String propertyName;

    private String columnName;

    private String comment;

    private Integer order;

    @Override
    public int compareTo(ColumnInfo o) {
        return this.order.compareTo(o.getOrder());
    }
}
