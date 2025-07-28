package com.yummyerp.cloud.modules.system.controller;

import com.yummyerp.cloud.modules.common.result.Result;
import com.yummyerp.cloud.modules.system.service.RedisMonitorService;
import com.yummyerp.cloud.modules.system.service.SystemMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统监控控制器
 * @author 周欢
 * @since 2025-07-28
 */
@Api(tags = "系统监控")
@RestController
@RequestMapping("/system/monitor")
public class SysMonitorController {

    @Autowired
    private RedisMonitorService redisMonitorService;
    
    @Autowired
    private SystemMonitorService systemMonitorService;

    @ApiOperation("获取Redis基本信息")
    @GetMapping("/redis/info")
    public Result<Map<String, Object>> getRedisInfo() {
        return Result.success(redisMonitorService.getRedisInfo());
    }

    @ApiOperation("获取Redis内存信息")
    @GetMapping("/redis/memory")
    public Result<Map<String, Object>> getRedisMemoryInfo() {
        return Result.success(redisMonitorService.getMemoryInfo());
    }

    @ApiOperation("获取Redis键空间信息")
    @GetMapping("/redis/keyspace")
    public Result<Map<String, Object>> getRedisKeyspaceInfo() {
        return Result.success(redisMonitorService.getKeyspaceInfo());
    }

    @ApiOperation("获取Redis命令统计")
    @GetMapping("/redis/commandstats")
    public Result<Map<String, Object>> getRedisCommandStats() {
        return Result.success(redisMonitorService.getCommandStats());
    }

    @ApiOperation("清空Redis缓存")
    @PostMapping("/redis/flushdb")
    public Result<String> flushRedisDB() {
        return Result.success(redisMonitorService.flushDB());
    }

    @ApiOperation("获取系统基本信息")
    @GetMapping("/system/info")
    public Result<Map<String, Object>> getSystemInfo() {
        return Result.success(systemMonitorService.getSystemInfo());
    }

    @ApiOperation("获取JVM内存信息")
    @GetMapping("/system/jvm/memory")
    public Result<Map<String, Object>> getJvmMemoryInfo() {
        return Result.success(systemMonitorService.getJvmMemoryInfo());
    }

    @ApiOperation("获取垃圾回收信息")
    @GetMapping("/system/jvm/gc")
    public Result<Map<String, Object>> getGarbageCollectorInfo() {
        return Result.success(systemMonitorService.getGarbageCollectorInfo());
    }

    @ApiOperation("获取线程信息")
    @GetMapping("/system/thread")
    public Result<Map<String, Object>> getThreadInfo() {
        return Result.success(systemMonitorService.getThreadInfo());
    }

    @ApiOperation("获取磁盘信息")
    @GetMapping("/system/disk")
    public Result<Map<String, Object>> getDiskInfo() {
        return Result.success(systemMonitorService.getDiskInfo());
    }
}
