// 库位信息项目
export interface WarehouseLocationItem {
  id: number
  warehouseId: number
  code: string
  name: string
  type: number // 1-普通库位 2-冷藏库位 3-危险品库位
  capacity: number // 存储容量
  status: number // 0-禁用 1-正常
  remark: string
  createTime: string
  updateTime: string
  createUserId: string
  warehouseName?: string // 仓库名称（用于显示，后端LEFT JOIN查询获取）
}