package com.leepsmart.code.common.generator.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * mybatis-plus generator 代码生成器配置
 *
 * @author leepsmart
 */
public class Generator {
    /**
     * 模块名
     */
    private final static String MODEL_NAME = "test";
    /**
     * 是否生成controller类
     */
    private final static boolean GENERATE_CONTROLLER = true;
    /**
     * 表名 (可以同时操作多个, 建议同时操作同一模块下的表)
     */
    private final static String[] TABLE_NAME = new String[]{"a_base_table"};
    /**
     * 主键生成策略
     */
    private final static IdType ID_TYPE = IdType.AUTO;
    /**
     * 是否开启乐观锁, 乐观锁字段名为 version
     */
    private final static boolean OPEN_VERSION = true;

    private static AutoGenerator mpg = new AutoGenerator();

    public static void main(String[] args) throws IOException {

        // generator 相关配置
        generatorConfig();

        // 数据源相关配置
        dataBaseConfig();

        // 目录模块配置信息
        packageConfig();

        // 数据表相关信息配置
        strategyConfig();

        // 自定义模板
        templateConfig();

        // 设置模板引擎为 freeMarket
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }

    // 数据表相关配置
    public static void strategyConfig() {
        StrategyConfig strategy = new StrategyConfig();

        // 指定表名（可以同时操作多个表，使用 , 隔开）
        strategy.setInclude(TABLE_NAME)
                // 配置数据表与实体类名之间映射的策略
                .setNaming(NamingStrategy.underline_to_camel)
                // 配置数据表的字段与实体类的属性名之间映射的策略
                .setColumnNaming(NamingStrategy.underline_to_camel)
                // 配置 lombok 模式
                .setEntityLombokModel(false)
                // 配置 rest 风格的控制器（@RestController）
                .setRestControllerStyle(true)
                // 配置驼峰转连字符
                .setControllerMappingHyphenStyle(false)
                // 开启 builder 模式
                .setChainModel(true)
                .setEntityColumnConstant(true);

        if (OPEN_VERSION) {
            // 设置乐观锁版本号字段
            strategy.setVersionFieldName("version");
        }

        // 配置表前缀，生成实体时去除表前缀
        mpg.setStrategy(strategy);
    }

    /**
     * 目录模块配置信息
     */
    public static void packageConfig() {
        PackageConfig packageConfig = new PackageConfig();

        String packageName = Generator.class.getCanonicalName();
        if (null != packageName) {
            String[] split = packageName.split("\\.");
            if (split.length > 3) {
                packageName = split[0] + "." + split[1] + "." + split[2];
            }
        }
        // 配置父包名
        packageConfig.setParent(packageName)
                // 配置模块名
                .setModuleName(MODEL_NAME)
                // 配置 entity 包名
                .setEntity("pojo")
                // 配置 mapper 包名
                .setMapper("mapper")
                // 配置 service 包名
                .setService("service")
                // 配置 controller 包名
                .setController("controller");

        mpg.setPackageInfo(packageConfig);
    }

    /**
     * 数据源相关配置
     */
    public static void dataBaseConfig() throws IOException {
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();

        // 配置数据库 url 地址
        File path = new File("");
        String filePath = path.getCanonicalPath();
        InputStream inputStream = new FileInputStream(filePath + "/target/classes/application.yaml");
        Yaml yaml = new Yaml();
        String dump = yaml.dump(yaml.load(inputStream));
        Map map = yaml.load(dump);
        Map spring = (Map) map.get("spring");
        Map datasource = (Map) spring.get("datasource");
        Map druid = (Map) datasource.get("druid");

        // 数据库链接
        dataSourceConfig.setUrl((String) druid.get("url"))

                // .setSchemaName("testMyBatisPlus"); // 可以直接在 url 中指定数据库名
                // 配置数据库驱动
                .setDriverName("com.mysql.jdbc.Driver")

                // 配置数据库连接用户名
                .setUsername((String) druid.get("username"))

                // 配置数据库连接密码
                .setPassword(druid.get("password").toString());
        mpg.setDataSource(dataSourceConfig);
    }

    /**
     * 配置自定义模板
     */
    public static void templateConfig() {
        // 设置自定义模板路径
        TemplateConfig templateConfig = new TemplateConfig()
                .setEntity("templates/generator/entity.java")
                .setService("templates/generator/service.java")
                .setServiceImpl("templates/generator/serviceImpl.java")
                .setMapper("templates/generator/mapper.java")
                .setXml("templates/generator/mapper.xml");

        // 根据布尔值决定是否生成controller模板
        if (GENERATE_CONTROLLER) {
            templateConfig.setController("templates/generator/controller.java");
        } else {
            templateConfig.setController(null);
        }

        // 自定义属性配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            //在.ftl(或者是.vm)模板中，通过${cfg.属性名}获取属性
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                // 是否生成实体类字段常量字符
                map.put("finalFields", true);
                // 父包名，默认 com.leepsmart.baseservice
                String packageName = Generator.class.getCanonicalName();
                if (null != packageName) {
                    String[] split = packageName.split("\\.");
                    if (split.length > 3) {
                        packageName = split[0] + "." + split[1] + "." + split[2];
                    }
                }
                map.put("packageName", packageName);

                this.setMap(map);
            }
        };

        // 自定义覆盖文件
        injectionConfig.setFileCreate((configBuilder, fileType, filePath) -> {
            //如果是Entity则直接返回true表示写文件
            if (fileType == FileType.ENTITY) {
                return true;
            }
            //否则先判断文件是否存在
            File file = new File(filePath);
            boolean exist = file.exists();
            if (!exist) {
                file.getParentFile().mkdirs();
            }
            //文件不存在或者全局配置的fileOverride为true才写文件
            return !exist;
        });

        mpg.setTemplate(templateConfig);

        mpg.setCfg(injectionConfig);
    }

    /**
     * Generator 配置信息
     */
    public static void generatorConfig() throws IOException {
        GlobalConfig globalConfig = new GlobalConfig();

        // 配置项目路径
        File file = new File("");
        String filePath = file.getCanonicalPath();
        globalConfig.setOutputDir(filePath + "/src/main/java")
                // 配置开发者信息（可选）
                .setAuthor("leepsmart generator")

                // 配置是否打开目录，false 为不打开（可选）
                .setOpen(false)

                // 实体属性 Swagger2 注解，添加 Swagger 依赖，开启 Swagger2 模式（可选）
                .setSwagger2(true)

                // 配置主键生成策略，此处为 ASSIGN_ID（可选）
                .setIdType(ID_TYPE)

                // 配置日期类型，此处为 ONLY_DATE（可选）
                .setDateType(DateType.ONLY_DATE)

                // 覆盖默认生成的service接口名称, 默认生成的 service 会有 I 前缀
                .setServiceName("%sService")

                // 生成公共查询字段列
                .setBaseColumnList(true)

                .setBaseResultMap(true)

                // 重新生成文件时是否覆盖，false 表示不覆盖（可选）
                .setFileOverride(true);

        mpg.setGlobalConfig(globalConfig);
    }
}
