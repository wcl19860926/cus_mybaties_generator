package com.study.generator.jdbc.config;

import java.util.List;

import static com.study.generator.util.StringUtility.stringHasValue;
import static com.study.generator.util.message.Messages.getString;

public class PluginConfiguration extends TypedPropertyHolder {
    public PluginConfiguration() {
        super();
    }

    public void validate(List<String> errors, String contextId) {
        if (!stringHasValue(getConfigurationType())) {
            errors.add(getString("ValidationError.17", //$NON-NLS-1$
                    contextId));
        }
    }
}
