package com.yummyerp.cloud.modules.basic.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yummyerp.cloud.annotation.Log;
import com.yummyerp.cloud.constant.LogConst;
import com.yummyerp.cloud.modules.basic.dto.ErpWarehouseLocationQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpWarehouseLocation;
import com.yummyerp.cloud.modules.basic.service.ErpWarehouseLocationService;
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
 * 库位信息表 前端控制器
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Api(tags = "库位管理")
@RestController
@RequestMapping("/basic/erp-warehouse-location")
public class ErpWarehouseLocationController {

    private final ErpWarehouseLocationService erpWarehouseLocationService;

    public ErpWarehouseLocationController(ErpWarehouseLocationService erpWarehouseLocationService) {
        this.erpWarehouseLocationService = erpWarehouseLocationService;
    }

    @ApiOperation("获取库位分页列表")
    @GetMapping("/getList")
    @SaCheckPermission("basic:loc:list")
    @Log(title = "库位管理")
    public Result<PageResult<ErpWarehouseLocation>> getList(
            @Valid PageRequest pageRequest,
            @Valid ErpWarehouseLocationQuery query) {
        return Result.success(erpWarehouseLocationService.getWarehouseLocationPageList(pageRequest, query));
    }

    @ApiOperation("获取库位详情")
    @GetMapping("/getDetail")
    @SaCheckPermission("basic:loc:detail")
    @Log(title = "库位管理")
    public Result<ErpWarehouseLocation> getDetail(@RequestParam Long id) {
        ErpWarehouseLocation warehouseLocation = erpWarehouseLocationService.getById(id);
        return Result.success(warehouseLocation);
    }

    @ApiOperation("新增库位")
    @PostMapping("/add")
    @SaCheckPermission("basic:loc:add")
    @Log(title = "库位管理", businessType = LogConst.BusinessType.INSERT)
    public Result<ErpWarehouseLocation> add(@RequestBody ErpWarehouseLocation erpWarehouseLocation) {
        erpWarehouseLocationService.save(erpWarehouseLocation);
        return Result.success(erpWarehouseLocation);
    }

    @ApiOperation("修改库位")
    @PostMapping("/update")
    @SaCheckPermission("basic:loc:edit")
    @Log(title = "库位管理", businessType = LogConst.BusinessType.UPDATE)
    public Result<ErpWarehouseLocation> update(@RequestBody ErpWarehouseLocation erpWarehouseLocation) {
        erpWarehouseLocationService.updateById(erpWarehouseLocation);
        return Result.success(erpWarehouseLocation);
    }

    @ApiOperation("删除库位")
    @PostMapping("/delete")
    @SaCheckPermission("basic:loc:del")
    @Log(title = "库位管理", businessType = LogConst.BusinessType.DELETE)
    public Result<Boolean> delete(@RequestBody Map<String, List<Integer>> params) {
        List<Integer> ids = params.get("ids");
        List<Long> locationIds = ids.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        boolean result = erpWarehouseLocationService.removeByIds(locationIds);
        return Result.success(result);
    }
}
