-- 系统角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `code` varchar(50) NOT NULL COMMENT '角色编码',
  `sort` int(11) DEFAULT 0 COMMENT '显示顺序',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `type` tinyint(1) DEFAULT 2 COMMENT '角色类型：1-系统角色 2-普通角色',
  `data_scope` tinyint(1) DEFAULT 1 COMMENT '数据范围：1-全部数据权限 2-自定数据权限 3-本部门数据权限 4-本部门及以下数据权限 5-仅本人数据权限',
  `description` varchar(500) DEFAULT NULL COMMENT '角色描述',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '删除标志：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- 插入初始数据
INSERT INTO `sys_role` (`id`, `name`, `code`, `sort`, `status`, `type`, `data_scope`, `description`, `create_user_id`) VALUES
(1, '超级管理员', 'role_admin', 1, 1, 1, 1, '系统初始角色，拥有所有权限', 1),
(2, '普通用户', 'role_user', 2, 1, 2, 5, '普通用户，无系统管理权限，系统管理菜单无权访问', 1),
(3, '普通用户2', 'role_user2', 3, 0, 2, 5, '禁用状态的角色', 1);