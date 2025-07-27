package com.yummyerp.cloud.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yummyerp.cloud.modules.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 系统字典表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("sys_dict")
@ApiModel(value = "SysDict对象", description = "系统字典表")
public class SysDict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    @TableField("name")
    @ApiModelProperty("字典名称")
    private String name;

    /**
     * 字典编码
     */
    @TableField("code")
    @ApiModelProperty("字典编码")
    private String code;

    /**
     * 显示顺序
     */
    @TableField("sort")
    @ApiModelProperty("显示顺序")
    private Integer sort;

    /**
     * 状态：0-禁用 1-正常
     */
    @TableField("status")
    @ApiModelProperty("状态：0-禁用 1-正常")
    private Integer status;

    /**
     * 字典描述
     */
    @ApiModelProperty("字典描述")
    @TableField("description")
    private String description;

    /**
     * 创建用户ID
     */
    @ApiModelProperty("创建用户ID")
    @TableField("create_user_id")
    private Long createUserId;
}
