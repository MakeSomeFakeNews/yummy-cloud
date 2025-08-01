<!--
  @file HeaderRightBar 组件
  @description 头部右侧工具栏组件，包含项目配置、消息通知、全屏切换、主题切换和用户菜单等功能
-->
<template>
  <a-row justify="end" align="center">
    <a-space size="medium">
      <!-- 项目配置按钮 -->
      <a-tooltip content="项目配置" position="bl">
        <a-button size="mini" class="gi_hover_btn" @click="handleOpenSettings">
          <template #icon>
            <icon-settings :size="18" />
          </template>
        </a-button>
      </a-tooltip>



      <!-- 全屏切换按钮 -->
      <a-tooltip v-if="!['xs', 'sm'].includes(breakpoint)" content="全屏切换" position="bottom">
        <a-button size="mini" class="gi_hover_btn" @click="toggle">
          <template #icon>
            <icon-fullscreen v-if="!isFullscreen" :size="18" />
            <icon-fullscreen-exit v-else :size="18" />
          </template>
        </a-button>
      </a-tooltip>

      <!-- 暗黑模式切换 -->
      <a-tooltip content="主题切换" position="bottom">
        <GiThemeBtn></GiThemeBtn>
      </a-tooltip>

      <!-- 管理员账户 -->
      <a-dropdown trigger="hover">
        <a-row align="center" :wrap="false" class="user">
          <!-- 管理员头像 -->
          <a-avatar :size="32">
            <img :src="userStore.avatar" />
          </a-avatar>
          <span class="username">{{ userStore.name }}</span>
          <icon-down />
        </a-row>

        <template #content>
          <a-doption v-for="item in userMenuItems" :key="item.key" @click="item.onClick">
            <template #icon>
              <GiIconBox :color="item.iconColor">
                <component :is="item.icon" />
              </GiIconBox>
            </template>
            <span>{{ item.label }}</span>
          </a-doption>
          <a-divider :margin="0" />
          <a-doption @click="handleLogout">
            <template #icon>
              <GiIconBox color="warning">
                <icon-export />
              </GiIconBox>
            </template>
            <span>退出登录</span>
          </a-doption>
        </template>
      </a-dropdown>
    </a-space>
  </a-row>

  <SettingDrawer ref="SettingDrawerRef"></SettingDrawer>
  <ChangePasswordModal ref="ChangePasswordModalRef"></ChangePasswordModal>
</template>

<script setup lang="ts">
import {Modal} from '@arco-design/web-vue'
import {useFullscreen} from '@vueuse/core'
import SettingDrawer from './SettingDrawer.vue'
import ChangePasswordModal from './ChangePasswordModal.vue'
import {useUserStore} from '@/stores'
import {useBreakpoint} from '@/hooks'

/** 组件名称 */
defineOptions({ name: 'HeaderRight' })

/** 路由实例 */
const router = useRouter()

/** 状态管理 */
const userStore = useUserStore()

/** 响应式断点 */
const { breakpoint } = useBreakpoint()

/** 全屏控制 */
const { isFullscreen, toggle } = useFullscreen()

/** 组件引用 */
const SettingDrawerRef = useTemplateRef('SettingDrawerRef')
const ChangePasswordModalRef = useTemplateRef('ChangePasswordModalRef')

/** 用户菜单配置 */
const userMenuItems = [
  {
    key: 'password',
    label: '修改密码',
    icon: 'icon-unlock',
    iconColor: 'primary',
    onClick: () => {
      ChangePasswordModalRef.value?.open()
    }
  }
]

/** 打开设置抽屉 */
const handleOpenSettings = () => {
  SettingDrawerRef.value?.open()
}

/** 处理退出登录 */
const handleLogout = () => {
  Modal.warning({
    title: '提示',
    content: '确认退出登录？',
    hideCancel: false,
    closable: true,
    onBeforeOk: async () => {
      try {
        await userStore.logout()
        router.replace('/login')
        return true
      } catch (error) {
        return false
      }
    }
  })
}
</script>

<style lang="scss" scoped>
.arco-dropdown-open .arco-icon-down {
  transform: rotate(180deg);
}

.doption-icon {
  width: 20px;
  height: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-shrink: 0;
  color: #fff;
  border-radius: 4px;

  &.primary {
    background-color: rgba(var(--primary-6));
  }

  &.success {
    background-color: rgba(var(--success-6));
  }

  &.warning {
    background-color: rgba(var(--warning-6));
  }
}

.user {
  cursor: pointer;
  color: var(--color-text-1);

  .username {
    margin-left: 10px;
    white-space: nowrap;
  }

  .arco-icon-down {
    transition: all 0.3s;
    margin-left: 2px;
  }
}
</style>
