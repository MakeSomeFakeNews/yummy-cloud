<template>
  <a-drawer
    :visible="props.visible"
    width="600px"
    @cancel="handleCancel"
    :footer="false"
    class="warehouse-location-detail-drawer">
    
    <template #title>
      <div class="drawer-title">
        <icon-home class="title-icon" />
        <span>库位详情</span>
      </div>
    </template>
    
    <div v-if="loading" class="loading-container">
      <a-spin size="large" />
      <div class="loading-text">正在加载详情...</div>
    </div>

    <div v-else-if="warehouseLocationDetail" class="detail-container">
      <!-- 基本信息卡片 -->
      <div class="info-card">
        <div class="card-header">
          <icon-storage class="header-icon" />
          <h3>基本信息</h3>
          <div class="status-badge">
            <GiCellStatus :status="warehouseLocationDetail.status" />
          </div>
        </div>
        
        <div class="card-content">
          <div class="info-grid">
            <div class="info-item">
              <div class="info-label">
                <icon-home />
                所属仓库
              </div>
              <div class="info-value warehouse-name">
                {{ warehouseName }}
              </div>
            </div>
            
            <div class="info-item">
              <div class="info-label">
                <icon-code />
                库位编码
              </div>
              <div class="info-value code-value">
                {{ warehouseLocationDetail.code }}
              </div>
            </div>
            
            <div class="info-item">
              <div class="info-label">
                <icon-tag />
                库位名称
              </div>
              <div class="info-value name-value">
                {{ warehouseLocationDetail.name }}
              </div>
            </div>
            
            <div class="info-item">
              <div class="info-label">
                <icon-apps />
                库位类型
              </div>
              <div class="info-value">
                <a-tag :color="getTypeColor(warehouseLocationDetail.type)" class="type-tag">
                  <icon-layers />
                  {{ warehouseLocationTypeOptions?.find(opt => opt.value == warehouseLocationDetail.type)?.label }}
                </a-tag>
              </div>
            </div>
            
            <div class="info-item">
              <div class="info-label">
                <icon-dashboard />
                存储容量
              </div>
              <div class="info-value capacity-value">
                <span class="capacity-number">{{ warehouseLocationDetail.capacity }}</span>
                <span class="capacity-unit">立方米</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 备注信息卡片 -->
      <div class="info-card" v-if="warehouseLocationDetail.remark">
        <div class="card-header">
          <icon-edit class="header-icon" />
          <h3>备注信息</h3>
        </div>
        <div class="card-content">
          <div class="remark-content">
            {{ warehouseLocationDetail.remark }}
          </div>
        </div>
      </div>

      <!-- 时间信息卡片 -->
      <div class="info-card">
        <div class="card-header">
          <icon-clock-circle class="header-icon" />
          <h3>时间信息</h3>
        </div>
        <div class="card-content">
          <div class="time-grid">
            <div class="time-item">
              <div class="time-label">创建时间</div>
              <div class="time-value">{{ formatTime(warehouseLocationDetail.createTime) }}</div>
            </div>
            <div class="time-item">
              <div class="time-label">更新时间</div>
              <div class="time-value">{{ formatTime(warehouseLocationDetail.updateTime) }}</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 操作按钮 -->
      <div class="footer-actions">
        <a-button type="primary" size="large" @click="handleEdit" class="edit-btn">
          <template #icon><icon-edit /></template>
          编辑库位
        </a-button>
      </div>
    </div>

    <div v-else class="empty-container">
      <a-empty description="暂无数据" />
    </div>
  </a-drawer>
</template>

<script setup lang="ts">
import { ref, watch, computed } from 'vue'
import { Message } from '@arco-design/web-vue'
import { warehouseLocationAPI, type WarehouseLocationItem } from '@/apis/warehouseLocation'
import { warehouseAPI } from '@/apis/warehouse'
import { useDict } from '@/hooks/app'
import {
  IconHome,
  IconStorage,
  IconCode,
  IconTag,
  IconApps,
  IconLayers,
  IconDashboard,
  IconEdit,
  IconClockCircle
} from '@arco-design/web-vue/es/icon'
import dayjs from 'dayjs'

interface Props {
  visible: boolean
  warehouseLocationId?: string
}

