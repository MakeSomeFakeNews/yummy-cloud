package com.yummyerp.cloud.modules.basic.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yummyerp.cloud.annotation.Log;
import com.yummyerp.cloud.constant.LogConst;
import com.yummyerp.cloud.modules.basic.dto.ErpWarehouseQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpWarehouse;
import com.yummyerp.cloud.modules.basic.service.ErpWarehouseService;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;
import com.yummyerp.cloud.modules.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 仓库信息表 前端控制器
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Api(tags = "仓库管理")
@RestController
@RequestMapping("/basic/erp-warehouse")
public class ErpWarehouseController {

    private final ErpWarehouseService erpWarehouseService;

    public ErpWarehouseController(ErpWarehouseService erpWarehouseService) {
        this.erpWarehouseService = erpWarehouseService;
    }

    @ApiOperation("获取仓库分页列表")
    @GetMapping("/getList")
    @SaCheckPermission("basic:wh:list")
    @Log(title = "仓库管理")
    public Result<PageResult<ErpWarehouse>> getList(
            @Valid PageRequest pageRequest,
            @Valid ErpWarehouseQuery query) {
        return Result.success(erpWarehouseService.getWarehousePageList(pageRequest, query));
    }

    @ApiOperation("获取仓库详情")
    @GetMapping("/getDetail")
    @SaCheckPermission("basic:wh:detail")
    @Log(title = "仓库管理")
    public Result<ErpWarehouse> getDetail(@RequestParam Long id) {
        ErpWarehouse warehouse = erpWarehouseService.getById(id);
        return Result.success(warehouse);
    }

    @ApiOperation("新增仓库")
    @PostMapping("/add")
    @SaCheckPermission("basic:wh:add")
    @Log(title = "仓库管理", businessType = LogConst.BusinessType.INSERT)
    public Result<ErpWarehouse> add(@RequestBody ErpWarehouse erpWarehouse) {
        erpWarehouseService.save(erpWarehouse);
        return Result.success(erpWarehouse);
    }

    @ApiOperation("修改仓库")
    @PostMapping("/update")
    @SaCheckPermission("basic:wh:edit")
    @Log(title = "仓库管理", businessType = LogConst.BusinessType.UPDATE)
    public Result<ErpWarehouse> update(@RequestBody ErpWarehouse erpWarehouse) {
        erpWarehouseService.updateById(erpWarehouse);
        return Result.success(erpWarehouse);
    }

    @ApiOperation("删除仓库")
    @PostMapping("/delete")
    @SaCheckPermission("basic:wh:del")
    @Log(title = "仓库管理", businessType = LogConst.BusinessType.DELETE)
    public Result<Boolean> delete(@RequestBody Map<String, List<Integer>> params) {
        List<Integer> ids = params.get("ids");
        List<Long> warehouseIds = ids.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        boolean result = erpWarehouseService.removeByIds(warehouseIds);
        return Result.success(result);
    }
}
