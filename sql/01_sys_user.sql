-- 系统用户表
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

-- 插入初始数据
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `real_name`, `gender`, `avatar`, `email`, `phone`, `status`, `type`, `dept_id`, `description`, `create_user_id`) VALUES
(1, 'admin', '$2a$10$7JB720yubVSuKdl1H3.UKO3LIaGJjQgq3VU0qsKjIHF7WG6DEXOqq', '管理员', '超级管理员', 1, 'https://img0.baidu.com/it/u=2746352008,2041591833&fm=253&fmt=auto&app=138&f=JPEG?w=360&h=360', '326010228@qq.com', '19900006962', 1, 1, 1, '系统初始用户', 1),
(2, 'user', '$2a$10$7JB720yubVSuKdl1H3.UKO3LIaGJjQgq3VU0qsKjIHF7WG6DEXOqq', '木糖醇', '普通用户', 2, 'https://img1.baidu.com/it/u=1817951587,3188870642&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '15500008810@qq.com', '15500008810', 1, 2, 4, '无法访问系统管理菜单', 1);