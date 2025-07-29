<template>
  <a-modal
      :visible="props.visible"
      :title="isEdit ? '编辑客户' : '新增客户'"
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
import {customerAPI, type CustomerItem} from '@/apis/customer'
import type {ColumnItem} from '@/components/GiForm'
import {useDict} from '@/hooks/app'

interface Props {
  visible: boolean
  customerData?: CustomerItem
}

interface Emits {
  (e: 'update:visible', value: boolean): void

  (e: 'success'): void
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  customerData: undefined
})

const emit = defineEmits<Emits>()

const {data: statusOptions} = useDict({code: 'status'})
const {data: customerTypeOptions} = useDict({code: 'customer_type'})
const {data: customerLevelOptions} = useDict({code: 'customer_level'})

const formRef = ref()
const loading = ref(false)

const isEdit = computed(() => !!props.customerData?.id)

const formData = reactive({
  id: '',
  code: '',
  name: '',
  shortName: '',
  type: 1,
  level: 2,
  industry: '',
  contactPerson: '',
  contactPhone: '',
  contactEmail: '',
  address: '',
  creditCode: '',
  creditLimit: 0,
  creditDays: 30,
  taxRate: 0.13,
  paymentTerms: '',
  deliveryTerms: '',
  status: undefined,
  remark: ''
})

const formColumns = computed(() => [
  {
    type: 'input',
    label: '客户编码',
    field: 'code',
    props: {
      placeholder: '请输入客户编码',
      maxLength: 50
    },
    rules: [{required: true, message: '客户编码不能为空'}]
  },
  {
    type: 'input',
    label: '客户名称',
    field: 'name',
    props: {
      placeholder: '请输入客户名称',
      maxLength: 100
    },
    rules: [{required: true, message: '客户名称不能为空'}]
  },
  {
    type: 'input',
    label: '简称',
    field: 'shortName',
    props: {
      placeholder: '请输入简称',
      maxLength: 50
    }
  },
  {
    type: 'select',
    label: '客户类型',
    field: 'type',
    props: {
      placeholder: '请选择客户类型',
      options: customerTypeOptions.value || [
        { label: '企业客户', value: 1 },
        { label: '个人客户', value: 2 }
      ]
    },
    rules: [{required: true, message: '客户类型不能为空'}]
  },
  {
    type: 'select',
    label: '客户级别',
    field: 'level',
    props: {
      placeholder: '请选择客户级别',
      options: customerLevelOptions.value || [
        { label: 'VIP', value: 1 },
        { label: '普通', value: 2 },
        { label: '潜在', value: 3 }
      ]
    },
    rules: [{required: true, message: '客户级别不能为空'}]
  },
  {
    type: 'input',
    label: '所属行业',
    field: 'industry',
    props: {
      placeholder: '请输入所属行业',
      maxLength: 50
    }
  },
  {
    type: 'input',
    label: '联系人',
    field: 'contactPerson',
    props: {
      placeholder: '请输入联系人',
      maxLength: 50
    },
    rules: [{required: true, message: '联系人不能为空'}]
  },
  {
    type: 'input',
    label: '联系电话',
    field: 'contactPhone',
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
    type: 'input',
    label: '联系邮箱',
    field: 'contactEmail',
    props: {
      placeholder: '请输入联系邮箱',
      maxLength: 100
    },
    rules: [
      {type: 'email', message: '请输入有效的邮箱地址'}
    ]
  },
  {
    type: 'textarea',
    label: '地址',
    field: 'address',
    props: {
      placeholder: '请输入地址',
      maxLength: 200,
      rows: 2
    },
    span: 24
  },
  {
    type: 'input',
    label: '统一社会信用代码',
    field: 'creditCode',
    props: {
      placeholder: '请输入统一社会信用代码',
      maxLength: 18
    },
    rules: [
      {pattern: /^[0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}$/, message: '请输入有效的统一社会信用代码'}
    ]
  },
  {
    type: 'input-number',
    label: '信用额度',
    field: 'creditLimit',
    props: {
      placeholder: '请输入信用额度',
      min: 0,
      precision: 2,
      step: 1000
    }
  },
  {
    type: 'input-number',
    label: '信用期限(天)',
    field: 'creditDays',
    props: {
      placeholder: '请输入信用期限',
      min: 0,
      max: 365,
      step: 1
    }
  },
  {
    type: 'input-number',
    label: '税率',
    field: 'taxRate',
    props: {
      placeholder: '请输入税率',
      min: 0,
      max: 1,
      precision: 3,
      step: 0.01
    }
  },
  {
    type: 'input',
    label: '付款条件',
    field: 'paymentTerms',
    props: {
      placeholder: '请输入付款条件',
      maxLength: 100
    }
  },
  {
    type: 'input',
    label: '交货条件',
    field: 'deliveryTerms',
    props: {
      placeholder: '请输入交货条件',
      maxLength: 100
    }
  },
  {
    type: 'select',
    label: '状态',
    field: 'status',
    props: {
      placeholder: '请选择状态',
      options: statusOptions.value || [
        { label: '正常', value: 1 },
        { label: '禁用', value: 0 }
      ]
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
    if (props.customerData) {
      // 编辑模式，填充数据
      Object.assign(formData, props.customerData)
    }
  }
})

const resetForm = () => {
  Object.assign(formData, {
    id: '',
    code: '',
    name: '',
    shortName: '',
    type: 1,
    level: 2,
    industry: '',
    contactPerson: '',
    contactPhone: '',
    contactEmail: '',
    address: '',
    creditCode: '',
    creditLimit: 0,
    creditDays: 30,
    taxRate: 0.13,
    paymentTerms: '',
    deliveryTerms: '',
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
        await customerAPI.update(formData)
        Message.success('客户修改成功')
      } else {
        await customerAPI.add(formData)
        Message.success('客户新增成功')
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