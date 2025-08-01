<template>
  <a-modal v-model:visible="visible" :title="title" width="90%" :mask-closable="false"
    :modal-style="{ maxWidth: '520px' }" @before-ok="save" @close="close">
    <a-form ref="formRef" :model="form" :rules="rules" size="medium" auto-label-width>
      <a-form-item label="角色名称" field="name">
        <a-input v-model.trim="form.name" placeholder="请输入角色名称" allow-clear :max-length="10"> </a-input>
      </a-form-item>
      <a-form-item label="角色编码" field="code">
        <a-input v-model.trim="form.code" placeholder="请输入角色编码" allow-clear :disabled="isEdit" :max-length="10">
        </a-input>
      </a-form-item>
      <a-form-item label="描述" field="description">
        <a-textarea v-model.trim="form.description" placeholder="请填写描述" :max-length="200" show-word-limit
          :auto-size="{ minRows: 3, maxRows: 5 }" />
      </a-form-item>
      <a-form-item label="状态" field="status">
        <a-switch v-model="form.status" type="round" :checked-value="1" :unchecked-value="0" checked-text="正常"
          unchecked-text="禁用" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import {type FormInstance, Message} from '@arco-design/web-vue'
import {baseAPI} from '@/apis/system/role'
import {useResetReactive} from '@/hooks'

const emit = defineEmits<{
  (e: 'save-success'): void
}>()

const formRef = useTemplateRef('formRef')
const roleId = ref('')
const isEdit = computed(() => !!roleId.value)
const title = computed(() => (isEdit.value ? '编辑角色' : '新增角色'))
const visible = ref(false)

const [form, resetForm] = useResetReactive({
  name: '',
  code: '',
  status: 1,
  description: ''
})

const rules: FormInstance['rules'] = {
  name: [
    { required: true, message: '请输入角色名称' },
    { min: 3, max: 10, message: '长度在 3 - 10个字符' }
  ],
  code: [
    { required: true, message: '请输入角色编码' },
    { match: /^[a-z]\w*$/i, message: '格式不对！只能英文开头，包含英文数字下划线' }
  ],
  status: [{ required: true }]
}

const add = () => {
  roleId.value = ''
  visible.value = true
}

const edit = async (id: string) => {
  visible.value = true
  roleId.value = id
  const res = await baseAPI.getDetail({ id })
  Object.assign(form, res.data)
}

const close = () => {
  formRef.value?.resetFields()
  resetForm()
}

const save = async () => {
  const valid = await formRef.value?.validate()
  if (valid) return false

  let res
  if (isEdit.value) {
    res = await baseAPI.update({ ...form, id: roleId.value })
  } else {
    res = await baseAPI.add(form)
  }

  if (res) {
    Message.success(isEdit.value ? '角色更新成功' : '角色新增成功')
    emit('save-success')
    return true
  }
  return false
}

defineExpose({ add, edit })
</script>
