<template>
  <GiPageLayout>
    <GiForm v-model="form" search :columns="searchColumns"
      :grid-item-props="{ span: { xs: 24, sm: 12, md: 8, lg: 8, xl: 6, xxl: 6 } }" 
      :collapsed="true"
      @search="search" @reset="search">
    </GiForm>

    <GiTable row-key="id" :loading="loading" :columns="columns" :data="tableData"
      :scroll="{ x: '100%', y: '100%', minWidth: 1600 }" :row-selection="{ type: 'checkbox', showCheckedAll: true }"
      :pagination="pagination" :disabled-column-keys="['序号', 'name']" @refresh="getTableData">
      <template #custom-title>
        <a-button type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <span>新增</span>
        </a-button>
        <a-button type="primary" status="danger" @click="onMulDelete">
          <template #icon><icon-delete /></template>
          <span>删除</span>
        </a-button>
      </template>
      
      <template #type="{ record }">
        <a-tag :color="record.type === 1 ? 'blue' : 'green'">
          {{ record.type === 1 ? '企业客户' : '个人客户' }}
        </a-tag>
      </template>
      
      <template #level="{ record }">
        <a-tag 
          :color="record.level === 1 ? 'red' : record.level === 2 ? 'orange' : 'gray'">
          {{ record.level === 1 ? 'VIP' : record.level === 2 ? '普通' : '潜在' }}
        </a-tag>
      </template>
      
      <template #status="{ record }">
        <GiCellStatus :status="record.status"></GiCellStatus>
      </template>
      
      <template #action="{ record }">
        <a-space>
          <a-button type="primary" size="mini" @click="onEdit(record)">修改</a-button>
          <a-button size="mini" @click="onDetail(record)">详情</a-button>
          <a-popconfirm type="warning" content="您确定要删除该客户吗?" @before-ok="() => onDelete(record)">
            <a-button type="primary" status="danger" size="mini">删除</a-button>
          </a-popconfirm>
        </a-space>
      </template>
    </GiTable>

    <GiFooter></GiFooter>
    
    <!-- 新增/编辑弹窗 -->
    <CustomerModal 
      v-model:visible="modalVisible" 
      :customer-data="currentCustomer"
      @success="handleModalSuccess" />
    
    <!-- 客户详情抽屉 -->
    <CustomerDetail 
      v-model:visible="detailVisible"
      :customer-id="currentCustomerId"
      @edit="handleDetailEdit" />
  </GiPageLayout>
</template>

<script setup lang="ts">
import { ref, reactive, computed, h } from 'vue'
import { Message, type PopconfirmInstance, type TableInstance } from '@arco-design/web-vue'
import { useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import type * as T from '@/apis/customer'
import { customerAPI } from '@/apis/customer'
import type { ColumnItem } from '@/components/GiForm'
import CustomerModal from './CustomerModal.vue'
import CustomerDetail from './CustomerDetail.vue'

defineOptions({ name: 'CustomerManagement' })

const { data: statusOptions } = useDict({ code: 'status' })
const { data: customerTypeOptions } = useDict({ code: 'customer_type' })
const { data: customerLevelOptions } = useDict({ code: 'customer_level' })

const form = reactive({})

const searchColumns = computed(() => [
  {
    type: 'input',
    label: '客户编码',
    field: 'code'
  },
  {
    type: 'input',
    label: '客户名称',
    field: 'name'
  },
  {
    type: 'select',
    label: '客户类型',
    field: 'type',
    props: {
      options: customerTypeOptions.value || [
        { label: '企业客户', value: 1 },
        { label: '个人客户', value: 2 }
      ]
    }
  },
  {
    type: 'select',
    label: '客户级别',
    field: 'level',
    props: {
      options: customerLevelOptions.value || [
        { label: 'VIP', value: 1 },
        { label: '普通', value: 2 },
        { label: '潜在', value: 3 }
      ]
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
      options: statusOptions.value || [
        { label: '正常', value: 1 },
        { label: '禁用', value: 0 }
      ]
    }
  },
  {
    type: 'input',
    label: '所属行业',
    field: 'industry'
  }
] as ColumnItem[])

const columns: TableInstance['columns'] = [
  { title: '序号', width: 66, align: 'center', render: ({ rowIndex }) => h('span', {}, rowIndex + 1) },
  { title: '客户编码', dataIndex: 'code', width: 120 },
  { title: '客户名称', dataIndex: 'name', width: 150 },
  { title: '简称', dataIndex: 'shortName', width: 120 },
  { title: '客户类型', width: 100, align: 'center', slotName: 'type' },
  { title: '客户级别', width: 100, align: 'center', slotName: 'level' },
  { title: '联系人', dataIndex: 'contactPerson', width: 120 },
  { title: '联系电话', dataIndex: 'contactPhone', width: 130 },
  { title: '联系邮箱', dataIndex: 'contactEmail', width: 150, ellipsis: true, tooltip: true },
  { title: '所属行业', dataIndex: 'industry', width: 120 },
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

const { tableData, getTableData, pagination, search, loading } = useTable((p) => customerAPI.getList(p))

// 弹窗相关状态
const modalVisible = ref(false)
const currentCustomer = ref<T.CustomerItem | undefined>()

// 详情抽屉相关状态
const detailVisible = ref(false)
const currentCustomerId = ref<string | undefined>()

const onAdd = () => {
  currentCustomer.value = undefined
  modalVisible.value = true
}

const onEdit = (record: T.CustomerItem) => {
  currentCustomer.value = record
  modalVisible.value = true
}

const onDetail = (record: T.CustomerItem) => {
  currentCustomerId.value = record.id
  detailVisible.value = true
}

const onMulDelete = () => {
  Message.error('批量删除功能待开发')
}

const handleModalSuccess = () => {
  getTableData()
}

// 从详情页面跳转到编辑
const handleDetailEdit = (customer: T.CustomerItem) => {
  currentCustomer.value = customer
  modalVisible.value = true
}

const onDelete = async (record: T.CustomerItem): Promise<boolean> => {
  try {
    await customerAPI.delete({ ids: [record.id] })
    Message.success(`客户 ${record.name} 删除成功`)
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