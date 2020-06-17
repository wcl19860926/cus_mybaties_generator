package com.study.generator.gen;

import com.study.generator.jdbc.FullyQualifiedTable;
import com.study.generator.jdbc.introspect.IntrospectedColumn;
import com.study.generator.jdbc.introspect.IntrospectedTable;
import com.study.generator.jdbc.java.FullyQualifiedJavaType;
import com.study.generator.model.ColumnInfo;
import com.study.generator.model.TableInfo;
import com.study.generator.util.CollectionUtils;
import com.study.generator.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.study.generator.util.message.Messages.getString;

public class FileGeneratorHelper {


    public static TableInfo transferToTableInfo(IntrospectedTable introspectedTable) {
        TableInfo   tableInfo  =  new TableInfo();
        FullyQualifiedTable fullyQualifiedTable   = introspectedTable.getFullyQualifiedTable();
        tableInfo.setTableName(fullyQualifiedTable.getIntrospectedTableName());
        tableInfo.setDomainName(fullyQualifiedTable.getDomainObjectName());
        tableInfo.setKeyColumns(getKeyColumnInfo(introspectedTable.getPrimaryKeyColumns() ,tableInfo));
        tableInfo.setColumns( getColumnInfo(introspectedTable.getBaseColumns() , tableInfo));
        tableInfo.setBaseResultMapId(introspectedTable.getBaseResultMapId());
        tableInfo.setDomainType(introspectedTable.getBaseRecordType());
        tableInfo.setXmlFileName(introspectedTable.getMyBatis3XmlMapperFileName());
        tableInfo.setXmlFileNameSpace(introspectedTable.getMyBatis3FallbackSqlMapNamespace());
        tableInfo.setJavaMapperType(introspectedTable.getMyBatis3JavaMapperType());
        tableInfo.setInsertStatementId(introspectedTable.getInsertStatementId());
        tableInfo.setInsertSelectiveStatementId(introspectedTable.getInsertSelectiveStatementId());
        tableInfo.setDeleteByPrimaryKeyStatementId(introspectedTable.getDeleteByPrimaryKeyStatementId());
        tableInfo.setUpdateByPrimaryKeySelectiveStatementId(introspectedTable.getUpdateByPrimaryKeySelectiveStatementId());
        tableInfo.setUpdateByPrimaryKeyStatementId(introspectedTable.getUpdateByPrimaryKeyStatementId());
        tableInfo.setSelectByPrimaryKeyStatementId(introspectedTable.getSelectByPrimaryKeyStatementId());
        tableInfo.setSelectListStatementId(introspectedTable.getSelectListStatementId());
        tableInfo.setJavaEntityPackage(introspectedTable.getEntityPackage());
        tableInfo.setJavaEntityTargetProject(introspectedTable.getEntityProject());
        tableInfo.setXmlTargetPackage(introspectedTable.getXmlPackage());
        tableInfo.setXmlTargetProject(introspectedTable.getXmlProject());
        tableInfo.setJavaMapperPackage(introspectedTable.getJavaPackage());
        tableInfo.setJavaMapperTargetProject(introspectedTable.getJavaProject());
        tableInfo.setDomainSuperClass( introspectedTable.getSuperEntity());
        tableInfo.setSuperMapper(introspectedTable.getSuperMapper());
        tableInfo.setSuperService(introspectedTable.getBaseService());
        tableInfo.setSupperServiceImpl(introspectedTable.getBaseServiceImpl());
        tableInfo.setTableComment(introspectedTable.getRemarks());
        buildCommaDelimitedFields(tableInfo);

        tableInfo.getAllColumns().addAll(tableInfo.getKeyColumns());
        tableInfo.getAllColumns().addAll(tableInfo.getColumns());
        Collections.sort(tableInfo.getAllColumns());
        return   tableInfo;
    }

    private static List<ColumnInfo>  getColumnInfo(  List<IntrospectedColumn> baseColumns ,TableInfo   tableInfo) {
        ColumnInfo columnInfo;
        List<ColumnInfo>  columnInfos  = new ArrayList<>();
        int i=10;
        for(IntrospectedColumn   introspectedColumn  :baseColumns  ){
            columnInfo  = new  ColumnInfo();
            setColumnValue(columnInfo, introspectedColumn);
            columnInfos.add(columnInfo);
            columnInfo.setOrder(i++);
        }
        return  columnInfos;
    }

