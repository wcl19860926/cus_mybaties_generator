package com.study.generator.context;

import com.study.generator.config.CommentGeneratorConfiguration;
import com.study.generator.gen.FileGeneratorHelper;
import com.study.generator.jdbc.ObjectFactory;
import com.study.generator.jdbc.config.*;
import com.study.generator.jdbc.connection.ConnectionFactory;
import com.study.generator.jdbc.connection.JDBCConnectionFactory;
import com.study.generator.jdbc.db.DatabaseIntrospector;
import com.study.generator.jdbc.introspect.IntrospectedTable;
import com.study.generator.jdbc.java.JavaTypeResolver;
import com.study.generator.model.TableInfo;
import com.study.generator.velocity.AbstractTemplateEngine;
import com.study.generator.velocity.VelocityTemplateEngine;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.study.generator.util.StringUtility.*;
import static com.study.generator.util.message.Messages.getString;

public class Context extends PropertyHolder {

    private String id;

    private JDBCConnectionConfiguration jdbcConnectionConfiguration;

    private ConnectionFactoryConfiguration connectionFactoryConfiguration;

    private CommentGeneratorConfiguration commentGeneratorConfiguration;

    private SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration;

    private JavaTypeResolverConfiguration javaTypeResolverConfiguration;

    private JavaModelGeneratorConfiguration javaModelGeneratorConfiguration;

    private JavaClientGeneratorConfiguration javaClientGeneratorConfiguration;

    private ArrayList<TableConfiguration> tableConfigurations;


    private String beginningDelimiter = "\""; //$NON-NLS-1$

    private String endingDelimiter = "\""; //$NON-NLS-1$


    private List<PluginConfiguration> pluginConfigurations;

    private String targetRuntime;

    private String introspectedColumnImpl;

    private Boolean autoDelimitKeywords;

    private AbstractTemplateEngine abstractTemplateEngine;


    private boolean isJava8Targeted = true;

    public Context() {
        super();
        tableConfigurations = new ArrayList<>();
        pluginConfigurations = new ArrayList<>();
        abstractTemplateEngine = new VelocityTemplateEngine();
    }

    public void addTableConfiguration(TableConfiguration tc) {
        tableConfigurations.add(tc);
    }

    public JDBCConnectionConfiguration getJdbcConnectionConfiguration() {
        return jdbcConnectionConfiguration;
    }

    public JavaClientGeneratorConfiguration getJavaClientGeneratorConfiguration() {
        return javaClientGeneratorConfiguration;
    }

    public JavaModelGeneratorConfiguration getJavaModelGeneratorConfiguration() {
        return javaModelGeneratorConfiguration;
    }

    public JavaTypeResolverConfiguration getJavaTypeResolverConfiguration() {
        return javaTypeResolverConfiguration;
    }

    public SqlMapGeneratorConfiguration getSqlMapGeneratorConfiguration() {
        return sqlMapGeneratorConfiguration;
    }

    public void addPluginConfiguration(
            PluginConfiguration pluginConfiguration) {
        pluginConfigurations.add(pluginConfiguration);
    }

