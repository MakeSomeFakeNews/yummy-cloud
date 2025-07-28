package com.yummyerp.cloud.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;
import com.yummyerp.cloud.modules.system.dto.SysOperLogQuery;
import com.yummyerp.cloud.modules.system.entity.SysOperLog;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作日志 服务层
 */
public interface SysOperLogService  extends IService<SysOperLog> {
    /**
     * 获取操作日志列表
     *
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @param title 模块标题
     * @param businessType 业务类型
     * @param operName 操作人员
     * @param status 操作状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 操作日志分页列表
     */
    List<SysOperLog> getOperLogList(Integer pageNum, Integer pageSize, String title, Integer businessType,
                                    String operName, Integer status, Date startTime, Date endTime);
    
    /**
     * 获取操作日志分页数据
     *
     * @param pageRequest 分页请求参数
     * @param query 查询条件
     * @return 分页结果
     */
    PageResult<SysOperLog> getOperLogPage(PageRequest pageRequest, SysOperLogQuery query);

    /**
     * 获取操作日志分页数据（兼容旧版本）
     *
     * @param pageRequest 分页请求参数
     * @param title 模块标题
     * @param businessType 业务类型
     * @param operName 操作人员
     * @param status 操作状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    @Deprecated
    PageResult<SysOperLog> getOperLogPage(PageRequest pageRequest, String title, Integer businessType,
                                         String operName, Integer status, Date startTime, Date endTime);
    
    /**
     * 获取操作日志分页数据（保留旧版本兼容）
     *
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @param title 模块标题
     * @param businessType 业务类型
     * @param operName 操作人员
     * @param status 操作状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 包含分页记录和总记录数的Map
     */
    @Deprecated
    Map<String, Object> getOperLogPage(Integer pageNum, Integer pageSize, String title, Integer businessType,
                                      String operName, Integer status, Date startTime, Date endTime);

    /**
     * 批量删除日志
     *
     * @param ids 日志ID字符串，逗号分隔
     * @return 是否成功
     */
    Boolean deleteByIds(String ids);

    /**
     * 清空操作日志
     */
    void cleanOperLog();

    /**
     * 异步保存操作日志
     * 
     * @param operLog 操作日志信息
     */
    void asyncInsertOperLog(SysOperLog operLog);
} 