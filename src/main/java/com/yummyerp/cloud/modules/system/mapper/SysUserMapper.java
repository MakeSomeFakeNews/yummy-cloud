package com.yummyerp.cloud.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yummyerp.cloud.modules.system.dto.UserRolePermissionDto;
import com.yummyerp.cloud.modules.system.entity.SysMenu;
import com.yummyerp.cloud.modules.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户ID查询用户角色和权限信息
     */
    UserRolePermissionDto getUserRolePermissionByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID查询用户可访问的菜单路由
     */
    List<SysMenu> getUserMenusByUserId(@Param("userId") Long userId);

}
