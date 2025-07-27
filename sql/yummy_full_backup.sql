-- MySQL dump 10.13  Distrib 9.3.0, for macos15 (x86_64)
--
-- Host: localhost    Database: yummy
-- ------------------------------------------------------
-- Server version	9.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `erp_accounts_payable`
--

DROP TABLE IF EXISTS `erp_accounts_payable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_accounts_payable` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ap_no` varchar(50) NOT NULL COMMENT '应付单号',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `order_id` bigint DEFAULT NULL COMMENT '关联订单ID',
  `invoice_no` varchar(100) DEFAULT NULL COMMENT '发票号',
  `ap_date` date NOT NULL COMMENT '应付日期',
  `due_date` date DEFAULT NULL COMMENT '到期日期',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT '1.0000' COMMENT '汇率',
  `ap_amount` decimal(15,2) NOT NULL COMMENT '应付金额',
  `paid_amount` decimal(15,2) DEFAULT '0.00' COMMENT '已付金额',
  `remaining_amount` decimal(15,2) DEFAULT '0.00' COMMENT '未付金额',
  `overdue_days` int DEFAULT '0' COMMENT '逾期天数',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-未付款 2-部分付款 3-已付款 4-逾期',
  `buyer_id` bigint DEFAULT NULL COMMENT '负责采购员ID',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ap_no` (`ap_no`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_ap_date` (`ap_date`),
  KEY `idx_due_date` (`due_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应付账款';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_accounts_payable`
--

LOCK TABLES `erp_accounts_payable` WRITE;
/*!40000 ALTER TABLE `erp_accounts_payable` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_accounts_payable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_accounts_receivable`
--

DROP TABLE IF EXISTS `erp_accounts_receivable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_accounts_receivable` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ar_no` varchar(50) NOT NULL COMMENT '应收单号',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `order_id` bigint DEFAULT NULL COMMENT '关联订单ID',
  `invoice_no` varchar(100) DEFAULT NULL COMMENT '发票号',
  `ar_date` date NOT NULL COMMENT '应收日期',
  `due_date` date DEFAULT NULL COMMENT '到期日期',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT '1.0000' COMMENT '汇率',
  `ar_amount` decimal(15,2) NOT NULL COMMENT '应收金额',
  `received_amount` decimal(15,2) DEFAULT '0.00' COMMENT '已收金额',
  `remaining_amount` decimal(15,2) DEFAULT '0.00' COMMENT '未收金额',
  `overdue_days` int DEFAULT '0' COMMENT '逾期天数',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-未收款 2-部分收款 3-已收款 4-逾期',
  `salesperson_id` bigint DEFAULT NULL COMMENT '负责销售员ID',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ar_no` (`ar_no`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_ar_date` (`ar_date`),
  KEY `idx_due_date` (`due_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应收账款';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_accounts_receivable`
--

LOCK TABLES `erp_accounts_receivable` WRITE;
/*!40000 ALTER TABLE `erp_accounts_receivable` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_accounts_receivable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_customer`
--

DROP TABLE IF EXISTS `erp_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_customer` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) NOT NULL COMMENT '客户编码',
  `name` varchar(100) NOT NULL COMMENT '客户名称',
  `short_name` varchar(50) DEFAULT NULL COMMENT '简称',
  `type` tinyint DEFAULT '1' COMMENT '客户类型：1-企业客户 2-个人客户',
  `level` tinyint DEFAULT '1' COMMENT '客户级别：1-VIP 2-普通 3-潜在',
  `industry` varchar(50) DEFAULT NULL COMMENT '所属行业',
  `contact_person` varchar(50) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `contact_email` varchar(100) DEFAULT NULL COMMENT '联系邮箱',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `credit_code` varchar(50) DEFAULT NULL COMMENT '统一社会信用代码',
  `credit_limit` decimal(15,2) DEFAULT '0.00' COMMENT '信用额度',
  `credit_days` int DEFAULT '0' COMMENT '信用期限（天）',
  `tax_rate` decimal(5,4) DEFAULT '0.0000' COMMENT '税率',
  `payment_terms` varchar(100) DEFAULT NULL COMMENT '付款条件',
  `delivery_terms` varchar(100) DEFAULT NULL COMMENT '交货条件',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `remark` text COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_customer_code` (`code`),
  KEY `idx_customer_name` (`name`),
  KEY `idx_customer_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_customer`
--

LOCK TABLES `erp_customer` WRITE;
/*!40000 ALTER TABLE `erp_customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_expense`
--

DROP TABLE IF EXISTS `erp_expense`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_expense` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `expense_no` varchar(50) NOT NULL COMMENT '费用单号',
  `expense_date` date NOT NULL COMMENT '费用日期',
  `expense_type` tinyint NOT NULL COMMENT '费用类型：1-差旅费 2-办公费 3-通讯费 4-招待费 5-培训费 6-其他',
  `dept_id` bigint NOT NULL COMMENT '费用部门ID',
  `applicant_id` bigint NOT NULL COMMENT '申请人ID',
  `expense_amount` decimal(15,2) NOT NULL COMMENT '费用金额',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT '1.0000' COMMENT '汇率',
  `description` varchar(500) NOT NULL COMMENT '费用说明',
  `receipt_count` int DEFAULT '0' COMMENT '发票张数',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-待审核 2-已审核 3-已驳回 4-已报销',
  `auditor_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核意见',
  `payment_date` date DEFAULT NULL COMMENT '报销日期',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_expense_no` (`expense_no`),
  KEY `idx_dept_id` (`dept_id`),
  KEY `idx_applicant_id` (`applicant_id`),
  KEY `idx_expense_date` (`expense_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='费用管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_expense`
--

LOCK TABLES `erp_expense` WRITE;
/*!40000 ALTER TABLE `erp_expense` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_expense` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_inventory`
--

DROP TABLE IF EXISTS `erp_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `location_id` bigint DEFAULT NULL COMMENT '库位ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `batch_no` varchar(100) DEFAULT NULL COMMENT '批次号',
  `current_stock` decimal(15,2) DEFAULT '0.00' COMMENT '当前库存',
  `available_stock` decimal(15,2) DEFAULT '0.00' COMMENT '可用库存',
  `locked_stock` decimal(15,2) DEFAULT '0.00' COMMENT '锁定库存',
  `cost_price` decimal(15,2) DEFAULT '0.00' COMMENT '成本价',
  `production_date` date DEFAULT NULL COMMENT '生产日期',
  `expiry_date` date DEFAULT NULL COMMENT '过期日期',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_inventory_unique` (`warehouse_id`,`location_id`,`product_id`,`variant_id`,`batch_no`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_variant_id` (`variant_id`),
  KEY `idx_batch_no` (`batch_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_inventory`
--

LOCK TABLES `erp_inventory` WRITE;
/*!40000 ALTER TABLE `erp_inventory` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_inventory_alert`
--

DROP TABLE IF EXISTS `erp_inventory_alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_inventory_alert` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `alert_type` tinyint NOT NULL COMMENT '预警类型：1-低库存 2-超库存 3-过期预警 4-呆滞预警',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `current_stock` decimal(15,2) DEFAULT '0.00' COMMENT '当前库存',
  `warning_stock` decimal(15,2) DEFAULT '0.00' COMMENT '预警库存',
  `alert_message` varchar(500) DEFAULT NULL COMMENT '预警信息',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-待处理 2-已处理 3-已忽略',
  `handler_id` bigint DEFAULT NULL COMMENT '处理人ID',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `handle_remark` varchar(500) DEFAULT NULL COMMENT '处理备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_product_warehouse` (`product_id`,`warehouse_id`),
  KEY `idx_alert_type` (`alert_type`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存预警记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_inventory_alert`
--

LOCK TABLES `erp_inventory_alert` WRITE;
/*!40000 ALTER TABLE `erp_inventory_alert` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_inventory_alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_inventory_check`
--

DROP TABLE IF EXISTS `erp_inventory_check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_inventory_check` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `check_no` varchar(50) NOT NULL COMMENT '盘点单号',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `check_date` date NOT NULL COMMENT '盘点日期',
  `check_type` tinyint DEFAULT '1' COMMENT '盘点类型：1-全盘 2-抽盘 3-循环盘点',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-盘点中 2-已完成 3-已审核',
  `checker_id` bigint DEFAULT NULL COMMENT '盘点员ID',
  `auditor_id` bigint DEFAULT NULL COMMENT '审核员ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_check_no` (`check_no`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_check_date` (`check_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存盘点单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_inventory_check`
--

LOCK TABLES `erp_inventory_check` WRITE;
/*!40000 ALTER TABLE `erp_inventory_check` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_inventory_check` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_inventory_check_detail`
--

DROP TABLE IF EXISTS `erp_inventory_check_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_inventory_check_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `check_id` bigint NOT NULL COMMENT '盘点单ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `batch_no` varchar(100) DEFAULT NULL COMMENT '批次号',
  `location_id` bigint DEFAULT NULL COMMENT '库位ID',
  `book_stock` decimal(15,2) DEFAULT '0.00' COMMENT '账面库存',
  `actual_stock` decimal(15,2) DEFAULT '0.00' COMMENT '实际库存',
  `diff_stock` decimal(15,2) DEFAULT '0.00' COMMENT '盘点差异',
  `cost_price` decimal(15,2) DEFAULT '0.00' COMMENT '成本价',
  `diff_amount` decimal(15,2) DEFAULT '0.00' COMMENT '差异金额',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_check_id` (`check_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存盘点明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_inventory_check_detail`
--

LOCK TABLES `erp_inventory_check_detail` WRITE;
/*!40000 ALTER TABLE `erp_inventory_check_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_inventory_check_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_inventory_transaction`
--

DROP TABLE IF EXISTS `erp_inventory_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_inventory_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `transaction_no` varchar(50) NOT NULL COMMENT '流水号',
  `transaction_type` tinyint NOT NULL COMMENT '流水类型：1-入库 2-出库 3-调拨 4-盘点',
  `business_type` tinyint NOT NULL COMMENT '业务类型：1-采购入库 2-销售出库 3-生产入库 4-生产出库 5-调拨入库 6-调拨出库 7-盘点盈亏 8-其他入库 9-其他出库',
  `reference_no` varchar(50) DEFAULT NULL COMMENT '关联单号',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `location_id` bigint DEFAULT NULL COMMENT '库位ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `batch_no` varchar(100) DEFAULT NULL COMMENT '批次号',
  `quantity` decimal(15,2) NOT NULL COMMENT '数量（正数入库，负数出库）',
  `unit_price` decimal(15,2) DEFAULT '0.00' COMMENT '单价',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '总金额',
  `before_stock` decimal(15,2) DEFAULT '0.00' COMMENT '变动前库存',
  `after_stock` decimal(15,2) DEFAULT '0.00' COMMENT '变动后库存',
  `operator_id` bigint DEFAULT NULL COMMENT '操作员ID',
  `transaction_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '流水时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_transaction_no` (`transaction_no`),
  KEY `idx_reference_no` (`reference_no`),
  KEY `idx_warehouse_product` (`warehouse_id`,`product_id`),
  KEY `idx_transaction_time` (`transaction_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存流水表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_inventory_transaction`
--

LOCK TABLES `erp_inventory_transaction` WRITE;
/*!40000 ALTER TABLE `erp_inventory_transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_inventory_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_inventory_transfer`
--

DROP TABLE IF EXISTS `erp_inventory_transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_inventory_transfer` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `transfer_no` varchar(50) NOT NULL COMMENT '调拨单号',
  `transfer_date` date NOT NULL COMMENT '调拨日期',
  `from_warehouse_id` bigint NOT NULL COMMENT '调出仓库ID',
  `to_warehouse_id` bigint NOT NULL COMMENT '调入仓库ID',
  `applicant_id` bigint NOT NULL COMMENT '申请人ID',
  `transfer_reason` varchar(500) DEFAULT NULL COMMENT '调拨原因',
  `total_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '调拨总数量',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '调拨总金额',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-待审核 2-已审核 3-已调出 4-已调入 5-已完成 6-已驳回',
  `auditor_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核意见',
  `out_operator_id` bigint DEFAULT NULL COMMENT '调出操作员ID',
  `out_time` datetime DEFAULT NULL COMMENT '调出时间',
  `in_operator_id` bigint DEFAULT NULL COMMENT '调入操作员ID',
  `in_time` datetime DEFAULT NULL COMMENT '调入时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_transfer_no` (`transfer_no`),
  KEY `idx_from_warehouse` (`from_warehouse_id`),
  KEY `idx_to_warehouse` (`to_warehouse_id`),
  KEY `idx_transfer_date` (`transfer_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存调拨单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_inventory_transfer`
--

LOCK TABLES `erp_inventory_transfer` WRITE;
/*!40000 ALTER TABLE `erp_inventory_transfer` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_inventory_transfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_inventory_transfer_detail`
--

DROP TABLE IF EXISTS `erp_inventory_transfer_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_inventory_transfer_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `transfer_id` bigint NOT NULL COMMENT '调拨单ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `batch_no` varchar(100) DEFAULT NULL COMMENT '批次号',
  `from_location_id` bigint DEFAULT NULL COMMENT '调出库位ID',
  `to_location_id` bigint DEFAULT NULL COMMENT '调入库位ID',
  `transfer_quantity` decimal(15,2) NOT NULL COMMENT '调拨数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_cost` decimal(15,2) DEFAULT '0.00' COMMENT '单位成本',
  `total_cost` decimal(15,2) DEFAULT '0.00' COMMENT '总成本',
  `out_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '已调出数量',
  `in_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '已调入数量',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_transfer_id` (`transfer_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存调拨明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_inventory_transfer_detail`
--

LOCK TABLES `erp_inventory_transfer_detail` WRITE;
/*!40000 ALTER TABLE `erp_inventory_transfer_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_inventory_transfer_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_payment_made`
--

DROP TABLE IF EXISTS `erp_payment_made`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_payment_made` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `payment_no` varchar(50) NOT NULL COMMENT '付款单号',
  `ap_id` bigint NOT NULL COMMENT '应付账款ID',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `payment_date` date NOT NULL COMMENT '付款日期',
  `payment_method` tinyint DEFAULT '1' COMMENT '付款方式：1-现金 2-银行转账 3-支票 4-承兑汇票 5-其他',
  `payment_account` varchar(100) DEFAULT NULL COMMENT '付款账户',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT '1.0000' COMMENT '汇率',
  `payment_amount` decimal(15,2) NOT NULL COMMENT '付款金额',
  `bank_charges` decimal(15,2) DEFAULT '0.00' COMMENT '银行手续费',
  `actual_amount` decimal(15,2) DEFAULT '0.00' COMMENT '实际付款金额',
  `reference_no` varchar(100) DEFAULT NULL COMMENT '参考号（银行流水号等）',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-已付款 2-已核销',
  `cashier_id` bigint DEFAULT NULL COMMENT '付款员ID',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_payment_no` (`payment_no`),
  KEY `idx_ap_id` (`ap_id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_payment_date` (`payment_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='付款记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_payment_made`
--

LOCK TABLES `erp_payment_made` WRITE;
/*!40000 ALTER TABLE `erp_payment_made` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_payment_made` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_payment_received`
--

DROP TABLE IF EXISTS `erp_payment_received`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_payment_received` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `payment_no` varchar(50) NOT NULL COMMENT '收款单号',
  `ar_id` bigint NOT NULL COMMENT '应收账款ID',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `payment_date` date NOT NULL COMMENT '收款日期',
  `payment_method` tinyint DEFAULT '1' COMMENT '收款方式：1-现金 2-银行转账 3-支票 4-承兑汇票 5-其他',
  `payment_account` varchar(100) DEFAULT NULL COMMENT '收款账户',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT '1.0000' COMMENT '汇率',
  `payment_amount` decimal(15,2) NOT NULL COMMENT '收款金额',
  `bank_charges` decimal(15,2) DEFAULT '0.00' COMMENT '银行手续费',
  `actual_amount` decimal(15,2) DEFAULT '0.00' COMMENT '实际到账金额',
  `reference_no` varchar(100) DEFAULT NULL COMMENT '参考号（银行流水号等）',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-已收款 2-已核销',
  `cashier_id` bigint DEFAULT NULL COMMENT '收款员ID',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_payment_no` (`payment_no`),
  KEY `idx_ar_id` (`ar_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_payment_date` (`payment_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='收款记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_payment_received`
--

LOCK TABLES `erp_payment_received` WRITE;
/*!40000 ALTER TABLE `erp_payment_received` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_payment_received` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_product`
--

DROP TABLE IF EXISTS `erp_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `category_id` bigint NOT NULL COMMENT '产品分类ID',
  `code` varchar(50) NOT NULL COMMENT '产品编码',
  `name` varchar(200) NOT NULL COMMENT '产品名称',
  `short_name` varchar(100) DEFAULT NULL COMMENT '产品简称',
  `sku` varchar(100) DEFAULT NULL COMMENT 'SKU编码',
  `barcode` varchar(50) DEFAULT NULL COMMENT '条形码',
  `brand` varchar(100) DEFAULT NULL COMMENT '品牌',
  `model` varchar(100) DEFAULT NULL COMMENT '型号',
  `specification` text COMMENT '规格参数',
  `unit_id` bigint NOT NULL COMMENT '基本单位ID',
  `weight` decimal(10,3) DEFAULT NULL COMMENT '重量（KG）',
  `volume` decimal(10,3) DEFAULT NULL COMMENT '体积（立方米）',
  `purchase_price` decimal(15,2) DEFAULT '0.00' COMMENT '采购价格',
  `sale_price` decimal(15,2) DEFAULT '0.00' COMMENT '销售价格',
  `cost_price` decimal(15,2) DEFAULT '0.00' COMMENT '成本价格',
  `min_stock` decimal(15,2) DEFAULT '0.00' COMMENT '最小库存',
  `max_stock` decimal(15,2) DEFAULT '0.00' COMMENT '最大库存',
  `safety_stock` decimal(15,2) DEFAULT '0.00' COMMENT '安全库存',
  `lead_time` int DEFAULT '0' COMMENT '采购周期（天）',
  `shelf_life` int DEFAULT '0' COMMENT '保质期（天）',
  `storage_condition` varchar(200) DEFAULT NULL COMMENT '存储条件',
  `main_image` varchar(500) DEFAULT NULL COMMENT '主图片URL',
  `description` text COMMENT '产品描述',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_code` (`code`),
  UNIQUE KEY `uk_product_sku` (`sku`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_product_name` (`name`),
  KEY `idx_barcode` (`barcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_product`
--

LOCK TABLES `erp_product` WRITE;
/*!40000 ALTER TABLE `erp_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_product_category`
--

DROP TABLE IF EXISTS `erp_product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_product_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint DEFAULT '0' COMMENT '父分类ID',
  `ancestors` varchar(500) DEFAULT NULL COMMENT '祖级列表',
  `code` varchar(50) NOT NULL COMMENT '分类编码',
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `sort` int DEFAULT '0' COMMENT '显示顺序',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `remark` text COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_category_code` (`code`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_category_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_product_category`
--

LOCK TABLES `erp_product_category` WRITE;
/*!40000 ALTER TABLE `erp_product_category` DISABLE KEYS */;
INSERT INTO `erp_product_category` VALUES (1,0,'0','RAW','原材料',1,NULL,1,NULL,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(2,0,'0','SEMI','半成品',2,NULL,1,NULL,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(3,0,'0','FINISHED','成品',3,NULL,1,NULL,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(4,0,'0','CONSUMABLE','消耗品',4,NULL,1,NULL,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0);
/*!40000 ALTER TABLE `erp_product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_product_image`
--

DROP TABLE IF EXISTS `erp_product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_product_image` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `image_url` varchar(500) NOT NULL COMMENT '图片URL',
  `image_name` varchar(200) DEFAULT NULL COMMENT '图片名称',
  `image_type` tinyint DEFAULT '1' COMMENT '图片类型：1-主图 2-详情图 3-规格图',
  `sort` int DEFAULT '0' COMMENT '排序',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_variant_id` (`variant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品图片表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_product_image`
--

LOCK TABLES `erp_product_image` WRITE;
/*!40000 ALTER TABLE `erp_product_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_product_variant`
--

DROP TABLE IF EXISTS `erp_product_variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_product_variant` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `code` varchar(50) NOT NULL COMMENT '变体编码',
  `name` varchar(200) NOT NULL COMMENT '变体名称',
  `sku` varchar(100) DEFAULT NULL COMMENT 'SKU编码',
  `barcode` varchar(50) DEFAULT NULL COMMENT '条形码',
  `attributes` json DEFAULT NULL COMMENT '变体属性（颜色、尺寸等）',
  `purchase_price` decimal(15,2) DEFAULT '0.00' COMMENT '采购价格',
  `sale_price` decimal(15,2) DEFAULT '0.00' COMMENT '销售价格',
  `cost_price` decimal(15,2) DEFAULT '0.00' COMMENT '成本价格',
  `weight` decimal(10,3) DEFAULT NULL COMMENT '重量（KG）',
  `image` varchar(500) DEFAULT NULL COMMENT '变体图片URL',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_variant_code` (`code`),
  UNIQUE KEY `uk_variant_sku` (`sku`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_variant_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品变体表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_product_variant`
--

LOCK TABLES `erp_product_variant` WRITE;
/*!40000 ALTER TABLE `erp_product_variant` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_product_variant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_purchase_order`
--

DROP TABLE IF EXISTS `erp_purchase_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_purchase_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `order_date` date NOT NULL COMMENT '订单日期',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `buyer_id` bigint NOT NULL COMMENT '采购员ID',
  `delivery_date` date DEFAULT NULL COMMENT '交货日期',
  `delivery_address` varchar(200) DEFAULT NULL COMMENT '交货地址',
  `payment_terms` varchar(100) DEFAULT NULL COMMENT '付款条件',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT '1.0000' COMMENT '汇率',
  `total_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '总数量',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '订单总金额',
  `tax_rate` decimal(5,4) DEFAULT '0.0000' COMMENT '税率',
  `tax_amount` decimal(15,2) DEFAULT '0.00' COMMENT '税额',
  `total_with_tax` decimal(15,2) DEFAULT '0.00' COMMENT '含税总金额',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-待确认 2-已确认 3-部分收货 4-已收货 5-已完成 6-已取消',
  `contract_no` varchar(100) DEFAULT NULL COMMENT '合同号',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_buyer_id` (`buyer_id`),
  KEY `idx_order_date` (`order_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_purchase_order`
--

LOCK TABLES `erp_purchase_order` WRITE;
/*!40000 ALTER TABLE `erp_purchase_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_purchase_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_purchase_order_detail`
--

DROP TABLE IF EXISTS `erp_purchase_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_purchase_order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `quantity` decimal(15,2) NOT NULL COMMENT '订购数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT '0.00' COMMENT '单价',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '总金额',
  `received_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '已收货数量',
  `remaining_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '未收货数量',
  `required_date` date DEFAULT NULL COMMENT '需求日期',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购订单明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_purchase_order_detail`
--

LOCK TABLES `erp_purchase_order_detail` WRITE;
/*!40000 ALTER TABLE `erp_purchase_order_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_purchase_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_purchase_receipt`
--

DROP TABLE IF EXISTS `erp_purchase_receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_purchase_receipt` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `receipt_no` varchar(50) NOT NULL COMMENT '收货单号',
  `order_id` bigint NOT NULL COMMENT '采购订单ID',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `receipt_date` date NOT NULL COMMENT '收货日期',
  `warehouse_id` bigint NOT NULL COMMENT '收货仓库ID',
  `receiver_id` bigint NOT NULL COMMENT '收货员ID',
  `delivery_note_no` varchar(100) DEFAULT NULL COMMENT '送货单号',
  `transport_mode` varchar(50) DEFAULT NULL COMMENT '运输方式',
  `transport_cost` decimal(15,2) DEFAULT '0.00' COMMENT '运费',
  `total_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '收货总数量',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '收货总金额',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-待质检 2-质检通过 3-质检不通过 4-已入库',
  `quality_inspector_id` bigint DEFAULT NULL COMMENT '质检员ID',
  `quality_result` tinyint DEFAULT NULL COMMENT '质检结果：1-合格 2-不合格',
  `quality_remark` varchar(500) DEFAULT NULL COMMENT '质检备注',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_receipt_no` (`receipt_no`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_receipt_date` (`receipt_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购收货单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_purchase_receipt`
--

LOCK TABLES `erp_purchase_receipt` WRITE;
/*!40000 ALTER TABLE `erp_purchase_receipt` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_purchase_receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_purchase_receipt_detail`
--

DROP TABLE IF EXISTS `erp_purchase_receipt_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_purchase_receipt_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `receipt_id` bigint NOT NULL COMMENT '收货单ID',
  `order_detail_id` bigint NOT NULL COMMENT '订单明细ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `batch_no` varchar(100) DEFAULT NULL COMMENT '批次号',
  `production_date` date DEFAULT NULL COMMENT '生产日期',
  `expiry_date` date DEFAULT NULL COMMENT '过期日期',
  `ordered_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '订购数量',
  `received_quantity` decimal(15,2) NOT NULL COMMENT '收货数量',
  `qualified_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '合格数量',
  `unqualified_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '不合格数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT '0.00' COMMENT '单价',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '总金额',
  `location_id` bigint DEFAULT NULL COMMENT '存放库位ID',
  `quality_result` tinyint DEFAULT NULL COMMENT '质检结果：1-合格 2-不合格',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_receipt_id` (`receipt_id`),
  KEY `idx_order_detail_id` (`order_detail_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购收货明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_purchase_receipt_detail`
--

LOCK TABLES `erp_purchase_receipt_detail` WRITE;
/*!40000 ALTER TABLE `erp_purchase_receipt_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_purchase_receipt_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_purchase_request`
--

DROP TABLE IF EXISTS `erp_purchase_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_purchase_request` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `request_no` varchar(50) NOT NULL COMMENT '申请单号',
  `request_date` date NOT NULL COMMENT '申请日期',
  `dept_id` bigint NOT NULL COMMENT '申请部门ID',
  `requester_id` bigint NOT NULL COMMENT '申请人ID',
  `request_reason` varchar(500) DEFAULT NULL COMMENT '申请原因',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '申请总金额',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-待审核 2-已审核 3-已驳回 4-已完成',
  `auditor_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核意见',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_request_no` (`request_no`),
  KEY `idx_dept_id` (`dept_id`),
  KEY `idx_requester_id` (`requester_id`),
  KEY `idx_request_date` (`request_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购申请单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_purchase_request`
--

LOCK TABLES `erp_purchase_request` WRITE;
/*!40000 ALTER TABLE `erp_purchase_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_purchase_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_purchase_request_detail`
--

DROP TABLE IF EXISTS `erp_purchase_request_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_purchase_request_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `request_id` bigint NOT NULL COMMENT '申请单ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `quantity` decimal(15,2) NOT NULL COMMENT '申请数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `estimated_price` decimal(15,2) DEFAULT '0.00' COMMENT '预估价格',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '总金额',
  `required_date` date DEFAULT NULL COMMENT '需求日期',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_request_id` (`request_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购申请明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_purchase_request_detail`
--

LOCK TABLES `erp_purchase_request_detail` WRITE;
/*!40000 ALTER TABLE `erp_purchase_request_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_purchase_request_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_purchase_return`
--

DROP TABLE IF EXISTS `erp_purchase_return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_purchase_return` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `return_no` varchar(50) NOT NULL COMMENT '退货单号',
  `order_id` bigint NOT NULL COMMENT '关联采购订单ID',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `return_date` date NOT NULL COMMENT '退货日期',
  `return_reason` varchar(500) DEFAULT NULL COMMENT '退货原因',
  `warehouse_id` bigint NOT NULL COMMENT '退货出库仓库ID',
  `handler_id` bigint NOT NULL COMMENT '处理人ID',
  `total_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '退货总数量',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '退货总金额',
  `refund_amount` decimal(15,2) DEFAULT '0.00' COMMENT '退款金额',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-待审核 2-已审核 3-已出库 4-已退款 5-已驳回',
  `auditor_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核意见',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_return_no` (`return_no`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_return_date` (`return_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购退货单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_purchase_return`
--

LOCK TABLES `erp_purchase_return` WRITE;
/*!40000 ALTER TABLE `erp_purchase_return` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_purchase_return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_purchase_return_detail`
--

DROP TABLE IF EXISTS `erp_purchase_return_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_purchase_return_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `return_id` bigint NOT NULL COMMENT '退货单ID',
  `order_detail_id` bigint NOT NULL COMMENT '订单明细ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `batch_no` varchar(100) DEFAULT NULL COMMENT '批次号',
  `original_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '原订购数量',
  `return_quantity` decimal(15,2) NOT NULL COMMENT '退货数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT '0.00' COMMENT '单价',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '退货金额',
  `quality_status` tinyint DEFAULT '1' COMMENT '货品状况：1-完好 2-损坏 3-过期',
  `location_id` bigint DEFAULT NULL COMMENT '出库库位ID',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_return_id` (`return_id`),
  KEY `idx_order_detail_id` (`order_detail_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购退货明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_purchase_return_detail`
--

LOCK TABLES `erp_purchase_return_detail` WRITE;
/*!40000 ALTER TABLE `erp_purchase_return_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_purchase_return_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_sales_order`
--

DROP TABLE IF EXISTS `erp_sales_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_sales_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `order_date` date NOT NULL COMMENT '订单日期',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `salesperson_id` bigint NOT NULL COMMENT '销售员ID',
  `quotation_id` bigint DEFAULT NULL COMMENT '关联报价单ID',
  `delivery_date` date DEFAULT NULL COMMENT '交货日期',
  `delivery_address` varchar(200) DEFAULT NULL COMMENT '交货地址',
  `payment_terms` varchar(100) DEFAULT NULL COMMENT '付款条件',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT '1.0000' COMMENT '汇率',
  `total_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '总数量',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '订单总金额',
  `tax_rate` decimal(5,4) DEFAULT '0.0000' COMMENT '税率',
  `tax_amount` decimal(15,2) DEFAULT '0.00' COMMENT '税额',
  `total_with_tax` decimal(15,2) DEFAULT '0.00' COMMENT '含税总金额',
  `received_amount` decimal(15,2) DEFAULT '0.00' COMMENT '已收款金额',
  `remaining_amount` decimal(15,2) DEFAULT '0.00' COMMENT '待收款金额',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-待确认 2-已确认 3-生产中 4-待发货 5-部分发货 6-已发货 7-已完成 8-已取消',
  `contract_no` varchar(100) DEFAULT NULL COMMENT '合同号',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_salesperson_id` (`salesperson_id`),
  KEY `idx_order_date` (`order_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_sales_order`
--

LOCK TABLES `erp_sales_order` WRITE;
/*!40000 ALTER TABLE `erp_sales_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_sales_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_sales_order_detail`
--

DROP TABLE IF EXISTS `erp_sales_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_sales_order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `quantity` decimal(15,2) NOT NULL COMMENT '订购数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT '0.00' COMMENT '单价',
  `discount_rate` decimal(5,4) DEFAULT '0.0000' COMMENT '折扣率',
  `discount_amount` decimal(15,2) DEFAULT '0.00' COMMENT '折扣金额',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '总金额',
  `shipped_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '已发货数量',
  `remaining_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '未发货数量',
  `delivery_date` date DEFAULT NULL COMMENT '交货日期',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售订单明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_sales_order_detail`
--

LOCK TABLES `erp_sales_order_detail` WRITE;
/*!40000 ALTER TABLE `erp_sales_order_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_sales_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_sales_quotation`
--

DROP TABLE IF EXISTS `erp_sales_quotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_sales_quotation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `quotation_no` varchar(50) NOT NULL COMMENT '报价单号',
  `quotation_date` date NOT NULL COMMENT '报价日期',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `salesperson_id` bigint NOT NULL COMMENT '销售员ID',
  `valid_until` date DEFAULT NULL COMMENT '有效期至',
  `currency` varchar(10) DEFAULT 'CNY' COMMENT '币种',
  `exchange_rate` decimal(10,4) DEFAULT '1.0000' COMMENT '汇率',
  `payment_terms` varchar(100) DEFAULT NULL COMMENT '付款条件',
  `delivery_terms` varchar(100) DEFAULT NULL COMMENT '交货条件',
  `total_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '总数量',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '报价总金额',
  `tax_rate` decimal(5,4) DEFAULT '0.0000' COMMENT '税率',
  `tax_amount` decimal(15,2) DEFAULT '0.00' COMMENT '税额',
  `total_with_tax` decimal(15,2) DEFAULT '0.00' COMMENT '含税总金额',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-草稿 2-已发送 3-客户确认 4-已过期 5-已取消',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_quotation_no` (`quotation_no`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_salesperson_id` (`salesperson_id`),
  KEY `idx_quotation_date` (`quotation_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售报价单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_sales_quotation`
--

LOCK TABLES `erp_sales_quotation` WRITE;
/*!40000 ALTER TABLE `erp_sales_quotation` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_sales_quotation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_sales_quotation_detail`
--

DROP TABLE IF EXISTS `erp_sales_quotation_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_sales_quotation_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `quotation_id` bigint NOT NULL COMMENT '报价单ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `quantity` decimal(15,2) NOT NULL COMMENT '报价数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT '0.00' COMMENT '单价',
  `discount_rate` decimal(5,4) DEFAULT '0.0000' COMMENT '折扣率',
  `discount_amount` decimal(15,2) DEFAULT '0.00' COMMENT '折扣金额',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '总金额',
  `delivery_date` date DEFAULT NULL COMMENT '交货日期',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_quotation_id` (`quotation_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售报价明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_sales_quotation_detail`
--

LOCK TABLES `erp_sales_quotation_detail` WRITE;
/*!40000 ALTER TABLE `erp_sales_quotation_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_sales_quotation_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_sales_return`
--

DROP TABLE IF EXISTS `erp_sales_return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_sales_return` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `return_no` varchar(50) NOT NULL COMMENT '退货单号',
  `order_id` bigint NOT NULL COMMENT '关联销售订单ID',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `return_date` date NOT NULL COMMENT '退货日期',
  `return_reason` varchar(500) DEFAULT NULL COMMENT '退货原因',
  `warehouse_id` bigint NOT NULL COMMENT '退货入库仓库ID',
  `handler_id` bigint NOT NULL COMMENT '处理人ID',
  `total_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '退货总数量',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '退货总金额',
  `refund_amount` decimal(15,2) DEFAULT '0.00' COMMENT '退款金额',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-待审核 2-已审核 3-已入库 4-已退款 5-已驳回',
  `auditor_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核意见',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_return_no` (`return_no`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_return_date` (`return_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售退货单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_sales_return`
--

LOCK TABLES `erp_sales_return` WRITE;
/*!40000 ALTER TABLE `erp_sales_return` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_sales_return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_sales_return_detail`
--

DROP TABLE IF EXISTS `erp_sales_return_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_sales_return_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `return_id` bigint NOT NULL COMMENT '退货单ID',
  `order_detail_id` bigint NOT NULL COMMENT '订单明细ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `batch_no` varchar(100) DEFAULT NULL COMMENT '批次号',
  `original_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '原订购数量',
  `return_quantity` decimal(15,2) NOT NULL COMMENT '退货数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT '0.00' COMMENT '单价',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '退货金额',
  `quality_status` tinyint DEFAULT '1' COMMENT '货品状况：1-完好 2-损坏 3-过期',
  `location_id` bigint DEFAULT NULL COMMENT '入库库位ID',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_return_id` (`return_id`),
  KEY `idx_order_detail_id` (`order_detail_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售退货明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_sales_return_detail`
--

LOCK TABLES `erp_sales_return_detail` WRITE;
/*!40000 ALTER TABLE `erp_sales_return_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_sales_return_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_sales_shipment`
--

DROP TABLE IF EXISTS `erp_sales_shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_sales_shipment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `shipment_no` varchar(50) NOT NULL COMMENT '出库单号',
  `order_id` bigint NOT NULL COMMENT '销售订单ID',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `shipment_date` date NOT NULL COMMENT '出库日期',
  `warehouse_id` bigint NOT NULL COMMENT '出库仓库ID',
  `shipper_id` bigint NOT NULL COMMENT '发货员ID',
  `delivery_address` varchar(200) DEFAULT NULL COMMENT '发货地址',
  `transport_mode` varchar(50) DEFAULT NULL COMMENT '运输方式',
  `transport_cost` decimal(15,2) DEFAULT '0.00' COMMENT '运费',
  `tracking_no` varchar(100) DEFAULT NULL COMMENT '物流单号',
  `total_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '出库总数量',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '出库总金额',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-已出库 2-运输中 3-已送达 4-客户确认',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_shipment_no` (`shipment_no`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_shipment_date` (`shipment_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售出库单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_sales_shipment`
--

LOCK TABLES `erp_sales_shipment` WRITE;
/*!40000 ALTER TABLE `erp_sales_shipment` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_sales_shipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_sales_shipment_detail`
--

DROP TABLE IF EXISTS `erp_sales_shipment_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_sales_shipment_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `shipment_id` bigint NOT NULL COMMENT '出库单ID',
  `order_detail_id` bigint NOT NULL COMMENT '订单明细ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `variant_id` bigint DEFAULT NULL COMMENT '变体ID',
  `batch_no` varchar(100) DEFAULT NULL COMMENT '批次号',
  `ordered_quantity` decimal(15,2) DEFAULT '0.00' COMMENT '订购数量',
  `shipped_quantity` decimal(15,2) NOT NULL COMMENT '出库数量',
  `unit_id` bigint NOT NULL COMMENT '单位ID',
  `unit_price` decimal(15,2) DEFAULT '0.00' COMMENT '单价',
  `total_amount` decimal(15,2) DEFAULT '0.00' COMMENT '总金额',
  `location_id` bigint DEFAULT NULL COMMENT '出库库位ID',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_shipment_id` (`shipment_id`),
  KEY `idx_order_detail_id` (`order_detail_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售出库明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_sales_shipment_detail`
--

LOCK TABLES `erp_sales_shipment_detail` WRITE;
/*!40000 ALTER TABLE `erp_sales_shipment_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_sales_shipment_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_supplier`
--

DROP TABLE IF EXISTS `erp_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_supplier` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) NOT NULL COMMENT '供应商编码',
  `name` varchar(100) NOT NULL COMMENT '供应商名称',
  `short_name` varchar(50) DEFAULT NULL COMMENT '简称',
  `type` tinyint DEFAULT '1' COMMENT '供应商类型：1-生产商 2-贸易商 3-服务商',
  `level` tinyint DEFAULT '1' COMMENT '供应商级别：1-A级 2-B级 3-C级',
  `contact_person` varchar(50) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `contact_email` varchar(100) DEFAULT NULL COMMENT '联系邮箱',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `credit_code` varchar(50) DEFAULT NULL COMMENT '统一社会信用代码',
  `bank_name` varchar(100) DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(50) DEFAULT NULL COMMENT '银行账户',
  `tax_rate` decimal(5,4) DEFAULT '0.0000' COMMENT '税率',
  `payment_terms` varchar(100) DEFAULT NULL COMMENT '付款条件',
  `delivery_terms` varchar(100) DEFAULT NULL COMMENT '交货条件',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `remark` text COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_supplier_code` (`code`),
  KEY `idx_supplier_name` (`name`),
  KEY `idx_supplier_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='供应商信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_supplier`
--

LOCK TABLES `erp_supplier` WRITE;
/*!40000 ALTER TABLE `erp_supplier` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_supplier_evaluation`
--

DROP TABLE IF EXISTS `erp_supplier_evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_supplier_evaluation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `evaluation_date` date NOT NULL COMMENT '评价日期',
  `evaluator_id` bigint NOT NULL COMMENT '评价人ID',
  `quality_score` tinyint DEFAULT '0' COMMENT '质量评分（0-100）',
  `delivery_score` tinyint DEFAULT '0' COMMENT '交期评分（0-100）',
  `service_score` tinyint DEFAULT '0' COMMENT '服务评分（0-100）',
  `price_score` tinyint DEFAULT '0' COMMENT '价格评分（0-100）',
  `total_score` decimal(5,2) DEFAULT '0.00' COMMENT '综合评分',
  `evaluation_period` varchar(20) DEFAULT NULL COMMENT '评价周期（如：2025Q1）',
  `comments` text COMMENT '评价意见',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_evaluation_date` (`evaluation_date`),
  KEY `idx_evaluator_id` (`evaluator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='供应商评价';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_supplier_evaluation`
--

LOCK TABLES `erp_supplier_evaluation` WRITE;
/*!40000 ALTER TABLE `erp_supplier_evaluation` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_supplier_evaluation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_unit`
--

DROP TABLE IF EXISTS `erp_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_unit` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(20) NOT NULL COMMENT '单位编码',
  `name` varchar(50) NOT NULL COMMENT '单位名称',
  `symbol` varchar(10) DEFAULT NULL COMMENT '单位符号',
  `type` tinyint DEFAULT '1' COMMENT '单位类型：1-基本单位 2-长度 3-重量 4-体积 5-面积',
  `base_unit_id` bigint DEFAULT NULL COMMENT '基础单位ID',
  `conversion_rate` decimal(15,6) DEFAULT '1.000000' COMMENT '转换比率',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_unit_code` (`code`),
  KEY `idx_unit_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='计量单位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_unit`
--

LOCK TABLES `erp_unit` WRITE;
/*!40000 ALTER TABLE `erp_unit` DISABLE KEYS */;
INSERT INTO `erp_unit` VALUES (1,'PCS','个','pcs',1,NULL,1.000000,1,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(2,'SET','套','set',1,NULL,1.000000,1,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(3,'KG','千克','kg',3,NULL,1.000000,1,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(4,'G','克','g',3,NULL,1.000000,1,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(5,'TON','吨','t',3,NULL,1.000000,1,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(6,'M','米','m',2,NULL,1.000000,1,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(7,'CM','厘米','cm',2,NULL,1.000000,1,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(8,'MM','毫米','mm',2,NULL,1.000000,1,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(9,'M3','立方米','m³',4,NULL,1.000000,1,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(10,'L','升','L',4,NULL,1.000000,1,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(11,'ML','毫升','ml',4,NULL,1.000000,1,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(12,'M2','平方米','m²',5,NULL,1.000000,1,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0);
/*!40000 ALTER TABLE `erp_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_warehouse`
--

DROP TABLE IF EXISTS `erp_warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_warehouse` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) NOT NULL COMMENT '仓库编码',
  `name` varchar(100) NOT NULL COMMENT '仓库名称',
  `type` tinyint DEFAULT '1' COMMENT '仓库类型：1-原料仓 2-成品仓 3-半成品仓 4-次品仓',
  `address` varchar(200) DEFAULT NULL COMMENT '仓库地址',
  `manager` varchar(50) DEFAULT NULL COMMENT '仓库管理员',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `area` decimal(10,2) DEFAULT NULL COMMENT '仓库面积（平方米）',
  `capacity` decimal(15,2) DEFAULT NULL COMMENT '存储容量',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `remark` text COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_warehouse_code` (`code`),
  KEY `idx_warehouse_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='仓库信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_warehouse`
--

LOCK TABLES `erp_warehouse` WRITE;
/*!40000 ALTER TABLE `erp_warehouse` DISABLE KEYS */;
INSERT INTO `erp_warehouse` VALUES (1,'WH001','原料仓库',1,NULL,'张三',NULL,NULL,NULL,1,NULL,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(2,'WH002','成品仓库',2,NULL,'李四',NULL,NULL,NULL,1,NULL,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0),(3,'WH003','半成品仓库',3,NULL,'王五',NULL,NULL,NULL,1,NULL,'2025-07-27 20:28:07','2025-07-27 20:28:07',1,0);
/*!40000 ALTER TABLE `erp_warehouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erp_warehouse_location`
--

DROP TABLE IF EXISTS `erp_warehouse_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `erp_warehouse_location` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `code` varchar(50) NOT NULL COMMENT '库位编码',
  `name` varchar(100) NOT NULL COMMENT '库位名称',
  `type` tinyint DEFAULT '1' COMMENT '库位类型：1-普通库位 2-冷藏库位 3-危险品库位',
  `capacity` decimal(15,2) DEFAULT NULL COMMENT '存储容量',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `remark` text COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标识：0-正常 1-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_location_code` (`code`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_location_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `erp_warehouse_location`
--

LOCK TABLES `erp_warehouse_location` WRITE;
/*!40000 ALTER TABLE `erp_warehouse_location` DISABLE KEYS */;
/*!40000 ALTER TABLE `erp_warehouse_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `parent_id` bigint DEFAULT '0' COMMENT '父部门ID',
  `ancestors` varchar(500) DEFAULT NULL COMMENT '祖级列表',
  `name` varchar(50) NOT NULL COMMENT '部门名称',
  `sort` int DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(50) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `description` varchar(500) DEFAULT NULL COMMENT '部门描述',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (1,0,'0','悠米科技有限公司',1,'管理员','19900006962','326010228@qq.com',1,'本部',1,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(2,1,'0,1','广州总部',1,NULL,NULL,NULL,1,'',1,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(3,2,'0,1,2','研发部',1,NULL,NULL,NULL,1,'',1,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(4,2,'0,1,2','UI部',2,NULL,NULL,NULL,1,'',1,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(5,2,'0,1,2','测试部',3,NULL,NULL,NULL,1,'',1,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(6,2,'0,1,2','运维部',4,NULL,NULL,NULL,1,'',1,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(7,3,'0,1,2,3','研发一组',1,NULL,NULL,NULL,1,'',1,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(8,3,'0,1,2,3','研发二组',2,NULL,NULL,NULL,1,'',1,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(9,3,'0,1,2,3','研发三组',3,NULL,NULL,NULL,1,'禁用测试',1,'2025-07-27 15:26:33','2025-07-27 21:46:38',1);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `code` varchar(100) NOT NULL COMMENT '字典编码',
  `sort` int DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `description` varchar(500) DEFAULT NULL COMMENT '字典描述',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` VALUES (1,'性别','gender',1,1,'性别字典',1,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(2,'状态','status',2,1,'通用状态字典',1,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(3,'爱好','hobbys',3,1,'基础表单-兴趣爱好',1,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(4,'测试','test',0,1,'1',NULL,'2025-07-27 20:08:23','2025-07-27 20:08:28',1);
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典数据ID',
  `dict_id` bigint NOT NULL COMMENT '字典ID',
  `dict_code` varchar(100) NOT NULL COMMENT '字典编码',
  `name` varchar(100) NOT NULL COMMENT '字典标签',
  `value` varchar(100) NOT NULL COMMENT '字典键值',
  `sort` int DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `color` varchar(50) DEFAULT NULL COMMENT '标签颜色',
  `css_class` varchar(100) DEFAULT NULL COMMENT 'CSS类名',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` tinyint(1) DEFAULT '0' COMMENT '是否默认：0-否 1-是',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_dict_id` (`dict_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_dict_code` (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,1,'gender','男','1',1,1,'blue','','',0,'',1,'2025-07-27 15:26:33','2025-07-27 19:10:51',0),(2,1,'gender','女','2',2,1,'pink','','',0,'',1,'2025-07-27 15:26:33','2025-07-27 19:10:51',0),(3,1,'gender','未知','3',3,1,'gray','','',0,'',1,'2025-07-27 15:26:33','2025-07-27 19:10:51',0),(4,2,'status','正常','1',1,1,'green','','',1,'',1,'2025-07-27 15:26:33','2025-07-27 19:10:51',0),(5,2,'status','禁用','0',2,1,'red','','',0,'',1,'2025-07-27 15:26:33','2025-07-27 19:10:51',0),(6,3,'hobbys','运动','运动',1,1,'red','','',0,'',1,'2025-07-27 15:26:33','2025-07-27 19:10:51',0),(7,3,'hobbys','音乐','音乐',2,1,'green','','',0,'',1,'2025-07-27 15:26:33','2025-07-27 19:10:51',0),(8,3,'hobbys','电影','电影',3,1,'cyan','','',0,'',1,'2025-07-27 15:26:33','2025-07-27 19:10:51',0),(9,3,'hobbys','旅行','旅行',4,1,'arcoblue','','',0,'',1,'2025-07-27 15:26:33','2025-07-27 19:10:51',0),(10,3,'hobbys','美食','美食',5,1,'purple','','',0,'',1,'2025-07-27 15:26:33','2025-07-27 19:10:51',0);
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `path` varchar(200) DEFAULT NULL COMMENT '路由地址',
  `component` varchar(200) DEFAULT NULL COMMENT '组件路径',
  `redirect` varchar(200) DEFAULT NULL COMMENT '重定向地址',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '菜单类型：1-目录 2-菜单 3-按钮',
  `title` varchar(50) NOT NULL COMMENT '菜单名称',
  `svg_icon` varchar(100) DEFAULT NULL COMMENT 'SVG图标',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `keep_alive` tinyint(1) DEFAULT '0' COMMENT '是否缓存：0-不缓存 1-缓存',
  `hidden` tinyint(1) DEFAULT '0' COMMENT '是否隐藏：0-显示 1-隐藏',
  `sort` int DEFAULT '0' COMMENT '显示顺序',
  `active_menu` varchar(200) DEFAULT NULL COMMENT '当前激活的菜单',
  `breadcrumb` tinyint(1) DEFAULT '1' COMMENT '是否显示面包屑：0-不显示 1-显示',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `permission` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `show_in_tabs` tinyint(1) DEFAULT '1' COMMENT '是否显示在标签页：0-不显示 1-显示',
  `always_show` tinyint(1) DEFAULT '0' COMMENT '是否总是显示：0-不显示 1-显示',
  `affix` tinyint(1) DEFAULT '0' COMMENT '是否固定在标签页：0-不固定 1-固定',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=810234 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,0,'/analyse','Layout','/analyse/index',1,'分析页','','',0,0,2,'',1,1,'',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 17:17:02',1),(2,0,'/data','Layout','/data/index',1,'数据管理','menu-data','icon-list',1,0,3,'',1,1,'',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 17:17:20',1),(8,0,'/system','Layout','noRedirect',1,'系统管理','menu-system','IconUser',0,0,99,'',1,1,'',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 20:07:31',0),(81,0,'/test','Layout','noRedirect',1,'权限测试','item-github','',0,0,10,'',1,1,'',1,1,0,NULL,'2025-07-27 15:26:33','2025-07-27 19:13:03',1),(101,1,'/analyse/index','analyse/index','',2,'分析页','menu-chart','',0,0,1,'',0,1,'',1,0,1,NULL,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(201,2,'/data/index','data/main/index','',2,'数据管理','','icon-list',1,0,0,'',0,1,'',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(202,2,'/data/detail/:id','data/detail/index','',2,'详情','','',0,1,0,'/data/index',1,1,'',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(203,2,'/data/form','data/form/index','',2,'新增','','',0,1,0,'/data/index',1,1,'',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(801,8,'/system/user','system/user/index','',2,'用户管理','','icon-user',0,0,1,'',1,1,'',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 21:10:34',0),(802,8,'/system/role','system/role/index','',2,'角色管理','','icon-common',0,0,2,'',1,1,'',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(803,8,'/system/dept','system/dept/index','',2,'部门管理','','icon-mind-mapping',0,0,3,'',1,1,'',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(804,8,'/system/menu','system/menu/index','',2,'菜单管理','','icon-menu',0,0,4,'',1,1,'',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(805,8,'/system/dict','system/dict/index','',2,'字典管理','','icon-bookmark',0,0,5,'',1,1,'',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(8102,81,'/test/page2','@/views/test/page2/index.vue','',2,'测试页面2','avatar-man','icon-menu',0,0,0,'',1,1,'',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 19:13:00',1),(810201,8102,'','','',3,'按钮新增','','',0,0,0,'',1,1,'user:btn:add',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(810202,8102,'','','',3,'按钮编辑','','',0,0,0,'',1,1,'user:btn:edit',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(810203,8102,'','','',3,'按钮删除','','',0,0,0,'',1,1,'user:btn:delete',1,0,0,NULL,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(810204,801,'','','',3,'ces','','',0,1,1,NULL,1,1,'asd',1,0,0,NULL,'2025-07-27 17:26:09','2025-07-27 17:26:16',1),(810205,801,'','','',3,'删除用户','','',0,1,0,NULL,1,1,'sys:user:del',1,0,0,NULL,'2025-07-27 21:17:52','2025-07-27 21:17:52',0),(810206,801,'','','',3,'新增用户','','',0,1,0,NULL,1,1,'sys:user:add',1,0,0,NULL,'2025-07-27 21:19:27','2025-07-27 21:19:27',0),(810207,801,'','','',3,'编辑用户','','',0,1,0,NULL,1,1,'sys:user:edit',1,0,0,NULL,'2025-07-27 21:19:46','2025-07-27 21:19:46',0),(810208,802,'','','',3,'新增角色','','',0,1,0,NULL,1,1,'sys:user:add',1,0,0,NULL,'2025-07-27 21:23:56','2025-07-27 21:23:56',0),(810209,802,'','','',3,'删除角色','','',0,1,0,NULL,1,1,'sys:role:del',1,0,0,NULL,'2025-07-27 21:24:12','2025-07-27 21:24:12',0),(810210,802,'','','',3,'编辑角色','','',0,1,0,NULL,1,1,'sys:role:edit',1,0,0,NULL,'2025-07-27 21:24:36','2025-07-27 21:24:36',0),(810211,802,'','','',3,'查看角色','','',0,1,0,NULL,1,1,'sys:role:list',1,0,0,NULL,'2025-07-27 21:25:03','2025-07-27 21:25:03',0),(810212,801,'','','',3,'查看用户','','',0,1,0,NULL,1,1,'sys:user:list',1,0,0,NULL,'2025-07-27 21:25:21','2025-07-27 21:25:21',0),(810213,803,'','','',3,'查看部门','','',0,1,0,NULL,1,1,'sys:dept:list',1,0,0,NULL,'2025-07-27 21:25:46','2025-07-27 21:25:46',0),(810214,803,'','','',3,'新增部门','','',0,1,0,NULL,1,1,'sys:dept:add',1,0,0,NULL,'2025-07-27 21:26:02','2025-07-27 21:26:02',0),(810215,803,'','','',3,'编辑部门','','',0,1,0,NULL,1,1,'sys:dept:edit',1,0,0,NULL,'2025-07-27 21:26:21','2025-07-27 21:26:21',0),(810216,803,'','','',3,'删除部门','','',0,1,0,NULL,1,1,'sys:dept:del',1,0,0,NULL,'2025-07-27 21:26:46','2025-07-27 21:26:46',0),(810217,803,'','','',3,'部门详情','','',0,1,0,NULL,1,1,'sys:dept:detail',1,0,0,NULL,'2025-07-27 21:27:46','2025-07-27 21:27:46',0),(810218,804,'','','',3,'新增菜单','','',0,1,0,NULL,1,1,'sys:menu:add',1,0,0,NULL,'2025-07-27 21:28:06','2025-07-27 21:28:06',0),(810219,804,'','','',3,'删除菜单','','',0,1,0,NULL,1,1,'sys:menu:del',1,0,0,NULL,'2025-07-27 21:28:21','2025-07-27 21:28:21',0),(810220,804,'','','',3,'编辑菜单','','',0,1,0,NULL,1,1,'sys:menu:edit',1,0,0,NULL,'2025-07-27 21:28:34','2025-07-27 21:28:34',0),(810221,804,'','','',3,'查看菜单','','',0,1,0,NULL,1,1,'sys:menu:list',1,0,0,NULL,'2025-07-27 21:29:09','2025-07-27 21:29:09',0),(810222,804,'','','',3,'菜单详情','','',0,1,0,NULL,1,1,'sys:menu:detail',1,0,0,NULL,'2025-07-27 21:29:29','2025-07-27 21:29:29',0),(810223,805,'','','',3,'新增字典','','',0,1,0,NULL,1,1,'sys:dict:add',1,0,0,NULL,'2025-07-27 21:29:51','2025-07-27 21:29:51',0),(810224,805,'','','',3,'删除字典','','',0,1,0,NULL,1,1,'sys:dict:del',1,0,0,NULL,'2025-07-27 21:30:05','2025-07-27 21:30:05',0),(810225,805,'','','',3,'编辑字典','','',0,1,0,NULL,1,1,'sys:dict:edit',1,0,0,NULL,'2025-07-27 21:30:28','2025-07-27 21:30:28',0),(810226,805,'','','',3,'查看字典','','',0,1,0,NULL,1,1,'sys:dict:list',1,0,0,NULL,'2025-07-27 21:31:01','2025-07-27 21:31:01',0),(810227,805,'','','',3,'查看字典数据','','',0,1,0,NULL,1,1,'sys:dict:data:list',1,0,0,NULL,'2025-07-27 21:32:02','2025-07-27 21:32:02',0),(810228,805,'','','',3,'新增字典数据','','',0,1,0,NULL,1,1,'sys:dict:data:add',1,0,0,NULL,'2025-07-27 21:32:16','2025-07-27 21:32:16',0),(810229,805,'','','',3,'删除字典数据','','',0,1,0,NULL,1,1,'sys:dict:data:del',1,0,0,NULL,'2025-07-27 21:32:28','2025-07-27 21:32:28',0),(810230,805,'','','',3,'更新字典数据','','',0,1,0,NULL,1,1,'sys:dict:data:edit',1,0,0,NULL,'2025-07-27 21:32:39','2025-07-27 21:32:39',0),(810231,802,'','','',3,'角色权限','','',0,1,0,NULL,1,1,'sys:role:menu:list',1,0,0,NULL,'2025-07-27 21:44:50','2025-07-27 21:44:50',0),(810232,802,'','','',3,'角色授权','','',0,1,0,NULL,1,1,'sys:role:menu:edit',1,0,0,NULL,'2025-07-27 21:45:17','2025-07-27 21:45:17',0),(810233,801,'','','',3,'用户详情','','',0,1,0,NULL,1,1,'sys:user:detail',1,0,0,NULL,'2025-07-27 22:08:39','2025-07-27 22:08:39',0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `code` varchar(50) NOT NULL COMMENT '角色编码',
  `sort` int DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `type` tinyint(1) DEFAULT '2' COMMENT '角色类型：1-系统角色 2-普通角色',
  `data_scope` tinyint(1) DEFAULT '1' COMMENT '数据范围：1-全部数据权限 2-自定数据权限 3-本部门数据权限 4-本部门及以下数据权限 5-仅本人数据权限',
  `description` varchar(500) DEFAULT NULL COMMENT '角色描述',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','role_admin',1,1,1,1,'拥有所有权限的超级管理员',1,'2025-07-27 15:26:33','2025-07-27 15:26:33',0),(2,'普通用户','role_user',2,1,2,5,'普通用户，无系统管理权限，系统管理菜单无权访问',1,'2025-07-27 15:26:33','2025-07-27 19:28:37',0),(3,'普通用户2','role_user2',3,0,2,5,'禁用状态的角色',1,'2025-07-27 15:26:33','2025-07-27 19:27:45',1),(4,'测试','test',0,1,2,1,'测试',NULL,'2025-07-27 18:31:55','2025-07-27 19:27:51',1);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_dept` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_dept` (`role_id`,`dept_id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_dept_id` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept`
--

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_menu` (`role_id`,`menu_id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (47,2,805,'2025-07-27 19:36:09'),(130,1,8,'2025-07-27 22:08:44'),(131,1,801,'2025-07-27 22:08:44'),(132,1,810205,'2025-07-27 22:08:44'),(133,1,810206,'2025-07-27 22:08:44'),(134,1,810207,'2025-07-27 22:08:44'),(135,1,810212,'2025-07-27 22:08:44'),(136,1,802,'2025-07-27 22:08:44'),(137,1,810232,'2025-07-27 22:08:44'),(138,1,810208,'2025-07-27 22:08:44'),(139,1,810209,'2025-07-27 22:08:44'),(140,1,810210,'2025-07-27 22:08:44'),(141,1,810211,'2025-07-27 22:08:44'),(142,1,810231,'2025-07-27 22:08:44'),(143,1,803,'2025-07-27 22:08:44'),(144,1,810213,'2025-07-27 22:08:44'),(145,1,810214,'2025-07-27 22:08:44'),(146,1,810215,'2025-07-27 22:08:44'),(147,1,810216,'2025-07-27 22:08:44'),(148,1,810217,'2025-07-27 22:08:44'),(149,1,804,'2025-07-27 22:08:44'),(150,1,810218,'2025-07-27 22:08:44'),(151,1,810219,'2025-07-27 22:08:44'),(152,1,810220,'2025-07-27 22:08:44'),(153,1,810221,'2025-07-27 22:08:44'),(154,1,810222,'2025-07-27 22:08:44'),(155,1,805,'2025-07-27 22:08:44'),(156,1,810227,'2025-07-27 22:08:44'),(157,1,810228,'2025-07-27 22:08:44'),(158,1,810230,'2025-07-27 22:08:44'),(159,1,810229,'2025-07-27 22:08:44'),(160,1,810223,'2025-07-27 22:08:44'),(161,1,810224,'2025-07-27 22:08:44'),(162,1,810225,'2025-07-27 22:08:44'),(163,1,810226,'2025-07-27 22:08:44'),(164,1,810233,'2025-07-27 22:08:44');
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `gender` tinyint(1) DEFAULT '1' COMMENT '性别：1-男 2-女 3-未知',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `type` tinyint(1) DEFAULT '2' COMMENT '用户类型：1-系统用户 2-普通用户',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_dept_id` (`dept_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'admin','$2a$10$Hdekdmb.z0cfW8wh/1Itru659OhO/sr6E2uiQosMFwSk.3lKUipRa','管理员','超级管理员',1,'https://img0.baidu.com/it/u=2746352008,2041591833&fm=253&fmt=auto&app=138&f=JPEG?w=360&h=360','326010228@qq.com','19900006962',NULL,1,1,1,NULL,NULL,'系统初始用户',1,'2025-07-27 15:26:33','2025-07-27 16:11:51',0),(2,'user','$2a$10$Hdekdmb.z0cfW8wh/1Itru659OhO/sr6E2uiQosMFwSk.3lKUipRa','木糖醇','普通用户',2,'https://img1.baidu.com/it/u=1817951587,3188870642&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500','15500008810@qq.com','15500008810',NULL,1,2,1,NULL,NULL,'无法访问系统管理菜单1',1,'2025-07-27 15:26:33','2025-07-27 19:34:47',0),(4,'test','$2a$10$5vuuPcHXXs27e5UbCUuTpOhTFqgtWbaaxIaqdIkzNlmyGd.7d2si6','测试',NULL,1,NULL,'','',NULL,1,2,2,NULL,NULL,'test',NULL,'2025-07-27 18:42:37','2025-07-27 18:43:59',1);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`,`role_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1,1,'2025-07-27 15:26:33'),(8,2,2,'2025-07-27 20:07:56');
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-27 22:32:44
