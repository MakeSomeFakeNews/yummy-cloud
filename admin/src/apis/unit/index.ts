import type * as T from './type'
import { getBaseApi } from '@/apis/base'
import http from '@/utils/http'

export type * from './type'

/** 计量单位管理模块 */
export const unitAPI = {
  ...getBaseApi<T.UnitItem>({ baseUrl: '/basic/erp-unit' })
}