package com.study.generator.velocity;


import com.study.generator.config.TemplateConfig;
import com.study.generator.constants.StringPool;
import com.study.generator.model.TableInfo;
import com.study.generator.util.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.study.generator.constants.Constants.JAVA_SUFFIX;

public abstract class AbstractTemplateEngine {


    /**
     * The Unix separator character.
     */
    private static final char UNIX_SEPARATOR = '/';

    /**
     * The Windows separator character.
     */
    private static final char WINDOWS_SEPARATOR = '\\';

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);


    public AbstractTemplateEngine() {

    }

    /*
     * 输出 java xml 文件
     */
    public AbstractTemplateEngine batchOutput(List<TableInfo> tableInfoList) {
        try {
            for (TableInfo tableInfo : tableInfoList) {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                // Mp.java
                String entityName = tableInfo.getDomainName();

                String entityFile = String.format(getGeneratePath(tableInfo.getJavaEntityTargetProject(), tableInfo.getJavaEntityPackage()).getAbsolutePath() + File.separator + "%s" + JAVA_SUFFIX, entityName);
                writer(objectMap, templateFilePath(TemplateConfig.entity), entityFile);


                // MpMapper.java
                /*if (null != tableInfo.getMapperName() && null != pathInfo.get(Constants.MAPPER_PATH)) {
                    String mapperFile = String.format((pathInfo.get(Constants.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.MAPPER, mapperFile)) {
                        writer(objectMap, templateFilePath(template.getMapper()), mapperFile);
                    }
                }
                // MpMapper.xml
                if (null != tableInfo.getXmlName() && null != pathInfo.get(Constants.XML_PATH)) {
                    String xmlFile = String.format((pathInfo.get(Constants.XML_PATH) + File.separator + tableInfo.getXmlName() + Constants.XML_SUFFIX), entityName);
                    if (isCreate(FileType.XML, xmlFile)) {
                        writer(objectMap, templateFilePath(template.getXml()), xmlFile);
                    }
                }
                // IMpService.java
                if (null != tableInfo.getServiceName() && null != pathInfo.get(Constants.SERVICE_PATH)) {
                    String serviceFile = String.format((pathInfo.get(Constants.SERVICE_PATH) + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.SERVICE, serviceFile)) {
                        writer(objectMap, templateFilePath(template.getService()), serviceFile);
                    }
                }
                // MpServiceImpl.java
                if (null != tableInfo.getServiceImplName() && null != pathInfo.get(Constants.SERVICE_IMPL_PATH)) {
                    String implFile = String.format((pathInfo.get(Constants.SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.SERVICE_IMPL, implFile)) {
                        writer(objectMap, templateFilePath(template.getServiceImpl()), implFile);
                    }
                }
                // MpController.java
                if (null != tableInfo.getControllerName() && null != pathInfo.get(Constants.CONTROLLER_PATH)) {
                    String controllerFile = String.format((pathInfo.get(Constants.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.CONTROLLER, controllerFile)) {
                        writer(objectMap, templateFilePath(template.getController()), controllerFile);
                    }
                }*/
            }
        } catch (Exception e) {
            logger.error("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }









    private File getGeneratePath(String targetProject, String packagePath) {
        Path curPath = Paths.get("");
        System.out.println(curPath.toString());
        packagePath  = packagePathToFilePath(packagePath);
        Path targetPath = Paths.get(targetProject, packagePath);
        String path = curPath.resolve(targetPath).toAbsolutePath().toString();
        path = FilenameUtils.normalize(path);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 将模板转化成为文件
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     */
    public abstract void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception;





    private  String  packagePathToFilePath(String packagePath){
        if(packagePath==null || StringUtils.isEmpty(packagePath)){
            return  "";
        }
       return   StringUtils.join(File.separatorChar +"" , packagePath.split("\\."));

    }

    /**
     * 渲染对象 MAP 信息
     *
     * @param tableInfo 表信息对象
     * @return ignore
     */
    public Map<String, Object> getObjectMap(TableInfo tableInfo) {
        Map<String, Object> objectMap = new HashMap<>(30);
        objectMap.put("author", "wclTest");
        try {
            BeanInfo tableBeanInfo = Introspector.getBeanInfo(tableInfo.getClass());
            PropertyDescriptor[] propertyDescriptors = tableBeanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : propertyDescriptors) {
                Method readMethod = pd.getReadMethod();
                objectMap.put(pd.getName(), readMethod.invoke(tableInfo, readMethod.getParameters()));
            }
        } catch (Exception e) {
            logger.error("解析bean失败！", e);
        }
        return objectMap;
    }


    /**
     * 获取类名
     *
     * @param classPath ignore
     * @return ignore
     */
    private String getSuperClassName(String classPath) {
        if (StringUtils.isEmpty(classPath)) {
            return null;
        }
        return classPath.substring(classPath.lastIndexOf(StringPool.DOT) + 1);
    }


    /**
     * 模板真实文件路径
     *
     * @param filePath 文件路径
     * @return ignore
     */
    public abstract String templateFilePath(String filePath);


}

