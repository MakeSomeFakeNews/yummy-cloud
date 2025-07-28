<template>
  <a-card class="redis-card" :loading="loading">
    <template #title>
      <icon-code />
      <span class="ml-2">命令执行统计</span>
    </template>
    
    <template #extra>
      <a-input-search
        v-model="searchText"
        placeholder="搜索命令"
        style="width: 200px"
        allow-clear
      />
    </template>
    
    <a-table 
      :columns="columns" 
      :data="filteredData" 
      :pagination="{ pageSize: 10, showTotal: true }"
      :scroll="{ y: 400 }"
    >
      <template #command="{ record }">
        <a-tag color="arcoblue">{{ record.command.toUpperCase() }}</a-tag>
      </template>
      
      <template #calls="{ record }">
        <span class="number-value">{{ formatNumber(record.calls) }}</span>
      </template>
      
      <template #usec="{ record }">
        <span class="number-value">{{ formatTime(record.usec) }}</span>
      </template>
      
      <template #usec_per_call="{ record }">
        <span class="number-value">{{ formatTime(record.usec_per_call) }}</span>
      </template>
      
    </a-table>
  </a-card>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { getRedisCommandStats } from '@/apis/system/monitor'
import mittBus from '@/utils/mitt'

defineOptions({ name: 'RedisCommandStats' })

const loading = ref(false)
const commandStats = ref<any>({})
const searchText = ref('')

const columns = [
  {
    title: '命令',
    dataIndex: 'command',
    slotName: 'command',
    width: 100,
    fixed: 'left'
  },
  {
    title: '调用次数',
    dataIndex: 'calls',
    slotName: 'calls',
    width: 120,
    sortable: {
      sortDirections: ['ascend', 'descend']
    }
  },
  {
    title: '总耗时(μs)',
    dataIndex: 'usec',
    slotName: 'usec',
    width: 120,
    sortable: {
      sortDirections: ['ascend', 'descend']
    }
  },
  {
    title: '平均耗时(μs)',
    dataIndex: 'usec_per_call',
    slotName: 'usec_per_call',
    width: 130,
    sortable: {
      sortDirections: ['ascend', 'descend']
    }
  },
]

// 表格数据
const tableData = computed(() => {
  const data = []
  for (const command in commandStats.value) {
    const stats = commandStats.value[command]
    if (stats && typeof stats === 'object') {
      data.push({
        command,
        calls: stats.calls || '0',
        usec: stats.usec || '0',
        usec_per_call: stats.usec_per_call || '0'
      })
    }
  }
  // 按调用次数降序排序
  return data.sort((a, b) => parseInt(b.calls) - parseInt(a.calls))
})

// 过滤后的数据
const filteredData = computed(() => {
  if (!searchText.value) return tableData.value
  return tableData.value.filter(item => 
    item.command.toLowerCase().includes(searchText.value.toLowerCase())
  )
})


// 获取Redis命令统计信息
const fetchCommandStats = async () => {
  loading.value = true
  try {
    const res = await getRedisCommandStats()
    commandStats.value = res.data || {}
  } catch (error) {
    console.error('获取Redis命令统计失败:', error)
  } finally {
    loading.value = false
  }
}

// 格式化数字
const formatNumber = (num: string) => {
  const n = parseInt(num)
  if (n >= 1000000) {
    return (n / 1000000).toFixed(1) + 'M'
  }
  if (n >= 1000) {
    return (n / 1000).toFixed(1) + 'K'
  }
  return n.toString()
}

// 格式化时间
const formatTime = (usec: string) => {
  const us = parseInt(usec)
  if (us >= 1000000) {
    return (us / 1000000).toFixed(2) + 's'
  }
  if (us >= 1000) {
    return (us / 1000).toFixed(2) + 'ms'
  }
  return us + 'μs'
}


onMounted(() => {
  fetchCommandStats()
  
  // 监听刷新事件
  mittBus.on('redis-refresh', fetchCommandStats)
})
</script>

<style lang="scss" scoped>
.redis-card {
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