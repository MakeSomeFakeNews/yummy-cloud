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
 * 产品变体表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("erp_product_variant")
@ApiModel(value = "ErpProductVariant对象", description = "产品变体表")
public class ErpProductVariant extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 产品ID
     */
    @ApiModelProperty("产品ID")
    @TableField("product_id")
    private Long productId;

    /**
     * 变体编码
     */
    @TableField("code")
    @ApiModelProperty("变体编码")
    private String code;

    /**
     * 变体名称
     */
    @TableField("name")
    @ApiModelProperty("变体名称")
    private String name;

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
     * 变体属性（颜色、尺寸等）
     */
    @TableField("attributes")
    @ApiModelProperty("变体属性（颜色、尺寸等）")
    private String attributes;

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
     * 重量（KG）
     */
    @TableField("weight")
    @ApiModelProperty("重量（KG）")
    private BigDecimal weight;

    /**
     * 变体图片URL
     */
    @TableField("image")
    @ApiModelProperty("变体图片URL")
    private String image;

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
