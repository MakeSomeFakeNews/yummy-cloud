<template>
  <a-card class="disk-card" :loading="loading">
    <template #title>
      <icon-storage />
      <span class="ml-2">磁盘使用情况</span>
    </template>
    
    <a-table 
      :columns="columns" 
      :data="tableData" 
      :pagination="false"
      :scroll="{ y: 400 }"
    >
      <template #disk_name="{ record }">
        <a-tag color="blue">{{ record.disk_name }}</a-tag>
      </template>
      
      <template #total="{ record }">
        <span class="number-value">{{ record.total }}</span>
      </template>
      
      <template #used="{ record }">
        <span class="number-value">{{ record.used }}</span>
      </template>
      
      <template #free="{ record }">
        <span class="number-value">{{ record.free }}</span>
      </template>
      
      <template #usage_percent="{ record }">
        <a-tag v-if="record.usage_percent" :color="getUsageColor(record.usage_percent)">
          {{ record.usage_percent }}%
        </a-tag>
        <span v-else>-</span>
      </template>
    </a-table>
    
    <a-empty v-if="tableData.length === 0" description="暂无磁盘数据" />
  </a-card>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { getDiskInfo } from '@/apis/system/monitor'
import mittBus from '@/utils/mitt'

defineOptions({ name: 'DiskInfo' })

const loading = ref(false)
const diskInfo = ref<any>({})

const columns = [
  {
    title: '磁盘',
    dataIndex: 'disk_name',
    slotName: 'disk_name',
    width: 100,
    fixed: 'left'
  },
  {
    title: '总大小',
    dataIndex: 'total',
    slotName: 'total',
    width: 120,
    sortable: {
      sortDirections: ['ascend', 'descend']
    }
  },
  {
    title: '已使用',
    dataIndex: 'used',
    slotName: 'used',
    width: 120,
    sortable: {
      sortDirections: ['ascend', 'descend']
    }
  },
  {
    title: '可用空间',
    dataIndex: 'free',
    slotName: 'free',
    width: 120,
    sortable: {
      sortDirections: ['ascend', 'descend']
    }
  },
  {
    title: '使用率',
    dataIndex: 'usage_percent',
    slotName: 'usage_percent',
    width: 100
  }
]

// 表格数据
const tableData = computed(() => {
  const data = []
  for (const key in diskInfo.value) {
    const disk = diskInfo.value[key]
    if (disk && typeof disk === 'object') {
      data.push({
        disk_name: formatDiskName(key),
        total: disk.total || '-',
        used: disk.used || '-',
        free: disk.free || '-',
        usage_percent: disk.usage_percent || null
      })
    }
  }
  return data.sort((a, b) => {
    const aUsage = parseFloat(a.usage_percent || '0')
    const bUsage = parseFloat(b.usage_percent || '0')
    return bUsage - aUsage
  })
})

// 获取磁盘信息
const fetchDiskInfo = async () => {
  loading.value = true
  try {
    const res = await getDiskInfo()
    diskInfo.value = res.data || {}
  } catch (error) {
    console.error('获取磁盘信息失败:', error)
  } finally {
    loading.value = false
  }
}

// 格式化磁盘名称
const formatDiskName = (name: string) => {
  if (!name) return '未知'
  if (name === '') return '根目录 /'
  return name.toUpperCase() + ':'
}

// 获取使用率颜色
const getUsageColor = (percent: string) => {
  const num = parseFloat(percent || '0')
  if (num < 70) return 'green'
  if (num < 90) return 'orange'
  return 'red'
}

onMounted(() => {
  fetchDiskInfo()
  
  // 监听刷新事件
  mittBus.on('system-refresh', fetchDiskInfo)
})
</script>

<style lang="scss" scoped>
.disk-card {
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