package com.yummyerp.cloud.modules.basic.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.springframework.util.StringUtils;
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
    @SaCheckPermission("basic:customer:list")
    @Log(title = "客户管理")
    public Result<PageResult<ErpCustomer>> getList(
            @Valid PageRequest pageRequest,
            @Valid ErpCustomerQuery query) {

        LambdaQueryWrapper<ErpCustomer> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(query.getCode())) {
            wrapper.like(ErpCustomer::getCode, query.getCode());
        }
        if (StringUtils.hasText(query.getName())) {
            wrapper.like(ErpCustomer::getName, query.getName());
        }
        if (query.getType() != null) {
            wrapper.eq(ErpCustomer::getType, query.getType());
        }
        if (query.getLevel() != null) {
            wrapper.eq(ErpCustomer::getLevel, query.getLevel());
        }
        if (StringUtils.hasText(query.getContactPerson())) {
            wrapper.like(ErpCustomer::getContactPerson, query.getContactPerson());
        }
        if (StringUtils.hasText(query.getContactPhone())) {
            wrapper.like(ErpCustomer::getContactPhone, query.getContactPhone());
        }
        if (query.getStatus() != null) {
            wrapper.eq(ErpCustomer::getStatus, query.getStatus());
        }
        if (StringUtils.hasText(query.getIndustry())) {
            wrapper.like(ErpCustomer::getIndustry, query.getIndustry());
        }

        wrapper.orderByDesc(ErpCustomer::getCreateTime);

        Page<ErpCustomer> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        Page<ErpCustomer> result = erpCustomerService.page(page, wrapper);

        return Result.success(PageResult.of(result));
    }

    @ApiOperation("获取客户详情")
    @GetMapping("/getDetail")
    @SaCheckPermission("basic:customer:detail")
    @Log(title = "客户管理")
    public Result<ErpCustomer> getDetail(@RequestParam Long id) {
        ErpCustomer customer = erpCustomerService.getById(id);
        return Result.success(customer);
    }

    @ApiOperation("新增客户")
    @PostMapping("/add")
    @SaCheckPermission("basic:customer:add")
    @Log(title = "客户管理", businessType = LogConst.BusinessType.INSERT)
    public Result<ErpCustomer> add(@RequestBody ErpCustomer erpCustomer) {
        erpCustomerService.save(erpCustomer);
        return Result.success(erpCustomer);
    }

    @ApiOperation("修改客户")
    @PostMapping("/update")
    @SaCheckPermission("basic:customer:edit")
    @Log(title = "客户管理", businessType = LogConst.BusinessType.UPDATE)
    public Result<ErpCustomer> update(@RequestBody ErpCustomer erpCustomer) {
        erpCustomerService.updateById(erpCustomer);
        return Result.success(erpCustomer);
    }

    @ApiOperation("删除客户")
    @PostMapping("/delete")
    @SaCheckPermission("basic:customer:del")
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
