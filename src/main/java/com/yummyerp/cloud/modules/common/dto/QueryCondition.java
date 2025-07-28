package com.yummyerp.cloud.modules.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 通用查询条件基础类
 *
 * @author 周欢
 * @since 2025-07-28
 */
@Data
@ApiModel(description = "查询条件基类")
public class QueryCondition {

    @ApiModelProperty(value = "关键词搜索", example = "用户管理")
    private String keyword;

    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "开始时间", example = "2025-01-01")
    private Date startTime;

    @ApiModelProperty(value = "结束时间", example = "2025-12-31")
    private Date endTime;

    @ApiModelProperty(value = "创建人ID")
    private Long createUserId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "ID列表")
    private List<Long> ids;

    @ApiModelProperty(value = "排除的ID列表")
    private List<Long> excludeIds;

    /**
     * 检查时间范围是否有效
     */
    public boolean hasValidTimeRange() {
        return startTime != null && endTime != null;
    }

    /**
     * 检查是否有关键词
     */
    public boolean hasKeyword() {
        return keyword != null && !keyword.trim().isEmpty();
    }

    /**
     * 检查是否有状态条件
     */
    public boolean hasStatus() {
        return status != null;
    }

    /**
     * 检查是否有ID列表
     */
    public boolean hasIds() {
        return ids != null && !ids.isEmpty();
    }

    /**
     * 检查是否有排除ID列表
     */
    public boolean hasExcludeIds() {
        return excludeIds != null && !excludeIds.isEmpty();
    }
}