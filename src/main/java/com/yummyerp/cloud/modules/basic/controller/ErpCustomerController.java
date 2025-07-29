package com.yummyerp.cloud.modules.basic.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yummyerp.cloud.annotation.Log;
import com.yummyerp.cloud.constant.LogConst;
import com.yummyerp.cloud.modules.basic.dto.ErpCustomerQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpCustomer;
import com.yummyerp.cloud.modules.basic.service.ErpCustomerService;
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
 * 客户信息表 前端控制器
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Api(tags = "客户管理")
@RestController
@RequestMapping("/basic/erp-customer")
public class ErpCustomerController {

    private final ErpCustomerService erpCustomerService;

    public ErpCustomerController(ErpCustomerService erpCustomerService) {
        this.erpCustomerService = erpCustomerService;
    }

    @ApiOperation("获取客户分页列表")
    @GetMapping("/getList")
    @SaCheckPermission("basic:cus:list")
    @Log(title = "客户管理")
    public Result<PageResult<ErpCustomer>> getList(
            @Valid PageRequest pageRequest,
            @Valid ErpCustomerQuery query) {
        return Result.success(erpCustomerService.getCustomerPageList(pageRequest, query));
    }

    @ApiOperation("获取客户详情")
    @GetMapping("/getDetail")
    @SaCheckPermission("basic:cus:detail")
    @Log(title = "客户管理")
    public Result<ErpCustomer> getDetail(@RequestParam Long id) {
        ErpCustomer customer = erpCustomerService.getById(id);
        return Result.success(customer);
    }

    @ApiOperation("新增客户")
    @PostMapping("/add")
    @SaCheckPermission("basic:cus:add")
    @Log(title = "客户管理", businessType = LogConst.BusinessType.INSERT)
    public Result<ErpCustomer> add(@RequestBody ErpCustomer erpCustomer) {
        erpCustomerService.save(erpCustomer);
        return Result.success(erpCustomer);
    }

    @ApiOperation("修改客户")
    @PostMapping("/update")
    @SaCheckPermission("basic:cus:edit")
    @Log(title = "客户管理", businessType = LogConst.BusinessType.UPDATE)
    public Result<ErpCustomer> update(@RequestBody ErpCustomer erpCustomer) {
        erpCustomerService.updateById(erpCustomer);
        return Result.success(erpCustomer);
    }

    @ApiOperation("删除客户")
    @PostMapping("/delete")
    @SaCheckPermission("basic:cus:del")
    @Log(title = "客户管理", businessType = LogConst.BusinessType.DELETE)
    public Result<Boolean> delete(@RequestBody Map<String, List<Integer>> params) {
        List<Integer> ids = params.get("ids");
        List<Long> customerIds = ids.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        boolean result = erpCustomerService.removeByIds(customerIds);
        return Result.success(result);
    }
}
