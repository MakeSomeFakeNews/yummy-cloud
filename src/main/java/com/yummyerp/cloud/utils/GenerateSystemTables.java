package com.yummyerp.cloud.utils;

/**
 * 生成权限管理系统相关表的工具类
 */
public class GenerateSystemTables {
    
    public static void main(String[] args) {
        System.out.println("开始生成权限管理系统相关表代码...");
        
        // 生成系统表代码
        String tableNames = "sys_dept,sys_dict,sys_dict_data,sys_menu,sys_role,sys_role_dept,sys_role_menu,sys_user,sys_user_role";
        String moduleName = "system";
        String author = "周欢";
        
        System.out.println("将生成以下表的代码：");
        System.out.println(tableNames);
        System.out.println("模块名：" + moduleName);
        System.out.println("作者：" + author);
        
        CodeGenerator.generateCode(tableNames, moduleName, author);
        
        System.out.println("权限管理系统相关表代码生成完成！");
    }
}