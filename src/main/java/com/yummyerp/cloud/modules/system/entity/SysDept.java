package com.yummyerp.cloud.modules.system.entity;

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
 * 系统部门表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("sys_dept")
@ApiModel(value = "SysDept对象", description = "系统部门表")
public class SysDept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父部门ID
     */
    @TableField("parent_id")
    @ApiModelProperty("父部门ID")
    private Long parentId;

    /**
     * 祖级列表
     */
    @TableField("ancestors")
    @ApiModelProperty("祖级列表")
    private String ancestors;

    /**
     * 部门名称
     */
    @TableField("name")
    @ApiModelProperty("部门名称")
    private String name;

    /**
     * 显示顺序
     */
    @TableField("sort")
    @ApiModelProperty("显示顺序")
    private Integer sort;

    /**
     * 负责人
     */
    @TableField("leader")
    @ApiModelProperty("负责人")
    private String leader;

    /**
     * 联系电话
     */
    @TableField("phone")
    @ApiModelProperty("联系电话")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 状态：0-禁用 1-正常
     */
    @TableField("status")
    @ApiModelProperty("状态：0-禁用 1-正常")
    private Integer status;

    /**
     * 部门描述
     */
    @ApiModelProperty("部门描述")
    @TableField("description")
    private String description;

    /**
     * 创建用户ID
     */
    @ApiModelProperty("创建用户ID")
    @TableField("create_user_id")
    private Long createUserId;

    // 以下为非数据库字段，用于前端显示
    
    /**
     * 子部门列表
     */
    @TableField(exist = false)
    @ApiModelProperty("子部门列表")
    private java.util.List<SysDept> children;
}
