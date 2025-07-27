package com.yummyerp.cloud.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yummyerp.cloud.modules.system.entity.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 操作日志记录表 Mapper 接口
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
    
    /**
     * 清空操作日志
     */
    void cleanOperLog();
} 