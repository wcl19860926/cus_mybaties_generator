package com.study.generator.jdbc.config;

import java.util.List;

import static com.study.generator.util.StringUtility.stringHasValue;
import static com.study.generator.util.message.Messages.getString;

public class IgnoredColumnException extends IgnoredColumn {

    public IgnoredColumnException(String columnName) {
        super(columnName);
    }

    @Override
    public void validate(List<String> errors, String tableName) {
        if (!stringHasValue(columnName)) {
            errors.add(getString("ValidationError.26", //$NON-NLS-1$
                    tableName));
        }
    }
}
