<template>
  <a-card class="gc-card" :loading="loading">
    <template #title>
      <icon-delete />
      <span class="ml-2">垃圾回收统计</span>
    </template>
    
    <a-table 
      :columns="columns" 
      :data="tableData" 
      :pagination="false"
      :scroll="{ y: 400 }"
    >
      <template #gc_name="{ record }">
        <a-tag color="blue">{{ record.gc_name }}</a-tag>
      </template>
      
      <template #collection_count="{ record }">
        <span class="number-value">{{ record.collection_count }}</span>
      </template>
      
      <template #collection_time="{ record }">
        <span class="number-value">{{ record.collection_time }}</span>
      </template>
      
      <template #avg_time="{ record }">
        <span class="number-value">{{ record.avg_time }}</span>
      </template>
    </a-table>
    
    <a-empty v-if="tableData.length === 0" description="暂无垃圾回收器数据" />
  </a-card>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { getGarbageCollectorInfo } from '@/apis/system/monitor'
import mittBus from '@/utils/mitt'

defineOptions({ name: 'GarbageCollector' })

const loading = ref(false)
const gcInfo = ref<any>({})

const columns = [
  {
    title: '垃圾回收器名称',
    dataIndex: 'gc_name',
    slotName: 'gc_name',
    width: 200,
    fixed: 'left'
  },
  {
    title: '回收次数',
    dataIndex: 'collection_count',
    slotName: 'collection_count',
    width: 120,
    sortable: {
      sortDirections: ['ascend', 'descend']
    }
  },
  {
    title: '回收时间',
    dataIndex: 'collection_time',
    slotName: 'collection_time',
    width: 120,
    sortable: {
      sortDirections: ['ascend', 'descend']
    }
  },
  {
    title: '平均时间',
    dataIndex: 'avg_time',
    slotName: 'avg_time',
    width: 120
  }
]

// 表格数据
const tableData = computed(() => {
  const data = []
  for (const key in gcInfo.value) {
    const gc = gcInfo.value[key]
    if (gc && typeof gc === 'object') {
      const count = parseInt(gc.collection_count || '0')
      const time = parseInt(gc.collection_time?.replace('ms', '') || '0')
      data.push({
        gc_name: formatGcName(key),
        collection_count: gc.collection_count || '0',
        collection_time: gc.collection_time || '0ms',
        avg_time: count > 0 ? (time / count).toFixed(2) + 'ms' : '0ms'
      })
    }
  }
  return data.sort((a, b) => parseInt(b.collection_count) - parseInt(a.collection_count))
})

// 获取垃圾回收信息
const fetchGarbageCollectorInfo = async () => {
  loading.value = true
  try {
    const res = await getGarbageCollectorInfo()
    gcInfo.value = res.data || {}
  } catch (error) {
    console.error('获取垃圾回收信息失败:', error)
  } finally {
    loading.value = false
  }
}

// 格式化GC名称
const formatGcName = (name: string) => {
  return name.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase())
}

onMounted(() => {
  fetchGarbageCollectorInfo()
  
  // 监听刷新事件
  mittBus.on('system-refresh', fetchGarbageCollectorInfo)
})
</script>

<style lang="scss" scoped>
.gc-card {
  margin-bottom: 20px;
}

.ml-2 {
  margin-left: 8px;
}

.number-value {
  font-family: 'Courier New', monospace;
  font-weight: 500;
}
</style>