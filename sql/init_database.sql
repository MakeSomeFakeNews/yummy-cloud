-- ==========================================
-- 权限管理系统数据库初始化脚本
-- 基于 admin/mock 数据结构设计
-- ==========================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `yummy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `yummy`;

-- ==========================================
-- 1. 系统用户表
-- ==========================================
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `gender` tinyint(1) DEFAULT 1 COMMENT '性别：1-男 2-女 3-未知',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `type` tinyint(1) DEFAULT 2 COMMENT '用户类型：1-系统用户 2-普通用户',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '删除标志：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_dept_id` (`dept_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- ==========================================
-- 2. 系统角色表
-- ==========================================
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

-- ==========================================
-- 3. 系统菜单表
-- ==========================================
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '父菜单ID',
  `path` varchar(200) DEFAULT NULL COMMENT '路由地址',
  `component` varchar(200) DEFAULT NULL COMMENT '组件路径',
  `redirect` varchar(200) DEFAULT NULL COMMENT '重定向地址',
  `type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '菜单类型：1-目录 2-菜单 3-按钮',
  `title` varchar(50) NOT NULL COMMENT '菜单名称',
  `svg_icon` varchar(100) DEFAULT NULL COMMENT 'SVG图标',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `keep_alive` tinyint(1) DEFAULT 0 COMMENT '是否缓存：0-不缓存 1-缓存',
  `hidden` tinyint(1) DEFAULT 0 COMMENT '是否隐藏：0-显示 1-隐藏',
  `sort` int(11) DEFAULT 0 COMMENT '显示顺序',
  `active_menu` varchar(200) DEFAULT NULL COMMENT '当前激活的菜单',
  `breadcrumb` tinyint(1) DEFAULT 1 COMMENT '是否显示面包屑：0-不显示 1-显示',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `permission` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `show_in_tabs` tinyint(1) DEFAULT 1 COMMENT '是否显示在标签页：0-不显示 1-显示',
  `always_show` tinyint(1) DEFAULT 0 COMMENT '是否总是显示：0-不显示 1-显示',
  `affix` tinyint(1) DEFAULT 0 COMMENT '是否固定在标签页：0-不固定 1-固定',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '删除标志：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

-- ==========================================
-- 4. 系统部门表
-- ==========================================
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '父部门ID',
  `ancestors` varchar(500) DEFAULT NULL COMMENT '祖级列表',
  `name` varchar(50) NOT NULL COMMENT '部门名称',
  `sort` int(11) DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(50) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `description` varchar(500) DEFAULT NULL COMMENT '部门描述',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '删除标志：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统部门表';

-- ==========================================
-- 5. 系统字典表
-- ==========================================
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `code` varchar(100) NOT NULL COMMENT '字典编码',
  `sort` int(11) DEFAULT 0 COMMENT '显示顺序',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `description` varchar(500) DEFAULT NULL COMMENT '字典描述',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '删除标志：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统字典表';

-- 系统字典数据表
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典数据ID',
  `dict_id` bigint(20) NOT NULL COMMENT '字典ID',
  `name` varchar(100) NOT NULL COMMENT '字典标签',
  `value` varchar(100) NOT NULL COMMENT '字典键值',
  `sort` int(11) DEFAULT 0 COMMENT '显示顺序',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `color` varchar(50) DEFAULT NULL COMMENT '标签颜色',
  `css_class` varchar(100) DEFAULT NULL COMMENT 'CSS类名',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` tinyint(1) DEFAULT 0 COMMENT '是否默认：0-否 1-是',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '删除标志：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_dict_id` (`dict_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统字典数据表';

-- ==========================================
-- 6. 关联关系表
-- ==========================================

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

-- ==========================================
-- 7. 插入初始化数据
-- ==========================================

-- 插入部门数据
INSERT INTO `sys_dept` (`id`, `parent_id`, `ancestors`, `name`, `sort`, `leader`, `phone`, `email`, `status`, `description`, `create_user_id`) VALUES
(1, 0, '0', 'XXX科技有限公司', 1, '管理员', '19900006962', '326010228@qq.com', 1, '本部', 1),
(2, 1, '0,1', '广州总部', 1, NULL, NULL, NULL, 1, '', 1),
(3, 2, '0,1,2', '研发部', 1, NULL, NULL, NULL, 1, '', 1),
(4, 2, '0,1,2', 'UI部', 2, NULL, NULL, NULL, 1, '', 1),
(5, 2, '0,1,2', '测试部', 3, NULL, NULL, NULL, 1, '', 1),
(6, 2, '0,1,2', '运维部', 4, NULL, NULL, NULL, 1, '', 1),
(7, 3, '0,1,2,3', '研发一组', 1, NULL, NULL, NULL, 1, '', 1),
(8, 3, '0,1,2,3', '研发二组', 2, NULL, NULL, NULL, 1, '', 1),
(9, 3, '0,1,2,3', '研发三组', 3, NULL, NULL, NULL, 0, '禁用测试', 1);

-- 插入角色数据
INSERT INTO `sys_role` (`id`, `name`, `code`, `sort`, `status`, `type`, `data_scope`, `description`, `create_user_id`) VALUES
(1, '超级管理员', 'role_admin', 1, 1, 1, 1, '系统初始角色，拥有所有权限', 1),
(2, '普通用户', 'role_user', 2, 1, 2, 5, '普通用户，无系统管理权限，系统管理菜单无权访问', 1),
(3, '普通用户2', 'role_user2', 3, 0, 2, 5, '禁用状态的角色', 1);

-- 插入用户数据（密码为123456的BCrypt加密）
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `real_name`, `gender`, `avatar`, `email`, `phone`, `status`, `type`, `dept_id`, `description`, `create_user_id`) VALUES
(1, 'admin', '$2a$10$7JB720yubVSuKdl1H3.UKO3LIaGJjQgq3VU0qsKjIHF7WG6DEXOqq', '管理员', '超级管理员', 1, 'https://img0.baidu.com/it/u=2746352008,2041591833&fm=253&fmt=auto&app=138&f=JPEG?w=360&h=360', '326010228@qq.com', '19900006962', 1, 1, 1, '系统初始用户', 1),
(2, 'user', '$2a$10$7JB720yubVSuKdl1H3.UKO3LIaGJjQgq3VU0qsKjIHF7WG6DEXOqq', '木糖醇', '普通用户', 2, 'https://img1.baidu.com/it/u=1817951587,3188870642&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '15500008810@qq.com', '15500008810', 1, 2, 7, '无法访问系统管理菜单', 1);

-- 插入菜单数据
INSERT INTO `sys_menu` (`id`, `parent_id`, `path`, `component`, `redirect`, `type`, `title`, `svg_icon`, `icon`, `keep_alive`, `hidden`, `sort`, `active_menu`, `breadcrumb`, `status`, `permission`, `show_in_tabs`, `always_show`, `affix`) VALUES
-- 分析页
(1, 0, '/analyse', 'Layout', '/analyse/index', 1, '分析页', '', '', 0, 0, 2, '', 1, 1, '', 1, 0, 0),
(101, 1, '/analyse/index', 'analyse/index', '', 2, '分析页', 'menu-chart', '', 0, 0, 1, '', 0, 1, '', 1, 0, 1),
-- 数据管理
(2, 0, '/data', 'Layout', '/data/index', 1, '数据管理', 'menu-data', 'icon-list', 1, 0, 3, '', 1, 1, '', 1, 0, 0),
(201, 2, '/data/index', 'data/main/index', '', 2, '数据管理', '', 'icon-list', 1, 0, 0, '', 0, 1, '', 1, 0, 0),
(202, 2, '/data/detail/:id', 'data/detail/index', '', 2, '详情', '', '', 0, 1, 0, '/data/index', 1, 1, '', 1, 0, 0),
(203, 2, '/data/form', 'data/form/index', '', 2, '新增', '', '', 0, 1, 0, '/data/index', 1, 1, '', 1, 0, 0),
-- 系统管理
(8, 0, '/system', 'Layout', 'noRedirect', 1, '系统管理', 'menu-system', '', 0, 0, 9, '', 1, 1, '', 1, 0, 0),
(801, 8, '/system/user', 'system/user/index', '', 2, '用户管理', '', 'icon-user', 0, 0, 1, '', 1, 1, '', 1, 0, 0),
(802, 8, '/system/role', 'system/role/index', '', 2, '角色管理', '', 'icon-common', 0, 0, 2, '', 1, 1, '', 1, 0, 0),
(803, 8, '/system/dept', 'system/dept/index', '', 2, '部门管理', '', 'icon-mind-mapping', 0, 0, 3, '', 1, 1, '', 1, 0, 0),
(804, 8, '/system/menu', 'system/menu/index', '', 2, '菜单管理', '', 'icon-menu', 0, 0, 4, '', 1, 1, '', 1, 0, 0),
(805, 8, '/system/dict', 'system/dict/index', '', 2, '字典管理', '', 'icon-bookmark', 0, 0, 5, '', 1, 1, '', 1, 0, 0),
-- 权限测试
(81, 0, '/test', 'Layout', 'noRedirect', 1, '权限测试', 'menu-test', '', 0, 0, 10, '', 1, 1, '', 1, 1, 0),
(8102, 81, '/test/page2', 'test/page2/index', '', 2, '测试页面2', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),
-- 按钮权限
(810201, 8102, '', '', '', 3, '按钮新增', '', '', 0, 0, 0, '', 1, 1, 'user:btn:add', 1, 0, 0),
(810202, 8102, '', '', '', 3, '按钮编辑', '', '', 0, 0, 0, '', 1, 1, 'user:btn:edit', 1, 0, 0),
(810203, 8102, '', '', '', 3, '按钮删除', '', '', 0, 0, 0, '', 1, 1, 'user:btn:delete', 1, 0, 0);

-- 插入字典数据
INSERT INTO `sys_dict` (`id`, `name`, `code`, `sort`, `status`, `description`, `create_user_id`) VALUES
(1, '性别', 'gender', 1, 1, '性别字典', 1),
(2, '状态', 'status', 2, 1, '通用状态字典', 1),
(3, '爱好', 'hobbys', 3, 1, '基础表单-兴趣爱好', 1);

-- 插入字典数据项
INSERT INTO `sys_dict_data` (`id`, `dict_id`, `name`, `value`, `sort`, `status`, `color`, `css_class`, `list_class`, `is_default`, `description`, `create_user_id`) VALUES
-- 性别字典数据
(1, 1, '男', '1', 1, 1, 'blue', '', '', 0, '', 1),
(2, 1, '女', '2', 2, 1, 'pink', '', '', 0, '', 1),
(3, 1, '未知', '3', 3, 1, 'gray', '', '', 0, '', 1),
-- 状态字典数据
(4, 2, '正常', '1', 1, 1, 'green', '', '', 1, '', 1),
(5, 2, '禁用', '0', 2, 1, 'red', '', '', 0, '', 1),
-- 爱好字典数据
(6, 3, '运动', '运动', 1, 1, 'red', '', '', 0, '', 1),
(7, 3, '音乐', '音乐', 2, 1, 'green', '', '', 0, '', 1),
(8, 3, '电影', '电影', 3, 1, 'cyan', '', '', 0, '', 1),
(9, 3, '旅行', '旅行', 4, 1, 'arcoblue', '', '', 0, '', 1),
(10, 3, '美食', '美食', 5, 1, 'purple', '', '', 0, '', 1);

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
(1, 8), (1, 801), (1, 802), (1, 803), (1, 804), (1, 805), -- 系统管理
(1, 81), (1, 8102), (1, 810201), (1, 810202), (1, 810203), -- 权限测试
-- 普通用户角色的菜单权限（除系统管理外的菜单）
(2, 1), (2, 101), -- 分析页
(2, 2), (2, 201), (2, 202), (2, 203), -- 数据管理
(2, 81), (2, 8102), (2, 810201), (2, 810202), (2, 810203); -- 权限测试（部分按钮权限）

-- ==========================================
-- 初始化完成
-- ==========================================