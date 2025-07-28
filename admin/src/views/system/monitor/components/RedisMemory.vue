<template>
  <a-card class="redis-card" :loading="loading">
    <template #title>
      <icon-file />
      <span class="ml-2">内存使用分析</span>
    </template>
    
    <a-descriptions :column="2" bordered>
      <a-descriptions-item label="已用内存">
        <a-tag color="blue">{{ memoryInfo.used_memory_human || '-' }}</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="系统内存">
        <span>{{ memoryInfo.used_memory_rss_human || '-' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="内存峰值">
        <span>{{ memoryInfo.used_memory_peak_human || '-' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="数据内存">
        <span>{{ formatBytes(memoryInfo.used_memory_dataset) }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="开销内存">
        <span>{{ formatBytes(memoryInfo.used_memory_overhead) }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="系统总内存">
        <span>{{ memoryInfo.total_system_memory_human || '-' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="最大内存">
        <span>{{ memoryInfo.maxmemory_human || '无限制' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="内存策略">
        <a-tag>{{ memoryInfo.maxmemory_policy || 'noeviction' }}</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="碎片率">
        <a-tag :color="getFragmentationColor()">
          {{ memoryInfo.mem_fragmentation_ratio || '-' }}
        </a-tag>
      </a-descriptions-item>
    </a-descriptions>
  </a-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getRedisMemoryInfo } from '@/apis/system/monitor'
import mittBus from '@/utils/mitt'

defineOptions({ name: 'RedisMemory' })

const loading = ref(false)
const memoryInfo = ref<any>({})

// 获取Redis内存信息
const fetchMemoryInfo = async () => {
  loading.value = true
  try {
    const res = await getRedisMemoryInfo()
    memoryInfo.value = res.data || {}
  } catch (error) {
    console.error('获取Redis内存信息失败:', error)
  } finally {
    loading.value = false
  }
}

// 格式化字节数
const formatBytes = (bytes: string) => {
  if (!bytes) return '-'
  const num = parseInt(bytes)
  if (num === 0) return '0 B'
  
  const k = 1024
  const sizes = ['B', 'KB', 'MB', 'GB', 'TB']
  const i = Math.floor(Math.log(num) / Math.log(k))
  
  return parseFloat((num / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i]
}


// 获取碎片率颜色
const getFragmentationColor = () => {
  const ratio = parseFloat(memoryInfo.value.mem_fragmentation_ratio || '1')
  if (ratio < 1.5) return 'green'
  if (ratio < 2) return 'orange'
  return 'red'
}

onMounted(() => {
  fetchMemoryInfo()
  
  // 监听刷新事件
  mittBus.on('redis-refresh', fetchMemoryInfo)
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