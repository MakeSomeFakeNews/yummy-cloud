package com.yummyerp.cloud.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
import java.util.Scanner;

/**
 * MyBatis-Plus代码生成器
 */
public class CodeGenerator {

    /**
     * 数据库连接配置
     */
    private static final String URL = "jdbc:mysql://localhost:3306/yummy?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "aaa123..";

    /**
     * 项目路径
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");

    public static void main(String[] args) {
        // 交互式选择生成模式
        Scanner scanner = new Scanner(System.in);
        System.out.println("请选择生成模式：");
        System.out.println("1. 生成权限管理系统相关表（推荐）");
        System.out.println("2. 自定义表名生成");
        System.out.print("请输入选择（1或2）：");
        
        String choice = scanner.nextLine();
        String tableNames;
        String moduleName;
        String author;
        
        if ("1".equals(choice)) {
            // 预定义权限管理系统相关表
            tableNames = "sys_dept,sys_dict,sys_dict_data,sys_menu,sys_role,sys_role_dept,sys_role_menu,sys_user,sys_user_role";
            moduleName = "system";
            System.out.println("请输入作者名：");
            author = scanner.nextLine();
            
            System.out.println("将生成以下表的代码：");
            System.out.println("sys_dept, sys_dict, sys_dict_data, sys_menu, sys_role, sys_role_dept, sys_role_menu, sys_user, sys_user_role");
            System.out.println("模块名：system");
        } else {
            // 自定义模式
            System.out.println("请输入表名（多个表名用英文逗号分隔）：");
            tableNames = scanner.nextLine();
            
            System.out.println("请输入模块名（如：system、user等）：");
            moduleName = scanner.nextLine();
            
            System.out.println("请输入作者名：");
            author = scanner.nextLine();
        }

        // 开始生成代码
        generateCode(tableNames, moduleName, author);
        
        System.out.println("代码生成完成！");
    }

    /**
     * 快速生成权限管理系统相关表代码
     */
    public static void generateSystemTables(String author) {
        String tableNames = "sys_dept,sys_dict,sys_dict_data,sys_menu,sys_role,sys_role_dept,sys_role_menu,sys_user,sys_user_role";
        generateCode(tableNames, "system", author);
    }

    /**
     * 执行代码生成
     */
    public static void generateCode(String tableNames, String moduleName, String author) {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
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
                            // .enableActiveRecord() // 关闭 ActiveRecord 模式，避免泛型问题
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
                // 模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                .templateEngine(new FreemarkerTemplateEngine())
                // 执行生成
                .execute();
    }
}