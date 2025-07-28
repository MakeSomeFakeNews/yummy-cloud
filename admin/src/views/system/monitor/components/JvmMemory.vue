<template>
  <a-card class="jvm-card" :loading="loading">
    <template #title>
      <icon-file />
      <span class="ml-2">JVM内存信息</span>
    </template>
    
    <!-- 堆内存信息 -->
    <a-card class="memory-section" title="堆内存 (Heap Memory)" size="small">
      <a-descriptions :column="2" bordered>
        <a-descriptions-item label="初始大小">
          <span>{{ jvmInfo.heap_memory?.init || '-' }}</span>
        </a-descriptions-item>
        <a-descriptions-item label="已使用">
          <a-tag color="blue">{{ jvmInfo.heap_memory?.used || '-' }}</a-tag>
        </a-descriptions-item>
        <a-descriptions-item label="已提交">
          <span>{{ jvmInfo.heap_memory?.committed || '-' }}</span>
        </a-descriptions-item>
        <a-descriptions-item label="最大值">
          <span>{{ jvmInfo.heap_memory?.max || '-' }}</span>
        </a-descriptions-item>
        <a-descriptions-item label="使用率" :span="2">
          <a-tag :color="getUsageColor(jvmInfo.heap_memory?.usage_percent)">
            {{ jvmInfo.heap_memory?.usage_percent || '0.00' }}%
          </a-tag>
        </a-descriptions-item>
      </a-descriptions>
    </a-card>
    
    <!-- 非堆内存信息 -->
    <a-card class="memory-section" title="非堆内存 (Non-Heap Memory)" size="small">
      <a-descriptions :column="2" bordered>
        <a-descriptions-item label="初始大小">
          <span>{{ jvmInfo.non_heap_memory?.init || '-' }}</span>
        </a-descriptions-item>
        <a-descriptions-item label="已使用">
          <a-tag color="purple">{{ jvmInfo.non_heap_memory?.used || '-' }}</a-tag>
        </a-descriptions-item>
        <a-descriptions-item label="已提交">
          <span>{{ jvmInfo.non_heap_memory?.committed || '-' }}</span>
        </a-descriptions-item>
        <a-descriptions-item label="最大值">
          <span>{{ jvmInfo.non_heap_memory?.max || '-' }}</span>
        </a-descriptions-item>
      </a-descriptions>
    </a-card>
    
    <!-- 内存池信息 -->
    <a-card class="memory-section" title="内存池详情" size="small">
      <a-table 
        :columns="poolColumns" 
        :data="poolData" 
        :pagination="false"
        :scroll="{ y: 300 }"
      >
        <template #pool_name="{ record }">
          <span class="pool-name">{{ record.pool_name }}</span>
        </template>
        
        <template #type="{ record }">
          <a-tag :color="getTypeColor(record.type)">{{ record.type }}</a-tag>
        </template>
        
        <template #used="{ record }">
          <span class="number-value">{{ record.used }}</span>
        </template>
        
        <template #max="{ record }">
          <span class="number-value">{{ record.max }}</span>
        </template>
        
        <template #usage_percent="{ record }">
          <a-tag v-if="record.usage_percent" :color="getUsageColor(record.usage_percent)">
            {{ record.usage_percent }}%
          </a-tag>
          <span v-else>-</span>
        </template>
      </a-table>
    </a-card>
  </a-card>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { getJvmMemoryInfo } from '@/apis/system/monitor'
import mittBus from '@/utils/mitt'

defineOptions({ name: 'JvmMemory' })

const loading = ref(false)
const jvmInfo = ref<any>({})

const poolColumns = [
  {
    title: '内存池名称',
    dataIndex: 'pool_name',
    slotName: 'pool_name',
    width: 200,
    fixed: 'left'
  },
  {
    title: '类型',
    dataIndex: 'type',
    slotName: 'type',
    width: 100
  },
  {
    title: '已使用',
    dataIndex: 'used',
    slotName: 'used',
    width: 120
  },
  {
    title: '最大值',
    dataIndex: 'max',
    slotName: 'max',
    width: 120
  },
  {
    title: '使用率',
    dataIndex: 'usage_percent',
    slotName: 'usage_percent',
    width: 100
  }
]

// 内存池表格数据
const poolData = computed(() => {
  const data = []
  for (const key in jvmInfo.value) {
    if (key !== 'heap_memory' && key !== 'non_heap_memory') {
      const pool = jvmInfo.value[key]
      if (pool && typeof pool === 'object') {
        data.push({
          pool_name: key.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase()),
          type: pool.type || '-',
          used: pool.used || '-',
          max: pool.max || '-',
          usage_percent: pool.usage_percent || null
        })
      }
    }
  }
  return data
})

// 获取JVM内存信息
const fetchJvmMemoryInfo = async () => {
  loading.value = true
  try {
    const res = await getJvmMemoryInfo()
    jvmInfo.value = res.data || {}
  } catch (error) {
    console.error('获取JVM内存信息失败:', error)
  } finally {
    loading.value = false
  }
}

// 获取使用率颜色
const getUsageColor = (percent: string) => {
  const num = parseFloat(percent || '0')
  if (num < 70) return 'green'
  if (num < 90) return 'orange'
  return 'red'
}

// 获取类型颜色
const getTypeColor = (type: string) => {
  if (type === 'HEAP') return 'blue'
  if (type === 'NON_HEAP') return 'purple'
  return 'gray'
}

onMounted(() => {
  fetchJvmMemoryInfo()
  
  // 监听刷新事件
  mittBus.on('system-refresh', fetchJvmMemoryInfo)
})
</script>

<style lang="scss" scoped>
.jvm-card {
  margin-bottom: 20px;
}

.memory-section {
  margin-bottom: 16px;
  
  &:last-child {
    margin-bottom: 0;
  }
}

.ml-2 {
  margin-left: 8px;
}

.pool-name {
  font-weight: 500;
}

.number-value {
  font-family: 'Courier New', monospace;
  font-weight: 500;
}
</style>