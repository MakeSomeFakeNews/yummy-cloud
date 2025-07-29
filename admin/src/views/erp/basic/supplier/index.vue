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
          {{ supplierTypeOptions?.find(opt => opt.value == record.type)?.label }}
        </a-tag>
      </template>
      
      <template #level="{ record }">
        <a-tag>
          {{ supplierLevelOptions?.find(opt => opt.value == record.level)?.label }}
        </a-tag>
      </template>
      
      <template #status="{ record }">
        <GiCellStatus :status="record.status"></GiCellStatus>
      </template>
      
      <template #action="{ record }">
        <a-space>
          <a-button type="primary" size="mini" @click="onEdit(record)">修改</a-button>
          <a-button size="mini" @click="onDetail(record)">详情</a-button>
          <a-popconfirm type="warning" content="您确定要删除该供应商吗?" @before-ok="() => onDelete(record)">
            <a-button type="primary" status="danger" size="mini">删除</a-button>
          </a-popconfirm>
        </a-space>
      </template>
    </GiTable>

    <GiFooter></GiFooter>
    
    <!-- 新增/编辑弹窗 -->
    <SupplierModal 
      v-model:visible="modalVisible" 
      :supplier-data="currentSupplier"
      @success="handleModalSuccess" />
    
    <!-- 供应商详情抽屉 -->
    <SupplierDetail 
      v-model:visible="detailVisible"
      :supplier-id="currentSupplierId"
      @edit="handleDetailEdit" />
  </GiPageLayout>
</template>

<script setup lang="ts">
import { ref, reactive, computed, h } from 'vue'
import { Message, type PopconfirmInstance, type TableInstance } from '@arco-design/web-vue'
import { useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import type * as T from '@/apis/supplier'
import { supplierAPI } from '@/apis/supplier'
import type { ColumnItem } from '@/components/GiForm'
import SupplierModal from './SupplierModal.vue'
import SupplierDetail from './SupplierDetail.vue'
import { IconPlus } from '@arco-design/web-vue/es/icon'

defineOptions({ name: 'SupplierManagement' })

const { data: statusOptions } = useDict({ code: 'status' })
const { data: supplierTypeOptions } = useDict({ code: 'supType' })
const { data: supplierLevelOptions } = useDict({ code: 'supLevel' })

const form = reactive({})

const searchColumns = computed(() => [
  {
    type: 'input',
    label: '供应商编码',
    field: 'code'
  },
  {
    type: 'input',
    label: '供应商名称',
    field: 'name'
  },
  {
    type: 'select',
    label: '供应商类型',
    field: 'type',
    props: {
      options: supplierTypeOptions.value
    }
  },
  {
    type: 'select',
    label: '供应商级别',
    field: 'level',
    props: {
      options: supplierLevelOptions.value
    }
  },
  {
    type: 'input',
    label: '联系人',
    field: 'contactPerson'
  },
  {
    type: 'input',
    label: '联系电话',
    field: 'contactPhone',
    props: {
      maxLength: 11
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
  { title: '供应商编码', dataIndex: 'code', width: 120 },
  { title: '供应商名称', dataIndex: 'name', width: 150 },
  { title: '简称', dataIndex: 'shortName', width: 120 },
  { title: '供应商类型', width: 100, align: 'center', slotName: 'type' },
  { title: '供应商级别', width: 100, align: 'center', slotName: 'level' },
  { title: '联系人', dataIndex: 'contactPerson', width: 120 },
  { title: '联系电话', dataIndex: 'contactPhone', width: 130 },
  { title: '联系邮箱', dataIndex: 'contactEmail', width: 150, ellipsis: true, tooltip: true },
  { title: '地址', dataIndex: 'address', width: 200, ellipsis: true, tooltip: true },
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

const { tableData, getTableData, pagination, search, loading } = useTable((p) => supplierAPI.getList(p))

// 弹窗相关状态
const modalVisible = ref(false)
const currentSupplier = ref<T.SupplierItem | undefined>()

// 详情抽屉相关状态
const detailVisible = ref(false)
const currentSupplierId = ref<string | undefined>()

const onAdd = () => {
  currentSupplier.value = undefined
  modalVisible.value = true
}

const onEdit = (record: T.SupplierItem) => {
  currentSupplier.value = record
  modalVisible.value = true
}

const onDetail = (record: T.SupplierItem) => {
  currentSupplierId.value = record.id
  detailVisible.value = true
}

const handleModalSuccess = () => {
  getTableData()
}

// 从详情页面跳转到编辑
const handleDetailEdit = (supplier: T.SupplierItem) => {
  currentSupplier.value = supplier
  modalVisible.value = true
}

const onDelete = async (record: T.SupplierItem): Promise<boolean> => {
  try {
    await supplierAPI.delete({ ids: [record.id] })
    Message.success(`供应商 ${record.name} 删除成功`)
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