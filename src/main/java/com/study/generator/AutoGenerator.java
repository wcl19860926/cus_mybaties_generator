package com.study.generator;

import com.study.generator.config.Configuration;
import com.study.generator.config.xml.ConfigurationParser;
import com.study.generator.exception.InvalidConfigurationException;
import com.study.generator.exception.XMLParserException;
import com.study.generator.gen.CusAutoGenerator;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class AutoGenerator {

    public static void main(String[] args) {
        List<String> warnings = new ArrayList<>();
        Set<String> contexts = new HashSet<>();
        Set<String> fullyqualifiedTables = new HashSet<>();
        try {
            File configurationFile = loadConfigFile();
            ConfigurationParser cp = new ConfigurationParser(null, warnings);
            Configuration config = cp.parseConfiguration(configurationFile);
            CusAutoGenerator myBatisGenerator = new CusAutoGenerator(config, warnings);
            myBatisGenerator.generate(contexts, fullyqualifiedTables);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        }
    }


    private static File loadConfigFile() throws IOException {

        ClassPathResource pathResource = new ClassPathResource("generatorConfig.xml");
        return pathResource.getFile();
    }
}
