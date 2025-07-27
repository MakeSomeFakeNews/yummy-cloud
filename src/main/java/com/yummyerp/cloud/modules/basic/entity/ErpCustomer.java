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
 * 客户信息表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("erp_customer")
@ApiModel(value = "ErpCustomer对象", description = "客户信息表")
public class ErpCustomer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 客户编码
     */
    @TableField("code")
    @ApiModelProperty("客户编码")
    private String code;

    /**
     * 客户名称
     */
    @TableField("name")
    @ApiModelProperty("客户名称")
    private String name;

    /**
     * 简称
     */
    @ApiModelProperty("简称")
    @TableField("short_name")
    private String shortName;

    /**
     * 客户类型：1-企业客户 2-个人客户
     */
    @TableField("type")
    @ApiModelProperty("客户类型：1-企业客户 2-个人客户")
    private Integer type;

    /**
     * 客户级别：1-VIP 2-普通 3-潜在
     */
    @TableField("level")
    @ApiModelProperty("客户级别：1-VIP 2-普通 3-潜在")
    private Integer level;

    /**
     * 所属行业
     */
    @TableField("industry")
    @ApiModelProperty("所属行业")
    private String industry;

    /**
     * 联系人
     */
    @ApiModelProperty("联系人")
    @TableField("contact_person")
    private String contactPerson;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    @TableField("contact_phone")
    private String contactPhone;

    /**
     * 联系邮箱
     */
    @ApiModelProperty("联系邮箱")
    @TableField("contact_email")
    private String contactEmail;

    /**
     * 地址
     */
    @TableField("address")
    @ApiModelProperty("地址")
    private String address;

    /**
     * 统一社会信用代码
     */
    @TableField("credit_code")
    @ApiModelProperty("统一社会信用代码")
    private String creditCode;

    /**
     * 信用额度
     */
    @ApiModelProperty("信用额度")
    @TableField("credit_limit")
    private BigDecimal creditLimit;

    /**
     * 信用期限（天）
     */
    @TableField("credit_days")
    @ApiModelProperty("信用期限（天）")
    private Integer creditDays;

    /**
     * 税率
     */
    @ApiModelProperty("税率")
    @TableField("tax_rate")
    private BigDecimal taxRate;

    /**
     * 付款条件
     */
    @ApiModelProperty("付款条件")
    @TableField("payment_terms")
    private String paymentTerms;

    /**
     * 交货条件
     */
    @ApiModelProperty("交货条件")
    @TableField("delivery_terms")
    private String deliveryTerms;

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
