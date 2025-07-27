<template>
  <div class="login">
    <a-row align="stretch" class="login-box">
      <a-col :xs="0" :sm="12" :md="15">
        <div class="login-left">
          <img class="login-left__img" src="@/assets/svgs/login-img.svg" />
        </div>
      </a-col>
      <a-col :xs="24" :sm="12" :md="9">
        <div class="login-right">
          <a-form ref="formRef" :size="isMobile() ? 'large' : 'medium'" :model="form" :rules="rules"
            :style="{ width: '84%' }" :label-col-style="{ display: 'none' }" :wrapper-col-style="{ flex: 1 }">
            <h3 class="login-right__title">
              <span>Yummy</span>
            </h3>
            <a-form-item field="username">
              <a-input v-model="form.username" placeholder="账号 admin/user" allow-clear>
                <template #prefix><icon-user :stroke-width="1" :style="{ fontSize: '20px' }" /></template>
              </a-input>
            </a-form-item>
            <a-form-item field="password">
              <a-input-password v-model="form.password" placeholder="密码" allow-clear>
                <template #prefix><icon-lock :stroke-width="1" :style="{ fontSize: '20px' }" /></template>
              </a-input-password>
            </a-form-item>
            <a-form-item field="captcha">
              <a-row :gutter="8">
                <a-col :span="14">
                  <a-input v-model="form.captcha" placeholder="验证码" allow-clear>
                    <template #prefix><icon-safe :stroke-width="1" :style="{ fontSize: '20px' }" /></template>
                  </a-input>
                </a-col>
                <a-col :span="10">
                  <div class="captcha-image" @click="refreshCaptcha">
                    <img v-if="captchaImage" :src="captchaImage" alt="验证码" />
                    <div v-else class="captcha-loading">加载中...</div>
                  </div>
                </a-col>
              </a-row>
            </a-form-item>
            <a-form-item>
              <a-row justify="space-between" align="center" class="w-full">
                <a-checkbox v-model="checked">记住密码</a-checkbox>
                <a-link>忘记密码</a-link>
              </a-row>
            </a-form-item>
            <a-form-item>
              <a-space direction="vertical" fill class="w-full">
                <a-button type="primary" size="large" long :loading="loading" @click="login">登录</a-button>
                <a-button type="text" size="large" long class="register-btn">注册账号</a-button>
              </a-space>
            </a-form-item>
          </a-form>
        </div>
      </a-col>
    </a-row>

    <GiThemeBtn class="theme-btn"></GiThemeBtn>

    <LoginBg></LoginBg>
  </div>
</template>

<script setup lang="ts">
import { type FormInstance, Message } from '@arco-design/web-vue'
import LoginBg from './components/LoginBg/index.vue'
import { useTabsStore, useUserStore } from '@/stores'
import { useLoading } from '@/hooks'
import * as Regexp from '@/utils/regexp'
import { isMobile } from '@/utils'
import { getCaptcha } from '@/apis/user'

defineOptions({ name: 'Login' })
const router = useRouter()
const userStore = useUserStore()
const tabsStore = useTabsStore()

const form = reactive({
  username: 'admin',
  password: '123456',
  captcha: '',
  captchaId: ''
})

const rules: FormInstance['rules'] = {
  username: [{ required: true, message: '请输入账号' }],
  password: [
    { required: true, message: '请输入密码' },
    { match: Regexp.Password, message: '输入密码格式不正确' }
  ],
  captcha: [{ required: true, message: '请输入验证码' }]
}

// 记住密码
const checked = ref(false)
// 登录加载
const { loading, setLoading } = useLoading()
const errorMessage = ref('')

// 验证码
const captchaImage = ref('')

// 获取验证码
const refreshCaptcha = async () => {
  try {
    const response = await getCaptcha()
    form.captchaId = response.data.captchaId
    captchaImage.value = response.data.captchaImage
    // 清空之前输入的验证码
    form.captcha = ''
  } catch (error) {
    console.error('获取验证码失败:', error)
    Message.error('获取验证码失败')
  }
}

const formRef = useTemplateRef('formRef')
// 点击登录
const login = async () => {
  try {
    const valid = await formRef.value?.validate()
    if (valid) return
    setLoading(true)
    await userStore.login(form)
    tabsStore.reset()
    const { redirect, ...othersQuery } = router.currentRoute.value.query
    router.push({
      path: (redirect as string) || '/',
      query: {
        ...othersQuery
      }
    })
    Message.success('登录成功')
  } catch (error) {
    errorMessage.value = (error as Error).message
    // 登录失败时刷新验证码
    await refreshCaptcha()
  } finally {
    setLoading(false)
  }
}

// 页面加载时获取验证码
onMounted(() => {
  refreshCaptcha()
})
</script>

<style lang="scss" scoped>
.login {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: var(--color-bg-5);

  &-box {
    width: 86%;
    max-width: 720px;
    height: 380px;
    display: flex;
    z-index: 999;
    box-shadow: 0 2px 4px 2px rgba(0, 0, 0, 0.08);
  }
}

.login-left {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
  background: linear-gradient(60deg, rgb(var(--primary-6)), rgb(var(--primary-3)));

  &__img {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    transition: all 0.3s;
    object-fit: cover;
  }
}

.login-right {
  width: 100%;
  height: 100%;
  background: var(--color-bg-1);
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 30px;
  box-sizing: border-box;

  &__title {
    color: var(--color-text-1);
    font-weight: 500;
    font-size: 20px;
    line-height: 32px;
    margin-bottom: 20px;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;

    .logo {
      width: 32px;
      height: 32px;
      margin-right: 6px;
    }
  }
}

.register-btn,
.register-btn:hover {
  color: var(--color-text-2);
}

.theme-btn {
  position: fixed;
  top: 20px;
  left: 30px;
  z-index: 9999;
}

.captcha-image {
  height: 32px;
  border: 1px solid var(--color-border-2);
  border-radius: 4px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: var(--color-bg-1);
  transition: all 0.2s;
  overflow: hidden;

  &:hover {
    border-color: var(--color-border-3);
  }

  img {
    width: 100%;
    height: 100%;
    object-fit: contain;
    border-radius: 3px;
  }

  .captcha-loading {
    font-size: 12px;
    color: var(--color-text-3);
  }
}
</style>
