package com.study.generator.constants;


import java.nio.charset.StandardCharsets;

public interface Constants {


    String SERVICE = "Service";
    String SERVICE_IMPL = "ServiceImpl";
    String MAPPER = "Mapper";
    String XML = "Xml";
    String CONTROLLER = "Controller";

    String JAVA_TMPDIR = "java.io.tmpdir";
    String UTF8 = StandardCharsets.UTF_8.name();
    String JAVA_SUFFIX = StringPool.DOT_JAVA;

    String XML_SUFFIX = ".xml";

    String TEMPLATE_ENTITY_JAVA = "/templates/entity.java";
    String TEMPLATE_MAPPER = "/templates/mapper.java";
    String TEMPLATE_XML = "/templates/mapper.xml";
    String TEMPLATE_SERVICE = "/templates/service.java";
    String TEMPLATE_SERVICE_IMPL = "/templates/serviceImpl.java";
    String TEMPLATE_CONTROLLER = "/templates/controller.java";

    String VM_LOAD_PATH_KEY = "file.resource.loader.class";
    String VM_LOAD_PATH_VALUE = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";


}
