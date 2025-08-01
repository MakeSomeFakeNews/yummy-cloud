import type {AxiosInstance, AxiosRequestConfig, AxiosResponse} from 'axios'
import axios from 'axios'
import {Message, Notification} from '@arco-design/web-vue'
import NProgress from 'nprogress'
import {getToken} from '@/utils/auth'
import 'nprogress/nprogress.css'
import router from '@/router'

// 配置 NProgress
NProgress.configure({ showSpinner: false })

/** 状态码消息映射 */
const StatusCodeMessage: Record<number, string> = {
  200: '服务器成功返回请求的数据',
  201: '新建或修改数据成功',
  202: '一个请求已经进入后台排队（异步任务）',
  204: '删除数据成功',
  400: '请求错误(400)',
  401: '未授权，请重新登录(401)',
  403: '拒绝访问(403)',
  404: '请求出错(404)',
  408: '请求超时(408)',
  500: '服务器错误(500)',
  501: '服务未实现(501)',
  502: '网络错误(502)',
  503: '服务不可用(503)',
  504: '网络超时(504)'
}

/** 创建 axios 实例 */
const http: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_PREFIX,
  timeout: 30 * 1000
})

// 请求拦截器
http.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    NProgress.start()
    const token = getToken()
    if (token) {
      if (!config.headers) {
        config.headers = {}
      }
      config.headers.token = token
    }
    return config
  },
  (error) => {
    NProgress.done()
    return Promise.reject(error)
  }
)

// 响应拦截器
http.interceptors.response.use(
  (response: AxiosResponse) => {
    const { data } = response
    const { message, code } = data

    // token失效
    if (code === 401) {
      NProgress.done()
      // Message.error('token失效')
      router.replace('/login')
      return Promise.reject(new Error('token失效'))
    }

    // 判断请求是否成功（200为成功）
    if (code !== 200) {
      NProgress.done()
      // 如果错误信息长度过长，使用 Notification 进行提示
      if (message && message.length <= 15) {
        Message.error(message || '服务器端错误')
      } else {
        Notification.error(message || '服务器端错误')
      }
      return Promise.reject(new Error(message || 'Error'))
    }

    NProgress.done()
    return response
  },
  (error) => {
    NProgress.done()
    Message.clear()
    const response = Object.assign({}, error.response)
    response && Message.error(StatusCodeMessage[response.status] || '系统异常, 请检查网络或联系管理员！')
    return Promise.reject(error)
  }
)

/** 封装请求方法 */
const request = <T = unknown>(config: AxiosRequestConfig): Promise<ApiRes<T>> => {
  return new Promise((resolve, reject) => {
    http
      .request<T>(config)
      .then((res: AxiosResponse) => resolve(res.data))
      .catch((err: { message: string }) => reject(err))
  })
}

/** GET 请求 */
const get = <T = any>(url: string, params?: object, config?: AxiosRequestConfig): Promise<ApiRes<T>> => {
  return request({
    method: 'get',
    url,
    params,
    ...config
  })
}

/** POST 请求 */
const post = <T = any>(url: string, params?: object, config?: AxiosRequestConfig): Promise<ApiRes<T>> => {
  return request({
    method: 'post',
    url,
    data: params,
    ...config
  })
}

export default { get, post, request }
