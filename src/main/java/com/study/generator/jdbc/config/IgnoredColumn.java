package com.study.generator.jdbc.config;

import java.util.List;

import static com.study.generator.util.StringUtility.stringContainsSpace;
import static com.study.generator.util.StringUtility.stringHasValue;
import static com.study.generator.util.message.Messages.getString;

public class IgnoredColumn {

    protected String columnName;

    private boolean isColumnNameDelimited;

    public IgnoredColumn(String columnName) {
        super();
        this.columnName = columnName;
        isColumnNameDelimited = stringContainsSpace(columnName);
    }

    public String getColumnName() {
        return columnName;
    }

    public boolean isColumnNameDelimited() {
        return isColumnNameDelimited;
    }

    public void setColumnNameDelimited(boolean isColumnNameDelimited) {
        this.isColumnNameDelimited = isColumnNameDelimited;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IgnoredColumn)) {
            return false;
        }

        return columnName.equals(((IgnoredColumn) obj).getColumnName());
    }

    @Override
    public int hashCode() {
        return columnName.hashCode();
    }

    public void validate(List<String> errors, String tableName) {
        if (!stringHasValue(columnName)) {
            errors.add(getString("ValidationError.21", //$NON-NLS-1$
                    tableName));
        }
    }

    public boolean matches(String columnName) {
        if (isColumnNameDelimited) {
            return this.columnName.equals(columnName);
        } else {
            return this.columnName.equalsIgnoreCase(columnName);
        }
    }
}
