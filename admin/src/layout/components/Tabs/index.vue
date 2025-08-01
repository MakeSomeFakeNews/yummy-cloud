<!--
  @file Tabs 组件
  @description 系统标签页组件，支持多种标签样式、右键菜单和快捷操作
-->
<template>
  <div v-if="appStore.tabVisible" class="tabs">
    <a-tabs :class="`tabs__${appStore.tab}`" :type="tabsType" :active-key="route.path" editable hide-content
      size="medium" @tab-click="handleTabClick" @delete="tabsStore.closeCurrent">
      <a-tab-pane v-for="item of tabsStore.tabList" :key="item.path" :title="(item.meta?.title as string)"
        :closable="!item.meta?.affix">
        <template #title>
          <a-dropdown trigger="contextMenu">
            <div v-if="appStore.tab === 'custom2'" class="tabs-pane__wrapper" @dblclick="handleDoubleClick(item)">
              <a-tag class="tabs-pane__tag" :closable="!item.meta?.affix"
                :color="route.path === item.path ? 'arcoblue' : undefined" @close="tabsStore.closeCurrent(item.path)">
                {{ item.meta?.title }}
              </a-tag>
            </div>
            <div v-else class="tabs-pane__wrapper" @dblclick="handleDoubleClick(item)">
              {{ item.meta?.title }}
            </div>
            <template #content>
              <a-doption @click="tabsStore.closeCurrent(item.path)" :disabled="item.meta?.affix">
                <template #icon><icon-close /></template>
                <template #default>关闭当前 {{ item.meta?.affix ? '(固定)' : '' }}</template>
              </a-doption>
              <a-divider margin="4px" />
              <a-doption @click="tabsStore.closeRight(item.path)" :disabled="!hasTabsOnRight(item.path)">
                <template #icon><icon-arrow-right /></template>
                <template #default>关闭右侧</template>
              </a-doption>
              <a-doption @click="tabsStore.closeOther(item.path)" :disabled="!hasOtherTabs(item.path)">
                <template #icon><icon-eraser /></template>
                <template #default>关闭其他</template>
              </a-doption>
              <a-divider margin="4px" />
              <a-doption @click="tabsStore.closeAll" :disabled="tabsStore.tabList.every(tab => tab.meta?.affix)">
                <template #icon><icon-minus /></template>
                <template #default>关闭全部</template>
              </a-doption>
            </template>
          </a-dropdown>
        </template>
      </a-tab-pane>

      <template #extra>
        <a-space size="medium">
          <ReloadIcon></ReloadIcon>
          <a-dropdown trigger="hover">
            <MagicIcon></MagicIcon>
            <template #content>
              <a-doption @click="tabsStore.closeCurrent(route.path)">
                <template #icon><icon-close /></template>
                <template #default>关闭当前</template>
              </a-doption>
              <a-doption @click="tabsStore.closeRight(route.path)">
                <template #icon><icon-close /></template>
                <template #default>关闭右侧</template>
              </a-doption>
              <a-doption @click="tabsStore.closeOther(route.path)">
                <template #icon><icon-eraser /></template>
                <template #default>关闭其他</template>
              </a-doption>
              <a-doption @click="tabsStore.closeAll">
                <template #icon><icon-minus /></template>
                <template #default>关闭全部</template>
              </a-doption>
            </template>
          </a-dropdown>
        </a-space>
      </template>
    </a-tabs>
  </div>
</template>

<script setup lang="ts">
import type {TabsInstance} from '@arco-design/web-vue'
import Sortable from 'sortablejs'
import MagicIcon from './MagicIcon.vue'
import ReloadIcon from './ReloadIcon.vue'
import {useAppStore, useTabsStore} from '@/stores'
import {useRouteListener} from '@/hooks'

/** 组件名称 */
defineOptions({ name: 'Tabs' })
const route = useRoute()
const router = useRouter()
const appStore = useAppStore()
const tabsStore = useTabsStore()
const { listenerRouteChange } = useRouteListener()
const tabsType = computed(() => {
  return (['custom1', 'custom2'].includes(appStore.tab) ? 'card' : appStore.tab) as unknown as TabsInstance['type']
})

