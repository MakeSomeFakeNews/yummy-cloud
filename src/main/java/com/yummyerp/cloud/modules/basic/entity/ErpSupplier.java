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
 * 供应商信息表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("erp_supplier")
@ApiModel(value = "ErpSupplier对象", description = "供应商信息表")
public class ErpSupplier extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商编码
     */
    @TableField("code")
    @ApiModelProperty("供应商编码")
    private String code;

    /**
     * 供应商名称
     */
    @TableField("name")
    @ApiModelProperty("供应商名称")
    private String name;

    /**
     * 简称
     */
    @ApiModelProperty("简称")
    @TableField("short_name")
    private String shortName;

    /**
     * 供应商类型：1-生产商 2-贸易商 3-服务商
     */
    @TableField("type")
    @ApiModelProperty("供应商类型：1-生产商 2-贸易商 3-服务商")
    private Integer type;

    /**
     * 供应商级别：1-A级 2-B级 3-C级
     */
    @TableField("level")
    @ApiModelProperty("供应商级别：1-A级 2-B级 3-C级")
    private Integer level;

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
     * 开户银行
     */
    @TableField("bank_name")
    @ApiModelProperty("开户银行")
    private String bankName;

    /**
     * 银行账户
     */
    @ApiModelProperty("银行账户")
    @TableField("bank_account")
    private String bankAccount;

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
    @ApiModelProperty("创建人")
    @TableField("create_user")
    private String createUser;
}
