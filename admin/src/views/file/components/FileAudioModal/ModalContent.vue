<template>
  <transition name="slide-dynamic-origin">
    <div v-show="visible" ref="audioRef" class="audio-box" :style="audioStyle">
      <section style="padding: 10px 14px 14px 14px">
        <div ref="audioHeadRef" class="audio-box__header">
          <div class="audio-name">
            <icon-music :size="16" spin />
            <span>{{ props.data?.name }}.{{ props.data?.extendName }}</span>
          </div>
          <div class="close-icon" @click="close">
            <icon-close :size="12" />
          </div>
        </div>

        <!-- 音频组件 -->
        <audio class="audio" :src="audioSrc" controls autoplay></audio>
      </section>
    </div>
  </transition>
</template>

<script setup lang="ts">
import {useDraggable, useElementSize, useWindowSize} from '@vueuse/core'
import type {FileItem} from '@/apis/file'

interface Props {
  data: FileItem
  onClose: () => void
}
const props = withDefaults(defineProps<Props>(), {})

const visible = ref(false)
const audioRef = useTemplateRef('audioRef')
const audioHeadRef = useTemplateRef('audioHeadRef')

const audioSrc = computed(() => {
  return props.data?.src || ''
})

onMounted(() => {
  visible.value = true
})

const { width: windowWidth, height: windowHeight } = useWindowSize()
const { width: boxWidth, height: boxHeight } = useElementSize(audioRef)

const axis = ref({ top: 40, left: windowWidth.value - boxWidth.value })
const obj = JSON.parse(sessionStorage.getItem('AudioDialogXY') as string)
if (obj && obj.top && obj.left) {
  axis.value.top = obj.top
  axis.value.left = obj.left
}
const { x, y } = useDraggable(audioRef, {
  initialValue: { x: axis.value.left - boxWidth.value, y: axis.value.top }
})

const audioStyle = computed(() => {
  let left: number | string = x.value
  let top: number | string = y.value
  if (x.value > windowWidth.value - boxWidth.value) {
    left = windowWidth.value - boxWidth.value
  }
  if (x.value < 0) {
    left = 0
  }
  if (y.value > windowHeight.value - boxHeight.value) {
    top = windowHeight.value - boxHeight.value
  }
  if (y.value < 0) {
    top = 0
  }
  sessionStorage.setItem('AudioDialogXY', JSON.stringify({ top, left }))
  return {
    left: `${left}px`,
    top: `${top}px`
  }
})

const close = () => {
  visible.value = false
  props.onClose && props.onClose()
}
</script>

<style lang="scss" scoped>
.audio-box {
  width: 300px;
  position: fixed;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  background: linear-gradient(to right, $color-theme, rgb(var(--primary-2)));
  z-index: 9999;

  &__header {
    color: #fff;
    font-size: 16px;
    margin-bottom: 10px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    cursor: move;
    user-select: none;

    &:active {
      cursor: move;
    }

    .audio-name {
      display: flex;
      align-items: center;

      >span {
        margin-left: 8px;
      }
    }

    .close-icon {
      width: 24px;
      height: 24px;
      display: flex;
      justify-content: center;
      align-items: center;
      border-radius: 50%;
      background: rgba(0, 0, 0, 0);
      transition: all 0.2s;
      cursor: pointer;

      svg {
        transition: all 0.2s;
      }

      &:hover {
        background: rgba(0, 0, 0, 0.1);

        svg {
          transform: scale(1.3);
        }
      }
    }
  }

  .audio {
    width: 100%;

    &::-webkit-media-controls-enclosure {
      background: #fff;
    }
  }
}
</style>
