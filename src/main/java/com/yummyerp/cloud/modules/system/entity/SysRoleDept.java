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
 * 角色部门关联表
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Getter
@Setter
@ToString
@TableName("sys_role_dept")
@ApiModel(value = "SysRoleDept对象", description = "角色部门关联表")
public class SysRoleDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableField("role_id")
    @ApiModelProperty("角色ID")
    private Long roleId;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    @ApiModelProperty("部门ID")
    private Long deptId;
}
