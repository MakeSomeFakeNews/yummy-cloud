<template>
  <a-menu class="right-menu">
    <a-menu-item @click="onClick('add')">
      <template #icon><icon-plus-circle :size="16" :stroke-width="3" /></template>
      <span>新增</span>
    </a-menu-item>

    <a-menu-item @click="onClick('edit')">
      <template #icon><icon-edit :size="16" :stroke-width="3" /></template>
      <span>编辑</span>
    </a-menu-item>

    <a-popover v-model:popup-visible="popupVisible" position="right" trigger="click"
      :content-style="{ padding: 0, overflow: 'hidden' }" :unmount-on-close="true">
      <a-menu-item @click="onClick('move')">
        <template #icon><icon-export :size="16" :stroke-width="3" /></template>
        <a-row justify="space-between" align="center">
          <span>移动</span>
          <icon-right class="arrow-icon" />
        </a-row>
      </a-menu-item>
      <template #content>
        <a-scrollbar style="height: 100%; overflow: auto" outer-style="width: 260px;height: 500px">
          <a-tree show-line size="mini" :data="props.treeData?.[0]?.children"
            :field-names="{ key: 'id', title: 'name' }" @select="onTreeSelect">
          </a-tree>
        </a-scrollbar>
      </template>
    </a-popover>

    <a-menu-item @click="onClick('delete')">
      <template #icon><icon-delete :size="16" :stroke-width="3" /></template>
      <span>删除</span>
    </a-menu-item>
  </a-menu>
</template>

<script lang="ts" setup>
import type {TreeInstance} from '@arco-design/web-vue'
import type {CateTreeItem} from '@/apis/cate'

interface Props {
  treeData: CateTreeItem[]
}

const props = withDefaults(defineProps<Props>(), {
  treeData: () => []
})

const emit = defineEmits<{
  (e: 'on-menu-item-click', mode: string): void
  (e: 'on-tree-node-click', nodeData: CateTreeItem): void
}>()

const popupVisible = ref(false)

const onClick = (mode: string) => {
  emit('on-menu-item-click', mode)
}

const onTreeSelect: TreeInstance['onSelect'] = (selectedKeys, data) => {
  popupVisible.value = false
  emit('on-tree-node-click', data.node as CateTreeItem)
}
</script>

<style lang="scss" scoped>
:deep(.arco-menu-inner) {
  padding: 4px;

  .arco-menu-item {
    height: 34px;

    &:not(.arco-menu-selected) {
      color: $color-text-1;
    }

    &:last-child {
      margin-bottom: 0;
    }
  }
}

.right-menu {
  width: 120px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 4px;
  border: 1px solid var(--color-border-2);
  box-sizing: border-box;

  .arrow-icon {
    margin-right: 0;
  }
}
</style>
