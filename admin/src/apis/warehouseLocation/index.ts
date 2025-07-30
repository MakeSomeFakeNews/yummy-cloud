import type * as T from './type'
import { getBaseApi } from '@/apis/base'

export type * from './type'

/** 库位管理模块 */
export const warehouseLocationAPI = getBaseApi<T.WarehouseLocationItem>({ baseUrl: '/basic/erp-warehouse-location' })