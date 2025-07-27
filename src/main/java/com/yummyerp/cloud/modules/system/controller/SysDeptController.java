package com.yummyerp.cloud.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yummyerp.cloud.modules.common.result.Result;
import com.yummyerp.cloud.modules.system.entity.SysDept;
import com.yummyerp.cloud.modules.system.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统部门表 前端控制器
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Api(tags = "系统部门管理")
@RestController
@RequestMapping("/system/dept")
public class SysDeptController {

    private final SysDeptService sysDeptService;

    public SysDeptController(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    @ApiOperation("获取部门列表（树形结构）")
    @GetMapping("/getList")
    @SaCheckPermission("sys:dept:list")
    public Result<List<SysDept>> getList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status) {
        List<SysDept> deptList = sysDeptService.getDeptTreeList(name, status);
        return Result.success(deptList);
    }

    @ApiOperation("获取部门详情")
    @GetMapping("/getDetail")
    @SaCheckPermission("sys:dept:detail")
    public Result<SysDept> getDetail(@RequestParam String id) {
        SysDept dept = sysDeptService.getById(Long.parseLong(id));
        return Result.success(dept);
    }

    @ApiOperation("新增部门")
    @PostMapping("/add")
    @SaCheckPermission("sys:dept:add")
    public Result<SysDept> add(@RequestBody SysDept sysDept) {
        sysDeptService.saveDeptWithAncestors(sysDept);
        return Result.success(sysDept);
    }

    @ApiOperation("修改部门")
    @PostMapping("/update")
    @SaCheckPermission("sys:dept:edit")
    public Result<SysDept> update(@RequestBody SysDept sysDept) {
        sysDeptService.updateDeptWithAncestors(sysDept);
        return Result.success(sysDept);
    }

    @ApiOperation("删除部门")
    @PostMapping("/delete")
    @SaCheckPermission("sys:dept:del")
    public Result<Boolean> delete(@RequestBody Map<String, List<Integer>> params) {
        List<Integer> ids = params.get("ids");
        if (ids == null || ids.isEmpty()) {
            return Result.success(false);
        }

        List<Long> deptIds = ids.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        boolean result = sysDeptService.deleteDeptWithRelations(deptIds);
        return Result.success(result);
    }

}
