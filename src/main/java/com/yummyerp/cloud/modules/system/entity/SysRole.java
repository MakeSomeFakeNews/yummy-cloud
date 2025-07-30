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
 * 系统角色表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("sys_role")
@ApiModel(value = "SysRole对象", description = "系统角色表")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @TableField("name")
    @ApiModelProperty("角色名称")
    private String name;

    /**
     * 角色编码
     */
    @TableField("code")
    @ApiModelProperty("角色编码")
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
     * 角色类型：1-系统角色 2-普通角色
     */
    @TableField("type")
    @ApiModelProperty("角色类型：1-系统角色 2-普通角色")
    private Integer type;

    /**
     * 数据范围：1-全部数据权限 2-自定数据权限 3-本部门数据权限 4-本部门及以下数据权限 5-仅本人数据权限
     */
    @TableField("data_scope")
    @ApiModelProperty("数据范围：1-全部数据权限 2-自定数据权限 3-本部门数据权限 4-本部门及以下数据权限 5-仅本人数据权限")
    private Integer dataScope;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    @TableField("description")
    private String description;

    /**
     * 创建用户ID
     */
    @ApiModelProperty("创建人")
    @TableField("create_user")
    private String createUser;

    // 以下为非数据库字段，用于前端显示
    
    /**
     * 创建用户名称
     */
    @TableField(exist = false)
    @ApiModelProperty("创建用户名称")
    private String createUserString;
    
    /**
     * 是否禁用（用于前端）
     */
    @TableField(exist = false)
    @ApiModelProperty("是否禁用")
    private Boolean disabled;
}
