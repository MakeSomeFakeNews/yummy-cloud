# YummyERP 系统模块划分设计文档

## 项目概述

YummyERP 是基于 Spring Boot + MyBatis Plus 架构的企业资源规划系统，采用模块化设计，支持完整的供应链管理流程。

## 系统架构

### 技术栈
- **后端框架**: Spring Boot 2.6.13
- **ORM框架**: MyBatis Plus 3.5.12  
- **数据库**: MySQL 8.0+
- **缓存**: Redis
- **认证授权**: Sa-Token 1.39.0
- **API文档**: Knife4j 4.4.0
- **连接池**: Druid

### 项目结构
```
com.yummyerp.cloud
├── config/              # 配置类
├── exception/           # 全局异常处理
├── utils/              # 工具类
└── modules/            # 业务模块
    ├── common/         # 公共模块
    ├── system/         # 系统管理模块 (已存在)
    ├── basic/          # 基础数据模块
    ├── product/        # 产品管理模块
    ├── inventory/      # 库存管理模块
    ├── purchase/       # 采购管理模块
    ├── sales/          # 销售管理模块
    ├── finance/        # 财务管理模块
    └── report/         # 报表统计模块
```

## 核心业务模块详细设计

### 1. 系统管理模块 (System) - 已存在
**包路径**: `com.yummyerp.cloud.modules.system`

**功能**: 系统基础功能管理
- 用户管理 (SysUser)
- 角色管理 (SysRole)  
- 菜单管理 (SysMenu)
- 部门管理 (SysDept)
- 字典管理 (SysDict)
- 权限控制

**核心实体**: 已存在于项目中

---

### 2. 基础数据模块 (Basic)
**包路径**: `com.yummyerp.cloud.modules.basic`

**功能**: 系统基础数据维护
- 供应商管理
- 客户管理  
- 仓库库位管理
- 计量单位管理

**核心实体**:
```java
// 供应商信息
com.yummyerp.cloud.modules.basic.entity.Supplier
com.yummyerp.cloud.modules.basic.entity.SupplierEvaluation

// 客户信息  
com.yummyerp.cloud.modules.basic.entity.Customer

// 仓库管理
com.yummyerp.cloud.modules.basic.entity.Warehouse
com.yummyerp.cloud.modules.basic.entity.WarehouseLocation

// 计量单位
com.yummyerp.cloud.modules.basic.entity.Unit
```

**数据表**:
- `erp_supplier` - 供应商信息表
- `erp_supplier_evaluation` - 供应商评价表  
- `erp_customer` - 客户信息表
- `erp_warehouse` - 仓库信息表
- `erp_warehouse_location` - 库位信息表
- `erp_unit` - 计量单位表

---

### 3. 产品管理模块 (Product)
**包路径**: `com.yummyerp.cloud.modules.product`

**功能**: 产品信息全生命周期管理
- 产品分类管理 (树形结构)
- 产品基础信息管理
- 产品变体管理 (规格、颜色等)
- 产品图片管理
- 产品价格管理
- BOM清单管理

**核心实体**:
```java
// 产品分类
com.yummyerp.cloud.modules.product.entity.ProductCategory

// 产品信息
com.yummyerp.cloud.modules.product.entity.Product
com.yummyerp.cloud.modules.product.entity.ProductVariant
com.yummyerp.cloud.modules.product.entity.ProductImage
```

**数据表**:
- `erp_product_category` - 产品分类表
- `erp_product` - 产品信息表
- `erp_product_variant` - 产品变体表
- `erp_product_image` - 产品图片表

**业务特点**:
- 支持多级产品分类
- 支持产品多规格变体
- 支持产品多图片管理
- 支持库存预警设置

---

### 4. 库存管理模块 (Inventory)
**包路径**: `com.yummyerp.cloud.modules.inventory`

**功能**: 库存实时监控与管理
- 实时库存查询
- 库存出入库管理
- 库存流水记录
- 库存盘点管理
- 库存调拨管理
- 库存预警管理

**核心实体**:
```java
// 库存管理
com.yummyerp.cloud.modules.inventory.entity.Inventory
com.yummyerp.cloud.modules.inventory.entity.InventoryTransaction

// 库存盘点
com.yummyerp.cloud.modules.inventory.entity.InventoryCheck
com.yummyerp.cloud.modules.inventory.entity.InventoryCheckDetail

// 库存调拨
com.yummyerp.cloud.modules.inventory.entity.InventoryTransfer
com.yummyerp.cloud.modules.inventory.entity.InventoryTransferDetail

// 库存预警
com.yummyerp.cloud.modules.inventory.entity.InventoryAlert
```

