-- 系统菜单表
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

-- 插入初始数据
INSERT INTO `sys_menu` (`id`, `parent_id`, `path`, `component`, `redirect`, `type`, `title`, `svg_icon`, `icon`, `keep_alive`, `hidden`, `sort`, `active_menu`, `breadcrumb`, `status`, `permission`, `show_in_tabs`, `always_show`, `affix`) VALUES
-- 分析页
(1, 0, '/analyse', 'Layout', '/analyse/index', 1, '分析页', '', '', 0, 0, 2, '', 1, 1, '', 1, 0, 0),
(101, 1, '/analyse/index', 'analyse/index', '', 2, '分析页', 'menu-chart', '', 0, 0, 1, '', 0, 1, '', 1, 0, 1),

-- 数据管理
(2, 0, '/data', 'Layout', '/data/index', 1, '数据管理', 'menu-data', 'icon-list', 1, 0, 3, '', 1, 1, '', 1, 0, 0),
(201, 2, '/data/index', 'data/main/index', '', 2, '数据管理', '', 'icon-list', 1, 0, 0, '', 0, 1, '', 1, 0, 0),
(202, 2, '/data/detail/:id', 'data/detail/index', '', 2, '详情', '', '', 0, 1, 0, '/data/index', 1, 1, '', 1, 0, 0),
(203, 2, '/data/form', 'data/form/index', '', 2, '新增', '', '', 0, 1, 0, '/data/index', 1, 1, '', 1, 0, 0),

-- 文件管理
(3, 0, '/file', 'Layout', '/file/index', 1, '文件管理', 'menu-file', 'icon-folder', 1, 0, 4, '', 1, 1, '', 1, 0, 0),
(301, 3, '/file/index', 'file/main/index', '', 2, '文件管理', '', 'icon-folder', 1, 0, 0, '', 0, 1, '', 1, 0, 0),
(302, 3, '/file/detail', 'file/detail/index', '', 2, '详情', '', '', 0, 1, 0, '/file/index', 1, 1, '', 0, 0, 0),

-- 表单管理
(4, 0, '/form', 'Layout', '/form/base', 1, '表单管理', 'menu-form', '', 0, 0, 5, '', 1, 1, '', 1, 0, 0),
(401, 4, '/form/base', 'form/base/index', '', 2, '基础表单', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),
(402, 4, '/form/step', 'form/step/index', '', 2, '分步表单', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),
(403, 4, '/form/custom', 'form/custom/index', '', 2, '配置表单', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),
(404, 4, '/form/table', 'form/table/index', '', 2, '编辑表格', '', 'icon-menu', 1, 0, 0, '', 1, 1, '', 1, 0, 0),

-- 表格管理
(5, 0, '/table', 'Layout', '/table/base', 1, '表格管理', 'menu-table', '', 0, 0, 6, '', 1, 1, '', 1, 0, 0),
(501, 5, '/table/base', 'table/base/index', '', 2, '基础表格', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),
(502, 5, '/table/custom', 'table/custom/index', '', 2, '自定义表格', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),
(503, 5, '/table/custom2', 'table/custom2/index', '', 2, '配置化表格', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),

-- 错误页
(6, 0, '/error', 'Layout', '/error/403', 1, '错误页', 'menu-error', '', 0, 0, 7, '', 1, 1, '', 1, 0, 0),
(601, 6, '/error/403', 'error/403', '', 2, '403页', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),
(602, 6, '/error/404', 'error/404', '', 2, '404页', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),
(603, 6, '/error/500', 'error/500', '', 2, '500页', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),

-- 结果页
(7, 0, '/result', 'Layout', '/result/success', 1, '结果页', 'menu-result', '', 0, 0, 8, '', 1, 1, '', 1, 0, 0),
(701, 7, '/result/success', 'result/success/index', '', 2, '成功页', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),
(702, 7, '/result/fail', 'result/fail/index', '', 2, '失败页', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),

-- 系统管理
(8, 0, '/system', 'Layout', 'noRedirect', 1, '系统管理', 'menu-system', '', 0, 0, 9, '', 1, 1, '', 1, 0, 0),
(801, 8, '/system/user', 'system/user/index', '', 2, '用户管理', '', 'icon-user', 0, 0, 1, '', 1, 1, '', 1, 0, 0),
(802, 8, '/system/role', 'system/role/index', '', 2, '角色管理', '', 'icon-common', 0, 0, 2, '', 1, 1, '', 1, 0, 0),
(803, 8, '/system/dept', 'system/dept/index', '', 2, '部门管理', '', 'icon-mind-mapping', 0, 0, 3, '', 1, 1, '', 1, 0, 0),
(804, 8, '/system/menu', 'system/menu/index', '', 2, '菜单管理', '', 'icon-menu', 0, 0, 4, '', 1, 1, '', 1, 0, 0),
(805, 8, '/system/dict', 'system/dict/index', '', 2, '字典管理', '', 'icon-bookmark', 0, 0, 5, '', 1, 1, '', 1, 0, 0),
(806, 8, '/system/account', 'system/account/index', '', 2, '用户账户', '', 'icon-tool', 0, 0, 6, '', 1, 1, '', 1, 0, 0),

-- 权限测试
(81, 0, '/test', 'Layout', 'noRedirect', 1, '权限测试', 'menu-test', '', 0, 0, 9, '', 1, 1, '', 1, 1, 0),
(8101, 81, '/test/page1', 'test/page1/index', '', 2, '测试页面1', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),
(8102, 81, '/test/page2', 'test/page2/index', '', 2, '测试页面2', '', 'icon-menu', 0, 0, 0, '', 1, 1, '', 1, 0, 0),
-- 按钮权限
(810201, 8102, '', '', '', 3, '按钮新增', '', '', 0, 0, 0, '', 1, 1, 'user:btn:add', 1, 0, 0),
(810202, 8102, '', '', '', 3, '按钮编辑', '', '', 0, 0, 0, '', 1, 1, 'user:btn:edit', 1, 0, 0),
(810203, 8102, '', '', '', 3, '按钮删除', '', '', 0, 0, 0, '', 1, 1, 'user:btn:delete', 1, 0, 0),
(810204, 8102, '', '', '', 3, '按钮新增(测试)', '', '', 0, 0, 0, '', 1, 1, 'test:btn:add', 1, 0, 0),
(810205, 8102, '', '', '', 3, '按钮编辑(测试)', '', '', 0, 0, 0, '', 1, 1, 'test:btn:edit', 1, 0, 0),
(810206, 8102, '', '', '', 3, '按钮删除(测试)', '', '', 0, 0, 0, '', 1, 1, 'test:btn:delete', 1, 0, 0),

-- 示例页
(9, 0, '/demo', 'Layout', '/demo/index', 1, '示例页', '', '', 0, 0, 15, '', 1, 1, '', 1, 0, 0),
(901, 9, '/demo/index', 'demo/index', '', 2, '示例页', 'menu-example', '', 0, 0, 0, '', 0, 1, '', 1, 0, 0);