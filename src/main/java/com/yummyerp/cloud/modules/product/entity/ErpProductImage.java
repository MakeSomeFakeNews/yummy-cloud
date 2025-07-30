package com.yummyerp.cloud.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yummyerp.cloud.modules.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 产品图片表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("erp_product_image")
@ApiModel(value = "ErpProductImage对象", description = "产品图片表")
public class ErpProductImage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 产品ID
     */
    @ApiModelProperty("产品ID")
    @TableField("product_id")
    private Long productId;

    /**
     * 变体ID
     */
    @ApiModelProperty("变体ID")
    @TableField("variant_id")
    private Long variantId;

    /**
     * 图片URL
     */
    @TableField("image_url")
    @ApiModelProperty("图片URL")
    private String imageUrl;

    /**
     * 图片名称
     */
    @ApiModelProperty("图片名称")
    @TableField("image_name")
    private String imageName;

    /**
     * 图片类型：1-主图 2-详情图 3-规格图
     */
    @TableField("image_type")
    @ApiModelProperty("图片类型：1-主图 2-详情图 3-规格图")
    private Integer imageType;

    /**
     * 排序
     */
    @TableField("sort")
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 状态：0-禁用 1-正常
     */
    @TableField("status")
    @ApiModelProperty("状态：0-禁用 1-正常")
    private Integer status;

    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人")
    @TableField("create_user")
    private String createUser;
}