    /**
     * This method does a simple validate, it makes sure that all required fields have been filled in. It does not do
     * any more complex operations such as validating that database tables exist or validating that named columns exist
     *
     * @param errors the errors
     */
    public void validate(List<String> errors) {
        if (!stringHasValue(id)) {
            errors.add(getString("ValidationError.16")); //$NON-NLS-1$
        }

        if (jdbcConnectionConfiguration == null && connectionFactoryConfiguration == null) {
            // must specify one
            errors.add(getString("ValidationError.10", id)); //$NON-NLS-1$
        } else if (jdbcConnectionConfiguration != null && connectionFactoryConfiguration != null) {
            // must not specify both
            errors.add(getString("ValidationError.10", id)); //$NON-NLS-1$
        } else if (jdbcConnectionConfiguration != null) {
            jdbcConnectionConfiguration.validate(errors);
        } else {
            connectionFactoryConfiguration.validate(errors);
        }

        if (javaModelGeneratorConfiguration == null) {
            errors.add(getString("ValidationError.8", id)); //$NON-NLS-1$
        } else {
            javaModelGeneratorConfiguration.validate(errors, id);
        }

        if (javaClientGeneratorConfiguration != null) {
            javaClientGeneratorConfiguration.validate(errors, id);
        }

        IntrospectedTable it = null;
        try {
            it = ObjectFactory.createIntrospectedTableForValidation(this);
        } catch (Exception e) {
            errors.add(getString("ValidationError.25", id)); //$NON-NLS-1$
        }

        if (it != null) {
            sqlMapGeneratorConfiguration.validate(errors, id);
        }

        if (tableConfigurations.isEmpty()) {
            errors.add(getString("ValidationError.3", id)); //$NON-NLS-1$
        } else {
            for (int i = 0; i < tableConfigurations.size(); i++) {
                TableConfiguration tc = tableConfigurations.get(i);

                tc.validate(errors, i);
            }
        }

        for (PluginConfiguration pluginConfiguration : pluginConfigurations) {
            pluginConfiguration.validate(errors, id);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJavaClientGeneratorConfiguration(
            JavaClientGeneratorConfiguration javaClientGeneratorConfiguration) {
        this.javaClientGeneratorConfiguration = javaClientGeneratorConfiguration;
    }

    public void setJavaModelGeneratorConfiguration(
            JavaModelGeneratorConfiguration javaModelGeneratorConfiguration) {
        this.javaModelGeneratorConfiguration = javaModelGeneratorConfiguration;
    }

    public void setJavaTypeResolverConfiguration(
            JavaTypeResolverConfiguration javaTypeResolverConfiguration) {
        this.javaTypeResolverConfiguration = javaTypeResolverConfiguration;
    }

    public void setJdbcConnectionConfiguration(
            JDBCConnectionConfiguration jdbcConnectionConfiguration) {
        this.jdbcConnectionConfiguration = jdbcConnectionConfiguration;
    }

    public void setSqlMapGeneratorConfiguration(
            SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration) {
        this.sqlMapGeneratorConfiguration = sqlMapGeneratorConfiguration;
    }

    public List<TableConfiguration> getTableConfigurations() {
        return tableConfigurations;
    }

    public String getBeginningDelimiter() {
        return beginningDelimiter;
    }

    public String getEndingDelimiter() {
        return endingDelimiter;
    }

    @Override
    public void addProperty(String name, String value) {
        super.addProperty(name, value);

        if (PropertyRegistry.CONTEXT_BEGINNING_DELIMITER.equals(name)) {
            beginningDelimiter = value;
        } else if (PropertyRegistry.CONTEXT_ENDING_DELIMITER.equals(name)) {
            endingDelimiter = value;
        } else if (PropertyRegistry.CONTEXT_AUTO_DELIMIT_KEYWORDS.equals(name)
                && stringHasValue(value)) {
            autoDelimitKeywords = isTrue(value);
        } else if (PropertyRegistry.CONTEXT_TARGET_JAVA8.equals(name)
                && stringHasValue(value)) {
            isJava8Targeted = isTrue(value);
        }
    }


    public void setTargetRuntime(String targetRuntime) {
        this.targetRuntime = targetRuntime;
    }

    public String getIntrospectedColumnImpl() {
        return introspectedColumnImpl;
    }

    public void setIntrospectedColumnImpl(String introspectedColumnImpl) {
        this.introspectedColumnImpl = introspectedColumnImpl;
    }

    private List<IntrospectedTable> introspectedTables;


    public void generateFiles(
            List<String> warnings)
            throws InterruptedException {
        if (introspectedTables != null) {
            List<TableInfo> tableInfoList = new ArrayList<>();
            for (IntrospectedTable introspectedTable : introspectedTables) {
                introspectedTable.initialize();
                tableInfoList.add(FileGeneratorHelper.transferToTableInfo(introspectedTable));
            }
            abstractTemplateEngine.batchOutput(tableInfoList);
        }
    }


    public boolean autoDelimitKeywords() {
        return autoDelimitKeywords != null
                && autoDelimitKeywords.booleanValue();
    }

    public ConnectionFactoryConfiguration getConnectionFactoryConfiguration() {
        return connectionFactoryConfiguration;
    }

    public void setConnectionFactoryConfiguration(ConnectionFactoryConfiguration connectionFactoryConfiguration) {
        this.connectionFactoryConfiguration = connectionFactoryConfiguration;
    }


    public void introspectTables(
            List<String> warnings, Set<String> fullyQualifiedTableNames)
            throws SQLException, InterruptedException {

        introspectedTables = new ArrayList<>();
        JavaTypeResolver javaTypeResolver = ObjectFactory
                .createJavaTypeResolver(this, warnings);

        Connection connection = null;

        try {
            connection = getConnection();

            DatabaseIntrospector databaseIntrospector = new DatabaseIntrospector(
                    this, connection.getMetaData(), javaTypeResolver, warnings);

            for (TableConfiguration tc : tableConfigurations) {
                String tableName = composeFullyQualifiedTableName(tc.getCatalog(), tc
                        .getSchema(), tc.getTableName(), '.');

                if (fullyQualifiedTableNames != null
                        && !fullyQualifiedTableNames.isEmpty()
                        && !fullyQualifiedTableNames.contains(tableName)) {
                    continue;
                }

                if (!tc.areAnyStatementsEnabled()) {
                    warnings.add(getString("Warning.0", tableName)); //$NON-NLS-1$
                    continue;
                }


                List<IntrospectedTable> tables = databaseIntrospector
                        .introspectTables(tc);

                if (tables != null) {
                    introspectedTables.addAll(tables);
                }

            }
        } finally {
            closeConnection(connection);
        }
    }


    private Connection getConnection() throws SQLException {
        ConnectionFactory connectionFactory;
        if (jdbcConnectionConfiguration != null) {
            connectionFactory = new JDBCConnectionFactory(jdbcConnectionConfiguration);
        } else {
            connectionFactory = ObjectFactory.createConnectionFactory(this);
        }

        return connectionFactory.getConnection();
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }


    public void setCommentGeneratorConfiguration(CommentGeneratorConfiguration commentGeneratorConfiguration) {
        this.commentGeneratorConfiguration = commentGeneratorConfiguration;
    }
}
