import type * as T from './type'
import { getBaseApi } from '@/apis/base'

export type * from './type'

/** 仓库管理模块 */
export const warehouseAPI = getBaseApi<T.WarehouseItem>({ baseUrl: '/basic/erp-warehouse' }) 