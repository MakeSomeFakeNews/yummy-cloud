package com.yummyerp.cloud.modules.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 通用分页请求对象
 *
 * @author 周欢
 * @since 2025-07-28
 */
@Data
@ApiModel(description = "分页请求参数")
public class PageRequest {

    @ApiModelProperty(value = "页码", required = true, example = "1")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码必须大于0")
    private Integer page = 1;

    @ApiModelProperty(value = "每页大小", required = true, example = "10")
    @NotNull(message = "每页大小不能为空")
    @Min(value = 1, message = "每页大小必须大于0")
    @Max(value = 500, message = "每页大小不能超过500")
    private Integer size = 10;

    @ApiModelProperty(value = "排序字段")
    private String sortField;

    @ApiModelProperty(value = "排序方向（asc/desc）", example = "desc")
    private String sortOrder = "desc";

    /**
     * 获取 MyBatis Plus 的当前页码
     * MyBatis Plus 页码从1开始
     */
    public long getCurrent() {
        return this.page;
    }

    /**
     * 获取 MyBatis Plus 的每页大小
     */
    public long getSize() {
        return this.size;
    }

    /**
     * 是否降序排序
     */
    public boolean isDesc() {
        return "desc".equalsIgnoreCase(this.sortOrder);
    }

    /**
     * 是否升序排序
     */
    public boolean isAsc() {
        return "asc".equalsIgnoreCase(this.sortOrder);
    }
}