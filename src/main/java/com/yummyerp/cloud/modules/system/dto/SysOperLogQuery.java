package com.yummyerp.cloud.modules.system.dto;

import com.yummyerp.cloud.modules.common.dto.QueryCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日志查询条件
 *
 * @author 周欢
 * @since 2025-07-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "操作日志查询条件")
public class SysOperLogQuery extends QueryCondition {

    @ApiModelProperty(value = "操作模块")
    private String title;

    @ApiModelProperty(value = "业务类型")
    private Integer businessType;

    @ApiModelProperty(value = "操作人员")
    private String operName;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "操作IP")
    private String operIp;

    @ApiModelProperty(value = "操作地点")
    private String operLocation;

    /**
     * 检查是否有标题条件
     */
    public boolean hasTitle() {
        return title != null && !title.trim().isEmpty();
    }

    /**
     * 检查是否有业务类型条件
     */
    public boolean hasBusinessType() {
        return businessType != null;
    }

    /**
     * 检查是否有操作人员条件
     */
    public boolean hasOperName() {
        return operName != null && !operName.trim().isEmpty();
    }
}