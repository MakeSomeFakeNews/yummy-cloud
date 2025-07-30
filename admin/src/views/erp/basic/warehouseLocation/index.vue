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
      
      <template #warehouseName="{ record }">
        <span>{{ record.warehouseName || '-' }}</span>
      </template>
      
      <template #type="{ record }">
        <a-tag>
          {{ warehouseLocationTypeOptions?.find(opt => opt.value == record.type)?.label }}
        </a-tag>
      </template>
      
      <template #status="{ record }">
        <GiCellStatus :status="record.status"></GiCellStatus>
      </template>
      
      <template #action="{ record }">
        <a-space>
          <a-button type="primary" size="mini" @click="onEdit(record)">修改</a-button>
          <a-button size="mini" @click="onDetail(record)">详情</a-button>
          <a-popconfirm type="warning" content="您确定要删除该库位吗?" @before-ok="() => onDelete(record)">
            <a-button type="primary" status="danger" size="mini">删除</a-button>
          </a-popconfirm>
        </a-space>
      </template>
    </GiTable>

    <GiFooter></GiFooter>
    
    <!-- 新增/编辑弹窗 -->
    <WarehouseLocationModal 
      v-model:visible="modalVisible" 
      :warehouse-location-data="currentWarehouseLocation"
      @success="handleModalSuccess" />
    
    <!-- 库位详情抽屉 -->
    <WarehouseLocationDetail 
      v-model:visible="detailVisible"
      :warehouse-location-id="currentWarehouseLocationId"
      @edit="handleDetailEdit" />
  </GiPageLayout>
</template>

<script setup lang="ts">
import { ref, reactive, computed, h } from 'vue'
import { Message, type PopconfirmInstance, type TableInstance } from '@arco-design/web-vue'
import { useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import type * as T from '@/apis/warehouseLocation'
import { warehouseLocationAPI } from '@/apis/warehouseLocation'
import { warehouseAPI } from '@/apis/warehouse'
import type { ColumnItem } from '@/components/GiForm'
import WarehouseLocationModal from './WarehouseLocationModal.vue'
import WarehouseLocationDetail from './WarehouseLocationDetail.vue'
import { IconPlus } from '@arco-design/web-vue/es/icon'

defineOptions({ name: 'WarehouseLocationManagement' })

const { data: statusOptions } = useDict({ code: 'status' })
const { data: warehouseLocationTypeOptions } = useDict({ code: 'warehouse' })

// 仓库选项
const warehouseOptions = ref<Array<{ label: string; value: string | number }>>([])

// 搜索仓库选项
const searchWarehouseOptions = async (keyword: string) => {
  try {
    const res = await warehouseAPI.searchOptions(keyword)
    warehouseOptions.value = res.data || []
  } catch (error) {
    console.error('搜索仓库失败:', error)
  }
}

// 初始加载仓库选项
const loadInitialWarehouseOptions = async () => {
  try {
    const res = await warehouseAPI.searchOptions()
    warehouseOptions.value = res.data || []
  } catch (error) {
    console.error('加载仓库选项失败:', error)
  }
}

// 页面加载时获取选项
loadInitialWarehouseOptions()


const form = reactive<any>({})

const searchColumns = computed(() => [
  {
    type: 'select',
    label: '所属仓库',
    field: 'warehouseId',
    props: {
      placeholder: '请选择仓库',
      allowSearch: true,
      filterOption: false,
      options: warehouseOptions.value,
      onSearch: searchWarehouseOptions
    }
  },
  {
    type: 'input',
    label: '库位编码',
    field: 'code'
  },
  {
    type: 'input',
    label: '库位名称',
    field: 'name'
  },
  {
    type: 'select',
    label: '库位类型',
    field: 'type',
    props: {
      options: warehouseLocationTypeOptions.value
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
  { title: '所属仓库', width: 150, slotName: 'warehouseName' },
  { title: '库位编码', dataIndex: 'code', width: 120 },
  { title: '库位名称', dataIndex: 'name', width: 150 },
  { title: '库位类型', width: 120, align: 'center', slotName: 'type' },
  { title: '存储容量', dataIndex: 'capacity', width: 120 },
  { title: '状态', width: 80, slotName: 'status', align: 'center' },
  { title: '备注', dataIndex: 'remark', width: 200, ellipsis: true, tooltip: true },
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

const { tableData, getTableData, pagination, search, loading } = useTable((p) => warehouseLocationAPI.getList({ ...p, ...form }))

// 弹窗相关状态
const modalVisible = ref(false)
const currentWarehouseLocation = ref<T.WarehouseLocationItem | undefined>()

// 详情抽屉相关状态
const detailVisible = ref(false)
const currentWarehouseLocationId = ref<string | undefined>()

const onAdd = () => {
  currentWarehouseLocation.value = undefined
  modalVisible.value = true
}

const onEdit = (record: T.WarehouseLocationItem) => {
  currentWarehouseLocation.value = record
  modalVisible.value = true
}

const onDetail = (record: T.WarehouseLocationItem) => {
  currentWarehouseLocationId.value = record.id as string
  detailVisible.value = true
}

const handleModalSuccess = () => {
  getTableData()
}

// 从详情页面跳转到编辑
const handleDetailEdit = (warehouseLocation: T.WarehouseLocationItem) => {
  currentWarehouseLocation.value = warehouseLocation
  modalVisible.value = true
}

const onDelete = async (record: T.WarehouseLocationItem): Promise<boolean> => {
  try {
    await warehouseLocationAPI.delete({ ids: [record.id] })
    Message.success(`库位 ${record.name} 删除成功`)
    await getTableData()
    return true
  } catch (error) {
    console.error('删除失败:', error)
    Message.error('删除失败，请稍后重试')
    return false
  }
}
</script>

<style lang="scss" scoped></style>