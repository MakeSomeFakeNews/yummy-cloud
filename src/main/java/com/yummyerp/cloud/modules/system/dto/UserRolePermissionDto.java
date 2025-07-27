package com.yummyerp.cloud.modules.system.dto;

import lombok.Data;

/**
 * 用户角色权限信息DTO
 */
@Data
public class UserRolePermissionDto {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色编码字符串（逗号分隔）
     */
    private String roleCodes;

    /**
     * 权限编码字符串（逗号分隔）
     */
    private String permissionCodes;
}