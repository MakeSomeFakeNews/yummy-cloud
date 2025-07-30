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
 * 系统菜单表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("sys_menu")
@ApiModel(value = "SysMenu对象", description = "系统菜单表")
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父菜单ID
     */
    @TableField("parent_id")
    @ApiModelProperty("父菜单ID")
    private Long parentId;

    /**
     * 路由地址
     */
    @TableField("path")
    @ApiModelProperty("路由地址")
    private String path;

    /**
     * 组件路径
     */
    @TableField("component")
    @ApiModelProperty("组件路径")
    private String component;

    /**
     * 重定向地址
     */
    @TableField("redirect")
    @ApiModelProperty("重定向地址")
    private String redirect;

    /**
     * 菜单类型：1-目录 2-菜单 3-按钮
     */
    @TableField("type")
    @ApiModelProperty("菜单类型：1-目录 2-菜单 3-按钮")
    private Integer type;

    /**
     * 菜单名称
     */
    @TableField("title")
    @ApiModelProperty("菜单名称")
    private String title;

    /**
     * SVG图标
     */
    @TableField("svg_icon")
    @ApiModelProperty("SVG图标")
    private String svgIcon;

    /**
     * 菜单图标
     */
    @TableField("icon")
    @ApiModelProperty("菜单图标")
    private String icon;

    /**
     * 是否缓存：0-不缓存 1-缓存
     */
    @TableField("keep_alive")
    @ApiModelProperty("是否缓存：0-不缓存 1-缓存")
    private Boolean keepAlive;

    /**
     * 是否隐藏：0-显示 1-隐藏
     */
    @TableField("hidden")
    @ApiModelProperty("是否隐藏：0-显示 1-隐藏")
    private Boolean hidden;

    /**
     * 显示顺序
     */
    @TableField("sort")
    @ApiModelProperty("显示顺序")
    private Integer sort;

    /**
     * 当前激活的菜单
     */
    @TableField("active_menu")
    @ApiModelProperty("当前激活的菜单")
    private String activeMenu;

    /**
     * 是否显示面包屑：0-不显示 1-显示
     */
    @TableField("breadcrumb")
    @ApiModelProperty("是否显示面包屑：0-不显示 1-显示")
    private Boolean breadcrumb;

    /**
     * 状态：0-禁用 1-正常
     */
    @TableField("status")
    @ApiModelProperty("状态：0-禁用 1-正常")
    private Integer status;

    /**
     * 权限标识
     */
    @ApiModelProperty("权限标识")
    @TableField("permission")
    private String permission;

    /**
     * 是否显示在标签页：0-不显示 1-显示
     */
    @TableField("show_in_tabs")
    @ApiModelProperty("是否显示在标签页：0-不显示 1-显示")
    private Boolean showInTabs;

    /**
     * 是否总是显示：0-不显示 1-显示
     */
    @TableField("always_show")
    @ApiModelProperty("是否总是显示：0-不显示 1-显示")
    private Boolean alwaysShow;

    /**
     * 是否固定在标签页：0-不固定 1-固定
     */
    @TableField("affix")
    @ApiModelProperty("是否固定在标签页：0-不固定 1-固定")
    private Boolean affix;

    /**
     * 创建用户ID
     */
    @ApiModelProperty("创建人")
    @TableField("create_user")
    private String createUser;

    /**
     * 角色编码字符串（逗号分隔，非数据库字段）
     */
    @Getter
    @TableField(exist = false)
    @ApiModelProperty("角色编码字符串")
    private String roleCodes;

    /**
     * 子菜单列表（非数据库字段）
     */
    @Setter
    @Getter
    @TableField(exist = false)
    @ApiModelProperty("子菜单列表")
    private java.util.List<SysMenu> children;


}
