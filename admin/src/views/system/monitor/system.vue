<template>
  <a-card title="系统监控" class="gi_page_card">
    <template #extra>
      <a-space>
        <a-button type="primary" @click="refreshAll">
          <template #icon><icon-refresh /></template>
          刷新数据
        </a-button>
      </a-space>
    </template>
    
    <a-tabs default-active-key="1" type="card">
      <a-tab-pane key="1" title="系统信息">
        <SystemInfo />
      </a-tab-pane>
      <a-tab-pane key="2" title="JVM内存">
        <JvmMemory />
      </a-tab-pane>
      <a-tab-pane key="3" title="垃圾回收">
        <GarbageCollector />
      </a-tab-pane>
      <a-tab-pane key="4" title="线程信息">
        <ThreadInfo />
      </a-tab-pane>
      <a-tab-pane key="5" title="磁盘信息">
        <DiskInfo />
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<script setup lang="ts">
import mittBus from '@/utils/mitt'
import SystemInfo from './components/SystemInfo.vue'
import JvmMemory from './components/JvmMemory.vue'
import GarbageCollector from './components/GarbageCollector.vue'
import ThreadInfo from './components/ThreadInfo.vue'
import DiskInfo from './components/DiskInfo.vue'

defineOptions({ name: 'SystemMonitor' })

// 刷新所有数据
const refreshAll = () => {
  mittBus.emit('system-refresh')
}
</script>

<style lang="scss" scoped>
.gi_page_card {
  height: calc(100vh - 100px);
  
  :deep(.arco-card-body) {
    padding: 20px;
    height: calc(100% - 60px);
    overflow: auto;
  }
  
  :deep(.arco-tabs-content) {
    height: calc(100% - 44px);
    overflow: auto;
  }
}
</style>