<template>
  <a-card class="redis-card" :loading="loading">
    <template #title>
      <icon-storage />
      <span class="ml-2">键空间统计</span>
    </template>
    
    <a-row :gutter="16" class="mb-4">
      <a-col :span="6">
        <div class="stat-card">
          <div class="stat-value">{{ keyspaceInfo.current_db_keys || 0 }}</div>
          <div class="stat-label">当前数据库键数</div>
        </div>
      </a-col>
      <a-col :span="6">
        <div class="stat-card">
          <div class="stat-value">{{ getTotalKeys() }}</div>
          <div class="stat-label">所有数据库键数</div>
        </div>
      </a-col>
      <a-col :span="6">
        <div class="stat-card">
          <div class="stat-value">{{ getTotalExpires() }}</div>
          <div class="stat-label">设置过期键数</div>
        </div>
      </a-col>
      <a-col :span="6">
        <div class="stat-card">
          <div class="stat-value">{{ getDbCount() }}</div>
          <div class="stat-label">使用数据库数</div>
        </div>
      </a-col>
    </a-row>
    
    <a-table 
      :columns="columns" 
      :data="tableData" 
      :pagination="false"
      :scroll="{ y: 300 }"
    >
      <template #database="{ record }">
        <a-tag color="blue">{{ record.database }}</a-tag>
      </template>
      
      <template #keys="{ record }">
        <span class="number-value">{{ record.keys }}</span>
      </template>
      
      <template #expires="{ record }">
        <span class="number-value">{{ record.expires }}</span>
      </template>
      
      <template #avg_ttl="{ record }">
        <span class="number-value">{{ formatTTL(record.avg_ttl) }}</span>
      </template>
      
    </a-table>
  </a-card>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { getRedisKeyspaceInfo } from '@/apis/system/monitor'
import mittBus from '@/utils/mitt'

defineOptions({ name: 'RedisKeyStats' })

const loading = ref(false)
const keyspaceInfo = ref<any>({})

const columns = [
  {
    title: '数据库',
    dataIndex: 'database',
    slotName: 'database',
    width: 100
  },
  {
    title: '键数量',
    dataIndex: 'keys',
    slotName: 'keys',
    width: 120
  },
  {
    title: '过期键数',
    dataIndex: 'expires',
    slotName: 'expires',
    width: 120
  },
  {
    title: '平均TTL',
    dataIndex: 'avg_ttl',
    slotName: 'avg_ttl',
    width: 120
  },
]

// 表格数据
const tableData = computed(() => {
  const data = []
  for (const key in keyspaceInfo.value) {
    if (key.startsWith('db')) {
      const dbInfo = keyspaceInfo.value[key]
      if (dbInfo && typeof dbInfo === 'object') {
        data.push({
          database: key,
          keys: dbInfo.keys || '0',
          expires: dbInfo.expires || '0',
          avg_ttl: dbInfo.avg_ttl || '0'
        })
      }
    }
  }
  return data.sort((a, b) => a.database.localeCompare(b.database))
})

// 获取Redis键空间信息
const fetchKeyspaceInfo = async () => {
  loading.value = true
  try {
    const res = await getRedisKeyspaceInfo()
    keyspaceInfo.value = res.data || {}
  } catch (error) {
    console.error('获取Redis键空间信息失败:', error)
  } finally {
    loading.value = false
  }
}

// 获取总键数
const getTotalKeys = () => {
  let total = 0
  for (const key in keyspaceInfo.value) {
    if (key.startsWith('db')) {
      const dbInfo = keyspaceInfo.value[key]
      if (dbInfo && dbInfo.keys) {
        total += parseInt(dbInfo.keys)
      }
    }
  }
  return total
}

// 获取总过期键数
const getTotalExpires = () => {
  let total = 0
  for (const key in keyspaceInfo.value) {
    if (key.startsWith('db')) {
      const dbInfo = keyspaceInfo.value[key]
      if (dbInfo && dbInfo.expires) {
        total += parseInt(dbInfo.expires)
      }
    }
  }
  return total
}

// 获取使用的数据库数量
const getDbCount = () => {
  let count = 0
  for (const key in keyspaceInfo.value) {
    if (key.startsWith('db')) {
      count++
    }
  }
  return count
}

// 格式化TTL
const formatTTL = (ttl: string) => {
  if (!ttl || ttl === '0') return '-'
  const ms = parseInt(ttl)
  if (ms < 1000) return ms + 'ms'
  const seconds = Math.floor(ms / 1000)
  if (seconds < 60) return seconds + 's'
  const minutes = Math.floor(seconds / 60)
  if (minutes < 60) return minutes + 'm'
  const hours = Math.floor(minutes / 60)
  return hours + 'h'
}


onMounted(() => {
  fetchKeyspaceInfo()
  
  // 监听刷新事件
  mittBus.on('redis-refresh', fetchKeyspaceInfo)
})
</script>

<style lang="scss" scoped>
.redis-card {
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

.number-value {
  font-family: 'Courier New', monospace;
  font-weight: 500;
}
</style>