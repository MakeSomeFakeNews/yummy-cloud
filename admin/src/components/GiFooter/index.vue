<template>
  <div class="gi-footer">Copyright @{{ year }} Yummy cloud</div>
</template>

<script lang="ts" setup>
import Dayjs from 'dayjs'

defineOptions({ name: 'GiFooter' })

const year = Dayjs(new Date()).format('YYYY')
</script>

<style lang="scss" scoped>
.gi-footer {
  font-size: 12px;
  color: var(--color-text-3);
  margin-top: 12px;
  padding-top: $padding;
  border-top: 1px dashed var(--color-neutral-3);
  box-sizing: border-box;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
