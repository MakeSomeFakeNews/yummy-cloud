package com.yummyerp.cloud.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yummyerp.cloud.modules.common.result.Result;
import com.yummyerp.cloud.modules.system.entity.SysUser;
import com.yummyerp.cloud.modules.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Api(tags = "系统用户管理")
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("获取用户分页列表")
    @GetMapping("/getList")
    @SaCheckPermission("sys:user:list")
    public Result<Map<String, Object>> getList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long deptId) {
        return Result.success(sysUserService.getUserPageList(page, size, username, status, deptId));
    }

    @ApiOperation("获取用户详情")
    @GetMapping("/getDetail")
    @SaCheckPermission("sys:user:detail")
    public Result<SysUser> getDetail(@RequestParam String id) {
        SysUser user = sysUserService.getUserDetailById(Long.parseLong(id));
        return Result.success(user);
    }

    @ApiOperation("新增用户")
    @PostMapping("/add")
    @SaCheckPermission("sys:user:add")
    public Result<SysUser> add(@RequestBody SysUser sysUser) {
        sysUserService.saveUserWithRoles(sysUser);
        return Result.success(sysUser);
    }

    @ApiOperation("修改用户")
    @PostMapping("/update")
    @SaCheckPermission("sys:user:edit")
    public Result<SysUser> update(@RequestBody SysUser sysUser) {
        sysUserService.updateUserWithRoles(sysUser);
        return Result.success(sysUser);
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    @SaCheckPermission("sys:user:del")
    public Result<Boolean> delete(@RequestBody Map<String, List<Integer>> params) {
        List<Integer> ids = params.get("ids");
        List<Long> userIds = ids.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        boolean result = sysUserService.deleteUsersWithRelations(userIds);
        return Result.success(result);
    }

}
