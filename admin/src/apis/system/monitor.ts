import http from '@/utils/http'

// Redis监控相关API

/**
 * 获取Redis基本信息
 */
export const getRedisInfo = () => {
  return http.get('/system/monitor/redis/info')
}

/**
 * 获取Redis内存信息
 */
export const getRedisMemoryInfo = () => {
  return http.get('/system/monitor/redis/memory')
}

/**
 * 获取Redis键空间信息
 */
export const getRedisKeyspaceInfo = () => {
  return http.get('/system/monitor/redis/keyspace')
}

/**
 * 获取Redis命令统计信息
 */
export const getRedisCommandStats = () => {
  return http.get('/system/monitor/redis/commandstats')
}

/**
 * 清空Redis缓存
 */
export const flushRedisDB = () => {
  return http.post('/system/monitor/redis/flushdb')
}

// 系统监控相关API

/**
 * 获取系统基本信息
 */
export const getSystemInfo = () => {
  return http.get('/system/monitor/system/info')
}

/**
 * 获取JVM内存信息
 */
export const getJvmMemoryInfo = () => {
  return http.get('/system/monitor/system/jvm/memory')
}

/**
 * 获取垃圾回收信息
 */
export const getGarbageCollectorInfo = () => {
  return http.get('/system/monitor/system/jvm/gc')
}

/**
 * 获取线程信息
 */
export const getThreadInfo = () => {
  return http.get('/system/monitor/system/thread')
}

/**
 * 获取磁盘信息
 */
export const getDiskInfo = () => {
  return http.get('/system/monitor/system/disk')
}