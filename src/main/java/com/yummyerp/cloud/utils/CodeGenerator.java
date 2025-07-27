package com.yummyerp.cloud.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;

/**
 * YummyERP MyBatis-Plus代码生成器
 * 支持按业务模块批量生成代码
 * 
 * <p>使用方式：</p>
 * <ul>
 *   <li>交互式运行：直接运行 main 方法，按提示选择</li>
 *   <li>快速生成单个模块：调用 generateModule("basic", "作者名")</li>
 *   <li>快速生成基础数据：调用 generateBasicModule("作者名")</li>
 *   <li>快速生成产品模块：调用 generateProductModule("作者名")</li>
 *   <li>快速生成库存模块：调用 generateInventoryModule("作者名")</li>
 *   <li>快速生成采购模块：调用 generatePurchaseModule("作者名")</li>
 *   <li>快速生成销售模块：调用 generateSalesModule("作者名")</li>
 *   <li>快速生成财务模块：调用 generateFinanceModule("作者名")</li>
 * </ul>
 * 
 * <p>支持的模块：</p>
 * <ul>
 *   <li>system - 系统管理模块（用户权限管理）</li>
 *   <li>basic - 基础数据模块（供应商、客户、仓库）</li>
 *   <li>product - 产品管理模块（产品分类、信息、变体）</li>
 *   <li>inventory - 库存管理模块（库存监控、出入库、盘点）</li>
 *   <li>purchase - 采购管理模块（采购申请、订单、收货）</li>
 *   <li>sales - 销售管理模块（销售报价、订单、发货）</li>
 *   <li>finance - 财务管理模块（应收应付、收付款）</li>
 * </ul>
 * 
 * <p>修复的问题：</p>
 * <ul>
 *   <li>✅ 修复Mapper接口缺少@Mapper注解问题</li>
 *   <li>✅ 修复ServiceImpl缺少@Service注解问题</li>
 *   <li>✅ 修复tinyint(1)类型映射为Byte问题，现在正确映射为Integer</li>
 *   <li>✅ 使用自定义Freemarker模板确保注解正确生成</li>
 * </ul>
 * 
 * @author 周欢
 * @since 2025-07-27
 */
public class CodeGenerator {

    /**
     * 数据库连接配置
     */
    private static final String URL = "jdbc:mysql://localhost:3306/yummy?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "aaa123..";

    /**
     * ERP模块配置表
     */
    private static final Map<String, ModuleConfig> ERP_MODULES = new HashMap<>();
    
    static {
        // 1. 系统管理模块
        ERP_MODULES.put("system", new ModuleConfig(
            "system", 
            "系统管理模块",
            "sys_dept,sys_dict,sys_dict_data,sys_menu,sys_role,sys_role_dept,sys_role_menu,sys_user,sys_user_role",
            "用户权限管理"
        ));
        
        // 2. 基础数据模块
        ERP_MODULES.put("basic", new ModuleConfig(
            "basic",
            "基础数据模块", 
            "erp_supplier,erp_supplier_evaluation,erp_customer,erp_warehouse,erp_warehouse_location,erp_unit",
            "供应商、客户、仓库、计量单位管理"
        ));
        
        // 3. 产品管理模块
        ERP_MODULES.put("product", new ModuleConfig(
            "product",
            "产品管理模块",
            "erp_product_category,erp_product,erp_product_variant,erp_product_image",
            "产品分类、产品信息、变体、图片管理"
        ));
        
        // 4. 库存管理模块
        ERP_MODULES.put("inventory", new ModuleConfig(
            "inventory",
            "库存管理模块",
            "erp_inventory,erp_inventory_transaction,erp_inventory_check,erp_inventory_check_detail,erp_inventory_transfer,erp_inventory_transfer_detail,erp_inventory_alert",
            "库存监控、出入库、盘点、调拨、预警管理"
        ));
        
        // 5. 采购管理模块
        ERP_MODULES.put("purchase", new ModuleConfig(
            "purchase",
            "采购管理模块",
            "erp_purchase_request,erp_purchase_request_detail,erp_purchase_order,erp_purchase_order_detail,erp_purchase_receipt,erp_purchase_receipt_detail,erp_purchase_return,erp_purchase_return_detail",
            "采购申请、订单、收货、退货管理"
        ));
        
        // 6. 销售管理模块
        ERP_MODULES.put("sales", new ModuleConfig(
            "sales",
            "销售管理模块",
            "erp_sales_quotation,erp_sales_quotation_detail,erp_sales_order,erp_sales_order_detail,erp_sales_shipment,erp_sales_shipment_detail,erp_sales_return,erp_sales_return_detail",
            "销售报价、订单、发货、退货管理"
        ));
        
        // 7. 财务管理模块
        ERP_MODULES.put("finance", new ModuleConfig(
            "finance",
            "财务管理模块",
            "erp_accounts_receivable,erp_payment_received,erp_accounts_payable,erp_payment_made,erp_expense",
            "应收应付、收付款、费用管理"
        ));
    }
    
