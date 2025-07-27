-- 系统字典表
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