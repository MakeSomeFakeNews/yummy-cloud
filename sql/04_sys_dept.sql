-- 系统部门表
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

-- 插入初始数据
INSERT INTO `sys_dept` (`id`, `parent_id`, `ancestors`, `name`, `sort`, `leader`, `phone`, `email`, `status`, `description`, `create_user_id`) VALUES
-- 顶级部门
(1, 0, '0', 'XXX科技有限公司', 1, '管理员', '19900006962', '326010228@qq.com', 1, '本部', 1),

-- 一级部门
(2, 1, '0,1', '广州总部', 1, NULL, NULL, NULL, 1, '', 1),

-- 二级部门
(3, 2, '0,1,2', '研发部', 1, NULL, NULL, NULL, 1, '', 1),
(4, 2, '0,1,2', 'UI部', 2, NULL, NULL, NULL, 1, '', 1),
(5, 2, '0,1,2', '测试部', 3, NULL, NULL, NULL, 1, '', 1),
(6, 2, '0,1,2', '运维部', 4, NULL, NULL, NULL, 1, '', 1),

-- 三级部门（研发部下的组）
(7, 3, '0,1,2,3', '研发一组', 1, NULL, NULL, NULL, 1, '', 1),
(8, 3, '0,1,2,3', '研发二组', 2, NULL, NULL, NULL, 1, '', 1),
(9, 3, '0,1,2,3', '研发三组', 3, NULL, NULL, NULL, 0, '禁用测试', 1);