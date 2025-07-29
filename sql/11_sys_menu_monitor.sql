-- 添加系统监控菜单
-- 在系统管理(ID: 8)下添加监控管理子菜单

INSERT INTO `sys_menu` (`id`, `parent_id`, `path`, `component`, `redirect`, `type`, `title`, `svg_icon`, `icon`, `keep_alive`, `hidden`, `sort`, `active_menu`, `breadcrumb`, `status`, `permission`, `show_in_tabs`, `always_show`, `affix`) VALUES
-- 系统监控菜单
(807, 8, '/system/monitor', 'Layout', '/system/monitor/redis', 1, '系统监控', 'menu-monitor', 'icon-computer', 0, 0, 7, '', 1, 1, 'sys:monitor', 1, 0, 0),

-- Redis监控页面
(80701, 807, '/system/monitor/redis', 'system/monitor/redis', '', 2, 'Redis监控', '', 'icon-storage', 0, 0, 1, '', 1, 1, 'sys:monitor:redis', 1, 0, 0),

-- 系统监控页面
(80702, 807, '/system/monitor/system', 'system/monitor/system', '', 2, '系统监控', '', 'icon-desktop', 0, 0, 2, '', 1, 1, 'sys:monitor:system', 1, 0, 0),

-- 监控相关按钮权限
-- Redis监控权限
(8070101, 80701, '', '', '', 3, '查看Redis信息', '', '', 0, 0, 1, '', 1, 1, 'sys:monitor:redis:info', 1, 0, 0),
(8070102, 80701, '', '', '', 3, '清空Redis缓存', '', '', 0, 0, 2, '', 1, 1, 'sys:monitor:redis:flush', 1, 0, 0),

-- 系统监控权限
(8070201, 80702, '', '', '', 3, '查看系统信息', '', '', 0, 0, 1, '', 1, 1, 'sys:monitor:system:info', 1, 0, 0),
(8070202, 80702, '', '', '', 3, '查看JVM信息', '', '', 0, 0, 2, '', 1, 1, 'sys:monitor:system:jvm', 1, 0, 0),
(8070203, 80702, '', '', '', 3, '查看线程信息', '', '', 0, 0, 3, '', 1, 1, 'sys:monitor:system:thread', 1, 0, 0),
(8070204, 80702, '', '', '', 3, '查看磁盘信息', '', '', 0, 0, 4, '', 1, 1, 'sys:monitor:system:disk', 1, 0, 0);

-- 为超级管理员角色分配监控权限 (假设超级管理员角色ID为1)
-- 注意：实际使用时需要根据具体的角色权限表结构来调整
INSERT IGNORE INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
(1, 807),     -- 系统监控目录
(1, 80701),   -- Redis监控页面
(1, 80702),   -- 系统监控页面
(1, 8070101), -- 查看Redis信息权限
(1, 8070102), -- 清空Redis缓存权限
(1, 8070201), -- 查看系统信息权限
(1, 8070202), -- 查看JVM信息权限
(1, 8070203), -- 查看线程信息权限
(1, 8070204); -- 查看磁盘信息权限