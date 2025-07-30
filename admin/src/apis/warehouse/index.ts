import type * as T from './type'
import { getBaseApi } from '@/apis/base'
import http from '@/utils/http'

export type * from './type'

/** 仓库管理模块 */
export const warehouseAPI = {
  ...getBaseApi<T.WarehouseItem>({ baseUrl: '/basic/erp-warehouse' }),
  
  // 搜索仓库选项（用于下拉框）
  searchOptions: (keyword?: string) => {
    return http.get<{ label: string; value: string | number }[]>('/basic/erp-warehouse/options', { keyword, size: 20 })
  },
  
  // 根据ID获取仓库选项（用于编辑时回显）
  getOptionById: (id: string | number) => {
    return http.get<{ label: string; value: string | number }>(`/basic/erp-warehouse/option/${id}`)
  }
} 