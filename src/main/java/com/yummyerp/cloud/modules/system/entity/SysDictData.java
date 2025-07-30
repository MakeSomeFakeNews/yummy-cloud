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
 * 系统字典数据表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("sys_dict_data")
@ApiModel(value = "SysDictData对象", description = "系统字典数据表")
public class SysDictData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    @TableField("dict_id")
    @ApiModelProperty("字典ID")
    private Long dictId;

    /**
     * 字典编码（非数据库字段，用于前端传递）
     */
    @TableField("dict_code")
    @ApiModelProperty("字典编码")
    private String dictCode;

    /**
     * 字典标签
     */
    @TableField("name")
    @ApiModelProperty("字典标签")
    private String name;

    /**
     * 字典键值
     */
    @TableField("value")
    @ApiModelProperty("字典键值")
    private String value;

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
     * 标签颜色
     */
    @TableField("color")
    @ApiModelProperty("标签颜色")
    private String color;

    /**
     * CSS类名
     */
    @TableField("css_class")
    @ApiModelProperty("CSS类名")
    private String cssClass;

    /**
     * 表格回显样式
     */
    @TableField("list_class")
    @ApiModelProperty("表格回显样式")
    private String listClass;

    /**
     * 是否默认：0-否 1-是
     */
    @TableField("is_default")
    @ApiModelProperty("是否默认：0-否 1-是")
    private Integer isDefault;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @TableField("description")
    private String description;

    /**
     * 创建用户ID
     */
    @ApiModelProperty("创建人")
    @TableField("create_user")
    private String createUser;
}
