package com.study.generator.velocity;


import com.study.generator.constants.Constants;
import com.study.generator.constants.StringPool;
import com.study.generator.util.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;

public class VelocityTemplateEngine extends AbstractTemplateEngine {

    private static final String DOT_VM = ".vm";
    private VelocityEngine velocityEngine;

    public VelocityTemplateEngine() {
        super();
        init();
    }


    public VelocityTemplateEngine init() {

        if (null == velocityEngine) {
            Properties p = new Properties();
            p.setProperty(Constants.VM_LOAD_PATH_KEY, Constants.VM_LOAD_PATH_VALUE);
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, StringPool.EMPTY);
            p.setProperty(Velocity.ENCODING_DEFAULT, Constants.UTF8);
            p.setProperty(Velocity.INPUT_ENCODING, Constants.UTF8);
            p.setProperty("file.resource.loader.unicode", StringPool.TRUE);
            velocityEngine = new VelocityEngine(p);
        }
        return this;
    }


    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        if (StringUtils.isEmpty(templatePath)) {
            return;
        }
        Template template = velocityEngine.getTemplate(templatePath, Constants.UTF8);
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             OutputStreamWriter ow = new OutputStreamWriter(fos, Constants.UTF8);
             BufferedWriter writer = new BufferedWriter(ow)) {
            template.merge(new VelocityContext(objectMap), writer);

        }
        logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }


    @Override
    public String templateFilePath(String filePath) {
        if (null == filePath || filePath.contains(DOT_VM)) {
            return filePath;
        }
        return filePath + DOT_VM;
    }

}