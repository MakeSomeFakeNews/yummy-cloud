-- 用户角色关联表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 角色菜单关联表
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_menu` (`role_id`, `menu_id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- 角色部门关联表（数据权限）
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_dept` (`role_id`, `dept_id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_dept_id` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色部门关联表';

-- 插入用户角色关联数据
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
(1, 1), -- admin用户 -> 超级管理员角色
(2, 2); -- user用户 -> 普通用户角色

-- 插入角色菜单关联数据
-- 超级管理员拥有所有菜单权限
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
-- 超级管理员角色的菜单权限（所有菜单）
(1, 1), (1, 101), -- 分析页
(1, 2), (1, 201), (1, 202), (1, 203), -- 数据管理
(1, 3), (1, 301), (1, 302), -- 文件管理
(1, 4), (1, 401), (1, 402), (1, 403), (1, 404), -- 表单管理
(1, 5), (1, 501), (1, 502), (1, 503), -- 表格管理
(1, 6), (1, 601), (1, 602), (1, 603), -- 错误页
(1, 7), (1, 701), (1, 702), -- 结果页
(1, 8), (1, 801), (1, 802), (1, 803), (1, 804), (1, 805), (1, 806), -- 系统管理
(1, 81), (1, 8101), (1, 8102), (1, 810201), (1, 810202), (1, 810203), (1, 810204), (1, 810205), (1, 810206), -- 权限测试
(1, 9), (1, 901), -- 示例页

-- 普通用户角色的菜单权限（除系统管理外的菜单）
(2, 1), (2, 101), -- 分析页
(2, 2), (2, 201), (2, 202), (2, 203), -- 数据管理
(2, 3), (2, 301), (2, 302), -- 文件管理
(2, 4), (2, 401), (2, 402), (2, 403), (2, 404), -- 表单管理
(2, 5), (2, 501), (2, 502), (2, 503), -- 表格管理
(2, 6), (2, 601), (2, 602), (2, 603), -- 错误页
(2, 7), (2, 701), (2, 702), -- 结果页
(2, 81), (2, 8102), (2, 810201), (2, 810202), (2, 810203), -- 权限测试（部分按钮权限）
(2, 9), (2, 901); -- 示例页

-- 插入角色部门关联数据（数据权限范围）
-- 这里可以根据具体业务需求配置不同角色的数据权限范围
-- 例如：某个角色只能查看特定部门的数据
-- INSERT INTO `sys_role_dept` (`role_id`, `dept_id`) VALUES
-- (角色ID, 部门ID);