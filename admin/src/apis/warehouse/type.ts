// 仓库信息项目
export interface WarehouseItem {
  id: string
  code: string
  name: string
  type: string | number // 1-原料仓 2-成品仓 3-半成品仓 4-次品仓
  address: string
  manager: string
  phone: string
  area: number // 仓库面积（平方米）
  capacity: number // 存储容量
  status: string | number // 0-禁用 1-正常
  remark: string
  createTime: string
  updateTime: string
  createUserId: string
} 