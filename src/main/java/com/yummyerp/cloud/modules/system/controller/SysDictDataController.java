package com.yummyerp.cloud.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yummyerp.cloud.annotation.Log;
import com.yummyerp.cloud.constant.LogConst;
import com.yummyerp.cloud.modules.common.result.Result;
import com.yummyerp.cloud.modules.system.entity.SysDictData;
import com.yummyerp.cloud.modules.system.service.SysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统字典数据表 前端控制器
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Api(tags = "系统字典数据管理")
@RestController
@RequestMapping("/system/dictData")
public class SysDictDataController {

    @Autowired
    private SysDictDataService sysDictDataService;

    @ApiOperation("新增字典数据")
    @PostMapping("/add")
    @SaCheckPermission("sys:dict:data:add")
    @Log(title = "字典数据管理", businessType = LogConst.BusinessType.INSERT)
    public Result<SysDictData> add(@RequestBody SysDictData sysDictData) {
        sysDictDataService.save(sysDictData);
        return Result.success(sysDictData);
    }

    @ApiOperation("修改字典数据")
    @PostMapping("/update")
    @SaCheckPermission("sys:dict:data:edit")
    @Log(title = "字典数据管理", businessType = LogConst.BusinessType.UPDATE)
    public Result<SysDictData> update(@RequestBody SysDictData sysDictData) {
        sysDictDataService.updateById(sysDictData);
        return Result.success(sysDictData);
    }

    @ApiOperation("删除字典数据")
    @PostMapping("/delete")
    @SaCheckPermission("sys:dict:data:del")
    @Log(title = "字典数据管理", businessType = LogConst.BusinessType.DELETE)
    public Result<Boolean> delete(@RequestBody Map<String, Object> params) {
        @SuppressWarnings("unchecked")
        List<Object> ids = (List<Object>) params.get("ids");
        List<Long> dictDataIds = ids.stream()
                .map(id -> Long.parseLong(id.toString()))
                .collect(Collectors.toList());
        boolean result = sysDictDataService.removeByIds(dictDataIds);
        return Result.success(result);
    }
}
