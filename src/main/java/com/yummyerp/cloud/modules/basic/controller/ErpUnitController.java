package com.yummyerp.cloud.modules.basic.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yummyerp.cloud.annotation.Log;
import com.yummyerp.cloud.constant.LogConst;
import com.yummyerp.cloud.modules.basic.dto.ErpUnitQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpUnit;
import com.yummyerp.cloud.modules.basic.service.ErpUnitService;
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
 * 计量单位表 前端控制器
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Api(tags = "计量单位管理")
@RestController
@RequestMapping("/basic/erp-unit")
public class ErpUnitController {

    private final ErpUnitService erpUnitService;

    public ErpUnitController(ErpUnitService erpUnitService) {
        this.erpUnitService = erpUnitService;
    }

    @ApiOperation("获取计量单位分页列表")
    @GetMapping("/getList")
    @SaCheckPermission("basic:unit:list")
    @Log(title = "计量单位管理")
    public Result<PageResult<ErpUnit>> getList(
            @Valid PageRequest pageRequest,
            @Valid ErpUnitQuery query) {
        return Result.success(erpUnitService.getUnitPageList(pageRequest, query));
    }

    @ApiOperation("获取计量单位详情")
    @GetMapping("/getDetail")
    @SaCheckPermission("basic:unit:detail")
    @Log(title = "计量单位管理")
    public Result<ErpUnit> getDetail(@RequestParam Long id) {
        ErpUnit unit = erpUnitService.getById(id);
        return Result.success(unit);
    }

    @ApiOperation("新增计量单位")
    @PostMapping("/add")
    @SaCheckPermission("basic:unit:add")
    @Log(title = "计量单位管理", businessType = LogConst.BusinessType.INSERT)
    public Result<ErpUnit> add(@RequestBody ErpUnit erpUnit) {
        erpUnitService.save(erpUnit);
        return Result.success(erpUnit);
    }

    @ApiOperation("修改计量单位")
    @PostMapping("/update")
    @SaCheckPermission("basic:unit:edit")
    @Log(title = "计量单位管理", businessType = LogConst.BusinessType.UPDATE)
    public Result<ErpUnit> update(@RequestBody ErpUnit erpUnit) {
        erpUnitService.updateById(erpUnit);
        return Result.success(erpUnit);
    }

    @ApiOperation("删除计量单位")
    @PostMapping("/delete")
    @SaCheckPermission("basic:unit:del")
    @Log(title = "计量单位管理", businessType = LogConst.BusinessType.DELETE)
    public Result<Boolean> delete(@RequestBody Map<String, List<Integer>> params) {
        List<Integer> ids = params.get("ids");
        List<Long> unitIds = ids.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        boolean result = erpUnitService.removeByIds(unitIds);
        return Result.success(result);
    }

}
