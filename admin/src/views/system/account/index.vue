<template>
  <div class="gi_page account">
    <a-row :gutter="14">
      <a-col :xs="24" :sm="24" :md="24" :lg="10" :xl="8" :xxl="7">
        <section class="user-card">
          <div class="user-card__header">
            <a-avatar :size="60" :trigger-icon-style="{ color: '#3491FA' }">
              <img :src="userStore.avatar" />
              <template #trigger-icon>
                <IconCamera />
              </template>
            </a-avatar>
            <div class="name">{{ userStore.name }}</div>
            <a-typography-paragraph>尘缘已定，不念过往</a-typography-paragraph>
          </div>

          <a-descriptions :column="1" :label-style="{ marginRight: '0' }">
            <a-descriptions-item>
              <template #label><icon-bookmark :stroke-width="1" :size="16" /></template>
              <span>前端工程师</span>
            </a-descriptions-item>
            <a-descriptions-item>
              <template #label><icon-branch :stroke-width="1" :size="16" /></template>
              <span>中台-数据平台团队-前端创新团队-前端架构和平台工具团队</span>
            </a-descriptions-item>
            <a-descriptions-item>
              <template #label><icon-location :stroke-width="1" :size="16" /></template>
              <span>广州市</span>
            </a-descriptions-item>
          </a-descriptions>

          <a-row justify="space-around">
            <a-avatar :size="32"
              image-url="https://file.iviewui.com/admin-pro-dist/img/icon-social-weibo.cbf658a0.svg"></a-avatar>
            <a-avatar :size="32"
              image-url="https://file.iviewui.com/admin-pro-dist/img/icon-social-zhihu.1dc5a4ff.svg"></a-avatar>
            <a-avatar :size="32"
              image-url="https://file.iviewui.com/admin-pro-dist/img/icon-social-facebook.e95df60e.svg"></a-avatar>
            <a-avatar :size="32"
              image-url="https://file.iviewui.com/admin-pro-dist/img/icon-social-twitter.5db80e81.svg"></a-avatar>
          </a-row>

          <a-divider type="dashed" />

          <a-typography-title :heading="6">标签</a-typography-title>
          <a-space wrap :size="5">
            <a-tag>vue3</a-tag>
            <a-tag>pinia</a-tag>
            <a-tag>vite</a-tag>
            <a-tag>ts</a-tag>
            <a-tag>arco design</a-tag>
          </a-space>

          <a-descriptions :column="1" style="margin-top: 20px">
            <a-descriptions-item label="星座">双鱼座</a-descriptions-item>
            <a-descriptions-item label="生日">07月16日</a-descriptions-item>
            <a-descriptions-item label="爱好">
              <a-space wrap :size="5">
                <a-tag color="purple">王者荣耀</a-tag>
                <a-tag color="magenta">旅行</a-tag>
              </a-space>
            </a-descriptions-item>
          </a-descriptions>
        </section>
      </a-col>
      <a-col :xs="24" :sm="24" :md="24" :lg="14" :xl="16" :xxl="17">
        <RightBox></RightBox>
      </a-col>
    </a-row>
  </div>
</template>

<script lang="ts" setup>
import RightBox from './RightBox.vue'
import {useUserStore} from '@/stores'

defineOptions({ name: 'SystemAccount' })
const userStore = useUserStore()
</script>

<style lang="scss" scoped>
.user-card {
  width: 100%;
  height: fit-content;
  padding: $padding;
  box-sizing: border-box;
  background: var(--color-bg-1);
  border-radius: 2px;

  &__header {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .name {
      font-size: 20px;
      font-weight: bolder;
      line-height: 1.5;
      margin: 8px;
      color: $color-text-1;
    }

    .desc {
      font-size: 12px;
      color: $color-text-3;
    }
  }
}
</style>
