<template>
  <a-drawer
    :visible="props.visible"
    title="计量单位详情"
    width="800px"
    :footer="false"
    unmount-on-close
    @cancel="handleClose"
    class="unit-detail-drawer">
    
    <div v-if="loading" class="loading-container">
      <div class="loading-content">
        <a-spin :size="32" />
        <p class="loading-text">正在加载计量单位信息...</p>
      </div>
    </div>
    
    <div v-else-if="unitDetail" class="unit-detail">
      <!-- 计量单位头像和基本信息 -->
      <div class="unit-header">
        <div class="unit-avatar">
          <div class="avatar-circle">
            <icon-dashboard />
          </div>
        </div>
        <div class="unit-info">
          <h2 class="unit-name">{{ unitDetail.name }}</h2>
          <div class="unit-meta">
            <a-tag class="type-tag">
              {{ getTypeName(unitDetail.type) }}
            </a-tag>
            <div class="status-tag">
              <GiCellStatus :status="unitDetail.status"></GiCellStatus>
            </div>
          </div>
        </div>
      </div>

      <!-- 详细信息卡片 -->
      <div class="detail-cards">
        <!-- 基本信息 -->
        <div class="detail-card fade-in" style="animation-delay: 0.1s">
          <div class="card-header">
            <div class="header-icon basic">
              <icon-dashboard />
            </div>
            <h3>基本信息</h3>
          </div>
          <div class="card-body">
            <div class="info-grid">
              <div class="info-item">
                <div class="info-label">单位编码</div>
                <div class="info-value code">{{ unitDetail.code }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">单位名称</div>
                <div class="info-value">{{ unitDetail.name }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">单位符号</div>
                <div class="info-value symbol">{{ unitDetail.symbol || '-' }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">单位类型</div>
                <div class="info-value">{{ getTypeName(unitDetail.type) }}</div>
              </div>
            </div>
          </div>
        </div>


        <!-- 系统信息 -->
        <div class="detail-card fade-in" style="animation-delay: 0.2s">
          <div class="card-header">
            <div class="header-icon system">
              <icon-clock-circle />
            </div>
            <h3>系统信息</h3>
          </div>
          <div class="card-body">
            <div class="info-grid">
              <div class="info-item">
                <div class="info-label">创建时间</div>
                <div class="info-value">{{ formatDate(unitDetail.createTime) }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">更新时间</div>
                <div class="info-value">{{ formatDate(unitDetail.updateTime) }}</div>
              </div>
              <div v-if="unitDetail.createUserId" class="info-item">
                <div class="info-label">创建人ID</div>
                <div class="info-value">{{ unitDetail.createUserId }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>

    <div v-else class="error-container">
      <div class="error-content">
        <icon-exclamation-circle-fill class="error-icon" />
        <h3>计量单位信息加载失败</h3>
        <p>请检查网络连接后重试</p>
        <a-button type="primary" @click="fetchUnitDetail">重新加载</a-button>
      </div>
    </div>
  </a-drawer>
</template>

<script setup lang="ts">
import { ref, watch, computed } from 'vue'
import { Message } from '@arco-design/web-vue'
import { unitAPI, type UnitItem } from '@/apis/unit'
import { useDict } from '@/hooks/app'
import {
  IconDashboard,
  IconClockCircle,
  IconExclamationCircleFill
} from '@arco-design/web-vue/es/icon'

interface Props {
  visible: boolean
  unitId?: string | number
}

interface Emits {
  (e: 'update:visible', value: boolean): void
  (e: 'edit', unit: UnitItem): void
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  unitId: undefined
})

const emit = defineEmits<Emits>()

const { data: unitTypeOptions } = useDict({ code: 'unit' })

const loading = ref(false)
const unitDetail = ref<UnitItem | null>(null)

// 获取单位类型名称
const getTypeName = (type: string | number) => {
  const option = unitTypeOptions.value?.find(opt => opt.value == type)
  return option?.label || '未知类型'
}

// 格式化日期
const formatDate = (dateStr: string): string => {
  if (!dateStr) return '-'
  return new Date(dateStr).toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  })
}

// 获取计量单位详情
const fetchUnitDetail = async () => {
  if (!props.unitId) return

  try {
    loading.value = true
    const response = await unitAPI.getDetail({ id: String(props.unitId) })
    unitDetail.value = response.data
  } catch (error) {
    console.error('获取计量单位详情失败:', error)
    Message.error('获取计量单位详情失败，请稍后重试')
    unitDetail.value = null
  } finally {
    loading.value = false
  }
}

// 监听弹窗状态和单位ID变化
watch([() => props.visible, () => props.unitId], ([visible, unitId]) => {
  if (visible && unitId) {
    fetchUnitDetail()
  }
}, { immediate: true })

const handleClose = () => {
  emit('update:visible', false)
  unitDetail.value = null
}

const handleEdit = () => {
  if (unitDetail.value) {
    emit('edit', unitDetail.value)
    handleClose()
  }
}
</script>

<style lang="scss" scoped>
.unit-detail-drawer {
  :deep(.arco-drawer-header) {
    background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
    color: white;
    border-bottom: none;
    
    .arco-drawer-title {
      color: white;
      font-weight: 600;
      font-size: 18px;
    }
    
    .arco-drawer-close-btn {
      color: rgba(255, 255, 255, 0.8);
      
      &:hover {
        color: white;
        background-color: rgba(255, 255, 255, 0.1);
      }
    }
  }
  
  :deep(.arco-drawer-body) {
    padding: 0;
    background: linear-gradient(to bottom, #fef8ff 0%, #ffffff 100%);
  }
}

.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
  
  .loading-content {
    text-align: center;
    
    .loading-text {
      margin-top: 16px;
      color: var(--color-text-3);
      font-size: 14px;
    }
  }
}

.error-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
  text-align: center;
}

.error-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
}

