// 供应商信息项目
export interface SupplierItem {
  id: number
  /** 供应商编码 */
  code: string
  /** 供应商名称 */
  name: string
  /** 简称 */
  shortName: string
  /** 供应商类型：1-生产商 2-贸易商 3-服务商 */
  type: number
  /** 供应商级别：1-A级 2-B级 3-C级 */
  level: number
  /** 联系人 */
  contactPerson: string
  /** 联系电话 */
  contactPhone: string
  /** 联系邮箱 */
  contactEmail: string
  /** 地址 */
  address: string
  /** 统一社会信用代码 */
  creditCode: string
  /** 开户银行 */
  bankName: string
  /** 银行账户 */
  bankAccount: string
  /** 税率 */
  taxRate: number
  /** 付款条件 */
  paymentTerms: string
  /** 交货条件 */
  deliveryTerms: string
  /** 状态：0-禁用 1-正常 */
  status: number
  /** 备注 */
  remark: string
  /** 创建时间 */
  createTime: string
  /** 更新时间 */
  updateTime: string
  /** 创建人ID */
  createUserId: string
}

/**
 * 供应商查询条件
 */
export interface SupplierQuery {
  /** 供应商编码 */
  code?: string
  /** 供应商名称 */
  name?: string
  /** 供应商类型 */
  type?: number
  /** 供应商级别 */
  level?: number
  /** 联系人 */
  contactPerson?: string
  /** 联系电话 */
  contactPhone?: string
  /** 状态 */
  status?: number
} 