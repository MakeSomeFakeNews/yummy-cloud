<template>
  <a-config-provider update-at-scroll>
    <router-view></router-view>
    <template #loading>
      <img src="/static/images/loading.gif" class="loading-icon" />
    </template>
  </a-config-provider>
</template>

<script setup lang="ts">
import {useAppStore, useDictStore} from '@/stores'

defineOptions({ name: 'App' })
const appStore = useAppStore()
appStore.initTheme()
const dictStore = useDictStore()
dictStore.getDictData()
</script>

<style lang="scss" scoped>
.loading-icon {
  width: 30px;
}
</style>