**数据表**:
- `erp_inventory` - 库存主表
- `erp_inventory_transaction` - 库存流水表
- `erp_inventory_check` - 库存盘点单
- `erp_inventory_check_detail` - 库存盘点明细
- `erp_inventory_transfer` - 库存调拨单
- `erp_inventory_transfer_detail` - 库存调拨明细
- `erp_inventory_alert` - 库存预警记录

**业务特点**:
- 支持批次管理
- 支持先进先出 (FIFO)
- 支持多仓库多库位
- 支持自动预警

---

### 5. 采购管理模块 (Purchase)
**包路径**: `com.yummyerp.cloud.modules.purchase`

**功能**: 采购全流程管理
- 采购需求管理
- 采购订单管理
- 采购收货管理
- 采购退货管理
- 供应商管理
- 采购成本分析

**核心实体**:
```java
// 采购申请
com.yummyerp.cloud.modules.purchase.entity.PurchaseRequest
com.yummyerp.cloud.modules.purchase.entity.PurchaseRequestDetail

// 采购订单
com.yummyerp.cloud.modules.purchase.entity.PurchaseOrder
com.yummyerp.cloud.modules.purchase.entity.PurchaseOrderDetail

// 采购收货
com.yummyerp.cloud.modules.purchase.entity.PurchaseReceipt
com.yummyerp.cloud.modules.purchase.entity.PurchaseReceiptDetail

// 采购退货
com.yummyerp.cloud.modules.purchase.entity.PurchaseReturn
com.yummyerp.cloud.modules.purchase.entity.PurchaseReturnDetail
```

**数据表**:
- `erp_purchase_request` - 采购申请单
- `erp_purchase_request_detail` - 采购申请明细
- `erp_purchase_order` - 采购订单
- `erp_purchase_order_detail` - 采购订单明细
- `erp_purchase_receipt` - 采购收货单
- `erp_purchase_receipt_detail` - 采购收货明细
- `erp_purchase_return` - 采购退货单
- `erp_purchase_return_detail` - 采购退货明细

**业务流程**:
```
采购申请 → 采购审核 → 采购订单 → 供应商确认 → 收货验收 → 质检入库
```

---

### 6. 销售管理模块 (Sales)
**包路径**: `com.yummyerp.cloud.modules.sales`

**功能**: 销售全流程管理
- 销售报价管理
- 销售订单管理
- 销售发货管理
- 销售退货管理
- 客户管理
- 销售分析

**核心实体**:
```java
// 销售报价
com.yummyerp.cloud.modules.sales.entity.SalesQuotation
com.yummyerp.cloud.modules.sales.entity.SalesQuotationDetail

// 销售订单
com.yummyerp.cloud.modules.sales.entity.SalesOrder
com.yummyerp.cloud.modules.sales.entity.SalesOrderDetail

// 销售出库
com.yummyerp.cloud.modules.sales.entity.SalesShipment
com.yummyerp.cloud.modules.sales.entity.SalesShipmentDetail

// 销售退货
com.yummyerp.cloud.modules.sales.entity.SalesReturn
com.yummyerp.cloud.modules.sales.entity.SalesReturnDetail
```

**数据表**:
- `erp_sales_quotation` - 销售报价单
- `erp_sales_quotation_detail` - 销售报价明细
- `erp_sales_order` - 销售订单
- `erp_sales_order_detail` - 销售订单明细
- `erp_sales_shipment` - 销售出库单
- `erp_sales_shipment_detail` - 销售出库明细
- `erp_sales_return` - 销售退货单
- `erp_sales_return_detail` - 销售退货明细

**业务流程**:
```
客户询价 → 销售报价 → 订单确认 → 生产/备货 → 发货出库 → 客户收货
```

---

### 7. 财务管理模块 (Finance)
**包路径**: `com.yummyerp.cloud.modules.finance`

**功能**: 财务核算与管理
- 应收账款管理
- 应付账款管理
- 收付款管理
- 费用管理
- 财务报表
- 成本核算

**核心实体**:
```java
// 应收管理
com.yummyerp.cloud.modules.finance.entity.AccountsReceivable
com.yummyerp.cloud.modules.finance.entity.PaymentReceived

// 应付管理
com.yummyerp.cloud.modules.finance.entity.AccountsPayable
com.yummyerp.cloud.modules.finance.entity.PaymentMade

// 费用管理
com.yummyerp.cloud.modules.finance.entity.Expense
```

