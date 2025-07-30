<template>
  <GiPageLayout>
    <GiForm v-model="form" search :columns="searchColumns"
      :grid-item-props="{ span: { xs: 24, sm: 12, md: 8, lg: 8, xl: 6, xxl: 6 } }" 
      :default-collapsed="true"
      @search="search" @reset="search">
    </GiForm>

    <GiTable row-key="id" :loading="loading" :columns="columns" :data="tableData"
      :scroll="{ x: '100%', y: '100%', minWidth: 1400 }"
      :pagination="pagination" :disabled-column-keys="['序号', 'name']" @refresh="getTableData">
      <template #custom-title>
        <a-button type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <span>新增</span>
        </a-button>
      </template>
      
      <template #type="{ record }">
        <a-tag>
          {{ unitTypeOptions?.find(opt => opt.value == record.type)?.label }}
        </a-tag>
      </template>
      
      <template #status="{ record }">
        <GiCellStatus :status="record.status"></GiCellStatus>
      </template>
      
      <template #action="{ record }">
        <a-space>
          <a-button type="primary" size="mini" @click="onEdit(record)">修改</a-button>
          <a-button size="mini" @click="onDetail(record)">详情</a-button>
          <a-popconfirm type="warning" content="您确定要删除该计量单位吗?" @before-ok="() => onDelete(record)">
            <a-button type="primary" status="danger" size="mini">删除</a-button>
          </a-popconfirm>
        </a-space>
      </template>
    </GiTable>

    <GiFooter></GiFooter>
    
    <!-- 新增/编辑弹窗 -->
    <UnitModal 
      v-model:visible="modalVisible" 
      :unit-data="currentUnit"
      @success="handleModalSuccess" />
    
    <!-- 计量单位详情抽屉 -->
    <UnitDetail 
      v-model:visible="detailVisible"
      :unit-id="currentUnitId"
      @edit="handleDetailEdit" />
  </GiPageLayout>
</template>

<script setup lang="ts">
import { ref, reactive, computed, h } from 'vue'
import { Message, type PopconfirmInstance, type TableInstance } from '@arco-design/web-vue'
import { useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import type * as T from '@/apis/unit'
import { unitAPI } from '@/apis/unit'
import type { ColumnItem } from '@/components/GiForm'
import UnitModal from './UnitModal.vue'
import UnitDetail from './UnitDetail.vue'
import { IconPlus } from '@arco-design/web-vue/es/icon'

defineOptions({ name: 'UnitManagement' })

const { data: statusOptions } = useDict({ code: 'status' })
const { data: unitTypeOptions } = useDict({ code: 'unit' })

const form = reactive<any>({})

const searchColumns = computed(() => [
  {
    type: 'input',
    label: '单位编码',
    field: 'code'
  },
  {
    type: 'input',
    label: '单位名称',
    field: 'name'
  },
  {
    type: 'input',
    label: '单位符号',
    field: 'symbol',
    props: {
      maxLength: 10
    }
  },
  {
    type: 'select',
    label: '单位类型',
    field: 'type',
    props: {
      options: unitTypeOptions.value
    }
  },
  {
    type: 'select',
    label: '状态',
    field: 'status',
    props: {
      options: statusOptions.value
    }
  }
] as ColumnItem[])

const columns: TableInstance['columns'] = [
  { title: '序号', width: 66, align: 'center', render: ({ rowIndex }) => h('span', {}, rowIndex + 1) },
  { title: '单位编码', dataIndex: 'code', width: 120 },
  { title: '单位名称', dataIndex: 'name', width: 150 },
  { title: '单位符号', dataIndex: 'symbol', width: 100, align: 'center' },
  { title: '单位类型', width: 120, align: 'center', slotName: 'type' },
  { title: '状态', width: 80, slotName: 'status', align: 'center' },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    width: 150,
    ellipsis: true,
    tooltip: true,
    sortable: {
      sortDirections: ['ascend', 'descend']
    }
  },
  { title: '操作', width: 200, slotName: 'action', align: 'center', fixed: 'right' }
]

const { tableData, getTableData, pagination, search, loading } = useTable((p) => unitAPI.getList({ ...p, ...form }))

// 弹窗相关状态
const modalVisible = ref(false)
const currentUnit = ref<T.UnitItem | undefined>()

// 详情抽屉相关状态
const detailVisible = ref(false)
const currentUnitId = ref<string | undefined>()

const onAdd = () => {
  currentUnit.value = undefined
  modalVisible.value = true
}

const onEdit = (record: T.UnitItem) => {
  currentUnit.value = record
  modalVisible.value = true
}

const onDetail = (record: T.UnitItem) => {
  currentUnitId.value = record.id
  detailVisible.value = true
}

const handleModalSuccess = () => {
  getTableData()
}

// 从详情页面跳转到编辑
const handleDetailEdit = (unit: T.UnitItem) => {
  currentUnit.value = unit
  modalVisible.value = true
}

const onDelete = async (record: T.UnitItem): Promise<boolean> => {
  try {
    await unitAPI.delete({ ids: [record.id] })
    Message.success(`计量单位 ${record.name} 删除成功`)
    getTableData()
    return true
  } catch (error) {
    console.error('删除失败:', error)
    Message.error('删除失败，请稍后重试')
    return false
  }
}
</script>

<style lang="scss" scoped></style>