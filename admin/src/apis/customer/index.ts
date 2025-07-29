import type * as T from './type'
import { getBaseApi } from '@/apis/base'

export type * from './type'

/** 客户管理模块 */
export const customerAPI = getBaseApi<T.CustomerItem>({ baseUrl: '/basic/erp-customer' })