package com.yummyerp.cloud.modules.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单树形结构响应DTO
 */
@Data
@ApiModel("菜单树形结构响应")
public class MenuTreeResponse {

    @ApiModelProperty("菜单ID")
    private String id;

    @ApiModelProperty("父菜单ID")
    private String parentId;

    @ApiModelProperty("路由地址")
    private String path;

    @ApiModelProperty("组件路径")
    private String component;

    @ApiModelProperty("重定向地址")
    private String redirect;

    @ApiModelProperty("菜单类型：1-目录 2-菜单 3-按钮")
    private Integer type;

    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("SVG图标")
    private String svgIcon;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("是否缓存")
    private Boolean keepAlive;

    @ApiModelProperty("是否隐藏")
    private Boolean hidden;

    @ApiModelProperty("显示顺序")
    private Integer sort;

    @ApiModelProperty("当前激活的菜单")
    private String activeMenu;

    @ApiModelProperty("是否显示面包屑")
    private Boolean breadcrumb;

    @ApiModelProperty("状态：0-禁用 1-正常")
    private Integer status;

    @ApiModelProperty("角色列表")
    private List<String> roles;

    @ApiModelProperty("权限标识")
    private String permission;

    @ApiModelProperty("是否显示在标签页")
    private Boolean showInTabs;

    @ApiModelProperty("是否固定在标签页")
    private Boolean affix;

    @ApiModelProperty("是否总是显示")
    private Boolean alwaysShow;

    @ApiModelProperty("子菜单")
    private List<MenuTreeResponse> children;
}