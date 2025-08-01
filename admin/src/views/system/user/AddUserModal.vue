<template>
  <a-modal v-model:visible="visible" :title="title" width="90%" :mask-closable="false"
    :modal-style="{ maxWidth: '600px' }" :body-style="{ maxHeight: '70vh' }" @before-ok="save" @close="close">
    <a-form ref="formRef" :model="form" :rules="rules" size="medium" auto-label-width>
      <a-row>
        <a-col v-bind="col2Props">
          <a-form-item label="用户名" field="username">
            <a-input v-model.trim="form.username" placeholder="请输入用户名" allow-clear :disabled="form.disabled"
              :max-length="10"></a-input>
          </a-form-item>
        </a-col>
        <a-col v-bind="col2Props">
          <a-form-item label="昵称" field="nickname">
            <a-input v-model.trim="form.nickname" placeholder="请输入昵称" allow-clear :max-length="10"></a-input>
          </a-form-item>
        </a-col>
        <a-col v-bind="col2Props">
          <a-form-item label="手机号码" field="phone">
            <a-input v-model.trim="form.phone" placeholder="请输入手机号码" allow-clear :max-length="11"></a-input>
          </a-form-item>
        </a-col>
        <a-col v-bind="col2Props">
          <a-form-item label="邮箱" field="email">
            <a-input v-model.trim="form.email" placeholder="请输入邮箱" allow-clear :max-length="30"></a-input>
          </a-form-item>
        </a-col>
      </a-row>

      <a-form-item label="性别" field="gender">
        <a-radio-group v-model="form.gender">
          <a-radio :value="1">男</a-radio>
          <a-radio :value="2">女</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label="所属部门" field="deptId">
        <a-tree-select v-model="form.deptId" :data="deptList" :field-names="{
          key: 'id',
          title: 'name',
        }" placeholder="请选择所属部门" allow-clear allow-search :disabled="form.disabled" />
      </a-form-item>

      <a-form-item label="角色" field="roleIds" :disabled="form.disabled">
        <a-select v-model="form.roleIds" :options="roleOptions" placeholder="请选择所属角色" multiple allow-clear
          :allow-search="{ retainInputValue: true }" />
      </a-form-item>

      <a-form-item label="描述" field="description">
        <a-textarea v-model.trim="form.description" :max-length="200" placeholder="请填写描述" :auto-size="{ minRows: 3 }"
          show-word-limit />
      </a-form-item>

      <a-form-item label="状态" field="status">
        <a-switch v-model="form.status" type="round" :checked-value="1" :unchecked-value="0" checked-text="正常"
          unchecked-text="禁用" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import {type ColProps, type FormInstance, Message} from '@arco-design/web-vue'
import * as Regexp from '@/utils/regexp'
import {baseAPI} from '@/apis/system/user'
import {useDept, useRole} from '@/hooks/app'
import {useResetReactive} from '@/hooks'

const emit = defineEmits<{
  (e: 'save-success'): void
}>()

const { roleList, getRoleList } = useRole()
getRoleList()
const roleOptions = computed(() => roleList.value.map((i) => ({ label: i.name, value: i.id.toString() })))

const { deptList, getDeptList } = useDept()
getDeptList()

const col2Props: ColProps = { xs: 24, sm: 24, md: 12, lg: 12, xl: 12, xxl: 12 }
const formRef = useTemplateRef('formRef')
const userId = ref('')
const isEdit = computed(() => !!userId.value)
const title = computed(() => (isEdit.value ? '编辑用户' : '新增用户'))
const visible = ref(false)

const [form, resetForm] = useResetReactive({
  id: '',
  username: '', // 用户名
  nickname: '', // 昵称
  gender: 1 as Gender, // 性别 1男 2女
  phone: '', // 手机号
  email: '', // 邮箱
  deptId: '', // 部门
  roleIds: [] as string[], // 角色(可能多个)
  description: '', // 描述
  status: 1 as Status, // 状态 0禁用 1启用(正常)
  type: 2 as 1 | 2, // 类型 1系统内置(admin是系统内置) 2自定义
  disabled: false // 如果 type===1 这为 true, 主要作用是列表复选框禁用状态
})

const rules: FormInstance['rules'] = {
  username: [
    { required: true, message: '请输入用户名' },
    { min: 2, max: 4, message: '长度在 2 - 4个字符' }
  ],
  nickname: [
    { required: true, message: '请输入昵称' },
    { min: 2, max: 4, message: '长度在 2 - 4个字符' }
  ],
  email: [{ match: Regexp.Email, message: '邮箱格式不正确' }],
  phone: [{ match: Regexp.Phone, message: '手机号格式不正确' }],
  deptId: [{ required: true, message: '请选择所属部门' }],
  roleIds: [{ required: true, message: '请选择角色' }],
  status: [{ required: true, message: '请选择状态' }]
}

const add = () => {
  userId.value = ''
  visible.value = true
}

const edit = async (id: string) => {
  visible.value = true
  userId.value = id
  const res = await baseAPI.getDetail({ id })
  const userData = res.data
  
  // 将后端返回的数据转换为前端需要的格式
  const formData = {
    ...userData,
    roleIds: userData.roleIds ? userData.roleIds.map((id: number) => id.toString()) : []
  }
  
  Object.assign(form, formData)
}

const close = () => {
  formRef.value?.resetFields()
  resetForm()
}

const save = async () => {
  try {
    const valid = await formRef.value?.validate()
    if (valid) return false
    
    // 准备提交数据，将角色ID数组转换为数字类型
    const submitData = {
      ...form,
      roleIds: form.roleIds.map(id => parseInt(id))
    }
    
    let res
    if (isEdit.value) {
      // 编辑模式，调用更新接口
      res = await baseAPI.update(submitData)
    } else {
      // 新增模式，调用新增接口
      res = await baseAPI.add(submitData)
    }
    
    if (res) {
      Message.success(isEdit.value ? '用户更新成功' : '用户新增成功')
      emit('save-success')
      return true
    } else {
      return false
    }
  } catch (error) {
    console.error('保存用户失败:', error)
    Message.error('保存用户失败')
    return false
  }
}

defineExpose({ add, edit })
</script>
