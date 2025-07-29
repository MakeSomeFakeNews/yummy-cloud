<template>
  <a-modal
      :visible="props.visible"
      :title="isEdit ? '编辑供应商' : '新增供应商'"
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
import {supplierAPI, type SupplierItem} from '@/apis/supplier'
import type {ColumnItem} from '@/components/GiForm'
import {useDict} from '@/hooks/app'

interface Props {
  visible: boolean
  supplierData?: SupplierItem
}

interface Emits {
  (e: 'update:visible', value: boolean): void

  (e: 'success'): void
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  supplierData: undefined
})

const emit = defineEmits<Emits>()
const { data: statusOptions } = useDict({ code: 'status' })
const { data: supplierTypeOptions } = useDict({ code: 'supType' })
const { data: supplierLevelOptions } = useDict({ code: 'supLevel' })


const formRef = ref()
const loading = ref(false)

const isEdit = computed(() => !!props.supplierData?.id)

const formData = reactive({
  id: '',
  code: '',
  name: '',
  shortName: '',
  type: undefined,
  level: undefined,
  contactPerson: '',
  contactPhone: '',
  contactEmail: '',
  address: '',
  creditCode: '',
  bankName: '',
  bankAccount: '',
  taxRate: 0.13,
  paymentTerms: '',
  deliveryTerms: '',
  status: undefined,
  remark: ''
})

const formColumns = computed(() => [
  {
    type: 'input',
    label: '供应商编码',
    field: 'code',
    props: {
      placeholder: '请输入供应商编码',
      maxLength: 50
    },
    rules: [{required: true, message: '供应商编码不能为空'}]
  },
  {
    type: 'input',
    label: '供应商名称',
    field: 'name',
    props: {
      placeholder: '请输入供应商名称',
      maxLength: 100
    },
    rules: [{required: true, message: '供应商名称不能为空'}]
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
    label: '供应商类型',
    field: 'type',
    props: {
      placeholder: '请选择供应商类型',
      options: supplierTypeOptions.value
    },
    rules: [{required: true, message: '供应商类型不能为空'}]
  },
  {
    type: 'select',
    label: '供应商级别',
    field: 'level',
    props: {
      placeholder: '请选择供应商级别',
      options: supplierLevelOptions.value
    },
    rules: [{required: true, message: '供应商级别不能为空'}]
  },
  {
    type: 'input',
    label: '联系人',
    field: 'contactPerson',
    props: {
      placeholder: '请输入联系人',
      maxLength: 50
    }
  },
  {
    type: 'input',
    label: '联系电话',
    field: 'contactPhone',
    props: {
      placeholder: '请输入联系电话',
      maxLength: 20
    }
  },
  {
    type: 'input',
    label: '联系邮箱',
    field: 'contactEmail',
    props: {
      placeholder: '请输入联系邮箱',
      maxLength: 100
    }
  },
  {
    type: 'input',
    label: '地址',
    field: 'address',
    props: {
      placeholder: '请输入地址',
      maxLength: 200
    },
    colProps: { span: 24 }
  },
  {
    type: 'input',
    label: '统一社会信用代码',
    field: 'creditCode',
    props: {
      placeholder: '请输入统一社会信用代码',
      maxLength: 18
    }
  },
  {
    type: 'input',
    label: '开户银行',
    field: 'bankName',
    props: {
      placeholder: '请输入开户银行',
      maxLength: 100
    }
  },
  {
    type: 'input',
    label: '银行账户',
    field: 'bankAccount',
    props: {
      placeholder: '请输入银行账户',
      maxLength: 30
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
      step: 0.01,
      precision: 2
    }
  },
  {
    type: 'input',
    label: '付款条件',
    field: 'paymentTerms',
    props: {
      placeholder: '请输入付款条件',
      maxLength: 200
    },
    colProps: { span: 24 }
  },
  {
    type: 'input',
    label: '交货条件',
    field: 'deliveryTerms',
    props: {
      placeholder: '请输入交货条件',
      maxLength: 200
    },
    colProps: { span: 24 }
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
      showWordLimit: true,
      autoSize: { minRows: 3, maxRows: 5 }
    },
    colProps: { span: 24 }
  }
] as ColumnItem[])

// 重置表单函数
const resetForm = () => {
  Object.assign(formData, {
    id: '',
    code: '',
    name: '',
    shortName: '',
    type: undefined,
    level: undefined,
    contactPerson: '',
    contactPhone: '',
    contactEmail: '',
    address: '',
    creditCode: '',
    bankName: '',
    bankAccount: '',
    taxRate: 0.13,
    paymentTerms: '',
    deliveryTerms: '',
    status: undefined,
    remark: ''
  })
}

// 监听弹窗显示状态
watch(() => props.visible, (newVal) => {
  if (newVal) {
    resetForm()
    if (props.supplierData) {
      // 编辑模式，填充数据，确保字典字段为字符串类型
      Object.assign(formData, {
        ...props.supplierData,
        type: String(props.supplierData.type),
        level: String(props.supplierData.level),
        status: String(props.supplierData.status)
      })
    }
  }
})

const handleOk = async () => {
  try {
    const errors = await formRef.value?.formRef?.validate()
    if (errors) {
      return
    }

    loading.value = true

    if (isEdit.value) {
      await supplierAPI.update(formData)
      Message.success('供应商修改成功')
    } else {
      await supplierAPI.add(formData)
      Message.success('供应商新增成功')
    }
    emit('success')
    emit('update:visible', false)
    resetForm()
  } catch (error) {
    console.error('操作失败:', error)
    Message.error('操作失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

const handleCancel = () => {
  emit('update:visible', false)
}
</script>

<style lang="scss" scoped></style> 