/** 初始化标签页 */
tabsStore.init()

/** 监听路由变化 */
listenerRouteChange(({ to }) => {
  if (to.name) {
    const rawTo = toRaw(to)
    tabsStore.addTabItem(rawTo)
    tabsStore.addCacheItem(rawTo)
  }
})

/** 处理标签页点击 */
const handleTabClick = (key: string) => {
  const targetTab = tabsStore.tabList.find((tab) => tab.path === key)
  router.push(targetTab?.fullPath || targetTab?.path || key)
}

/** 处理双击关闭标签 */
const handleDoubleClick = (item: any) => {
  if (!item.meta?.affix) {
    tabsStore.closeCurrent(item.path)
  }
}

/** 检查是否有右侧标签页 */
const hasTabsOnRight = (currentPath: string): boolean => {
  const currentIndex = tabsStore.tabList.findIndex(tab => tab.path === currentPath)
  return currentIndex < tabsStore.tabList.length - 1 && 
         tabsStore.tabList.slice(currentIndex + 1).some(tab => !tab.meta?.affix)
}

/** 检查是否有其他可关闭的标签页 */
const hasOtherTabs = (currentPath: string): boolean => {
  return tabsStore.tabList.some(tab => tab.path !== currentPath && !tab.meta?.affix)
}

/** 键盘快捷键处理 */
const handleKeydown = (event: KeyboardEvent) => {
  // Ctrl/Cmd + W 关闭当前标签
  if ((event.ctrlKey || event.metaKey) && event.key === 'w') {
    event.preventDefault()
    const currentTab = tabsStore.tabList.find(tab => tab.path === route.path)
    if (currentTab && !currentTab.meta?.affix) {
      tabsStore.closeCurrent(route.path)
    }
  }
  
  // Ctrl/Cmd + Shift + W 关闭其他标签
  if ((event.ctrlKey || event.metaKey) && event.shiftKey && event.key === 'W') {
    event.preventDefault()
    tabsStore.closeOther(route.path)
  }
}

onMounted(() => {
  Sortable.create(document.querySelector('.arco-tabs-nav-tab-list') as HTMLElement, {
    draggable: '.arco-tabs-tab-closable',
    animation: 300,
    onEnd({ newIndex, oldIndex }) {
      const tabsList = [...tabsStore.tabList]
      const currRow = tabsList.splice(oldIndex as number, 1)[0]
      tabsList.splice(newIndex as number, 0, currRow)
      tabsStore.tabList = tabsList
    }
  })
  
  // 添加键盘事件监听
  document.addEventListener('keydown', handleKeydown)
})

onUnmounted(() => {
  // 清理键盘事件监听
  document.removeEventListener('keydown', handleKeydown)
})
</script>

<style lang="scss" scoped>
.tabs {
  padding-top: 5px;
  background-color: var(--color-bg-1);

  &-pane__tag {
    margin: 0 4px;
  }

  &-pane__wrapper {
    display: inline-block;
    width: 100%;
    height: 100%;
    cursor: pointer;
    user-select: none;
    transition: all 0.2s ease;
    
    &:hover {
      opacity: 0.8;
    }
  }
}

// 标签页导航样式
:deep(.arco-tabs-nav-tab) {
  .arco-tabs-tab {
    border-bottom-color: transparent !important;

    svg {
      width: 0;
      transition: all 0.15s;
    }

    &:hover svg {
      width: 1em;
    }
  }

  &:not(.arco-tabs-nav-tab-scroll) .arco-tabs-tab:first-child {
    border-left: 0;
  }
}

