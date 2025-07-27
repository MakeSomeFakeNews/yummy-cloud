package com.yummyerp.cloud.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yummyerp.cloud.modules.system.entity.SysOperLog;
import com.yummyerp.cloud.modules.system.mapper.SysOperLogMapper;
import com.yummyerp.cloud.modules.system.service.SysOperLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Service
@Slf4j
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

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
    @Override
    public List<SysOperLog> getOperLogList(Integer pageNum, Integer pageSize, String title, Integer businessType,
                                          String operName, Integer status, Date startTime, Date endTime) {
        // 构建查询条件
        LambdaQueryWrapper<SysOperLog> queryWrapper = buildQueryWrapper(title, businessType, operName, status, startTime, endTime);
        
        // 分页查询
        Page<SysOperLog> page = new Page<>(pageNum, pageSize);
        Page<SysOperLog> result = this.page(page, queryWrapper);
        
        return result.getRecords();
    }
    
    /**
     * 获取操作日志分页数据（包含总记录数）
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
    @Override
    public Map<String, Object> getOperLogPage(Integer pageNum, Integer pageSize, String title, Integer businessType,
                                             String operName, Integer status, Date startTime, Date endTime) {
        // 构建查询条件
        LambdaQueryWrapper<SysOperLog> queryWrapper = buildQueryWrapper(title, businessType, operName, status, startTime, endTime);
        
        // 分页查询
        Page<SysOperLog> page = new Page<>(pageNum, pageSize);
        Page<SysOperLog> result = this.page(page, queryWrapper);
        
        Map<String, Object> map = new HashMap<>();
        map.put("records", result.getRecords());
        map.put("total", result.getTotal());
        
        return map;
    }
    
    /**
     * 批量删除日志
     *
     * @param ids 日志ID字符串，逗号分隔
     * @return 是否成功
     */
    @Override
    public Boolean deleteByIds(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        List<Long> longIds = idList.stream().map(Long::valueOf).collect(Collectors.toList());
        return this.removeByIds(longIds);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        baseMapper.cleanOperLog();
    }

    @Override
    public void asyncInsertOperLog(SysOperLog operLog) {
        baseMapper.insert(operLog);
    }


    /**
     * 构建查询条件
     *
     * @param title 模块标题
     * @param businessType 业务类型
     * @param operName 操作人员
     * @param status 操作状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 查询条件
     */
    private LambdaQueryWrapper<SysOperLog> buildQueryWrapper(String title, Integer businessType,
                                                           String operName, Integer status,
                                                           Date startTime, Date endTime) {
        LambdaQueryWrapper<SysOperLog> queryWrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.isNotBlank(title)) {
            queryWrapper.like(SysOperLog::getTitle, title);
        }
        
        if (businessType != null) {
            queryWrapper.eq(SysOperLog::getBusinessType, businessType);
        }
        
        if (StringUtils.isNotBlank(operName)) {
            queryWrapper.like(SysOperLog::getOperName, operName);
        }
        
        if (status != null) {
            queryWrapper.eq(SysOperLog::getStatus, status);
        }
        
        if (startTime != null && endTime != null) {
            queryWrapper.between(SysOperLog::getOperTime, startTime, endTime);
        }
        
        // 默认按操作时间降序排序
        queryWrapper.orderByDesc(SysOperLog::getOperTime);
        
        return queryWrapper;
    }

} 