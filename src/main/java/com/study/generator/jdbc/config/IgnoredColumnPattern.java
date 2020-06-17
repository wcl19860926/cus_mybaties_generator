package com.study.generator.jdbc.config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.study.generator.util.StringUtility.stringHasValue;
import static com.study.generator.util.message.Messages.getString;

public class IgnoredColumnPattern {

    private String patternRegex;
    private Pattern pattern;
    private List<IgnoredColumnException> exceptions = new ArrayList<>();

    public IgnoredColumnPattern(String patternRegex) {
        this.patternRegex = patternRegex;
        pattern = Pattern.compile(patternRegex);
    }

    public void addException(IgnoredColumnException exception) {
        exceptions.add(exception);
    }

    public boolean matches(String columnName) {
        boolean matches = pattern.matcher(columnName).matches();

        if (matches) {
            for (IgnoredColumnException exception : exceptions) {
                if (exception.matches(columnName)) {
                    matches = false;
                    break;
                }
            }
        }

        return matches;
    }

    public void validate(List<String> errors, String tableName) {
        if (!stringHasValue(patternRegex)) {
            errors.add(getString("ValidationError.27", //$NON-NLS-1$
                    tableName));
        }
    }
}
