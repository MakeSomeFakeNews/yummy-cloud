package com.yummyerp.cloud.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yummyerp.cloud.annotation.Log;
import com.yummyerp.cloud.constant.LogConst;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;
import com.yummyerp.cloud.modules.common.result.Result;
import com.yummyerp.cloud.modules.system.dto.SysOperLogQuery;
import com.yummyerp.cloud.modules.system.entity.SysOperLog;
import com.yummyerp.cloud.modules.system.service.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public Result<PageResult<SysOperLog>> getList(
            @Valid PageRequest pageRequest,
            @Valid SysOperLogQuery query) {
        
        PageResult<SysOperLog> result = operLogService.getOperLogPage(pageRequest, query);
        return Result.success(result);
    }

    @ApiOperation("获取操作日志详情")
    @GetMapping("/getDetail")
    @SaCheckPermission("sys:log:detail")
    public Result<SysOperLog> getDetail(@RequestParam Long id) {
        return Result.success(operLogService.getById(id));
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