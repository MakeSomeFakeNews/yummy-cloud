import type * as T from './type'
import http from '@/utils/http'
import type {ListItem} from '@/apis/system/menu'

export type * from './type'

/** 获取验证码 */
export function getCaptcha() {
  return http.get<T.CaptchaResponse>('/user/captcha')
}

/** 登录 */
export function login(data: T.LoginRequest) {
  return http.post<T.Login>('/user/login', data)
}

/** 退出登录 */
export function logout() {
  return http.post('/user/logout')
}

/** 获取用户信息 */
export const getUserInfo = () => {
  return http.get<T.UserInfo>('/user/getUserInfo')
}

/** 获取用户路由信息 */
export const getUserRoutes = () => {
  return http.get<ListItem[]>('/user/getUserRoutes')
}

/** 修改密码 */
export function changePassword(data: T.ChangePasswordRequest) {
  return http.post('/user/changePassword', data)
}
