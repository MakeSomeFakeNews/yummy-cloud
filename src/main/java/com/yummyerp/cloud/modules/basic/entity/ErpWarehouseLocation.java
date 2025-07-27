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
 * 库位信息表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("erp_warehouse_location")
@ApiModel(value = "ErpWarehouseLocation对象", description = "库位信息表")
public class ErpWarehouseLocation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库ID
     */
    @ApiModelProperty("仓库ID")
    @TableField("warehouse_id")
    private Long warehouseId;

    /**
     * 库位编码
     */
    @TableField("code")
    @ApiModelProperty("库位编码")
    private String code;

    /**
     * 库位名称
     */
    @TableField("name")
    @ApiModelProperty("库位名称")
    private String name;

    /**
     * 库位类型：1-普通库位 2-冷藏库位 3-危险品库位
     */
    @TableField("type")
    @ApiModelProperty("库位类型：1-普通库位 2-冷藏库位 3-危险品库位")
    private Integer type;

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