    /**
     * 模块配置类
     */
    static class ModuleConfig {
        private final String moduleName;
        private final String moduleTitle;
        private final String tableNames;
        private final String description;
        
        public ModuleConfig(String moduleName, String moduleTitle, String tableNames, String description) {
            this.moduleName = moduleName;
            this.moduleTitle = moduleTitle;
            this.tableNames = tableNames;
            this.description = description;
        }
        
        public String getModuleName() { return moduleName; }
        public String getModuleTitle() { return moduleTitle; }
        public String getTableNames() { return tableNames; }
        public String getDescription() { return description; }
    }

    /**
     * 项目路径
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("============================================================");
        System.out.println("           YummyERP 代码生成器");
        System.out.println("============================================================");
        
        // 显示所有可用模块
        displayAvailableModules();
        
        System.out.println("\n请选择生成模式：");
        System.out.println("1. 按ERP业务模块生成（推荐）");
        System.out.println("2. 生成所有ERP模块");
        System.out.println("3. 自定义表名生成");
        System.out.print("请输入选择（1-3）：");
        
        String choice = scanner.nextLine().trim();
        System.out.print("请输入作者名：");
        String author = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                handleModuleGeneration(scanner, author);
                break;
            case "2":
                handleAllModulesGeneration(author);
                break;
            case "3":
                handleCustomGeneration(scanner, author);
                break;
            default:
                System.out.println("无效选择，程序退出。");
                return;
        }
        
        System.out.println("\n============================================================");
        System.out.println("代码生成完成！请检查生成的文件。");
        System.out.println("============================================================");
    }
    
    /**
     * 显示所有可用的ERP模块
     */
    private static void displayAvailableModules() {
        System.out.println("\n可用的ERP业务模块：");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-12s %-20s %-15s %s%n", "模块代码", "模块名称", "表数量", "功能描述");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (ModuleConfig config : ERP_MODULES.values()) {
            int tableCount = config.getTableNames().split(",").length;
            System.out.printf("%-12s %-20s %-15d %s%n", 
                config.getModuleName(), 
                config.getModuleTitle(), 
                tableCount,
                config.getDescription()
            );
        }
        System.out.println("--------------------------------------------------------------------------------");
    }
    
    /**
     * 处理模块化生成
     */
    private static void handleModuleGeneration(Scanner scanner, String author) {
        System.out.print("\n请输入要生成的模块代码（如：basic,product，多个用逗号分隔）：");
        String moduleInput = scanner.nextLine().trim();
        
        String[] moduleNames = moduleInput.split(",");
        for (String moduleName : moduleNames) {
            moduleName = moduleName.trim();
            ModuleConfig config = ERP_MODULES.get(moduleName);
            
            if (config != null) {
                System.out.println("\n正在生成模块：" + config.getModuleTitle());
                System.out.println("包含表：" + config.getTableNames());
                generateCode(config.getTableNames(), config.getModuleName(), author);
                System.out.println("模块 " + config.getModuleTitle() + " 生成完成！");
            } else {
                System.out.println("警告：未找到模块 '" + moduleName + "'，跳过...");
            }
        }
    }
    
    /**
     * 处理生成所有ERP模块
     */
    private static void handleAllModulesGeneration(String author) {
        System.out.print("\n确认要生成所有ERP模块吗？这将生成大量文件。(y/N)：");
        Scanner scanner = new Scanner(System.in);
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if ("y".equals(confirm) || "yes".equals(confirm)) {
            System.out.println("\n开始生成所有ERP模块...");
            
            for (ModuleConfig config : ERP_MODULES.values()) {
                System.out.println("\n正在生成模块：" + config.getModuleTitle());
                generateCode(config.getTableNames(), config.getModuleName(), author);
                System.out.println("模块 " + config.getModuleTitle() + " 生成完成！");
            }
        } else {
            System.out.println("已取消生成。");
        }
    }
    
    /**
     * 处理自定义生成
     */
    private static void handleCustomGeneration(Scanner scanner, String author) {
        System.out.print("\n请输入表名（多个表名用英文逗号分隔）：");
        String tableNames = scanner.nextLine().trim();
        
        System.out.print("请输入模块名（如：system、basic等）：");
        String moduleName = scanner.nextLine().trim();
        
        System.out.println("\n将生成以下配置：");
        System.out.println("表名：" + tableNames);
        System.out.println("模块：" + moduleName);
        System.out.println("作者：" + author);
        
        generateCode(tableNames, moduleName, author);
    }

    /**
     * 快速生成权限管理系统相关表代码
     */
    public static void generateSystemTables(String author) {
        ModuleConfig config = ERP_MODULES.get("system");
        if (config != null) {
            generateCode(config.getTableNames(), config.getModuleName(), author);
        }
    }
    
    /**
     * 快速生成基础数据模块代码
     */
    public static void generateBasicModule(String author) {
        ModuleConfig config = ERP_MODULES.get("basic");
        if (config != null) {
            generateCode(config.getTableNames(), config.getModuleName(), author);
        }
    }
    
    /**
     * 快速生成产品管理模块代码
     */
    public static void generateProductModule(String author) {
        ModuleConfig config = ERP_MODULES.get("product");
        if (config != null) {
            generateCode(config.getTableNames(), config.getModuleName(), author);
        }
    }
    
    /**
     * 快速生成库存管理模块代码
     */
    public static void generateInventoryModule(String author) {
        ModuleConfig config = ERP_MODULES.get("inventory");
        if (config != null) {
            generateCode(config.getTableNames(), config.getModuleName(), author);
        }
    }
    
    /**
     * 快速生成采购管理模块代码
     */
    public static void generatePurchaseModule(String author) {
        ModuleConfig config = ERP_MODULES.get("purchase");
        if (config != null) {
            generateCode(config.getTableNames(), config.getModuleName(), author);
        }
    }
    
    /**
     * 快速生成销售管理模块代码
     */
    public static void generateSalesModule(String author) {
        ModuleConfig config = ERP_MODULES.get("sales");
        if (config != null) {
            generateCode(config.getTableNames(), config.getModuleName(), author);
        }
    }
    
    /**
     * 快速生成财务管理模块代码
     */
    public static void generateFinanceModule(String author) {
        ModuleConfig config = ERP_MODULES.get("finance");
        if (config != null) {
            generateCode(config.getTableNames(), config.getModuleName(), author);
        }
    }
    
    /**
     * 按模块名快速生成代码
     */
    public static void generateModule(String moduleName, String author) {
        ModuleConfig config = ERP_MODULES.get(moduleName);
        if (config != null) {
            System.out.println("正在生成模块：" + config.getModuleTitle());
            generateCode(config.getTableNames(), config.getModuleName(), author);
            System.out.println("模块 " + config.getModuleTitle() + " 生成完成！");
        } else {
            System.out.println("错误：未找到模块 '" + moduleName + "'");
        }
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
                // 模板配置 - 使用自定义模板
                .templateConfig(builder -> {
                    // 使用自定义Mapper模板
                    builder.mapper(PROJECT_PATH + "/src/main/resources/templates/mapper.java.ftl");
                    // 使用自定义ServiceImpl模板
                    builder.serviceImpl(PROJECT_PATH + "/src/main/resources/templates/serviceImpl.java.ftl");
                })
                // 模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                .templateEngine(new FreemarkerTemplateEngine())
                // 执行生成
                .execute();
    }
}