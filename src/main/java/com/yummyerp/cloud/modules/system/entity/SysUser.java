package com.yummyerp.cloud.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yummyerp.cloud.modules.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "系统用户表")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableField("username")
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    @TableField("nickname")
    private String nickname;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    @ApiModelProperty("真实姓名")
    private String realName;

    /**
     * 性别：1-男 2-女 3-未知
     */
    @TableField("gender")
    @ApiModelProperty("性别：1-男 2-女 3-未知")
    private Integer gender;

    /**
     * 头像URL
     */
    @TableField("avatar")
    @ApiModelProperty("头像URL")
    private String avatar;

    /**
     * 邮箱
     */
    @TableField("email")
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone")
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 生日
     */
    @ApiModelProperty("生日")
    @TableField("birthday")
    private LocalDate birthday;

    /**
     * 状态：0-禁用 1-正常
     */
    @TableField("status")
    @ApiModelProperty("状态：0-禁用 1-正常")
    private Integer status;

    /**
     * 用户类型：1-系统用户 2-普通用户
     */
    @TableField("type")
    @ApiModelProperty("用户类型：1-系统用户 2-普通用户")
    private Integer type;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    @ApiModelProperty("部门ID")
    private Long deptId;

    /**
     * 最后登录IP
     */
    @TableField("login_ip")
    @ApiModelProperty("最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @TableField("login_time")
    @ApiModelProperty("最后登录时间")
    private LocalDateTime loginTime;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
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
     * 部门名称
     */
    @TableField(exist = false)
    @ApiModelProperty("部门名称")
    private String deptName;
    
    /**
     * 创建用户名称
     */
    @TableField(exist = false)
    @ApiModelProperty("创建用户名称")
    private String createUserString;
    
    /**
     * 角色ID列表
     */
    @TableField(exist = false)
    @ApiModelProperty("角色ID列表")
    private java.util.List<String> roleIds;
    
    /**
     * 角色名称列表
     */
    @TableField(exist = false)
    @ApiModelProperty("角色名称列表")
    private java.util.List<String> roleNames;
    
    /**
     * 是否禁用（用于前端）
     */
    @TableField(exist = false)
    @ApiModelProperty("是否禁用")
    private Boolean disabled;

}
