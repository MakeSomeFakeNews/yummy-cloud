<template>
  <a-card class="thread-card" :loading="loading">
    <template #title>
      <icon-code />
      <span class="ml-2">线程信息统计</span>
    </template>
    
    <a-row :gutter="16" class="mb-4">
      <a-col :span="6">
        <div class="stat-card">
          <div class="stat-value">{{ threadInfo.thread_count || 0 }}</div>
          <div class="stat-label">当前线程数</div>
        </div>
      </a-col>
      <a-col :span="6">
        <div class="stat-card">
          <div class="stat-value">{{ threadInfo.daemon_thread_count || 0 }}</div>
          <div class="stat-label">守护线程数</div>
        </div>
      </a-col>
      <a-col :span="6">
        <div class="stat-card">
          <div class="stat-value">{{ threadInfo.peak_thread_count || 0 }}</div>
          <div class="stat-label">峰值线程数</div>
        </div>
      </a-col>
      <a-col :span="6">
        <div class="stat-card">
          <div class="stat-value">{{ threadInfo.total_started_thread_count || 0 }}</div>
          <div class="stat-label">启动线程总数</div>
        </div>
      </a-col>
    </a-row>
    
    <a-descriptions :column="2" bordered>
      <a-descriptions-item label="当前活跃线程">
        <a-tag color="blue">{{ threadInfo.thread_count || '0' }}</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="守护线程">
        <a-tag color="green">{{ threadInfo.daemon_thread_count || '0' }}</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="峰值线程数">
        <a-tag color="orange">{{ threadInfo.peak_thread_count || '0' }}</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="启动线程总数">
        <span>{{ threadInfo.total_started_thread_count || '0' }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="用户线程数">
        <span>{{ getUserThreadCount() }}</span>
      </a-descriptions-item>
      <a-descriptions-item label="线程状态健康度">
        <a-tag :color="getHealthColor()">{{ getHealthStatus() }}</a-tag>
      </a-descriptions-item>
    </a-descriptions>
  </a-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getThreadInfo } from '@/apis/system/monitor'
import mittBus from '@/utils/mitt'

defineOptions({ name: 'ThreadInfo' })

const loading = ref(false)
const threadInfo = ref<any>({})

// 获取线程信息
const fetchThreadInfo = async () => {
  loading.value = true
  try {
    const res = await getThreadInfo()
    threadInfo.value = res.data || {}
  } catch (error) {
    console.error('获取线程信息失败:', error)
  } finally {
    loading.value = false
  }
}

// 计算用户线程数
const getUserThreadCount = () => {
  const total = parseInt(threadInfo.value.thread_count || '0')
  const daemon = parseInt(threadInfo.value.daemon_thread_count || '0')
  return total - daemon
}

// 获取健康状态
const getHealthStatus = () => {
  const current = parseInt(threadInfo.value.thread_count || '0')
  const peak = parseInt(threadInfo.value.peak_thread_count || '0')
  
  if (current === 0) return '无数据'
  if (current < peak * 0.5) return '良好'
  if (current < peak * 0.8) return '正常'
  return '警告'
}

// 获取健康状态颜色
const getHealthColor = () => {
  const status = getHealthStatus()
  switch (status) {
    case '良好': return 'green'
    case '正常': return 'blue'
    case '警告': return 'red'
    default: return 'gray'
  }
}

onMounted(() => {
  fetchThreadInfo()
  
  // 监听刷新事件
  mittBus.on('system-refresh', fetchThreadInfo)
})
</script>

<style lang="scss" scoped>
.thread-card {
  margin-bottom: 20px;
}

.ml-2 {
  margin-left: 8px;
}

.mb-4 {
  margin-bottom: 16px;
}

.stat-card {
  padding: 20px;
  text-align: center;
  background: var(--color-bg-2);
  border-radius: 6px;
  
  .stat-value {
    font-size: 24px;
    font-weight: 600;
    color: var(--color-text-1);
    margin-bottom: 8px;
  }
  
  .stat-label {
    font-size: 12px;
    color: var(--color-text-3);
  }
}
</style>