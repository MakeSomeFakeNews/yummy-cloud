package com.yummyerp.cloud.modules.basic.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yummyerp.cloud.annotation.Log;
import com.yummyerp.cloud.constant.LogConst;
import com.yummyerp.cloud.modules.basic.dto.ErpSupplierQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpSupplier;
import com.yummyerp.cloud.modules.basic.service.ErpSupplierService;
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
 * 供应商信息表 前端控制器
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Api(tags = "供应商管理")
@RestController
@RequestMapping("/basic/erp-supplier")
public class ErpSupplierController {

    private final ErpSupplierService erpSupplierService;

    public ErpSupplierController(ErpSupplierService erpSupplierService) {
        this.erpSupplierService = erpSupplierService;
    }

    @ApiOperation("获取供应商分页列表")
    @GetMapping("/getList")
    @SaCheckPermission("basic:sup:list")
    @Log(title = "供应商管理")
    public Result<PageResult<ErpSupplier>> getList(
            @Valid PageRequest pageRequest,
            @Valid ErpSupplierQuery query) {
        return Result.success(erpSupplierService.getSupplierPageList(pageRequest, query));
    }

    @ApiOperation("获取供应商详情")
    @GetMapping("/getDetail")
    @SaCheckPermission("basic:sup:detail")
    @Log(title = "供应商管理")
    public Result<ErpSupplier> getDetail(@RequestParam Long id) {
        ErpSupplier supplier = erpSupplierService.getById(id);
        return Result.success(supplier);
    }

    @ApiOperation("新增供应商")
    @PostMapping("/add")
    @SaCheckPermission("basic:sup:add")
    @Log(title = "供应商管理", businessType = LogConst.BusinessType.INSERT)
    public Result<ErpSupplier> add(@RequestBody ErpSupplier erpSupplier) {
        erpSupplierService.save(erpSupplier);
        return Result.success(erpSupplier);
    }

    @ApiOperation("修改供应商")
    @PostMapping("/update")
    @SaCheckPermission("basic:sup:edit")
    @Log(title = "供应商管理", businessType = LogConst.BusinessType.UPDATE)
    public Result<ErpSupplier> update(@RequestBody ErpSupplier erpSupplier) {
        erpSupplierService.updateById(erpSupplier);
        return Result.success(erpSupplier);
    }

    @ApiOperation("删除供应商")
    @PostMapping("/delete")
    @SaCheckPermission("basic:sup:del")
    @Log(title = "供应商管理", businessType = LogConst.BusinessType.DELETE)
    public Result<Boolean> delete(@RequestBody Map<String, List<Integer>> params) {
        List<Integer> ids = params.get("ids");
        List<Long> supplierIds = ids.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        boolean result = erpSupplierService.removeByIds(supplierIds);
        return Result.success(result);
    }
}
