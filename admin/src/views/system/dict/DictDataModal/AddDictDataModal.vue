<template>
  <a-modal v-model:visible="visible" :title="title" width="90%" :mask-closable="false"
           :modal-style="{ maxWidth: '520px' }" @before-ok="save" @close="close">
    <a-spin :loading="loading" class="w-full">
      <a-form ref="formRef" :model="form" :rules="rules" size="medium" auto-label-width>
        <a-form-item label="字典名" field="name">
          <a-input v-model.trim="form.name" placeholder="请输入字典名" allow-clear :max-length="10"></a-input>
        </a-form-item>
        <a-form-item label="字典值" field="value">
          <a-input v-model.trim="form.value" placeholder="请输入字典值" allow-clear :max-length="10"></a-input>
        </a-form-item>
        <a-form-item label="状态" field="status">
          <a-switch v-model="form.status" type="round" :checked-value="1" :unchecked-value="0" checked-text="正常"
                    unchecked-text="禁用"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script setup lang="ts">
import {type FormInstance, Message} from '@arco-design/web-vue'
import {addDictData, getDictDataDetail, updateDictData} from '@/apis/system/dict'
import {useResetReactive} from '@/hooks'

const emit = defineEmits<{
  (e: 'save-success'): void
}>()

const formRef = useTemplateRef('formRef')
const dictDataId = ref('')
const dictCode = ref('')
const isEdit = computed(() => !!dictDataId.value)
const title = computed(() => (isEdit.value ? '编辑字典数据' : '新增字典数据'))
const visible = ref(false)
const loading = ref(false)

const [form, resetForm] = useResetReactive({
  name: '',
  value: '',
  status: 1,
  dictCode: ''
})

const rules: FormInstance['rules'] = {
  name: [{required: true, message: '请输入字典名'}],
  value: [
    {required: true, message: '请输入字典值'},
    {match: /^\w*$/, message: '格式不对！只能包含英文数字下划线'}
  ],
  status: [{required: true}]
}

const add = (code: string) => {
  dictDataId.value = ''
  dictCode.value = code
  form.dictCode = code
  visible.value = true
}

const edit = async (data: { id: string, code: string }) => {
  visible.value = true
  dictDataId.value = data.id
  dictCode.value = data.code
  loading.value = true
  const res = await getDictDataDetail(data)
  Object.assign(form, {...res.data, dictCode: data.code})
  loading.value = false
}

const close = () => {
  formRef.value?.resetFields()
  resetForm()
}

const save = async () => {
  try {
    const valid = await formRef.value?.validate()
    if (valid) return false

    let res
    if (isEdit.value) {
      res = await updateDictData({...form, id: dictDataId.value})
    } else {
      res = await addDictData(form)
    }

    if (res) {
      Message.success(isEdit.value ? '字典数据更新成功' : '字典数据新增成功')
      emit('save-success')
      return true
    } else {
      return false
    }
  } catch (error) {
    console.error('保存字典数据失败:', error)
    Message.error('保存字典数据失败')
    return false
  }
}

defineExpose({add, edit})
</script>
