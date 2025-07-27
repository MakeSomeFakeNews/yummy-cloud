-- ==================================================
-- YummyERP 数据库表结构设计
-- 基于现有system模块扩展的完整ERP系统数据表
-- ==================================================

-- ==================================================
-- 1. 基础数据模块
-- ==================================================

-- 供应商信息表
CREATE TABLE `erp_supplier` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) NOT NULL COMMENT '供应商编码',
  `name` varchar(100) NOT NULL COMMENT '供应商名称',
  `short_name` varchar(50) COMMENT '简称',
  `type` tinyint DEFAULT 1 COMMENT '供应商类型：1-生产商 2-贸易商 3-服务商',
  `level` tinyint DEFAULT 1 COMMENT '供应商级别：1-A级 2-B级 3-C级',
  `contact_person` varchar(50) COMMENT '联系人',
  `contact_phone` varchar(20) COMMENT '联系电话',
  `contact_email` varchar(100) COMMENT '联系邮箱',
  `address` varchar(200) COMMENT '地址',
  `credit_code` varchar(50) COMMENT '统一社会信用代码',
  `bank_name` varchar(100) COMMENT '开户银行',
  `bank_account` varchar(50) COMMENT '银行账户',
  `tax_rate` decimal(5,4) DEFAULT 0.0000 COMMENT '税率',
  `payment_terms` varchar(100) COMMENT '付款条件',
  `delivery_terms` varchar(100) COMMENT '交货条件',
  `status` tinyint DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `remark` text COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_supplier_code` (`code`),
  KEY `idx_supplier_name` (`name`),
  KEY `idx_supplier_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商信息表';

-- 客户信息表
CREATE TABLE `erp_customer` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) NOT NULL COMMENT '客户编码',
  `name` varchar(100) NOT NULL COMMENT '客户名称',
  `short_name` varchar(50) COMMENT '简称',
  `type` tinyint DEFAULT 1 COMMENT '客户类型：1-企业客户 2-个人客户',
  `level` tinyint DEFAULT 1 COMMENT '客户级别：1-VIP 2-普通 3-潜在',
  `industry` varchar(50) COMMENT '所属行业',
  `contact_person` varchar(50) COMMENT '联系人',
  `contact_phone` varchar(20) COMMENT '联系电话',
  `contact_email` varchar(100) COMMENT '联系邮箱',  
  `address` varchar(200) COMMENT '地址',
  `credit_code` varchar(50) COMMENT '统一社会信用代码',
  `credit_limit` decimal(15,2) DEFAULT 0.00 COMMENT '信用额度',
  `credit_days` int DEFAULT 0 COMMENT '信用期限（天）',
  `tax_rate` decimal(5,4) DEFAULT 0.0000 COMMENT '税率',
  `payment_terms` varchar(100) COMMENT '付款条件',
  `delivery_terms` varchar(100) COMMENT '交货条件',
  `status` tinyint DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `remark` text COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_customer_code` (`code`),
  KEY `idx_customer_name` (`name`),
  KEY `idx_customer_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户信息表';

-- 仓库信息表
CREATE TABLE `erp_warehouse` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) NOT NULL COMMENT '仓库编码',
  `name` varchar(100) NOT NULL COMMENT '仓库名称',
  `type` tinyint DEFAULT 1 COMMENT '仓库类型：1-原料仓 2-成品仓 3-半成品仓 4-次品仓',
  `address` varchar(200) COMMENT '仓库地址',
  `manager` varchar(50) COMMENT '仓库管理员',
  `phone` varchar(20) COMMENT '联系电话',
  `area` decimal(10,2) COMMENT '仓库面积（平方米）',
  `capacity` decimal(15,2) COMMENT '存储容量',
  `status` tinyint DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `remark` text COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_warehouse_code` (`code`),
  KEY `idx_warehouse_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库信息表';