    private static void setColumnValue(ColumnInfo columnInfo, IntrospectedColumn introspectedColumn) {
        FullyQualifiedJavaType qualifiedJavaType;
        columnInfo.setColumnName(introspectedColumn.getActualColumnName());
        columnInfo.setPropertyName(introspectedColumn.getJavaProperty());
        columnInfo.setComment(introspectedColumn.getRemarks());
        columnInfo.setJdbcType(introspectedColumn.getJdbcTypeName());
        qualifiedJavaType = introspectedColumn.getFullyQualifiedJavaType();
        columnInfo.setJavaTypeFullName(qualifiedJavaType.getFullyQualifiedName());
        columnInfo.setJavaTypePackageName(qualifiedJavaType.getPackageName());
        columnInfo.setJavaTypeShortName(qualifiedJavaType.getShortName());
    }



    private  static void   buildCommaDelimitedFields(TableInfo tableInfo){
        StringBuilder  buf  = new StringBuilder("");
        List<ColumnInfo>  keyColumnInfos =tableInfo.getKeyColumns();
        List<ColumnInfo>  columnInfos =tableInfo.getColumns();

        for(ColumnInfo  columnInfo  : keyColumnInfos){
            buf.append(columnInfo.getColumnName());
            buf.append(", ");
        }
        int i = 1;
        for(ColumnInfo  columnInfo  : columnInfos){
            i++;
            buf.append(columnInfo.getColumnName());
            buf.append(", ");
            if(i%11==0){
                buf.append("\\r\\n ");
            }
        }
        String fileds  = buf.toString();
        fileds  = fileds.substring(0 ,fileds.length()-2);
        tableInfo.setCommaDelimitedFields(fileds);
    }


    private static List<ColumnInfo>  getKeyColumnInfo(  List<IntrospectedColumn> baseColumns  ,TableInfo   tableInfo) {
        ColumnInfo columnInfo;
        List<ColumnInfo>  columnInfos  = new ArrayList<>();
        FullyQualifiedJavaType qualifiedJavaType  =null;
        int  i =0 ;
        for(IntrospectedColumn   introspectedColumn  :baseColumns  ){
            qualifiedJavaType =introspectedColumn.getFullyQualifiedJavaType();
            columnInfo  = new  ColumnInfo();
            columnInfo.setOrder(i++);
            setColumnValue(columnInfo, introspectedColumn);
            if("id".equalsIgnoreCase(introspectedColumn.getJavaProperty())){
                tableInfo.setPrimaryKeyType(qualifiedJavaType.getFullyQualifiedName());
                tableInfo.setPrimaryKeyTypeShortName(qualifiedJavaType.getShortName());
                columnInfo.setOrder(-99);
            }


            columnInfos.add(columnInfo);
        }
        if(CollectionUtils.isNotEmpty(baseColumns) &&  StringUtils.isEmpty(tableInfo.getPrimaryKeyType())){
            tableInfo.setPrimaryKeyType(baseColumns.get(0).getFullyQualifiedJavaType().getShortName());
        }
        return  columnInfos;
    }


    private void writeGeneratedJavaFile(List<String> warnings)
            throws InterruptedException, IOException {
        File targetFile;
        String source = null;
        try {
            File directory = null;
            targetFile = getUniqueFileName(directory, "");
            warnings.add(getString(
                    "Warning.2", targetFile.getAbsolutePath())); //$NON-NLS-1$


            writeFile(targetFile, source, "UTF-8");
        } catch (Exception e) {
            warnings.add(e.getMessage());
        }
    }



    private void writeFile(File file, String content, String fileEncoding) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, false);
        OutputStreamWriter osw;
        if (fileEncoding == null) {
            osw = new OutputStreamWriter(fos);
        } else {
            osw = new OutputStreamWriter(fos, fileEncoding);
        }

        try (BufferedWriter bw = new BufferedWriter(osw)) {
            bw.write(content);
        }
    }


    private File getUniqueFileName(File directory, String fileName) {
        File answer = null;

        // try up to 1000 times to generate a unique file name
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 1000; i++) {
            sb.setLength(0);
            sb.append(fileName);
            sb.append('.');
            sb.append(i);

            File testFile = new File(directory, sb.toString());
            if (!testFile.exists()) {
                answer = testFile;
                break;
            }
        }

        if (answer == null) {
            throw new RuntimeException(getString(
                    "RuntimeError.3", directory.getAbsolutePath())); //$NON-NLS-1$
        }

        return answer;
    }



}
