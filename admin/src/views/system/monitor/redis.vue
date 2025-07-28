<template>
  <a-card title="Redis监控" class="gi_page_card">
    <a-tabs default-active-key="1" type="card">
      <a-tab-pane key="1" title="基础信息">
        <RedisBasicInfo></RedisBasicInfo>
      </a-tab-pane>
      <a-tab-pane key="2" title="内存分析">
        <RedisMemory></RedisMemory>
      </a-tab-pane>
      <a-tab-pane key="3" title="键统计">
        <RedisKeyStats></RedisKeyStats>
      </a-tab-pane>
      <a-tab-pane key="4" title="命令统计">
        <RedisCommandStats></RedisCommandStats>
      </a-tab-pane>
    </a-tabs>

    <template #extra>
      <a-space>
        <a-button type="primary" @click="refresh">
          <template #icon><icon-refresh /></template>
          <span>刷新</span>
        </a-button>
        <a-popconfirm type="warning" content="确定清空Redis缓存吗?" @before-ok="flushDB">
          <a-button type="primary" status="danger">
            <template #icon><icon-delete /></template>
            <span>清空缓存</span>
          </a-button>
        </a-popconfirm>
      </a-space>
    </template>
  </a-card>
</template>

<script setup lang="ts">
import RedisBasicInfo from './components/RedisBasicInfo.vue'
import RedisMemory from './components/RedisMemory.vue'
import RedisKeyStats from './components/RedisKeyStats.vue'
import RedisCommandStats from './components/RedisCommandStats.vue'
import { Message } from '@arco-design/web-vue'
import { flushRedisDB } from '@/apis/system/monitor'
import mittBus from '@/utils/mitt'

defineOptions({ name: 'SystemRedisMonitor' })

// 刷新所有组件数据
const refresh = () => {
  mittBus.emit('redis-refresh')
  Message.success('数据已刷新')
}

// 清空Redis缓存
const flushDB = async () => {
  const res = await flushRedisDB()
  Message.success(res)
  refresh()
}
</script>

<style lang="scss" scoped>
.redis-card {
  margin-bottom: 20px;
}
</style> 