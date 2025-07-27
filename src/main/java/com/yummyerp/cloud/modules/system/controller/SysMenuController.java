package com.yummyerp.cloud.modules.system.controller;

import com.yummyerp.cloud.modules.common.result.Result;
import com.yummyerp.cloud.modules.system.entity.SysMenu;
import com.yummyerp.cloud.modules.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
    public Result<List<SysMenu>> getList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status) {
        List<SysMenu> menuList = sysMenuService.getMenuTreeList(name, status);
        return Result.success(menuList);
    }

    @ApiOperation("获取菜单详情")
    @GetMapping("/getDetail")
    public Result<SysMenu> getDetail(@RequestParam String id) {
        SysMenu menu = sysMenuService.getById(Long.parseLong(id));
        return Result.success(menu);
    }

    @ApiOperation("新增菜单")
    @PostMapping("/add")
    public Result<SysMenu> add(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.success(sysMenu);
    }

    @ApiOperation("修改菜单")
    @PostMapping("/update")
    public Result<SysMenu> update(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.success(sysMenu);
    }

    @ApiOperation("删除菜单")
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody Map<String, Object> params) {
        @SuppressWarnings("unchecked")
        List<Object> ids = (List<Object>) params.get("ids");
        List<Long> menuIds = ids.stream()
                .map(id -> Long.parseLong(id.toString()))
                .collect(Collectors.toList());
        boolean result = sysMenuService.deleteMenuWithRelations(menuIds);
        return Result.success(result);
    }

    @ApiOperation("获取菜单选项（用于下拉选择）")
    @GetMapping("/getMenuOptions")
    public Result<List<Map<String, Object>>> getMenuOptions() {
        return Result.success(sysMenuService.getMenuOptions());
    }

    @ApiOperation("清理sys_role_menu表中不存在的菜单关联记录（测试方法）")
    @GetMapping("/cleanupInvalidRoleMenuRelations")
    public Result<Integer> cleanupInvalidRoleMenuRelations() {
        int cleanedCount = sysMenuService.cleanupInvalidRoleMenuRelations();
        return Result.success(cleanedCount);
    }
}
