package com.study.generator.jdbc.config;

import java.util.List;

import static com.study.generator.util.StringUtility.stringHasValue;
import static com.study.generator.util.message.Messages.getString;

public class SqlMapGeneratorConfiguration extends PropertyHolder {
    private String targetPackage;

    private String targetProject;

    public SqlMapGeneratorConfiguration() {
        super();
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public void validate(List<String> errors, String contextId) {
        if (!stringHasValue(targetProject)) {
            errors.add(getString("ValidationError.1", contextId)); //$NON-NLS-1$
        }

        if (!stringHasValue(targetPackage)) {
            errors.add(getString("ValidationError.12", //$NON-NLS-1$
                    "SQLMapGenerator", contextId)); //$NON-NLS-1$
        }
    }
}
