import http from '@/utils/http'
import { getBaseApi } from '../base'

export interface ListItem {
  id: string
  title: string
  businessType: number
  method: string
  requestMethod: string
  operName: string
  operUrl: string
  operIp: string
  operLocation: string
  operParam: string
  jsonResult: string
  status: number
  errorMsg: string
  operTime: string
}

/** 操作日志模块 */
export const baseAPI = {
  ...getBaseApi<ListItem>({ baseUrl: '/system/operLog' }),
  
  // 清空操作日志
  clean: () => {
    return http.post<void>('/system/operLog/clean')
  }
} 