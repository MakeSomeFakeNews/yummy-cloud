<template>
  <a-modal
      :visible="props.visible"
      :title="isEdit ? '编辑计量单位' : '新增计量单位'"
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
import {unitAPI, type UnitItem} from '@/apis/unit'
import type {ColumnItem} from '@/components/GiForm'
import {useDict} from '@/hooks/app'

interface Props {
  visible: boolean
  unitData?: UnitItem
}

interface Emits {
  (e: 'update:visible', value: boolean): void

  (e: 'success'): void
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  unitData: undefined
})

const emit = defineEmits<Emits>()

const {data: statusOptions} = useDict({code: 'status'})
const {data: unitTypeOptions} = useDict({code: 'unit'})

const formRef = ref()
const loading = ref(false)

const isEdit = computed(() => !!props.unitData?.id)

const formData = reactive({
  id: '',
  code: '',
  name: '',
  symbol: '',
  type: undefined,
  status: undefined
})

const formColumns = computed(() => [
  {
    type: 'input',
    label: '单位编码',
    field: 'code',
    props: {
      placeholder: '请输入单位编码',
      maxLength: 50
    },
    rules: [{required: true, message: '单位编码不能为空'}]
  },
  {
    type: 'input',
    label: '单位名称',
    field: 'name',
    props: {
      placeholder: '请输入单位名称',
      maxLength: 100
    },
    rules: [{required: true, message: '单位名称不能为空'}]
  },
  {
    type: 'input',
    label: '单位符号',
    field: 'symbol',
    props: {
      placeholder: '请输入单位符号',
      maxLength: 10
    }
  },
  {
    type: 'select',
    label: '单位类型',
    field: 'type',
    props: {
      placeholder: '请选择单位类型',
      options: unitTypeOptions.value
    },
    rules: [{required: true, message: '单位类型不能为空'}]
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
  }
] as ColumnItem[])

// 监听弹窗显示状态
watch(() => props.visible, (newVal) => {
  if (newVal) {
    resetForm()
    if (props.unitData) {
      // 编辑模式，填充数据
      Object.assign(formData, props.unitData)
    }
  }
})

const resetForm = () => {
  Object.assign(formData, {
    id: '',
    code: '',
    name: '',
    symbol: '',
    type: 1,
    status: 1
  })
}

const handleOk = async () => {
  try {
    const errors = await formRef.value?.formRef?.validate()
    if (!errors) {
      loading.value = true

      if (isEdit.value) {
        await unitAPI.update(formData)
        Message.success('计量单位修改成功')
      } else {
        await unitAPI.add(formData)
        Message.success('计量单位新增成功')
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