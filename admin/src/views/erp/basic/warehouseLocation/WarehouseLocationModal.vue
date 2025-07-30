<template>
  <a-modal
      :visible="props.visible"
      :title="isEdit ? '编辑库位' : '新增库位'"
      width="600px"
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
import {warehouseLocationAPI, type WarehouseLocationItem} from '@/apis/warehouseLocation'
import {warehouseAPI} from '@/apis/warehouse'
import type {ColumnItem} from '@/components/GiForm'
import {useDict} from '@/hooks/app'

interface Props {
  visible: boolean
  warehouseLocationData?: WarehouseLocationItem
}

interface Emits {
  (e: 'update:visible', value: boolean): void

  (e: 'success'): void
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  warehouseLocationData: undefined
})

const emit = defineEmits<Emits>()

const {data: statusOptions} = useDict({code: 'status'})
const {data: warehouseLocationTypeOptions} = useDict({code: 'warehouse'})

const formRef = ref()
const loading = ref(false)
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

// 加载当前仓库选项（编辑时使用）
const loadCurrentWarehouseOption = async (warehouseId: string | number) => {
  try {
    const res = await warehouseAPI.getOptionById(warehouseId)
    if (res.data) {
      // 编辑时先显示当前仓库，但保留搜索功能
      warehouseOptions.value = [res.data]
    }
  } catch (error) {
    console.error('加载当前仓库选项失败:', error)
    // 如果加载失败，使用默认的搜索接口
    loadInitialWarehouseOptions()
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

// 弹窗打开时加载选项或编辑时加载特定仓库
watch(() => props.visible, async (newVal) => {
  if (newVal) {
    if (props.warehouseLocationData?.warehouseId) {
      // 编辑模式：加载当前仓库选项
      await loadCurrentWarehouseOption(props.warehouseLocationData.warehouseId)
    } else {
      // 新增模式：加载默认选项
      if (warehouseOptions.value.length === 0) {
        await loadInitialWarehouseOptions()
      }
    }
  }
})


const isEdit = computed(() => !!props.warehouseLocationData?.id)

const formData = reactive({
  id: '',
  warehouseId: undefined,
  code: '',
  name: '',
  type: undefined,
  capacity: 0,
  status: undefined,
  remark: ''
})

const formColumns = computed(() => [
  {
    type: 'select',
    label: '所属仓库',
    field: 'warehouseId',
    props: {
      placeholder: '请选择所属仓库',
      allowSearch: true,
      filterOption: false,
      options: warehouseOptions.value,
      onSearch: searchWarehouseOptions
    },
    rules: [{required: true, message: '所属仓库不能为空'}]
  },
  {
    type: 'input',
    label: '库位编码',
    field: 'code',
    props: {
      placeholder: '请输入库位编码',
      maxLength: 50
    },
    rules: [{required: true, message: '库位编码不能为空'}]
  },
  {
    type: 'input',
    label: '库位名称',
    field: 'name',
    props: {
      placeholder: '请输入库位名称',
      maxLength: 100
    },
    rules: [{required: true, message: '库位名称不能为空'}]
  },
  {
    type: 'select',
    label: '库位类型',
    field: 'type',
    props: {
      placeholder: '请选择库位类型',
      options: warehouseLocationTypeOptions.value
    },
    rules: [{required: true, message: '库位类型不能为空'}]
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
    },
    rules: [{required: true, message: '存储容量不能为空'}]
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
    if (props.warehouseLocationData) {
      // 编辑模式，填充数据
      Object.assign(formData, props.warehouseLocationData)
    }
  }
})

const resetForm = () => {
  Object.assign(formData, {
    id: '',
    warehouseId: undefined,
    code: '',
    name: '',
    type: undefined,
    capacity: 0,
    status: 1,
    remark: ''
  })
}

const handleOk = async () => {
  try {
    const errors = await formRef.value?.formRef?.validate()
    if (!errors) {
      loading.value = true

      if (isEdit.value) {
        await warehouseLocationAPI.update(formData)
        Message.success('库位修改成功')
      } else {
        await warehouseLocationAPI.add(formData)
        Message.success('库位新增成功')
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