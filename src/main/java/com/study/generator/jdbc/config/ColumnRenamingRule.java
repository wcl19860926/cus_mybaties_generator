package com.study.generator.jdbc.config;

import java.util.List;

import static com.study.generator.util.StringUtility.stringHasValue;
import static com.study.generator.util.message.Messages.getString;

public class ColumnRenamingRule {
    private String searchString;
    private String replaceString;

    public String getReplaceString() {
        return replaceString;
    }

    public void setReplaceString(String replaceString) {
        this.replaceString = replaceString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public void validate(List<String> errors, String tableName) {
        if (!stringHasValue(searchString)) {
            errors.add(getString("ValidationError.14", tableName)); //$NON-NLS-1$
        }
    }
}
