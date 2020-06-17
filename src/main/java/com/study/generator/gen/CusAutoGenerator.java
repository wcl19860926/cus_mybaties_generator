package com.study.generator.gen;


import com.study.generator.config.Configuration;
import com.study.generator.config.xml.ConfigurationParser;
import com.study.generator.context.Context;
import com.study.generator.exception.InvalidConfigurationException;
import com.study.generator.jdbc.ObjectFactory;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.study.generator.util.ClassloaderUtility.getCustomClassloader;
import static com.study.generator.util.message.Messages.getString;


/**
 * This class is the main interface to MyBatis generator. A typical execution of the tool involves these steps:
 *
 * <ol>
 * <li>Create a Configuration object. The Configuration can be the result of a parsing the XML configuration file, or it
 * can be created solely in Java.</li>
 * <li>Create a MyBatisGenerator object</li>
 * <li>Call one of the generate() methods</li>
 * </ol>
 *
 * @author Jeff Butler
 * @see ConfigurationParser
 */
public class CusAutoGenerator {

    private Configuration configuration;


    private List<String> warnings;

    private Set<String> projects = new HashSet<>();


    public CusAutoGenerator(Configuration configuration,
                            List<String> warnings) throws InvalidConfigurationException {
        super();
        if (configuration == null) {
            throw new IllegalArgumentException(getString("RuntimeError.2")); //$NON-NLS-1$
        } else {
            this.configuration = configuration;
        }
        if (warnings == null) {
            this.warnings = new ArrayList<>();
        } else {
            this.warnings = warnings;
        }

        this.configuration.validate();
    }


    public void generate() throws SQLException,
            IOException, InterruptedException {
        generate(null, null, true);
    }


    public void generate(Set<String> contextIds)
            throws SQLException, IOException, InterruptedException {
        generate(contextIds, null, true);
    }


    public void generate(Set<String> contextIds,
                         Set<String> fullyQualifiedTableNames) throws SQLException,
            IOException, InterruptedException {
        generate(contextIds, fullyQualifiedTableNames, true);
    }


    public void generate(Set<String> contextIds,
                         Set<String> fullyQualifiedTableNames, boolean writeFiles) throws SQLException,
            IOException, InterruptedException {


        ObjectFactory.reset();

        List<Context> contextsToRun;
        if (contextIds == null || contextIds.isEmpty()) {
            contextsToRun = configuration.getContexts();
        } else {
            contextsToRun = new ArrayList<>();
            for (Context context : configuration.getContexts()) {
                if (contextIds.contains(context.getId())) {
                    contextsToRun.add(context);
                }
            }
        }
        if (!configuration.getClassPathEntries().isEmpty()) {
            ClassLoader classLoader = getCustomClassloader(configuration.getClassPathEntries());
            ObjectFactory.addExternalClassLoader(classLoader);
        }

        for (Context context : contextsToRun) {
            context.introspectTables(warnings,
                    fullyQualifiedTableNames);
        }

        for (Context context : contextsToRun) {
            context.generateFiles(warnings);
        }


    }


}