.error-icon {
  font-size: 48px;
  color: #f53f3f;
}

.unit-detail {
  padding: 16px;
}

.unit-header {
  display: flex;
  align-items: center;
  padding: 16px 20px;
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  border-radius: 12px;
  margin-bottom: 16px;
  box-shadow: 0 4px 20px rgba(240, 147, 251, 0.2);
  color: white;
  
  .unit-avatar {
    margin-right: 16px;
    
    .avatar-circle {
      width: 60px;
      height: 60px;
      border-radius: 50%;
      background: rgba(255, 255, 255, 0.2);
      backdrop-filter: blur(10px);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 24px;
      color: white;
      border: 2px solid rgba(255, 255, 255, 0.3);
    }
  }
  
  .unit-info {
    flex: 1;
    
    .unit-name {
      margin: 0 0 8px 0;
      font-size: 22px;
      font-weight: 700;
      color: white;
    }
    
    .unit-meta {
      display: flex;
      gap: 8px;
      flex-wrap: wrap;
      
      .type-tag,
      .status-tag {
        backdrop-filter: blur(10px);
        border: 1px solid rgba(255, 255, 255, 0.2);
        
        :deep(.arco-tag) {
          background: rgba(255, 255, 255, 0.1);
          color: white;
          border: none;
          display: inline-flex;
          align-items: center;
          gap: 4px;
        }
      }
    }
  }
}

.detail-cards {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.detail-card {
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  border: 1px solid rgba(0, 0, 0, 0.05);
  overflow: hidden;
  transition: all 0.3s ease;
  
  &:hover {
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    transform: translateY(-1px);
  }
  
  .card-header {
    display: flex;
    align-items: center;
    padding: 12px 16px;
    background: linear-gradient(135deg, #fef8ff 0%, #f8faff 100%);
    border-bottom: 1px solid rgba(0, 0, 0, 0.05);
    
    .header-icon {
      width: 32px;
      height: 32px;
      border-radius: 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 16px;
      margin-right: 10px;
      
      &.basic {
        background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        color: white;
      }
      
      &.conversion {
        background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
        color: white;
      }
      
      &.system {
        background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
        color: #333;
      }
    }
    
    h3 {
      margin: 0;
      font-size: 14px;
      font-weight: 600;
      color: var(--color-text-1);
    }
  }
  
  .card-body {
    padding: 16px;
  }
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
  
  .info-item {
    display: flex;
    flex-direction: column;
    
    &.full-width {
      grid-column: 1 / -1;
    }
    
    .info-label {
      font-size: 11px;
      font-weight: 500;
      color: var(--color-text-3);
      text-transform: uppercase;
      letter-spacing: 0.5px;
      margin-bottom: 4px;
    }
    
    .info-value {
      font-size: 13px;
      color: var(--color-text-1);
      font-weight: 500;
      display: flex;
      align-items: center;
      gap: 4px;
      
      .icon {
        color: var(--color-text-3);
        font-size: 12px;
      }
      
      &.code {
        font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
        background: var(--color-fill-1);
        padding: 2px 6px;
        border-radius: 3px;
        font-size: 12px;
      }
      
      &.symbol {
        font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
        background: var(--color-primary-1);
        color: var(--color-primary-6);
        padding: 2px 6px;
        border-radius: 3px;
        font-size: 12px;
        font-weight: 600;
      }
      
      &.amount {
        font-size: 14px;
        font-weight: 700;
        color: var(--color-success-6);
      }
    }
  }
}

// 动画效果
.fade-in {
  opacity: 0;
  transform: translateY(15px);
  animation: fadeIn 0.5s ease forwards;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(15px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

// 响应式设计
@media (max-width: 768px) {
  .unit-header {
    flex-direction: column;
    text-align: center;
    padding: 12px 16px;
    
    .unit-avatar {
      margin-right: 0;
      margin-bottom: 12px;
    }
  }
  
  .info-grid {
    grid-template-columns: 1fr;
    
    .info-item.full-width {
      grid-column: 1;
    }
  }
  
  .unit-detail {
    padding: 12px;
  }
}
</style>