-- 库位信息表
CREATE TABLE `erp_warehouse_location` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `code` varchar(50) NOT NULL COMMENT '库位编码',
  `name` varchar(100) NOT NULL COMMENT '库位名称',
  `type` tinyint DEFAULT 1 COMMENT '库位类型：1-普通库位 2-冷藏库位 3-危险品库位',
  `capacity` decimal(15,2) COMMENT '存储容量',
  `status` tinyint DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `remark` text COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_location_code` (`code`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_location_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库位信息表';

-- 计量单位表
CREATE TABLE `erp_unit` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(20) NOT NULL COMMENT '单位编码',
  `name` varchar(50) NOT NULL COMMENT '单位名称',
  `symbol` varchar(10) COMMENT '单位符号',
  `type` tinyint DEFAULT 1 COMMENT '单位类型：1-基本单位 2-长度 3-重量 4-体积 5-面积',
  `base_unit_id` bigint COMMENT '基础单位ID',
  `conversion_rate` decimal(15,6) DEFAULT 1.000000 COMMENT '转换比率',
  `status` tinyint DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_unit_code` (`code`),
  KEY `idx_unit_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计量单位表';

-- ==================================================
-- 2. 产品管理模块
-- ==================================================

-- 产品分类表
CREATE TABLE `erp_product_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint DEFAULT 0 COMMENT '父分类ID',
  `ancestors` varchar(500) COMMENT '祖级列表',
  `code` varchar(50) NOT NULL COMMENT '分类编码',
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `sort` int DEFAULT 0 COMMENT '显示顺序',
  `icon` varchar(100) COMMENT '图标',
  `status` tinyint DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `remark` text COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_category_code` (`code`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_category_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品分类表';

-- 产品信息表
CREATE TABLE `erp_product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `category_id` bigint NOT NULL COMMENT '产品分类ID',
  `code` varchar(50) NOT NULL COMMENT '产品编码',
  `name` varchar(200) NOT NULL COMMENT '产品名称',
  `short_name` varchar(100) COMMENT '产品简称',
  `sku` varchar(100) COMMENT 'SKU编码',
  `barcode` varchar(50) COMMENT '条形码',
  `brand` varchar(100) COMMENT '品牌',
  `model` varchar(100) COMMENT '型号',
  `specification` text COMMENT '规格参数',
  `unit_id` bigint NOT NULL COMMENT '基本单位ID',
  `weight` decimal(10,3) COMMENT '重量（KG）',
  `volume` decimal(10,3) COMMENT '体积（立方米）',
  `purchase_price` decimal(15,2) DEFAULT 0.00 COMMENT '采购价格',
  `sale_price` decimal(15,2) DEFAULT 0.00 COMMENT '销售价格',
  `cost_price` decimal(15,2) DEFAULT 0.00 COMMENT '成本价格',
  `min_stock` decimal(15,2) DEFAULT 0.00 COMMENT '最小库存',
  `max_stock` decimal(15,2) DEFAULT 0.00 COMMENT '最大库存',
  `safety_stock` decimal(15,2) DEFAULT 0.00 COMMENT '安全库存',
  `lead_time` int DEFAULT 0 COMMENT '采购周期（天）',
  `shelf_life` int DEFAULT 0 COMMENT '保质期（天）',
  `storage_condition` varchar(200) COMMENT '存储条件',
  `main_image` varchar(500) COMMENT '主图片URL',
  `description` text COMMENT '产品描述',
  `status` tinyint DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_code` (`code`),
  UNIQUE KEY `uk_product_sku` (`sku`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_product_name` (`name`),
  KEY `idx_barcode` (`barcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品信息表';

-- 产品变体表
CREATE TABLE `erp_product_variant` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `code` varchar(50) NOT NULL COMMENT '变体编码',
  `name` varchar(200) NOT NULL COMMENT '变体名称',
  `sku` varchar(100) COMMENT 'SKU编码',
  `barcode` varchar(50) COMMENT '条形码',
  `attributes` json COMMENT '变体属性（颜色、尺寸等）',
  `purchase_price` decimal(15,2) DEFAULT 0.00 COMMENT '采购价格',
  `sale_price` decimal(15,2) DEFAULT 0.00 COMMENT '销售价格',
  `cost_price` decimal(15,2) DEFAULT 0.00 COMMENT '成本价格',
  `weight` decimal(10,3) COMMENT '重量（KG）',
  `image` varchar(500) COMMENT '变体图片URL',
  `status` tinyint DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_variant_code` (`code`),
  UNIQUE KEY `uk_variant_sku` (`sku`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_variant_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品变体表';

-- 产品图片表
CREATE TABLE `erp_product_image` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `image_url` varchar(500) NOT NULL COMMENT '图片URL',
  `image_name` varchar(200) COMMENT '图片名称',
  `image_type` tinyint DEFAULT 1 COMMENT '图片类型：1-主图 2-详情图 3-规格图',
  `sort` int DEFAULT 0 COMMENT '排序',
  `status` tinyint DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_variant_id` (`variant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品图片表';

-- ==================================================
-- 3. 库存管理模块
-- ==================================================

-- 库存主表
CREATE TABLE `erp_inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `location_id` bigint COMMENT '库位ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `batch_no` varchar(100) COMMENT '批次号',
  `current_stock` decimal(15,2) DEFAULT 0.00 COMMENT '当前库存',
  `available_stock` decimal(15,2) DEFAULT 0.00 COMMENT '可用库存',
  `locked_stock` decimal(15,2) DEFAULT 0.00 COMMENT '锁定库存',
  `cost_price` decimal(15,2) DEFAULT 0.00 COMMENT '成本价',
  `production_date` date COMMENT '生产日期',
  `expiry_date` date COMMENT '过期日期',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_inventory_unique` (`warehouse_id`,`location_id`,`product_id`,`variant_id`,`batch_no`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_variant_id` (`variant_id`),
  KEY `idx_batch_no` (`batch_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存主表';

-- 库存流水表
CREATE TABLE `erp_inventory_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `transaction_no` varchar(50) NOT NULL COMMENT '流水号',
  `transaction_type` tinyint NOT NULL COMMENT '流水类型：1-入库 2-出库 3-调拨 4-盘点',
  `business_type` tinyint NOT NULL COMMENT '业务类型：1-采购入库 2-销售出库 3-生产入库 4-生产出库 5-调拨入库 6-调拨出库 7-盘点盈亏 8-其他入库 9-其他出库',
  `reference_no` varchar(50) COMMENT '关联单号',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `location_id` bigint COMMENT '库位ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `batch_no` varchar(100) COMMENT '批次号',
  `quantity` decimal(15,2) NOT NULL COMMENT '数量（正数入库，负数出库）',
  `unit_price` decimal(15,2) DEFAULT 0.00 COMMENT '单价',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '总金额',
  `before_stock` decimal(15,2) DEFAULT 0.00 COMMENT '变动前库存',
  `after_stock` decimal(15,2) DEFAULT 0.00 COMMENT '变动后库存',
  `operator_id` bigint COMMENT '操作员ID',
  `transaction_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '流水时间',
  `remark` varchar(500) COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_transaction_no` (`transaction_no`),
  KEY `idx_reference_no` (`reference_no`),
  KEY `idx_warehouse_product` (`warehouse_id`,`product_id`),
  KEY `idx_transaction_time` (`transaction_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存流水表';

-- 库存盘点单
CREATE TABLE `erp_inventory_check` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `check_no` varchar(50) NOT NULL COMMENT '盘点单号',  
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `check_date` date NOT NULL COMMENT '盘点日期',
  `check_type` tinyint DEFAULT 1 COMMENT '盘点类型：1-全盘 2-抽盘 3-循环盘点',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-盘点中 2-已完成 3-已审核',
  `checker_id` bigint COMMENT '盘点员ID',
  `auditor_id` bigint COMMENT '审核员ID',
  `audit_time` datetime COMMENT '审核时间',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_check_no` (`check_no`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_check_date` (`check_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存盘点单';

-- 库存盘点明细
CREATE TABLE `erp_inventory_check_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `check_id` bigint NOT NULL COMMENT '盘点单ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `batch_no` varchar(100) COMMENT '批次号',
  `location_id` bigint COMMENT '库位ID',
  `book_stock` decimal(15,2) DEFAULT 0.00 COMMENT '账面库存',
  `actual_stock` decimal(15,2) DEFAULT 0.00 COMMENT '实际库存',
  `diff_stock` decimal(15,2) DEFAULT 0.00 COMMENT '盘点差异',
  `cost_price` decimal(15,2) DEFAULT 0.00 COMMENT '成本价',
  `diff_amount` decimal(15,2) DEFAULT 0.00 COMMENT '差异金额',
  `remark` varchar(500) COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_check_id` (`check_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存盘点明细';

-- ==================================================
-- 4. 采购管理模块
-- ==================================================

-- 采购申请单
CREATE TABLE `erp_purchase_request` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `request_no` varchar(50) NOT NULL COMMENT '申请单号',
  `request_date` date NOT NULL COMMENT '申请日期',
  `dept_id` bigint NOT NULL COMMENT '申请部门ID',
  `requester_id` bigint NOT NULL COMMENT '申请人ID',
  `request_reason` varchar(500) COMMENT '申请原因',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '申请总金额',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-待审核 2-已审核 3-已驳回 4-已完成',
  `auditor_id` bigint COMMENT '审核人ID',
  `audit_time` datetime COMMENT '审核时间',
  `audit_remark` varchar(500) COMMENT '审核意见',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_request_no` (`request_no`),
  KEY `idx_dept_id` (`dept_id`),
  KEY `idx_requester_id` (`requester_id`),
  KEY `idx_request_date` (`request_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购申请单';

-- 采购申请明细
CREATE TABLE `erp_purchase_request_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `request_id` bigint NOT NULL COMMENT '申请单ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `quantity` decimal(15,2) NOT NULL COMMENT '申请数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `estimated_price` decimal(15,2) DEFAULT 0.00 COMMENT '预估价格',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '总金额',
  `required_date` date COMMENT '需求日期',
  `remark` varchar(500) COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_request_id` (`request_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购申请明细';

-- 采购订单
CREATE TABLE `erp_purchase_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `order_date` date NOT NULL COMMENT '订单日期',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `buyer_id` bigint NOT NULL COMMENT '采购员ID',
  `delivery_date` date COMMENT '交货日期',
  `delivery_address` varchar(200) COMMENT '交货地址',
  `payment_terms` varchar(100) COMMENT '付款条件',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT 1.0000 COMMENT '汇率',
  `total_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '总数量',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '订单总金额',
  `tax_rate` decimal(5,4) DEFAULT 0.0000 COMMENT '税率',
  `tax_amount` decimal(15,2) DEFAULT 0.00 COMMENT '税额',
  `total_with_tax` decimal(15,2) DEFAULT 0.00 COMMENT '含税总金额',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-待确认 2-已确认 3-部分收货 4-已收货 5-已完成 6-已取消',
  `contract_no` varchar(100) COMMENT '合同号',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_buyer_id` (`buyer_id`),
  KEY `idx_order_date` (`order_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购订单';

-- 采购订单明细
CREATE TABLE `erp_purchase_order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `quantity` decimal(15,2) NOT NULL COMMENT '订购数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT 0.00 COMMENT '单价',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '总金额',
  `received_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '已收货数量',
  `remaining_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '未收货数量',
  `required_date` date COMMENT '需求日期',
  `remark` varchar(500) COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购订单明细';

-- 采购收货单
CREATE TABLE `erp_purchase_receipt` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `receipt_no` varchar(50) NOT NULL COMMENT '收货单号',
  `order_id` bigint NOT NULL COMMENT '采购订单ID',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `receipt_date` date NOT NULL COMMENT '收货日期',
  `warehouse_id` bigint NOT NULL COMMENT '收货仓库ID',
  `receiver_id` bigint NOT NULL COMMENT '收货员ID',
  `delivery_note_no` varchar(100) COMMENT '送货单号',
  `transport_mode` varchar(50) COMMENT '运输方式',
  `transport_cost` decimal(15,2) DEFAULT 0.00 COMMENT '运费',
  `total_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '收货总数量',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '收货总金额',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-待质检 2-质检通过 3-质检不通过 4-已入库',
  `quality_inspector_id` bigint COMMENT '质检员ID',
  `quality_result` tinyint COMMENT '质检结果：1-合格 2-不合格',
  `quality_remark` varchar(500) COMMENT '质检备注',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_receipt_no` (`receipt_no`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_receipt_date` (`receipt_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购收货单';

-- 采购收货明细
CREATE TABLE `erp_purchase_receipt_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `receipt_id` bigint NOT NULL COMMENT '收货单ID',
  `order_detail_id` bigint NOT NULL COMMENT '订单明细ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `batch_no` varchar(100) COMMENT '批次号',
  `production_date` date COMMENT '生产日期',
  `expiry_date` date COMMENT '过期日期',
  `ordered_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '订购数量',
  `received_quantity` decimal(15,2) NOT NULL COMMENT '收货数量',
  `qualified_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '合格数量',
  `unqualified_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '不合格数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT 0.00 COMMENT '单价',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '总金额',
  `location_id` bigint COMMENT '存放库位ID',
  `quality_result` tinyint COMMENT '质检结果：1-合格 2-不合格',
  `remark` varchar(500) COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_receipt_id` (`receipt_id`),
  KEY `idx_order_detail_id` (`order_detail_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购收货明细';

-- ==================================================
-- 5. 销售管理模块
-- ==================================================

-- 销售报价单
CREATE TABLE `erp_sales_quotation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `quotation_no` varchar(50) NOT NULL COMMENT '报价单号',
  `quotation_date` date NOT NULL COMMENT '报价日期',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `salesperson_id` bigint NOT NULL COMMENT '销售员ID',
  `valid_until` date COMMENT '有效期至',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT 1.0000 COMMENT '汇率',
  `payment_terms` varchar(100) COMMENT '付款条件',
  `delivery_terms` varchar(100) COMMENT '交货条件',
  `total_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '总数量',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '报价总金额',
  `tax_rate` decimal(5,4) DEFAULT 0.0000 COMMENT '税率',
  `tax_amount` decimal(15,2) DEFAULT 0.00 COMMENT '税额',
  `total_with_tax` decimal(15,2) DEFAULT 0.00 COMMENT '含税总金额',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-草稿 2-已发送 3-客户确认 4-已过期 5-已取消',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_quotation_no` (`quotation_no`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_salesperson_id` (`salesperson_id`),
  KEY `idx_quotation_date` (`quotation_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售报价单';

-- 销售报价明细
CREATE TABLE `erp_sales_quotation_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `quotation_id` bigint NOT NULL COMMENT '报价单ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `quantity` decimal(15,2) NOT NULL COMMENT '报价数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT 0.00 COMMENT '单价',
  `discount_rate` decimal(5,4) DEFAULT 0.0000 COMMENT '折扣率',
  `discount_amount` decimal(15,2) DEFAULT 0.00 COMMENT '折扣金额',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '总金额',
  `delivery_date` date COMMENT '交货日期',
  `remark` varchar(500) COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_quotation_id` (`quotation_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售报价明细';

-- 销售订单
CREATE TABLE `erp_sales_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `order_date` date NOT NULL COMMENT '订单日期',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `salesperson_id` bigint NOT NULL COMMENT '销售员ID',
  `quotation_id` bigint COMMENT '关联报价单ID',
  `delivery_date` date COMMENT '交货日期',
  `delivery_address` varchar(200) COMMENT '交货地址',
  `payment_terms` varchar(100) COMMENT '付款条件',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT 1.0000 COMMENT '汇率',
  `total_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '总数量',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '订单总金额',
  `tax_rate` decimal(5,4) DEFAULT 0.0000 COMMENT '税率',
  `tax_amount` decimal(15,2) DEFAULT 0.00 COMMENT '税额',
  `total_with_tax` decimal(15,2) DEFAULT 0.00 COMMENT '含税总金额',
  `received_amount` decimal(15,2) DEFAULT 0.00 COMMENT '已收款金额',
  `remaining_amount` decimal(15,2) DEFAULT 0.00 COMMENT '待收款金额',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-待确认 2-已确认 3-生产中 4-待发货 5-部分发货 6-已发货 7-已完成 8-已取消',
  `contract_no` varchar(100) COMMENT '合同号',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_salesperson_id` (`salesperson_id`),
  KEY `idx_order_date` (`order_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售订单';

-- 销售订单明细
CREATE TABLE `erp_sales_order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `quantity` decimal(15,2) NOT NULL COMMENT '订购数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT 0.00 COMMENT '单价',
  `discount_rate` decimal(5,4) DEFAULT 0.0000 COMMENT '折扣率',
  `discount_amount` decimal(15,2) DEFAULT 0.00 COMMENT '折扣金额',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '总金额',
  `shipped_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '已发货数量',
  `remaining_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '未发货数量',
  `delivery_date` date COMMENT '交货日期',
  `remark` varchar(500) COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售订单明细';

-- 销售出库单
CREATE TABLE `erp_sales_shipment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `shipment_no` varchar(50) NOT NULL COMMENT '出库单号',
  `order_id` bigint NOT NULL COMMENT '销售订单ID',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `shipment_date` date NOT NULL COMMENT '出库日期',
  `warehouse_id` bigint NOT NULL COMMENT '出库仓库ID',
  `shipper_id` bigint NOT NULL COMMENT '发货员ID',
  `delivery_address` varchar(200) COMMENT '发货地址',
  `transport_mode` varchar(50) COMMENT '运输方式',
  `transport_cost` decimal(15,2) DEFAULT 0.00 COMMENT '运费',
  `tracking_no` varchar(100) COMMENT '物流单号',
  `total_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '出库总数量',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '出库总金额',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-已出库 2-运输中 3-已送达 4-客户确认',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_shipment_no` (`shipment_no`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_shipment_date` (`shipment_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售出库单';

-- 销售出库明细
CREATE TABLE `erp_sales_shipment_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `shipment_id` bigint NOT NULL COMMENT '出库单ID',
  `order_detail_id` bigint NOT NULL COMMENT '订单明细ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `batch_no` varchar(100) COMMENT '批次号',
  `ordered_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '订购数量',
  `shipped_quantity` decimal(15,2) NOT NULL COMMENT '出库数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT 0.00 COMMENT '单价',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '总金额',
  `location_id` bigint COMMENT '出库库位ID',
  `remark` varchar(500) COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_shipment_id` (`shipment_id`),
  KEY `idx_order_detail_id` (`order_detail_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售出库明细';

-- ==================================================
-- 6. 财务管理模块
-- ==================================================

-- 应收账款
CREATE TABLE `erp_accounts_receivable` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ar_no` varchar(50) NOT NULL COMMENT '应收单号',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `order_id` bigint COMMENT '关联订单ID',
  `invoice_no` varchar(100) COMMENT '发票号',
  `ar_date` date NOT NULL COMMENT '应收日期',
  `due_date` date COMMENT '到期日期',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT 1.0000 COMMENT '汇率',
  `ar_amount` decimal(15,2) NOT NULL COMMENT '应收金额',
  `received_amount` decimal(15,2) DEFAULT 0.00 COMMENT '已收金额',
  `remaining_amount` decimal(15,2) DEFAULT 0.00 COMMENT '未收金额',
  `overdue_days` int DEFAULT 0 COMMENT '逾期天数',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-未收款 2-部分收款 3-已收款 4-逾期',
  `salesperson_id` bigint COMMENT '负责销售员ID',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ar_no` (`ar_no`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_ar_date` (`ar_date`),
  KEY `idx_due_date` (`due_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应收账款';

-- 收款记录
CREATE TABLE `erp_payment_received` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `payment_no` varchar(50) NOT NULL COMMENT '收款单号',
  `ar_id` bigint NOT NULL COMMENT '应收账款ID',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `payment_date` date NOT NULL COMMENT '收款日期',
  `payment_method` tinyint DEFAULT 1 COMMENT '收款方式：1-现金 2-银行转账 3-支票 4-承兑汇票 5-其他',
  `payment_account` varchar(100) COMMENT '收款账户',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT 1.0000 COMMENT '汇率',
  `payment_amount` decimal(15,2) NOT NULL COMMENT '收款金额',
  `bank_charges` decimal(15,2) DEFAULT 0.00 COMMENT '银行手续费',
  `actual_amount` decimal(15,2) DEFAULT 0.00 COMMENT '实际到账金额',
  `reference_no` varchar(100) COMMENT '参考号（银行流水号等）',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-已收款 2-已核销',
  `cashier_id` bigint COMMENT '收款员ID',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_payment_no` (`payment_no`),
  KEY `idx_ar_id` (`ar_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_payment_date` (`payment_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收款记录';

-- 应付账款
CREATE TABLE `erp_accounts_payable` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ap_no` varchar(50) NOT NULL COMMENT '应付单号',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `order_id` bigint COMMENT '关联订单ID',
  `invoice_no` varchar(100) COMMENT '发票号',
  `ap_date` date NOT NULL COMMENT '应付日期',
  `due_date` date COMMENT '到期日期',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT 1.0000 COMMENT '汇率',
  `ap_amount` decimal(15,2) NOT NULL COMMENT '应付金额',
  `paid_amount` decimal(15,2) DEFAULT 0.00 COMMENT '已付金额',
  `remaining_amount` decimal(15,2) DEFAULT 0.00 COMMENT '未付金额',
  `overdue_days` int DEFAULT 0 COMMENT '逾期天数',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-未付款 2-部分付款 3-已付款 4-逾期',
  `buyer_id` bigint COMMENT '负责采购员ID',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ap_no` (`ap_no`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_ap_date` (`ap_date`),
  KEY `idx_due_date` (`due_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应付账款';

-- 付款记录
CREATE TABLE `erp_payment_made` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `payment_no` varchar(50) NOT NULL COMMENT '付款单号',
  `ap_id` bigint NOT NULL COMMENT '应付账款ID',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `payment_date` date NOT NULL COMMENT '付款日期',
  `payment_method` tinyint DEFAULT 1 COMMENT '付款方式：1-现金 2-银行转账 3-支票 4-承兑汇票 5-其他',
  `payment_account` varchar(100) COMMENT '付款账户',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT 1.0000 COMMENT '汇率',
  `payment_amount` decimal(15,2) NOT NULL COMMENT '付款金额',
  `bank_charges` decimal(15,2) DEFAULT 0.00 COMMENT '银行手续费',
  `actual_amount` decimal(15,2) DEFAULT 0.00 COMMENT '实际付款金额',
  `reference_no` varchar(100) COMMENT '参考号（银行流水号等）',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-已付款 2-已核销',
  `cashier_id` bigint COMMENT '付款员ID',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_payment_no` (`payment_no`),
  KEY `idx_ap_id` (`ap_id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_payment_date` (`payment_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='付款记录';

-- 费用管理
CREATE TABLE `erp_expense` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `expense_no` varchar(50) NOT NULL COMMENT '费用单号',
  `expense_date` date NOT NULL COMMENT '费用日期',
  `expense_type` tinyint NOT NULL COMMENT '费用类型：1-差旅费 2-办公费 3-通讯费 4-招待费 5-培训费 6-其他',
  `dept_id` bigint NOT NULL COMMENT '费用部门ID',
  `applicant_id` bigint NOT NULL COMMENT '申请人ID',
  `expense_amount` decimal(15,2) NOT NULL COMMENT '费用金额',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT 1.0000 COMMENT '汇率',
  `description` varchar(500) NOT NULL COMMENT '费用说明',
  `receipt_count` int DEFAULT 0 COMMENT '发票张数',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-待审核 2-已审核 3-已驳回 4-已报销',
  `auditor_id` bigint COMMENT '审核人ID',
  `audit_time` datetime COMMENT '审核时间',
  `audit_remark` varchar(500) COMMENT '审核意见',
  `payment_date` date COMMENT '报销日期',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_expense_no` (`expense_no`),
  KEY `idx_dept_id` (`dept_id`),
  KEY `idx_applicant_id` (`applicant_id`),
  KEY `idx_expense_date` (`expense_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='费用管理';

-- ==================================================
-- 7. 报表统计模块
-- ==================================================

-- 库存预警记录
CREATE TABLE `erp_inventory_alert` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `alert_type` tinyint NOT NULL COMMENT '预警类型：1-低库存 2-超库存 3-过期预警 4-呆滞预警',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `current_stock` decimal(15,2) DEFAULT 0.00 COMMENT '当前库存',
  `warning_stock` decimal(15,2) DEFAULT 0.00 COMMENT '预警库存',
  `alert_message` varchar(500) COMMENT '预警信息',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-待处理 2-已处理 3-已忽略',
  `handler_id` bigint COMMENT '处理人ID',
  `handle_time` datetime COMMENT '处理时间',
  `handle_remark` varchar(500) COMMENT '处理备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_product_warehouse` (`product_id`,`warehouse_id`),
  KEY `idx_alert_type` (`alert_type`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存预警记录';

-- 供应商评价
CREATE TABLE `erp_supplier_evaluation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `evaluation_date` date NOT NULL COMMENT '评价日期',
  `evaluator_id` bigint NOT NULL COMMENT '评价人ID',
  `quality_score` tinyint DEFAULT 0 COMMENT '质量评分（0-100）',
  `delivery_score` tinyint DEFAULT 0 COMMENT '交期评分（0-100）',
  `service_score` tinyint DEFAULT 0 COMMENT '服务评分（0-100）',
  `price_score` tinyint DEFAULT 0 COMMENT '价格评分（0-100）',
  `total_score` decimal(5,2) DEFAULT 0.00 COMMENT '综合评分',
  `evaluation_period` varchar(20) COMMENT '评价周期（如：2025Q1）',
  `comments` text COMMENT '评价意见',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_evaluation_date` (`evaluation_date`),
  KEY `idx_evaluator_id` (`evaluator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商评价';

-- 销售退货单
CREATE TABLE `erp_sales_return` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `return_no` varchar(50) NOT NULL COMMENT '退货单号',
  `order_id` bigint NOT NULL COMMENT '关联销售订单ID',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `return_date` date NOT NULL COMMENT '退货日期',
  `return_reason` varchar(500) COMMENT '退货原因',
  `warehouse_id` bigint NOT NULL COMMENT '退货入库仓库ID',
  `handler_id` bigint NOT NULL COMMENT '处理人ID',
  `total_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '退货总数量',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '退货总金额',
  `refund_amount` decimal(15,2) DEFAULT 0.00 COMMENT '退款金额',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-待审核 2-已审核 3-已入库 4-已退款 5-已驳回',
  `auditor_id` bigint COMMENT '审核人ID',
  `audit_time` datetime COMMENT '审核时间',
  `audit_remark` varchar(500) COMMENT '审核意见',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_return_no` (`return_no`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_return_date` (`return_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售退货单';

-- 销售退货明细
CREATE TABLE `erp_sales_return_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `return_id` bigint NOT NULL COMMENT '退货单ID',
  `order_detail_id` bigint NOT NULL COMMENT '订单明细ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `batch_no` varchar(100) COMMENT '批次号',
  `original_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '原订购数量',
  `return_quantity` decimal(15,2) NOT NULL COMMENT '退货数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT 0.00 COMMENT '单价',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '退货金额',
  `quality_status` tinyint DEFAULT 1 COMMENT '货品状况：1-完好 2-损坏 3-过期',
  `location_id` bigint COMMENT '入库库位ID',
  `remark` varchar(500) COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_return_id` (`return_id`),
  KEY `idx_order_detail_id` (`order_detail_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售退货明细';

-- 采购退货单
CREATE TABLE `erp_purchase_return` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `return_no` varchar(50) NOT NULL COMMENT '退货单号',
  `order_id` bigint NOT NULL COMMENT '关联采购订单ID',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `return_date` date NOT NULL COMMENT '退货日期',
  `return_reason` varchar(500) COMMENT '退货原因',
  `warehouse_id` bigint NOT NULL COMMENT '退货出库仓库ID',
  `handler_id` bigint NOT NULL COMMENT '处理人ID',
  `total_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '退货总数量',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '退货总金额',
  `refund_amount` decimal(15,2) DEFAULT 0.00 COMMENT '退款金额',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-待审核 2-已审核 3-已出库 4-已退款 5-已驳回',
  `auditor_id` bigint COMMENT '审核人ID',
  `audit_time` datetime COMMENT '审核时间',
  `audit_remark` varchar(500) COMMENT '审核意见',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_return_no` (`return_no`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_return_date` (`return_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购退货单';

-- 采购退货明细
CREATE TABLE `erp_purchase_return_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `return_id` bigint NOT NULL COMMENT '退货单ID',
  `order_detail_id` bigint NOT NULL COMMENT '订单明细ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `batch_no` varchar(100) COMMENT '批次号',
  `original_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '原订购数量',
  `return_quantity` decimal(15,2) NOT NULL COMMENT '退货数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT 0.00 COMMENT '单价',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '退货金额',
  `quality_status` tinyint DEFAULT 1 COMMENT '货品状况：1-完好 2-损坏 3-过期',
  `location_id` bigint COMMENT '出库库位ID',
  `remark` varchar(500) COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_return_id` (`return_id`),
  KEY `idx_order_detail_id` (`order_detail_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购退货明细';

-- 库存调拨单
CREATE TABLE `erp_inventory_transfer` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `transfer_no` varchar(50) NOT NULL COMMENT '调拨单号',
  `transfer_date` date NOT NULL COMMENT '调拨日期',
  `from_warehouse_id` bigint NOT NULL COMMENT '调出仓库ID',
  `to_warehouse_id` bigint NOT NULL COMMENT '调入仓库ID',
  `applicant_id` bigint NOT NULL COMMENT '申请人ID',
  `transfer_reason` varchar(500) COMMENT '调拨原因',
  `total_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '调拨总数量',
  `total_amount` decimal(15,2) DEFAULT 0.00 COMMENT '调拨总金额',
  `status` tinyint DEFAULT 1 COMMENT '状态：1-待审核 2-已审核 3-已调出 4-已调入 5-已完成 6-已驳回',
  `auditor_id` bigint COMMENT '审核人ID',
  `audit_time` datetime COMMENT '审核时间',
  `audit_remark` varchar(500) COMMENT '审核意见',
  `out_operator_id` bigint COMMENT '调出操作员ID',
  `out_time` datetime COMMENT '调出时间',
  `in_operator_id` bigint COMMENT '调入操作员ID',
  `in_time` datetime COMMENT '调入时间',
  `remark` varchar(500) COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint COMMENT '创建人ID',
  `deleted` tinyint DEFAULT 0 COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_transfer_no` (`transfer_no`),
  KEY `idx_from_warehouse` (`from_warehouse_id`),
  KEY `idx_to_warehouse` (`to_warehouse_id`),
  KEY `idx_transfer_date` (`transfer_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存调拨单';

-- 库存调拨明细
CREATE TABLE `erp_inventory_transfer_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `transfer_id` bigint NOT NULL COMMENT '调拨单ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint COMMENT '变体ID',
  `batch_no` varchar(100) COMMENT '批次号',
  `from_location_id` bigint COMMENT '调出库位ID',
  `to_location_id` bigint COMMENT '调入库位ID',
  `transfer_quantity` decimal(15,2) NOT NULL COMMENT '调拨数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_cost` decimal(15,2) DEFAULT 0.00 COMMENT '单位成本',
  `total_cost` decimal(15,2) DEFAULT 0.00 COMMENT '总成本',
  `out_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '已调出数量',
  `in_quantity` decimal(15,2) DEFAULT 0.00 COMMENT '已调入数量',
  `remark` varchar(500) COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_transfer_id` (`transfer_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存调拨明细';

-- ==================================================
-- 初始化数据
-- ==================================================

-- 初始化计量单位数据
INSERT INTO `erp_unit` (`code`, `name`, `symbol`, `type`, `status`, `create_user_id`) VALUES
('PCS', '个', 'pcs', 1, 1, 1),
('SET', '套', 'set', 1, 1, 1),
('KG', '千克', 'kg', 3, 1, 1),
('G', '克', 'g', 3, 1, 1),
('TON', '吨', 't', 3, 1, 1),
('M', '米', 'm', 2, 1, 1),
('CM', '厘米', 'cm', 2, 1, 1),
('MM', '毫米', 'mm', 2, 1, 1),
('M3', '立方米', 'm³', 4, 1, 1),
('L', '升', 'L', 4, 1, 1),
('ML', '毫升', 'ml', 4, 1, 1),
('M2', '平方米', 'm²', 5, 1, 1);

-- 初始化产品分类数据
INSERT INTO `erp_product_category` (`parent_id`, `ancestors`, `code`, `name`, `sort`, `status`, `create_user_id`) VALUES
(0, '0', 'RAW', '原材料', 1, 1, 1),
(0, '0', 'SEMI', '半成品', 2, 1, 1),
(0, '0', 'FINISHED', '成品', 3, 1, 1),
(0, '0', 'CONSUMABLE', '消耗品', 4, 1, 1);

-- 初始化仓库数据
INSERT INTO `erp_warehouse` (`code`, `name`, `type`, `manager`, `status`, `create_user_id`) VALUES
('WH001', '原料仓库', 1, '张三', 1, 1),
('WH002', '成品仓库', 2, '李四', 1, 1),
('WH003', '半成品仓库', 3, '王五', 1, 1);