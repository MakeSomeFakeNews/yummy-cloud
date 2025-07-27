package com.yummyerp.cloud.constant;

/**
 * 日志常量
 */
public interface LogConst {
    
    /**
     * 业务类型
     */
    interface BusinessType {
        /**
         * 其它
         */
        int OTHER = 0;
        
        /**
         * 新增
         */
        int INSERT = 1;
        
        /**
         * 修改
         */
        int UPDATE = 2;
        
        /**
         * 删除
         */
        int DELETE = 3;
        
        /**
         * 授权
         */
        int GRANT = 4;
        
        /**
         * 导出
         */
        int EXPORT = 5;
        
        /**
         * 导入
         */
        int IMPORT = 6;
        
        /**
         * 强退
         */
        int FORCE_LOGOUT = 7;
        
        /**
         * 生成代码
         */
        int GENCODE = 8;
        
        /**
         * 清空数据
         */
        int CLEAN = 9;
        
        /**
         * 查询
         */
        int QUERY = 10;
    }
    
    /**
     * 操作类型
     */
    interface OperatorType {
        /**
         * 后台用户
         */
        int MANAGE = 0;
        
        /**
         * 手机端用户
         */
        int MOBILE = 1;
        
        /**
         * 其它
         */
        int OTHER = 2;
    }
} 