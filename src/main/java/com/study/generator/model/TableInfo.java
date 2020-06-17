package com.study.generator.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableInfo {

    private String tableName;

    private  String  tableComment;


    private List<String>  importPackages = new ArrayList<>();

    private List<ColumnInfo> keyColumns;

    private  String  primaryKeyType;


    private List<ColumnInfo> columns;

    private String domainName;

    private String domainType;

    private String domainSuperClass;


    private String baseResultMapId;

    private  String  xmlFileName;

    private  String  xmlFileNameSpace;

    private  String  javaMapperType;



    private  String  xmlTargetPackage;

    private  String  xmlTargetProject;


    private String  javaEntityPackage;

    private String  javaEntityTargetProject;


    private  String  javaMapperPackage;

    private  String javaMapperTargetProject;



    private String insertStatementId;

    private String insertSelectiveStatementId;


    private String deleteByPrimaryKeyStatementId;


    private String updateByPrimaryKeyStatementId;


    private String updateByPrimaryKeySelectiveStatementId;


    private String selectByPrimaryKeyStatementId;


    private String selectListStatementId;


}
