<!--
  @file ChangePasswordModal 组件
  @description 修改密码弹窗组件
-->
<template>
  <a-modal
    v-model:visible="visible"
    title="修改密码"
    :width="400"
    :mask-closable="false"
    :esc-to-close="false"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-form ref="formRef" :model="form" :rules="rules" layout="vertical">
      <a-form-item label="当前密码" field="currentPassword">
        <a-input-password
          v-model="form.currentPassword"
          placeholder="请输入当前密码"
          allow-clear
        />
      </a-form-item>
      <a-form-item label="新密码" field="newPassword">
        <a-input-password
          v-model="form.newPassword"
          placeholder="请输入新密码"
          allow-clear
        />
      </a-form-item>
      <a-form-item label="确认新密码" field="confirmPassword">
        <a-input-password
          v-model="form.confirmPassword"
          placeholder="请再次输入新密码"
          allow-clear
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { Message } from '@arco-design/web-vue'
import type { FormInstance } from '@arco-design/web-vue'
import { useUserStore } from '@/stores'

/** 组件名称 */
defineOptions({ name: 'ChangePasswordModal' })

/** 状态管理 */
const userStore = useUserStore()

/** 弹窗显示状态 */
const visible = ref(false)

/** 表单引用 */
const formRef = ref<FormInstance>()

/** 表单数据 */
const form = reactive({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

/** 表单验证规则 */
const rules = {
  currentPassword: [
    { required: true, message: '请输入当前密码' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码' },
    { minLength: 6, message: '密码长度不能少于6位' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码' },
    {
      validator: (value: string, cb: (error?: string) => void) => {
        if (value !== form.newPassword) {
          cb('两次输入的密码不一致')
        }
        cb()
      }
    }
  ]
}

/** 打开弹窗 */
const open = () => {
  visible.value = true
}

/** 关闭弹窗 */
const close = () => {
  visible.value = false
  resetForm()
}

/** 重置表单 */
const resetForm = () => {
  form.currentPassword = ''
  form.newPassword = ''
  form.confirmPassword = ''
  formRef.value?.resetFields()
}

/** 处理提交 */
const handleSubmit = async () => {
  try {
    const valid = await formRef.value?.validate()
    if (!valid) {
      return false
    }

    await userStore.changePassword({
      currentPassword: form.currentPassword,
      newPassword: form.newPassword
    })

    Message.success('密码修改成功')
    close()
    return true
  } catch (error: any) {
    Message.error(error.message || '密码修改失败')
    return false
  }
}

/** 处理取消 */
const handleCancel = () => {
  close()
}

/** 暴露方法 */
defineExpose({
  open,
  close
})
</script>