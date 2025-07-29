package com.yummyerp.cloud.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yummyerp.cloud.annotation.Log;
import com.yummyerp.cloud.constant.LogConst;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;
import com.yummyerp.cloud.modules.common.result.Result;
import com.yummyerp.cloud.modules.system.dto.SysDictQuery;
import com.yummyerp.cloud.modules.system.entity.SysDict;
import com.yummyerp.cloud.modules.system.entity.SysDictData;
import com.yummyerp.cloud.modules.system.service.SysDictService;
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
 * 系统字典表 前端控制器
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Api(tags = "系统字典管理")
@RestController
@RequestMapping("/system/dict")
public class SysDictController {

    private final SysDictService sysDictService;

    public SysDictController(SysDictService sysDictService) {
        this.sysDictService = sysDictService;
    }

    @ApiOperation("获取字典分页列表")
    @GetMapping("/getList")
    @SaCheckPermission("sys:dict:list")
    @Log(title = "系统字典管理")
    public Result<PageResult<SysDict>> getList(
            @Valid PageRequest pageRequest,
            @Valid SysDictQuery query) {
        return Result.success(sysDictService.getDictPageList(pageRequest, query));
    }

    @ApiOperation("获取字典详情")
    @GetMapping("/getDetail")
    @Log(title = "系统字典管理")
    public Result<SysDict> getDetail(@RequestParam Long id) {
        SysDict dict = sysDictService.getById(id);
        return Result.success(dict);
    }

    @ApiOperation("新增字典")
    @PostMapping("/add")
    @SaCheckPermission("sys:dict:add")
    @Log(title = "系统字典管理", businessType = LogConst.BusinessType.INSERT)
    public Result<SysDict> add(@RequestBody SysDict sysDict) {
        sysDictService.save(sysDict);
        return Result.success(sysDict);
    }

    @ApiOperation("修改字典")
    @PostMapping("/update")
    @SaCheckPermission("sys:dict:edit")
    @Log(title = "系统字典管理", businessType = LogConst.BusinessType.UPDATE)
    public Result<SysDict> update(@RequestBody SysDict sysDict) {
        sysDictService.updateById(sysDict);
        return Result.success(sysDict);
    }

    @ApiOperation("删除字典")
    @PostMapping("/delete")
    @SaCheckPermission("sys:dict:del")
    @Log(title = "系统字典管理", businessType = LogConst.BusinessType.DELETE)
    public Result<Boolean> delete(@RequestBody Map<String, Object> params) {
        @SuppressWarnings("unchecked")
        List<Object> ids = (List<Object>) params.get("ids");
        List<Long> dictIds = ids.stream()
                .map(id -> Long.parseLong(id.toString()))
                .collect(Collectors.toList());
        boolean result = sysDictService.deleteDictsWithData(dictIds);
        return Result.success(result);
    }

    @ApiOperation("获取字典数据列表")
    @GetMapping("/getDictDataList")
    @Log(title = "字典数据管理")
    public Result<PageResult<SysDictData>> getDictDataList(
            @RequestParam String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(sysDictService.getDictDataPageList(code, page, size));
    }

    @ApiOperation("获取字典数据详情")
    @GetMapping("/getDictDataDetail")
    @Log(title = "字典数据管理")
    public Result<Object> getDictDataDetail(@RequestParam Long id) {
        Object dictData = sysDictService.getDictDataDetail(id);
        return Result.success(dictData);
    }

    @ApiOperation("获取所有字典数据映射")
    @GetMapping("/getDictData")
    @Log(title = "字典数据管理")
    public Result<Map<String, Object>> getDictData() {
        return Result.success(sysDictService.getAllDictDataMap());
    }
}
