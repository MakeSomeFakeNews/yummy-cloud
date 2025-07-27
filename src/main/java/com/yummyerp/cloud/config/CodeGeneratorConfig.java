package com.yummyerp.cloud.config;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * MyBatis-Plus代码生成器配置类
 */
@Component
public class CodeGeneratorConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 项目路径
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");

    /**
     * 生成代码
     *
     * @param tableNames 表名，多个用逗号分隔
     * @param moduleName 模块名
     * @param author     作者
     */
    public void generateCode(String tableNames, String moduleName, String author) {
        FastAutoGenerator.create(url, username, password)
                // 全局配置
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .dateType(DateType.TIME_PACK) // 时间策略
                            .commentDate("yyyy-MM-dd") // 注释日期
                            .outputDir(PROJECT_PATH + "/src/main/java"); // 指定输出目录
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("com.yummyerp.cloud.modules") // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .entity("entity") // 设置entity包名
                            .service("service") // 设置service包名
                            .serviceImpl("service.impl") // 设置serviceImpl包名
                            .mapper("mapper") // 设置mapper包名
                            .controller("controller") // 设置controller包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, PROJECT_PATH + "/src/main/resources/mapper/" + moduleName)); // 设置mapperXml生成路径
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tableNames.split(",")) // 设置需要生成的表名
                            .addTablePrefix("t_", "c_") // 设置过滤表前缀
                            // Entity 策略配置
                            .entityBuilder()
                            .enableLombok() // 开启 lombok 模式
                            .enableTableFieldAnnotation() // 开启生成实体时生成字段注解
                            .enableRemoveIsPrefix() // 开启 Boolean 类型字段移除 is 前缀
                            .enableActiveRecord() // 开启 ActiveRecord 模式
                            .logicDeleteColumnName("deleted") // 逻辑删除字段名(数据库)
                            .logicDeletePropertyName("deleted") // 逻辑删除属性名(实体)
                            .addSuperEntityColumns("id", "create_time", "update_time", "deleted") // 添加父类公共字段
                            .superClass("com.yummyerp.cloud.modules.common.entity.BaseEntity") // 设置父类
                            .formatFileName("%s") // 格式化文件名称
                            // Mapper 策略配置
                            .mapperBuilder()
                            .enableMapperAnnotation() // 开启 @Mapper 注解
                            .enableBaseResultMap() // 启用 BaseResultMap 生成
                            .enableBaseColumnList() // 启用 BaseColumnList
                            .formatMapperFileName("%sMapper") // 格式化 mapper 文件名称
                            .formatXmlFileName("%sMapper") // 格式化 xml 实现类文件名称
                            // Service 策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService") // 格式化 service 接口文件名称
                            .formatServiceImplFileName("%sServiceImpl") // 格式化 service 实现类文件名称
                            // Controller 策略配置
                            .controllerBuilder()
                            .enableHyphenStyle() // 开启驼峰转连字符
                            .enableRestStyle() // 开启生成@RestController 控制器
                            .formatFileName("%sController"); // 格式化文件名称
                })
                // 使用Freemarker引擎模板，速度更快
                .templateEngine(new FreemarkerTemplateEngine())
                // 执行生成
                .execute();
    }

    /**
     * 快速生成代码（使用默认配置）
     *
     * @param tableNames 表名，多个用逗号分隔
     * @param moduleName 模块名
     */
    public void quickGenerate(String tableNames, String moduleName) {
        generateCode(tableNames, moduleName, "CodeGenerator");
    }
}