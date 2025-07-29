import type * as T from './type'
import { getBaseApi } from '@/apis/base'

export type * from './type'

/** 供应商管理模块 */
export const supplierAPI = getBaseApi<T.SupplierItem>({ baseUrl: '/basic/erp-supplier' }) 