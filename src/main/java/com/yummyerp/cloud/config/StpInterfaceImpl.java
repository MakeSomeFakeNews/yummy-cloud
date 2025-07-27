package com.yummyerp.cloud.config;

import cn.dev33.satoken.stp.StpInterface;
import com.yummyerp.cloud.modules.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        try {
            Long userId = Long.parseLong(loginId.toString());
            return sysRoleService.getUserPermissions(userId);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        try {
            Long userId = Long.parseLong(loginId.toString());
            return sysRoleService.getUserRoles(userId);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}