**数据表**:
- `erp_accounts_receivable` - 应收账款
- `erp_payment_received` - 收款记录
- `erp_accounts_payable` - 应付账款
- `erp_payment_made` - 付款记录
- `erp_expense` - 费用管理

**业务特点**:
- 支持多币种管理
- 支持账期管理
- 支持预收预付款
- 支持费用报销流程

---

### 8. 报表统计模块 (Report)
**包路径**: `com.yummyerp.cloud.modules.report`

**功能**: 数据分析与报表
- 销售分析报表
- 采购分析报表
- 库存分析报表
- 财务分析报表
- 经营分析报表
- 自定义报表

**业务特点**:
- 支持图表可视化
- 支持报表导出
- 支持报表定时生成
- 支持钻取分析

---

## 数据表统计

| 模块 | 数据表数量 | 主要功能 |
|------|------------|----------|
| 系统管理 | 9张 | 用户权限管理 |  
| 基础数据 | 6张 | 供应商、客户、仓库管理 |
| 产品管理 | 4张 | 产品信息管理 |
| 库存管理 | 7张 | 库存监控与管理 |
| 采购管理 | 8张 | 采购全流程管理 |
| 销售管理 | 8张 | 销售全流程管理 |
| 财务管理 | 5张 | 财务核算管理 |
| 报表统计 | 3张 | 预警与评价管理 |
| **总计** | **50张** | **完整ERP功能** |

## 开发建议

### 1. 开发顺序
1. **基础数据模块** - 提供基础数据支撑
2. **产品管理模块** - 产品信息基础
3. **库存管理模块** - 库存核心功能  
4. **采购管理模块** - 进货流程
5. **销售管理模块** - 出货流程
6. **财务管理模块** - 财务结算
7. **报表统计模块** - 数据分析

### 2. 技术规范
- **实体类**: 继承 `BaseEntity`，使用 Lombok 注解
- **Mapper**: 继承 `BaseMapper<T>`，使用 `@Mapper` 注解，支持MyBatis Plus
- **Service**: 继承 `IService<T>`，实现类继承 `ServiceImpl`，使用 `@Service` 注解
- **Controller**: 使用 `@RestController`，统一返回 `Result<T>`
- **参数校验**: 使用 `@Valid` 和 `@Validated`
- **API文档**: 使用 `@ApiOperation` 和 `@ApiModel`
- **数据类型**: tinyint字段映射为Integer类型（已修复Byte问题）

### 3. 命名规范
- **包名**: 小写，采用模块化分层
- **类名**: 大驼峰，语义明确
- **方法名**: 小驼峰，动词在前
- **数据表**: `erp_` 前缀，下划线分隔
- **字段名**: 下划线分隔，见名知意

### 4. 数据库设计原则
- 所有表必须有主键 `id`
- 统一使用逻辑删除 `deleted`
- 时间字段使用 `create_time` 和 `update_time`
- 创建人字段使用 `create_user_id`
- 外键关联使用 `_id` 后缀
- 枚举值使用 `tinyint` 类型

### 5. 业务流程控制
- 使用状态机模式管理业务状态
- 重要业务操作需要审核流程
- 关键数据变更需要记录操作日志
- 库存变动必须记录流水

### 6. 代码生成器使用
项目提供了增强版的代码生成器 `CodeGenerator`，支持：

**交互式使用**：
```bash
# 运行主方法，按提示选择模块
java com.yummyerp.cloud.utils.CodeGenerator
```

**编程式使用**：
```java
// 生成单个模块
CodeGenerator.generateModule("basic", "作者名");

// 生成基础数据模块
CodeGenerator.generateBasicModule("作者名");

// 生成产品管理模块  
CodeGenerator.generateProductModule("作者名");

// 自定义表生成
CodeGenerator.generateCode("table1,table2", "moduleName", "作者名");
```


## 扩展方向

### 1. 生产管理模块
- 生产计划管理
- 工艺路线管理  
- 生产订单管理
- 生产进度跟踪

### 2. 质量管理模块
- 质检标准管理
- 质检流程管理
- 不合格品管理
- 质量分析报表

### 3. 设备管理模块
- 设备档案管理
- 设备维护保养
- 设备故障管理
- 设备效率分析

### 4. 移动端支持
- 仓库移动作业
- 销售移动开单
- 审批移动办公
- 数据移动查询

---

**文档版本**: v1.0  
**创建时间**: 2025-07-27  
**维护人员**: 周欢  
**更新说明**: 初始版本，完整ERP模块设计