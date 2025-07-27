package com.yummyerp.cloud.modules.basic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yummyerp.cloud.modules.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * <p>
 * 仓库信息表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("erp_warehouse")
@ApiModel(value = "ErpWarehouse对象", description = "仓库信息表")
public class ErpWarehouse extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库编码
     */
    @TableField("code")
    @ApiModelProperty("仓库编码")
    private String code;

    /**
     * 仓库名称
     */
    @TableField("name")
    @ApiModelProperty("仓库名称")
    private String name;

    /**
     * 仓库类型：1-原料仓 2-成品仓 3-半成品仓 4-次品仓
     */
    @TableField("type")
    @ApiModelProperty("仓库类型：1-原料仓 2-成品仓 3-半成品仓 4-次品仓")
    private Integer type;

    /**
     * 仓库地址
     */
    @TableField("address")
    @ApiModelProperty("仓库地址")
    private String address;

    /**
     * 仓库管理员
     */
    @TableField("manager")
    @ApiModelProperty("仓库管理员")
    private String manager;

    /**
     * 联系电话
     */
    @TableField("phone")
    @ApiModelProperty("联系电话")
    private String phone;

    /**
     * 仓库面积（平方米）
     */
    @TableField("area")
    @ApiModelProperty("仓库面积（平方米）")
    private BigDecimal area;

    /**
     * 存储容量
     */
    @TableField("capacity")
    @ApiModelProperty("存储容量")
    private BigDecimal capacity;

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