interface Emits {
  (e: 'update:visible', value: boolean): void
  (e: 'edit', warehouseLocation: WarehouseLocationItem): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const { data: warehouseLocationTypeOptions } = useDict({ code: 'warehouse' })

const loading = ref(false)
const warehouseLocationDetail = ref<WarehouseLocationItem | null>(null)
const warehouseName = ref('-')

const getWarehouseLocationDetail = async () => {
  if (!props.warehouseLocationId) return

  try {
    loading.value = true
    const res = await warehouseLocationAPI.getDetail({ id: props.warehouseLocationId })
    warehouseLocationDetail.value = res.data

    // 获取仓库名称
    if (res.data.warehouseId) {
      try {
        const warehouseRes = await warehouseAPI.getDetail({ id: res.data.warehouseId })
        warehouseName.value = warehouseRes.data.name
      } catch (error) {
        console.error('Failed to get warehouse name:', error)
      }
    }
  } catch (error) {
    console.error('获取库位详情失败:', error)
    Message.error('获取库位详情失败')
  } finally {
    loading.value = false
  }
}

watch(() => props.visible, (newVal) => {
  if (newVal && props.warehouseLocationId) {
    getWarehouseLocationDetail()
  }
})

const handleCancel = () => {
  emit('update:visible', false)
  warehouseLocationDetail.value = null
  warehouseName.value = '-'
}

const handleEdit = () => {
  if (warehouseLocationDetail.value) {
    emit('edit', warehouseLocationDetail.value)
    handleCancel()
  }
}

// 工具函数
const formatTime = (time: string) => {
  return time ? dayjs(time).format('YYYY-MM-DD HH:mm:ss') : '-'
}

const getTypeColor = (type: number) => {
  const colorMap: Record<number, string> = {
    1: 'blue',      // 普通库位
    2: 'cyan',      // 冷藏库位  
    3: 'orange'     // 危险品库位
  }
  return colorMap[type] || 'gray'
}
</script>

<style lang="scss" scoped>
.warehouse-location-detail-drawer {
  :deep(.arco-drawer-header) {
    border-bottom: 1px solid var(--color-neutral-3);
    padding: 16px 24px;
  }

  :deep(.arco-drawer-body) {
    padding: 24px;
    background: var(--color-bg-1);
  }
}

.drawer-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  font-weight: 600;
  color: var(--color-text-1);

  .title-icon {
    font-size: 18px;
    color: var(--color-primary-6);
  }
}

.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 300px;
  gap: 16px;

  .loading-text {
    color: var(--color-text-3);
    font-size: 14px;
  }
}

.detail-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.info-card {
  background: white;
  border-radius: 8px;
  border: 1px solid var(--color-neutral-3);
  overflow: hidden;

  .card-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 12px 16px;
    background: linear-gradient(135deg, var(--color-primary-1) 0%, var(--color-primary-2) 100%);
    border-bottom: 1px solid var(--color-neutral-3);

    .header-icon {
      font-size: 16px;
      color: var(--color-primary-6);
      margin-right: 8px;
    }

    h3 {
      font-size: 14px;
      font-weight: 600;
      color: var(--color-text-1);
      margin: 0;
      flex: 1;
      display: flex;
      align-items: center;
    }

    .status-badge {
      margin-left: 12px;
    }
  }

  .card-content {
    padding: 16px;
  }
}

.info-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 12px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  padding: 10px 12px;
  background: var(--color-fill-1);
  border-radius: 6px;
  border: 1px solid var(--color-neutral-2);

  .info-label {
    display: flex;
    align-items: center;
    gap: 6px;
    font-size: 13px;
    color: var(--color-text-3);
    font-weight: 500;
    flex-shrink: 0;

    .arco-icon {
      font-size: 14px;
      color: var(--color-primary-5);
    }
  }

  .info-value {
    font-size: 14px;
    color: var(--color-text-1);
    font-weight: 500;
    text-align: right;
    flex-shrink: 0;

    &.warehouse-name {
      color: var(--color-primary-6);
      font-weight: 600;
    }

    &.code-value {
      font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
      background: var(--color-neutral-1);
      padding: 3px 6px;
      border-radius: 4px;
      font-size: 12px;
      border: 1px solid var(--color-neutral-3);
    }

    &.name-value {
      font-weight: 600;
      color: var(--color-text-1);
    }

    &.capacity-value {
      display: flex;
      align-items: baseline;
      gap: 4px;
      justify-content: flex-end;

      .capacity-number {
        font-size: 16px;
        font-weight: 700;
        color: var(--color-success-6);
      }

      .capacity-unit {
        font-size: 12px;
        color: var(--color-text-3);
      }
    }
  }

  .type-tag {
    display: inline-flex;
    align-items: center;
    gap: 4px;
    font-weight: 500;

    .arco-icon {
      font-size: 12px;
    }
  }
}

.remark-content {
  background: var(--color-fill-1);
  padding: 12px;
  border-radius: 6px;
  border: 1px solid var(--color-neutral-2);
  font-size: 14px;
  line-height: 1.5;
  color: var(--color-text-2);
  white-space: pre-wrap;
}

.time-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;

  .time-item {
    display: flex;
    flex-direction: column;
    gap: 6px;
    padding: 10px 12px;
    background: var(--color-fill-1);
    border-radius: 6px;
    border: 1px solid var(--color-neutral-2);

    .time-label {
      font-size: 12px;
      color: var(--color-text-3);
      font-weight: 500;
    }

    .time-value {
      font-size: 13px;
      color: var(--color-text-2);
      font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
    }
  }
}

.footer-actions {
  margin-top: 20px;
  text-align: center;
  padding-top: 16px;
  border-top: 1px solid var(--color-neutral-3);

  .edit-btn {
    padding: 8px 24px;
    height: auto;
    font-size: 14px;
    font-weight: 600;
    border-radius: 6px;
  }
}

.empty-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
}

// 响应式适配
@media (max-width: 768px) {
  .warehouse-location-detail-drawer {
    :deep(.arco-drawer-body) {
      padding: 16px;
    }
  }

  .time-grid {
    grid-template-columns: 1fr;
  }

  .footer-actions .edit-btn {
    width: 100%;
  }
}
</style>