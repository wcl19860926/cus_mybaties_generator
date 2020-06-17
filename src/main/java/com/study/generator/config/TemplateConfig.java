package com.study.generator.config;


import com.study.generator.constants.Constants;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TemplateConfig {


    // @Getter(AccessLevel.NONE)
    public static String entity = Constants.TEMPLATE_ENTITY_JAVA;


    public static String service = Constants.TEMPLATE_SERVICE;

    public static String serviceImpl = Constants.TEMPLATE_SERVICE_IMPL;

    public static String mapper = Constants.TEMPLATE_MAPPER;

    public static String xml = Constants.TEMPLATE_XML;

    public static String controller = Constants.TEMPLATE_CONTROLLER;

}
