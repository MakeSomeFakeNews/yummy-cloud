-- 为sys_dict_data表添加dict_code字段
ALTER TABLE `sys_dict_data` ADD COLUMN `dict_code` varchar(100) DEFAULT NULL COMMENT '字典编码' AFTER `dict_id`;

-- 添加索引
ALTER TABLE `sys_dict_data` ADD KEY `idx_dict_code` (`dict_code`);

-- 更新现有数据的dict_code字段
UPDATE `sys_dict_data` sdd 
JOIN `sys_dict` sd ON sdd.dict_id = sd.id 
SET sdd.dict_code = sd.code;

-- 将dict_code字段设置为非空
ALTER TABLE `sys_dict_data` MODIFY COLUMN `dict_code` varchar(100) NOT NULL COMMENT '字典编码';