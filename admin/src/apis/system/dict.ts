import type {SelectOptionData} from '@arco-design/web-vue'
import {getBaseApi} from '@/apis/base'
import http from '@/utils/http'

export interface ListItem {
  id: string
  createUserString: string
  createTime: string
  name: string
  code: string
  sort: number
  status: Status
  description: string
}

export type DictDetail = ListItem

export type DictDataItem = {
  id: string
  name: string
  value: string | number
  status: Status
}

/** 字典模块 */
export const baseAPI = getBaseApi<ListItem>({ baseUrl: '/system/dict' })

/** 字典数据列表 */
export function getDictDataList(params: { code: string } & Pagination) {
  return http.get<PageRes<DictDataItem[]>>('/system/dict/getDictDataList', params)
}

/** 字典数据详情 */
export function getDictDataDetail(params: { id: string, code: string }) {
  return http.get<DictDataItem>('/system/dict/getDictDataDetail', params)
}

/** 获取字典数据映射 */
export function getDictData() {
  return http.get<Record<string, SelectOptionData[]>>('/system/dict/getDictData')
}

/** 新增字典数据 */
export function addDictData(params: any) {
  return http.post<DictDataItem>('/system/dictData/add', params)
}

/** 修改字典数据 */
export function updateDictData(params: any) {
  return http.post<DictDataItem>('/system/dictData/update', params)
}

/** 删除字典数据 */
export function deleteDictData(params: { ids: string[] }) {
  return http.post<boolean>('/system/dictData/delete', params)
}
