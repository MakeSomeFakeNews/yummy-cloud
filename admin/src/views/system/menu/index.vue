<template>
  <a-card title="菜单管理" class="gi_page_card">
    <a-row justify="space-between">
      <a-space wrap>
        <a-button type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <span>新增</span>
        </a-button>
        <a-tooltip content="展开/折叠">
          <a-button type="primary" status="success" @click="onExpanded">
            <template #icon>
              <icon-list v-if="!isExpanded" />
              <icon-mind-mapping v-else />
            </template>
          </a-button>
        </a-tooltip>
      </a-space>

      <a-space wrap>
        <a-input v-model="queryParams.name" placeholder="输入菜单名称搜索" allow-clear style="width: 250px">
          <template #prefix><icon-search /></template>
        </a-input>
        <a-button type="primary" @click="search">
          <template #icon><icon-search /></template>
          <span>搜索</span>
        </a-button>
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <span>重置</span>
        </a-button>
      </a-space>
    </a-row>

    <a-table ref="tableRef" class="gi_table" row-key="id" :data="menuList" :loading="loading" :bordered="{ cell: true }"
      :scroll="{ x: '100%', y: '100%', minWidth: 1700 }" :pagination="false" size="medium"
      :default-expand-all-rows="true">
      <template #expand-icon="{ expanded, record }">
        <template v-if="record.children && record.children.length > 0">
          <IconDown v-if="expanded" />
          <IconRight v-else />
        </template>
        <span v-else class="no-children-marker" style="display: none;"></span>
      </template>
      <template #columns>
        <a-table-column title="菜单名称">
          <template #cell="{ record }">{{ record.title || '' }}</template>
        </a-table-column>
        <a-table-column title="类型" :width="80" align="center">
          <template #cell="{ record }">
            <a-tag v-if="record.type === 1" size="small" color="orangered">目录</a-tag>
            <a-tag v-if="record.type === 2" size="small" color="green">菜单</a-tag>
            <a-tag v-if="record.type === 3" size="small">按钮</a-tag>
          </template>
        </a-table-column>
        <a-table-column title="排序" :width="80" align="center">
          <template #cell="{ record }">{{ record.sort || 0 }}</template>
        </a-table-column>
        <a-table-column title="路由路径" data-index="path"> </a-table-column>
        <a-table-column title="路由名称">
          <template #cell="{ record }">{{ transformPathToName(record.path) }}</template>
        </a-table-column>
        <a-table-column title="组件路径" data-index="component"> </a-table-column>
        <a-table-column title="权限标识" data-index="permission"> </a-table-column>
        <a-table-column title="菜单图标" data-index="icon" :width="100" align="center">
          <template #cell="{ record }">
            <GiSvgIcon v-if="record.svgIcon" :size="24" :name="record.svgIcon"></GiSvgIcon>
            <template v-else>
              <component :is="record.icon" v-if="record.icon" :size="24"></component>
            </template>
          </template>
        </a-table-column>
        <a-table-column title="状态" :width="80" align="center">
          <template #cell="{ record }">
            <a-switch 
              type="round" 
              size="small" 
              :model-value="record.status === 1" 
              @change="(checked) => onStatusChange(record, checked)" />
          </template>
        </a-table-column>
        <a-table-column title="是否缓存" :width="100" align="center">
          <template #cell="{ record }">
            <a-tag v-if="record.keepAlive" size="small" color="green">是</a-tag>
            <a-tag v-else size="small" color="red">否</a-tag>
          </template>
        </a-table-column>
        <a-table-column title="是否隐藏" :width="100" align="center">
          <template #cell="{ record }">
            <a-tag v-if="record.hidden" size="small" color="green">是</a-tag>
            <a-tag v-else size="small" color="red">否</a-tag>
          </template>
        </a-table-column>
        <a-table-column title="是否外链" :width="100" align="center">
          <template #cell="{ record }">
            <a-tag v-if="isExternal(record.path)" size="small" color="green">是</a-tag>
            <a-tag v-else size="small" color="red">否</a-tag>
          </template>
        </a-table-column>
        <a-table-column title="操作" :width="200" align="left" :fixed="fixed">
          <template #cell="{ record }">
            <a-space>
              <a-button type="primary" size="mini" @click="onEdit(record)">
                <template #icon><icon-edit /></template>
                <span>编辑</span>
              </a-button>
              <a-popconfirm type="warning" content="您确定要删除该项吗?" @before-ok="onDelete(record)">
                <a-button type="primary" status="danger" size="mini">
                  <template #icon><icon-delete /></template>
                  <span>删除</span>
                </a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </a-table-column>
      </template>
    </a-table>

    <AddMenuModal ref="AddMenuModalRef" :menus="menuList" @save-success="search"></AddMenuModal>
  </a-card>
</template>

<script setup lang="ts">
import AddMenuModal from './AddMenuModal.vue'
import type * as T from '@/apis/system/menu'
import {baseAPI} from '@/apis/system/menu'
import {isExternal} from '@/utils/validate'
import {transformPathToName} from '@/utils'
import {useDict} from '@/hooks/app'
import {useTable} from '@/hooks'

defineOptions({ name: 'SystemMenu' })

const { data: options } = useDict({ code: 'status' })
const AddMenuModalRef = useTemplateRef('AddMenuModalRef')

const tableRef = useTemplateRef('tableRef')
const isExpanded = ref(false)
const onExpanded = () => {
  isExpanded.value = !isExpanded.value
  tableRef.value?.expandAll(isExpanded.value)
}

const queryParams = reactive({ name: '', status: '' })

const {
  loading,
  tableData: menuList,
  search,
  fixed
} = useTable(() => baseAPI.getList({ ...queryParams }), { immediate: true })

const reset = () => {
  queryParams.name = ''
  queryParams.status = ''
  search()
}

const onAdd = () => {
  AddMenuModalRef.value?.add()
}

const onEdit = (item: T.ListItem) => {
  AddMenuModalRef.value?.edit(item.id)
}

const onDelete = async (item: T.ListItem) => {
  try {
    await baseAPI.delete({ ids: [item.id] })
    search() // 删除成功后刷新表格
    return true
  } catch (error) {
    return false
  }
}

const onStatusChange = async (record: T.ListItem, checked: boolean) => {
  try {
    // 更新菜单状态
    await baseAPI.update({ 
      ...record, 
      status: checked ? 1 : 0 
    })
    // 更新本地数据
    record.status = checked ? 1 : 0
  } catch (error) {
    console.error('更新菜单状态失败:', error)
    // 如果更新失败，可以显示错误提示
  }
}

</script>

<style lang="scss" scoped>
:deep(.arco-table-expand-btn:has(.no-children-marker)) {
  display: none !important;
}
</style>
