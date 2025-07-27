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
import java.time.LocalDate;

/**
 * <p>
 * 供应商评价
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("erp_supplier_evaluation")
@ApiModel(value = "ErpSupplierEvaluation对象", description = "供应商评价")
public class ErpSupplierEvaluation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商ID
     */
    @ApiModelProperty("供应商ID")
    @TableField("supplier_id")
    private Long supplierId;

    /**
     * 评价日期
     */
    @ApiModelProperty("评价日期")
    @TableField("evaluation_date")
    private LocalDate evaluationDate;

    /**
     * 评价人ID
     */
    @ApiModelProperty("评价人ID")
    @TableField("evaluator_id")
    private Long evaluatorId;

    /**
     * 质量评分（0-100）
     */
    @TableField("quality_score")
    @ApiModelProperty("质量评分（0-100）")
    private Integer qualityScore;

    /**
     * 交期评分（0-100）
     */
    @TableField("delivery_score")
    @ApiModelProperty("交期评分（0-100）")
    private Integer deliveryScore;

    /**
     * 服务评分（0-100）
     */
    @TableField("service_score")
    @ApiModelProperty("服务评分（0-100）")
    private Integer serviceScore;

    /**
     * 价格评分（0-100）
     */
    @TableField("price_score")
    @ApiModelProperty("价格评分（0-100）")
    private Integer priceScore;

    /**
     * 综合评分
     */
    @ApiModelProperty("综合评分")
    @TableField("total_score")
    private BigDecimal totalScore;

    /**
     * 评价周期（如：2025Q1）
     */
    @TableField("evaluation_period")
    @ApiModelProperty("评价周期（如：2025Q1）")
    private String evaluationPeriod;

    /**
     * 评价意见
     */
    @TableField("comments")
    @ApiModelProperty("评价意见")
    private String comments;

    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    @TableField("create_user_id")
    private Long createUserId;
}
