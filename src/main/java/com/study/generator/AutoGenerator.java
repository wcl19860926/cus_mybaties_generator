package com.study.generator;

import com.study.generator.config.Configuration;
import com.study.generator.config.xml.ConfigurationParser;
import com.study.generator.gen.CusAutoGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AutoGenerator {


    protected static final Logger logger = LoggerFactory.getLogger(AutoGenerator.class);



    public  void  generator(){

        List<String> warnings = new ArrayList<>();
        Set<String> contexts = new HashSet<>();
        Set<String> qualifiedTables = new HashSet<>();
        try {
            File configurationFile = loadConfigFile();
            ConfigurationParser cp = new ConfigurationParser(null, warnings);
            Configuration config = cp.parseConfiguration(configurationFile);
            CusAutoGenerator myBatisGenerator = new CusAutoGenerator(config, warnings);
            myBatisGenerator.generate(contexts, qualifiedTables);
        } catch (Exception e) {
            logger.error("自动生成代码失败！" ,e);
        }

    }



    public static void main(String[] args) {
        AutoGenerator  autoGenerator  = new  AutoGenerator();
        autoGenerator.generator();
    }


    private static File loadConfigFile() throws Exception {
        try {
            ClassPathResource pathResource = new ClassPathResource("generatorConfig.xml");
            return pathResource.getFile();
        }catch (Exception e){
            throw new  Exception("在类路径下，未找到配置文件generatorConfig.xml" ,e);
        }

    }
}
