<template>
  <a-modal
      :visible="props.visible"
      :title="isEdit ? '编辑仓库' : '新增仓库'"
      width="800px"
      @ok="handleOk"
      @cancel="handleCancel"
      :confirm-loading="loading">

    <GiForm
        ref="formRef"
        v-model="formData"
        :columns="formColumns"
        :grid-item-props="{ span: { xs: 24, sm: 12, md: 12, lg: 12, xl: 12 } }"
        label-align="right"
        :label-col-props="{ span: 8 }"
        :wrapper-col-props="{ span: 16 }">
    </GiForm>
  </a-modal>
</template>

<script setup lang="ts">
import {ref, reactive, computed, watch} from 'vue'
import {Message} from '@arco-design/web-vue'
import {warehouseAPI, type WarehouseItem} from '@/apis/warehouse'
import type {ColumnItem} from '@/components/GiForm'
import {useDict} from '@/hooks/app'

interface Props {
  visible: boolean
  warehouseData?: WarehouseItem
}

interface Emits {
  (e: 'update:visible', value: boolean): void

  (e: 'success'): void
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  warehouseData: undefined
})

const emit = defineEmits<Emits>()

const {data: statusOptions} = useDict({code: 'status'})
const {data: warehouseTypeOptions} = useDict({code: 'warehouse'})

const formRef = ref()
const loading = ref(false)

const isEdit = computed(() => !!props.warehouseData?.id)

const formData = reactive({
  id: '',
  code: '',
  name: '',
  type: undefined,
  address: '',
  manager: '',
  phone: '',
  area: 0,
  capacity: 0,
  status: undefined,
  remark: ''
})

const formColumns = computed(() => [
  {
    type: 'input',
    label: '仓库编码',
    field: 'code',
    props: {
      placeholder: '请输入仓库编码',
      maxLength: 50
    },
    rules: [{required: true, message: '仓库编码不能为空'}]
  },
  {
    type: 'input',
    label: '仓库名称',
    field: 'name',
    props: {
      placeholder: '请输入仓库名称',
      maxLength: 100
    },
    rules: [{required: true, message: '仓库名称不能为空'}]
  },
  {
    type: 'select',
    label: '仓库类型',
    field: 'type',
    props: {
      placeholder: '请选择仓库类型',
      options: warehouseTypeOptions.value
    },
    rules: [{required: true, message: '仓库类型不能为空'}]
  },
  {
    type: 'textarea',
    label: '仓库地址',
    field: 'address',
    props: {
      placeholder: '请输入仓库地址',
      maxLength: 200,
      rows: 2
    },
    span: 24
  },
  {
    type: 'input',
    label: '仓库管理员',
    field: 'manager',
    props: {
      placeholder: '请输入仓库管理员',
      maxLength: 50
    },
    rules: [{required: true, message: '仓库管理员不能为空'}]
  },
  {
    type: 'input',
    label: '联系电话',
    field: 'phone',
    props: {
      placeholder: '请输入联系电话',
      maxLength: 20
    },
    rules: [
      {required: true, message: '联系电话不能为空'},
      {pattern: /^1[3-9]\d{9}$|^0\d{2,3}-?\d{7,8}$/, message: '请输入有效的电话号码'}
    ]
  },
  {
    type: 'input-number',
    label: '仓库面积(㎡)',
    field: 'area',
    props: {
      placeholder: '请输入仓库面积',
      min: 0,
      precision: 2,
      step: 10
    }
  },
  {
    type: 'input-number',
    label: '存储容量',
    field: 'capacity',
    props: {
      placeholder: '请输入存储容量',
      min: 0,
      precision: 2,
      step: 100
    }
  },
  {
    type: 'select',
    label: '状态',
    field: 'status',
    props: {
      placeholder: '请选择状态',
      options: statusOptions.value
    },
    rules: [{required: true, message: '状态不能为空'}]
  },
  {
    type: 'textarea',
    label: '备注',
    field: 'remark',
    props: {
      placeholder: '请输入备注',
      maxLength: 500,
      rows: 3
    },
    span: 24
  }
] as ColumnItem[])

// 监听弹窗显示状态
watch(() => props.visible, (newVal) => {
  if (newVal) {
    resetForm()
    if (props.warehouseData) {
      // 编辑模式，填充数据，确保字典字段为字符串类型
      Object.assign(formData, {
        ...props.warehouseData,
        type: String(props.warehouseData.type),
        status: String(props.warehouseData.status)
      })
    }
  }
})

const resetForm = () => {
  Object.assign(formData, {
    id: '',
    code: '',
    name: '',
    type: 1,
    address: '',
    manager: '',
    phone: '',
    area: 0,
    capacity: 0,
    status: undefined,
    remark: ''
  })
}

const handleOk = async () => {
  try {
    const errors = await formRef.value?.formRef?.validate()
    if (!errors) {
      loading.value = true

      if (isEdit.value) {
        await warehouseAPI.update(formData)
        Message.success('仓库修改成功')
      } else {
        await warehouseAPI.add(formData)
        Message.success('仓库新增成功')
      }

      emit('success')
      handleCancel()
    }
  } catch (error) {
    console.error('操作失败:', error)
    Message.error('操作失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

const handleCancel = () => {
  emit('update:visible', false)
  resetForm()
}
</script>

<style lang="scss" scoped>
:deep(.arco-form-item-label) {
  font-weight: 500;
}
</style> 