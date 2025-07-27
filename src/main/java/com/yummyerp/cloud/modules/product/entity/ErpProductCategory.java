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
 * 产品分类表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("erp_product_category")
@ApiModel(value = "ErpProductCategory对象", description = "产品分类表")
public class ErpProductCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父分类ID
     */
    @TableField("parent_id")
    @ApiModelProperty("父分类ID")
    private Long parentId;

    /**
     * 祖级列表
     */
    @TableField("ancestors")
    @ApiModelProperty("祖级列表")
    private String ancestors;

    /**
     * 分类编码
     */
    @TableField("code")
    @ApiModelProperty("分类编码")
    private String code;

    /**
     * 分类名称
     */
    @TableField("name")
    @ApiModelProperty("分类名称")
    private String name;

    /**
     * 显示顺序
     */
    @TableField("sort")
    @ApiModelProperty("显示顺序")
    private Integer sort;

    /**
     * 图标
     */
    @TableField("icon")
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 状态：0-禁用 1-正常
     */
    @TableField("status")
    @ApiModelProperty("状态：0-禁用 1-正常")
    private Integer status;

    /**
     * 备注
     */
    @TableField("remark")
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    @TableField("create_user_id")
    private Long createUserId;
}
