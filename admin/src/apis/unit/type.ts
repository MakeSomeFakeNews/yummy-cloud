// 计量单位信息项目
export interface UnitItem {
  id: number
  code: string
  name: string
  symbol: string
  type: number // 1-基本单位 2-长度 3-重量 4-体积 5-面积
  status: number // 0-禁用 1-正常
  createTime: string
  updateTime: string
  createUserId?: number
}

// 计量单位查询参数
export interface UnitQuery {
  code?: string
  name?: string
  symbol?: string
  type?: number
  status?: number
}

// 计量单位选项
export interface UnitOption {
  label: string
  value: number
}