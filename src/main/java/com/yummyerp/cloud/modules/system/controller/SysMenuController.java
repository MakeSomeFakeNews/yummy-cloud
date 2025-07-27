package com.yummyerp.cloud.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yummyerp.cloud.annotation.Log;
import com.yummyerp.cloud.constant.LogConst;
import com.yummyerp.cloud.modules.common.result.Result;
import com.yummyerp.cloud.modules.system.entity.SysMenu;
import com.yummyerp.cloud.modules.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Api(tags = "系统菜单管理")
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation("获取菜单列表（树形结构）")
    @GetMapping("/getList")
    @SaCheckPermission("sys:menu:list")
    @Log(title = "系统菜单管理", businessType = LogConst.BusinessType.OTHER)
    public Result<List<SysMenu>> getList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status) {
        List<SysMenu> menuList = sysMenuService.getMenuTreeList(name, status);
        return Result.success(menuList);
    }

    @ApiOperation("获取菜单详情")
    @GetMapping("/getDetail")
    @SaCheckPermission("sys:menu:detail")
    @Log(title = "系统菜单管理", businessType = LogConst.BusinessType.OTHER)
    public Result<SysMenu> getDetail(@RequestParam String id) {
        SysMenu menu = sysMenuService.getById(Long.parseLong(id));
        return Result.success(menu);
    }

    @ApiOperation("新增菜单")
    @PostMapping("/add")
    @SaCheckPermission("sys:menu:add")
    @Log(title = "系统菜单管理", businessType = LogConst.BusinessType.INSERT)
    public Result<SysMenu> add(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.success(sysMenu);
    }

    @ApiOperation("修改菜单")
    @PostMapping("/update")
    @SaCheckPermission("sys:menu:edit")
    @Log(title = "系统菜单管理", businessType = LogConst.BusinessType.UPDATE)
    public Result<SysMenu> update(@RequestBody SysMenu sysMenu) {
        System.out.println(sysMenu);
        sysMenuService.updateById(sysMenu);
        return Result.success(sysMenu);
    }

    @ApiOperation("删除菜单")
    @PostMapping("/delete")
    @SaCheckPermission("sys:menu:del")
    @Log(title = "系统菜单管理", businessType = LogConst.BusinessType.DELETE)
    public Result<Boolean> delete(@RequestBody Map<String, List<Integer>> params) {
        List<Integer> ids = params.get("ids");
        if (ids == null || ids.isEmpty()) {
            return Result.success(false);
        }

        List<Long> menuIds = ids.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        boolean result = sysMenuService.deleteMenuWithRelations(menuIds);
        return Result.success(result);
    }


    @ApiOperation("获取菜单选项（用于下拉选择）")
    @GetMapping("/getMenuOptions")
    @Log(title = "系统菜单管理", businessType = LogConst.BusinessType.OTHER)
    public Result<List<Map<String, Object>>> getMenuOptions() {
        return Result.success(sysMenuService.getMenuOptions());
    }

    @ApiOperation("清理sys_role_menu表中不存在的菜单关联记录（测试方法）")
    @GetMapping("/cleanupInvalidRoleMenuRelations")
    @Log(title = "系统菜单管理", businessType = LogConst.BusinessType.CLEAN)
    public Result<Integer> cleanupInvalidRoleMenuRelations() {
        int cleanedCount = sysMenuService.cleanupInvalidRoleMenuRelations();
        return Result.success(cleanedCount);
    }
}
