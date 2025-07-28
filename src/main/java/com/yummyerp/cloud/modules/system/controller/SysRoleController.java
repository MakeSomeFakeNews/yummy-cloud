package com.yummyerp.cloud.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yummyerp.cloud.annotation.Log;
import com.yummyerp.cloud.constant.LogConst;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;
import com.yummyerp.cloud.modules.common.result.Result;
import com.yummyerp.cloud.modules.system.entity.SysRole;
import com.yummyerp.cloud.modules.system.entity.SysUserRole;
import com.yummyerp.cloud.modules.system.service.SysRoleService;
import com.yummyerp.cloud.modules.system.service.SysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Api(tags = "系统角色管理")
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @ApiOperation("获取角色分页列表")
    @GetMapping("/getList")
    @SaCheckPermission("sys:role:list")
    @Log(title = "系统角色管理", businessType = LogConst.BusinessType.OTHER)
    public Result<PageResult<SysRole>> getList(
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status) {
        return Result.success(sysRoleService.getRolePageList(pageRequest, name, status));
    }

    @ApiOperation("获取角色详情")
    @GetMapping("/getDetail")
    @SaCheckPermission("sys:role:detail")
    @Log(title = "系统角色管理", businessType = LogConst.BusinessType.OTHER)
    public Result<SysRole> getDetail(@RequestParam String id) {
        SysRole role = sysRoleService.getById(Long.parseLong(id));
        return Result.success(role);
    }

    @ApiOperation("新增角色")
    @PostMapping("/add")
    @SaCheckPermission("sys:role:add")
    @Log(title = "系统角色管理", businessType = LogConst.BusinessType.INSERT)
    public Result<SysRole> add(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return Result.success(sysRole);
    }

    @ApiOperation("修改角色")
    @PostMapping("/update")
    @SaCheckPermission("sys:role:edit")
    @Log(title = "系统角色管理", businessType = LogConst.BusinessType.UPDATE)
    public Result<SysRole> update(@RequestBody SysRole sysRole) {
        sysRoleService.updateById(sysRole);
        return Result.success(sysRole);
    }

    @ApiOperation("删除角色")
    @PostMapping("/delete")
    @SaCheckPermission("sys:role:del")
    @Log(title = "系统角色管理", businessType = LogConst.BusinessType.DELETE)
    public Result<String> delete(@RequestBody Map<String, List<Integer>> params) {
        List<Integer> ids = params.get("ids");
        List<Long> roleIds = ids.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        roleIds.forEach(roleId -> {
            SysUserRole userRole = sysUserRoleService.getOne(new LambdaQueryWrapper<>(SysUserRole.class).eq(SysUserRole::getRoleId, roleId));
            SysRole sysRole = sysRoleService.getById(roleId);
            if (userRole != null) {
                throw new RuntimeException(sysRole.getName() + "角色下存在用户，无法删除");
            }
            sysRoleService.removeById(roleId);
        });
        return Result.success();
    }


    @ApiOperation("获取角色菜单权限")
    @GetMapping("/getRoleMenuIds")
    @SaCheckPermission("sys:role:menu:list")
    @Log(title = "系统角色菜单管理", businessType = LogConst.BusinessType.OTHER)
    public Result<List<String>> getRoleMenuIds(@RequestParam String role) {
        List<String> menuIds = sysRoleService.getRoleMenuIds(role);
        return Result.success(menuIds);
    }

    @ApiOperation("设置角色菜单权限")
    @PostMapping("/setRoleMenus")
    @SaCheckPermission("sys:role:menu:edit")
    @Log(title = "系统角色菜单管理", businessType = LogConst.BusinessType.GRANT)
    public Result<Boolean> setRoleMenus(@RequestBody Map<String, Object> params) {
        String roleCode = (String) params.get("roleCode");
        @SuppressWarnings("unchecked")
        List<String> menuIds = (List<String>) params.get("menuIds");
        boolean result = sysRoleService.setRoleMenus(roleCode, menuIds);
        return Result.success(result);
    }
}
