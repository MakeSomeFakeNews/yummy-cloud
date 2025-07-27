package com.yummyerp.cloud.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yummyerp.cloud.annotation.Log;
import com.yummyerp.cloud.constant.LogConst;
import com.yummyerp.cloud.modules.common.result.Result;
import com.yummyerp.cloud.modules.system.entity.SysOperLog;
import com.yummyerp.cloud.modules.system.service.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@RestController
@RequestMapping("/system/operLog")
@Api(tags = "操作日志管理")
public class SysOperLogController {

    private final SysOperLogService operLogService;

    public SysOperLogController(SysOperLogService operLogService) {
        this.operLogService = operLogService;
    }

    @ApiOperation("获取操作日志分页列表")
    @GetMapping("/getList")
    @SaCheckPermission("sys:log:list")
    public Result<Map<String, Object>> getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "businessType", required = false) Integer businessType,
            @RequestParam(value = "operName", required = false) String operName,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "startTime", required = false) Date startTime,
            @RequestParam(value = "endTime", required = false) Date endTime) {
        
        Map<String, Object> result = operLogService.getOperLogPage(page, size, title, businessType, 
                operName, status, startTime, endTime);
        return Result.success(result);
    }

    @ApiOperation("获取操作日志详情")
    @GetMapping("/getDetail")
    @SaCheckPermission("sys:log:detail")
    public Result<SysOperLog> getDetail(@RequestParam String id) {
        return Result.success(operLogService.getById(Long.parseLong(id)));
    }

    @ApiOperation("清空操作日志")
    @PostMapping("/clean")
    @SaCheckPermission("sys:log:del")
    @Log(title = "操作日志管理", businessType = LogConst.BusinessType.CLEAN)
    public Result<Void> clean() {
        operLogService.cleanOperLog();
        return Result.success();
    }
} 