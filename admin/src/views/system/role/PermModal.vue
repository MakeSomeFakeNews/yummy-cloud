<template>
  <a-modal v-model:visible="visible" :title="title" :fullscreen="isMobile()" :mask-closable="false" width="600px"
    @ok="save" @cancel="onCancel">
    <div class="perm-modal-content">
      <div class="perm-header">
        <a-space>
          <a-button size="small" @click="expandAll">
            <template #icon><icon-down /></template>
            展开全部
          </a-button>
          <a-button size="small" @click="collapseAll">
            <template #icon><icon-up /></template>
            收起全部
          </a-button>
          <a-button size="small" @click="checkAll">
            <template #icon><icon-check /></template>
            全选
          </a-button>
          <a-button size="small" @click="uncheckAll">
            <template #icon><icon-close /></template>
            全不选
          </a-button>
        </a-space>
      </div>
      <div class="perm-tree">
        <a-tree 
          ref="treeRef" 
          v-model:checked-keys="menuIds" 
          size="medium" 
          checkable 
          :check-strictly="true" 
          :data="treeData"
          :field-names="{ key: 'id', title: 'title', children: 'children' }" 
          :default-expand-all="false"
          show-line
        >
          <template #title="{ title, type }">
            <span class="tree-node-title">
              <icon-folder v-if="type === 'M'" class="menu-icon" />
              <icon-menu v-else-if="type === 'C'" class="menu-icon" />
              <icon-link v-else class="menu-icon" />
              {{ title }}
            </span>
          </template>
        </a-tree>
      </div>
    </div>
  </a-modal>
</template>

<script lang="ts" setup>
import { Message } from '@arco-design/web-vue'
import { getRoleMenuIds, setRoleMenus } from '@/apis/system/role'
import { type MenuOptionsItem, getMenuOptions } from '@/apis/system/menu'
import { isMobile } from '@/utils'

const treeRef = useTemplateRef('treeRef')
const treeData = ref<MenuOptionsItem[]>([])
const getTreeData = async () => {
  const res = await getMenuOptions()
  treeData.value = res.data
}
getTreeData()

// 控制树展开/收起
const expandAll = () => {
  treeRef.value?.expandAll()
}

const collapseAll = () => {
  treeRef.value?.collapseAll()
}

// 全选/取消全选
const checkAll = () => {
  const getAllNodeIds = (nodes: MenuOptionsItem[]): string[] => {
    let ids: string[] = []
    nodes.forEach(node => {
      ids.push(node.id)
      if (node.children && node.children.length > 0) {
        ids = ids.concat(getAllNodeIds(node.children))
      }
    })
    return ids
  }
  menuIds.value = getAllNodeIds(treeData.value)
}

const uncheckAll = () => {
  menuIds.value = []
}

const roleCode = ref('')
const title = computed(() => `分配权限 - ${roleCode.value}`)
const visible = ref(false)
const menuIds = ref<string[]>([])
const open = (data: { code: string, title: string }) => {
  menuIds.value = []
  roleCode.value = data.code
  visible.value = true
  getRoleMenuIds({ role: data.code }).then((res) => {
    menuIds.value = res.data
  })
}

const save = async () => {
  try {
    const res = await setRoleMenus({
      roleCode: roleCode.value,
      menuIds: menuIds.value
    })
    if (res) {
      Message.success('权限分配成功')
      visible.value = false
      return true
    }
    return false
  } catch (error) {
    Message.error('权限分配失败')
    return false
  }
}

const onCancel = () => {
  visible.value = false
}

defineExpose({ open })
</script>

<style lang="scss" scoped>
.perm-modal-content {
  .perm-header {
    margin-bottom: 16px;
    padding: 12px 0;
    border-bottom: 1px solid var(--color-border-2);
  }
  
  .perm-tree {
    max-height: 400px;
    overflow: auto;
    border: 1px solid var(--color-border-2);
    border-radius: 6px;
    padding: 12px;
    
    .tree-node-title {
      display: flex;
      align-items: center;
      gap: 6px;
      
      .menu-icon {
        font-size: 14px;
        
        &:deep(.arco-icon) {
          &[class*="icon-folder"] {
            color: #ff7d00;
          }
          &[class*="icon-menu"] {
            color: #165dff;
          }
          &[class*="icon-link"] {
            color: #00b42a;
          }
        }
      }
    }
  }
}

// 深色主题适配
body[arco-theme='dark'] {
  .perm-modal-content {
    .perm-header {
      border-bottom-color: var(--color-border-2);
    }
    
    .perm-tree {
      border-color: var(--color-border-2);
      background-color: var(--color-bg-1);
    }
  }
}
</style>
