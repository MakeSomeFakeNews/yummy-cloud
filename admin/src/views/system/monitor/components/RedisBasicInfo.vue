<template>
  <a-card class="redis-card" :loading="loading">
    <template #title>
      <icon-info />
      <span class="ml-2">Redis基本信息</span>
    </template>
    
    <a-descriptions :column="2" bordered>
      <a-descriptions-item label="Redis版本">
        <a-tag color="blue">{{ redisInfo.version || '-' }}</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="运行模式">
        <a-tag color="green">单机</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="端口">
        <a-tag>6379</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="客户端数">
        <a-tag color="orange">{{ redisInfo.connected_clients || '0' }}</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="运行时间">
        <span>{{ formatUptime(redisInfo.uptime_in_seconds) }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="使用内存">
        <span>{{ redisInfo.used_memory_human || '-' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="内存峰值">
        <span>{{ redisInfo.used_memory_peak_human || '-' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="内存策略">
        <a-tag>{{ redisInfo.maxmemory_policy || 'noeviction' }}</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="连接总数">
        <span>{{ redisInfo.total_connections_received || '0' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="命令总数">
        <span>{{ redisInfo.total_commands_processed || '0' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="每秒操作数">
        <a-tag color="red">{{ redisInfo.instantaneous_ops_per_sec || '0' }}</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="键空间命中率">
        <span>{{ getHitRate() }}%</span>
      </a-descriptions-item>
    </a-descriptions>
  </a-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getRedisInfo } from '@/apis/system/monitor'
import mittBus from '@/utils/mitt'

defineOptions({ name: 'RedisBasicInfo' })

const loading = ref(false)
const redisInfo = ref<any>({})

// 获取Redis基本信息
const fetchRedisInfo = async () => {
  loading.value = true
  try {
    const res = await getRedisInfo()
    redisInfo.value = res.data || {}
  } catch (error) {
    console.error('获取Redis信息失败:', error)
  } finally {
    loading.value = false
  }
}

// 格式化运行时间
const formatUptime = (seconds: string) => {
  if (!seconds) return '-'
  const sec = parseInt(seconds)
  const days = Math.floor(sec / (24 * 3600))
  const hours = Math.floor((sec % (24 * 3600)) / 3600)
  const minutes = Math.floor((sec % 3600) / 60)
  return `${days}天${hours}时${minutes}分`
}

// 计算命中率
const getHitRate = () => {
  const hits = parseInt(redisInfo.value.keyspace_hits || '0')
  const misses = parseInt(redisInfo.value.keyspace_misses || '0')
  const total = hits + misses
  if (total === 0) return '0.00'
  return ((hits / total) * 100).toFixed(2)
}

onMounted(() => {
  fetchRedisInfo()
  
  // 监听刷新事件
  mittBus.on('redis-refresh', fetchRedisInfo)
})
</script>

<style lang="scss" scoped>
.redis-card {
  margin-bottom: 20px;
}

.ml-2 {
  margin-left: 8px;
}
</style>