// 自定义样式1
:deep(.tabs__custom1) {
  .arco-tabs-nav-tab {
    .arco-tabs-tab {
      padding: 5px 20px;
      border: none;
      -webkit-mask: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAANoAAAAkBAMAAAAdqzmBAAAAMFBMVEVHcEwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlTPQ5AAAAD3RSTlMAr3DvEM8wgCBA379gj5//tJBPAAAAnUlEQVRIx2NgAAM27fj/tAO/xBsYkIHyf9qCT8iWMf6nNQhAsk2f5rYheY7Dnua2/U+A28ZEe8v+F9Ax2v7/F4DbxkUH2wzgtvHTwbYPo7aN2jZq26hto7aN2jZq25Cy7Qvctnw62PYNbls9HWz7S8/G6//PsI6H4396gAUQy1je08W2jxDbpv6nD4gB2uWp+J9eYPsEhv/0BPS1DQBvoBLVZ3BppgAAAABJRU5ErkJggg==);
      mask: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAANoAAAAkBAMAAAAdqzmBAAAAMFBMVEVHcEwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlTPQ5AAAAD3RSTlMAr3DvEM8wgCBA379gj5//tJBPAAAAnUlEQVRIx2NgAAM27fj/tAO/xBsYkIHyf9qCT8iWMf6nNQhAsk2f5rYheY7Dnua2/U+A28ZEe8v+F9Ax2v7/F4DbxkUH2wzgtvHTwbYPo7aN2jZq26hto7aN2jZq25Cy7Qvctnw62PYNbls9HWz7S8/G6//PsI6H4396gAUQy1je08W2jxDbpv6nD4gB2uWp+J9eYPsEhv/0BPS1DQBvoBLVZ3BppgAAAABJRU5ErkJggg==);
      -webkit-mask-size: 100% 100%;
      mask-size: 100% 100%;
    }
  }

  .arco-tabs-nav-tab-list {
    .arco-tabs-tab-active {
      background-color: rgba(var(--primary-6), 0.1);
      padding: 5px 20px;
      -webkit-mask: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAANoAAAAkBAMAAAAdqzmBAAAAMFBMVEVHcEwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlTPQ5AAAAD3RSTlMAr3DvEM8wgCBA379gj5//tJBPAAAAnUlEQVRIx2NgAAM27fj/tAO/xBsYkIHyf9qCT8iWMf6nNQhAsk2f5rYheY7Dnua2/U+A28ZEe8v+F9Ax2v7/F4DbxkUH2wzgtvHTwbYPo7aN2jZq26hto7aN2jZq25Cy7Qvctnw62PYNbls9HWz7S8/G6//PsI6H4396gAUQy1je08W2jxDbpv6nD4gB2uWp+J9eYPsEhv/0BPS1DQBvoBLVZ3BppgAAAABJRU5ErkJggg==);
      mask: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAANoAAAAkBAMAAAAdqzmBAAAAMFBMVEVHcEwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlTPQ5AAAAD3RSTlMAr3DvEM8wgCBA379gj5//tJBPAAAAnUlEQVRIx2NgAAM27fj/tAO/xBsYkIHyf9qCT8iWMf6nNQhAsk2f5rYheY7Dnua2/U+A28ZEe8v+F9Ax2v7/F4DbxkUH2wzgtvHTwbYPo7aN2jZq26hto7aN2jZq25Cy7Qvctnw62PYNbls9HWz7S8/G6//PsI6H4396gAUQy1je08W2jxDbpv6nD4gB2uWp+J9eYPsEhv/0BPS1DQBvoBLVZ3BppgAAAABJRU5ErkJggg==);
      -webkit-mask-size: 100% 100%;
      mask-size: 100% 100%;

      &:hover {
        padding: 5px 20px;
        background-color: rgba(var(--primary-6), 0.1);
      }
    }
  }
}

// 自定义样式2
:deep(.tabs__custom2) {
  .arco-tabs-nav {
    padding-bottom: 6px;
  }

  .arco-tabs-tab {
    padding: 0;
    border: 0;

    .arco-tabs-tab-close-btn {
      display: none;
    }

    &:hover {
      background-color: transparent;
    }
  }
}

:deep(.arco-dropdown-option-icon) {
  color: var(--color-text-3);
}
</style>
