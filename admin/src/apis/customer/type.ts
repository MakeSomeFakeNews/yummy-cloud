// 客户信息项目
export interface CustomerItem {
  id: number
  code: string
  name: string
  shortName: string
  type: number // 1-企业客户 2-个人客户
  level: number // 1-VIP 2-普通 3-潜在
  industry: string
  contactPerson: string
  contactPhone: string
  contactEmail: string
  address: string
  creditCode: string
  creditLimit: number
  creditDays: number
  taxRate: number
  paymentTerms: string
  deliveryTerms: string
  status: number // 0-禁用 1-正常
  remark: string
  createTime: string
  updateTime: string
  createUserId: string
}