<template>
  <a-card class="system-card" :loading="loading">
    <template #title>
      <icon-desktop />
      <span class="ml-2">系统基本信息</span>
    </template>
    
    <a-descriptions :column="2" bordered>
      <a-descriptions-item label="操作系统">
        <a-tag color="blue">{{ systemInfo.os_name || '-' }}</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="系统架构">
        <span>{{ systemInfo.os_arch || '-' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="系统版本">
        <span>{{ systemInfo.os_version || '-' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="CPU核心数">
        <a-tag color="green">{{ systemInfo.processors || '0' }}核</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="Java版本">
        <a-tag color="orange">{{ systemInfo.java_version || '-' }}</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="Java厂商">
        <span>{{ systemInfo.java_vendor || '-' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="Java安装路径">
        <span>{{ systemInfo.java_home || '-' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="系统运行时间">
        <span>{{ formatUptime(systemInfo.uptime) }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="系统CPU使用率">
        <a-tag :color="getCpuColor(systemInfo.system_cpu_load)">
          {{ systemInfo.system_cpu_load || '0.00' }}%
        </a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="进程CPU使用率">
        <a-tag :color="getCpuColor(systemInfo.process_cpu_load)">
          {{ systemInfo.process_cpu_load || '0.00' }}%
        </a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="系统总内存">
        <span>{{ systemInfo.total_physical_memory || '-' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="系统空闲内存">
        <span>{{ systemInfo.free_physical_memory || '-' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="系统已用内存">
        <span>{{ systemInfo.used_physical_memory || '-' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="启动时间">
        <span>{{ formatStartTime(systemInfo.start_time) }}</span>
      </a-descriptions-item>
    </a-descriptions>
  </a-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getSystemInfo } from '@/apis/system/monitor'
import mittBus from '@/utils/mitt'

defineOptions({ name: 'SystemInfo' })

const loading = ref(false)
const systemInfo = ref<any>({})

// 获取系统信息
const fetchSystemInfo = async () => {
  loading.value = true
  try {
    const res = await getSystemInfo()
    systemInfo.value = res.data || {}
  } catch (error) {
    console.error('获取系统信息失败:', error)
  } finally {
    loading.value = false
  }
}

// 格式化运行时间
const formatUptime = (uptime: string) => {
  if (!uptime) return '-'
  const ms = parseInt(uptime)
  const seconds = Math.floor(ms / 1000)
  const days = Math.floor(seconds / (24 * 3600))
  const hours = Math.floor((seconds % (24 * 3600)) / 3600)
  const minutes = Math.floor((seconds % 3600) / 60)
  return `${days}天${hours}时${minutes}分`
}

// 格式化启动时间
const formatStartTime = (timestamp: string) => {
  if (!timestamp) return '-'
  const date = new Date(parseInt(timestamp))
  return date.toLocaleString('zh-CN')
}

// 获取CPU颜色
const getCpuColor = (usage: string) => {
  const num = parseFloat(usage || '0')
  if (num < 50) return 'green'
  if (num < 80) return 'orange'
  return 'red'
}

onMounted(() => {
  fetchSystemInfo()
  
  // 监听刷新事件
  mittBus.on('system-refresh', fetchSystemInfo)
})
</script>

<style lang="scss" scoped>
.system-card {
  margin-bottom: 20px;
}

.ml-2 {
  margin-left: 8px;
}
</style>