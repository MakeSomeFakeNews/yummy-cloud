package com.yummyerp.cloud.modules.product.entity;

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
 * 产品信息表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("erp_product")
@ApiModel(value = "ErpProduct对象", description = "产品信息表")
public class ErpProduct extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 产品分类ID
     */
    @TableField("category_id")
    @ApiModelProperty("产品分类ID")
    private Long categoryId;

    /**
     * 产品编码
     */
    @TableField("code")
    @ApiModelProperty("产品编码")
    private String code;

    /**
     * 产品名称
     */
    @TableField("name")
    @ApiModelProperty("产品名称")
    private String name;

    /**
     * 产品简称
     */
    @ApiModelProperty("产品简称")
    @TableField("short_name")
    private String shortName;

    /**
     * SKU编码
     */
    @TableField("sku")
    @ApiModelProperty("SKU编码")
    private String sku;

    /**
     * 条形码
     */
    @TableField("barcode")
    @ApiModelProperty("条形码")
    private String barcode;

    /**
     * 品牌
     */
    @TableField("brand")
    @ApiModelProperty("品牌")
    private String brand;

    /**
     * 型号
     */
    @TableField("model")
    @ApiModelProperty("型号")
    private String model;

    /**
     * 规格参数
     */
    @ApiModelProperty("规格参数")
    @TableField("specification")
    private String specification;

    /**
     * 基本单位ID
     */
    @TableField("unit_id")
    @ApiModelProperty("基本单位ID")
    private Long unitId;

    /**
     * 重量（KG）
     */
    @TableField("weight")
    @ApiModelProperty("重量（KG）")
    private BigDecimal weight;

    /**
     * 体积（立方米）
     */
    @TableField("volume")
    @ApiModelProperty("体积（立方米）")
    private BigDecimal volume;

    /**
     * 采购价格
     */
    @ApiModelProperty("采购价格")
    @TableField("purchase_price")
    private BigDecimal purchasePrice;

    /**
     * 销售价格
     */
    @ApiModelProperty("销售价格")
    @TableField("sale_price")
    private BigDecimal salePrice;

    /**
     * 成本价格
     */
    @ApiModelProperty("成本价格")
    @TableField("cost_price")
    private BigDecimal costPrice;

    /**
     * 最小库存
     */
    @TableField("min_stock")
    @ApiModelProperty("最小库存")
    private BigDecimal minStock;

    /**
     * 最大库存
     */
    @TableField("max_stock")
    @ApiModelProperty("最大库存")
    private BigDecimal maxStock;

    /**
     * 安全库存
     */
    @ApiModelProperty("安全库存")
    @TableField("safety_stock")
    private BigDecimal safetyStock;

    /**
     * 采购周期（天）
     */
    @TableField("lead_time")
    @ApiModelProperty("采购周期（天）")
    private Integer leadTime;

    /**
     * 保质期（天）
     */
    @TableField("shelf_life")
    @ApiModelProperty("保质期（天）")
    private Integer shelfLife;

    /**
     * 存储条件
     */
    @ApiModelProperty("存储条件")
    @TableField("storage_condition")
    private String storageCondition;

    /**
     * 主图片URL
     */
    @TableField("main_image")
    @ApiModelProperty("主图片URL")
    private String mainImage;

    /**
     * 产品描述
     */
    @ApiModelProperty("产品描述")
    @TableField("description")
    private String description;

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
