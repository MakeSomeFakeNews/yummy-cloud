<template>
  <GiPageLayout>
    <GiForm v-model="form" search :columns="searchColumns"
      :grid-item-props="{ span: { xs: 24, sm: 12, md: 8, lg: 8, xl: 6, xxl: 6 } }" 
      :default-collapsed="true"
      @search="search" @reset="search">
    </GiForm>

    <GiTable row-key="id" :loading="loading" :columns="columns" :data="tableData"
      :scroll="{ x: '100%', y: '100%', minWidth: 1600 }"
      :pagination="pagination" :disabled-column-keys="['序号', 'name']" @refresh="getTableData">
      <template #custom-title>
        <a-button type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <span>新增</span>
        </a-button>
      </template>
      
      <template #type="{ record }">
        <a-tag>
          {{ warehouseTypeOptions?.find(opt => opt.value == record.type)?.label }}
        </a-tag>
      </template>
      
      <template #status="{ record }">
        <GiCellStatus :status="record.status"></GiCellStatus>
      </template>
      
      <template #action="{ record }">
        <a-space>
          <a-button type="primary" size="mini" @click="onEdit(record)">修改</a-button>
          <a-button size="mini" @click="onDetail(record)">详情</a-button>
          <a-popconfirm type="warning" content="您确定要删除该仓库吗?" @before-ok="() => onDelete(record)">
            <a-button type="primary" status="danger" size="mini">删除</a-button>
          </a-popconfirm>
        </a-space>
      </template>
    </GiTable>

    <GiFooter></GiFooter>
    
    <!-- 新增/编辑弹窗 -->
    <WarehouseModal 
      v-model:visible="modalVisible" 
      :warehouse-data="currentWarehouse"
      @success="handleModalSuccess" />
    
    <!-- 仓库详情抽屉 -->
    <WarehouseDetail 
      v-model:visible="detailVisible"
      :warehouse-id="currentWarehouseId"
      @edit="handleDetailEdit" />
  </GiPageLayout>
</template>

<script setup lang="ts">
import { ref, reactive, computed, h } from 'vue'
import { Message, type PopconfirmInstance, type TableInstance } from '@arco-design/web-vue'
import { useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import type * as T from '@/apis/warehouse'
import { warehouseAPI } from '@/apis/warehouse'
import type { ColumnItem } from '@/components/GiForm'
import WarehouseModal from './WarehouseModal.vue'
import WarehouseDetail from './WarehouseDetail.vue'
import { IconPlus } from '@arco-design/web-vue/es/icon'

defineOptions({ name: 'WarehouseManagement' })

const { data: statusOptions } = useDict({ code: 'status' })
const { data: warehouseTypeOptions } = useDict({ code: 'warehouse' })

const form = reactive({})

const searchColumns = computed(() => [
  {
    type: 'input',
    label: '仓库编码',
    field: 'code'
  },
  {
    type: 'input',
    label: '仓库名称',
    field: 'name'
  },
  {
    type: 'select',
    label: '仓库类型',
    field: 'type',
    props: {
      options: warehouseTypeOptions.value
    }
  },
  {
    type: 'input',
    label: '仓库管理员',
    field: 'manager'
  },
  {
    type: 'input',
    label: '联系电话',
    field: 'phone',
    props: {
      maxLength: 20
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
  { title: '仓库编码', dataIndex: 'code', width: 120 },
  { title: '仓库名称', dataIndex: 'name', width: 150 },
  { title: '仓库类型', width: 100, align: 'center', slotName: 'type' },
  { title: '仓库地址', dataIndex: 'address', width: 200, ellipsis: true, tooltip: true },
  { title: '仓库管理员', dataIndex: 'manager', width: 120 },
  { title: '联系电话', dataIndex: 'phone', width: 130 },
  { title: '仓库面积(㎡)', dataIndex: 'area', width: 120, align: 'right' },
  { title: '存储容量', dataIndex: 'capacity', width: 120, align: 'right' },
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

const { tableData, getTableData, pagination, search, loading } = useTable((p) => warehouseAPI.getList(p))

// 弹窗相关状态
const modalVisible = ref(false)
const currentWarehouse = ref<T.WarehouseItem | undefined>()

// 详情抽屉相关状态
const detailVisible = ref(false)
const currentWarehouseId = ref<string | undefined>()

const onAdd = () => {
  currentWarehouse.value = undefined
  modalVisible.value = true
}

const onEdit = (record: T.WarehouseItem) => {
  currentWarehouse.value = record
  modalVisible.value = true
}

const onDetail = (record: T.WarehouseItem) => {
  currentWarehouseId.value = record.id
  detailVisible.value = true
}

const handleModalSuccess = () => {
  getTableData()
}

// 从详情页面跳转到编辑
const handleDetailEdit = (warehouse: T.WarehouseItem) => {
  currentWarehouse.value = warehouse
  modalVisible.value = true
}

const onDelete = async (record: T.WarehouseItem): Promise<boolean> => {
  try {
    await warehouseAPI.delete({ ids: [record.id] })
    Message.success(`仓库 ${record.name} 删除成功`)
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