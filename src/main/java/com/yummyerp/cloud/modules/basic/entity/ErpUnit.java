package com.yummyerp.cloud.modules.basic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yummyerp.cloud.modules.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 计量单位表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("erp_unit")
@ApiModel(value = "ErpUnit对象", description = "计量单位表")
public class ErpUnit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 单位编码
     */
    @TableField("code")
    @ApiModelProperty("单位编码")
    private String code;

    /**
     * 单位名称
     */
    @TableField("name")
    @ApiModelProperty("单位名称")
    private String name;

    /**
     * 单位符号
     */
    @TableField("symbol")
    @ApiModelProperty("单位符号")
    private String symbol;

    /**
     * 单位类型：1-基本单位 2-长度 3-重量 4-体积 5-面积
     */
    @TableField("type")
    @ApiModelProperty("单位类型：1-基本单位 2-长度 3-重量 4-体积 5-面积")
    private Integer type;

    /**
     * 基础单位ID
     */
    @ApiModelProperty("基础单位ID")
    @TableField("base_unit_id")
    private Long baseUnitId;

    /**
     * 转换比率
     */
    @ApiModelProperty("转换比率")
    @TableField("conversion_rate")
    private BigDecimal conversionRate;

    /**
     * 状态：0-禁用 1-正常
     */
    @TableField("status")
    @ApiModelProperty("状态：0-禁用 1-正常")
    private Integer status;

    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    @TableField("create_user_id")
    private Long createUserId;
}
