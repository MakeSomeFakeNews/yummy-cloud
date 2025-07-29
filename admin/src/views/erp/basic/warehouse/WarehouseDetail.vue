<template>
  <a-drawer
    :visible="props.visible"
    title="仓库详情"
    width="800px"
    :footer="false"
    unmount-on-close
    @cancel="handleClose"
    class="warehouse-detail-drawer">
    
    <div v-if="loading" class="loading-container">
      <div class="loading-content">
        <a-spin :size="32" />
        <p class="loading-text">正在加载仓库信息...</p>
      </div>
    </div>
    
    <div v-else-if="warehouseDetail" class="warehouse-detail">
      <!-- 仓库头像和基本信息 -->
      <div class="warehouse-header">
        <div class="warehouse-avatar">
          <div class="avatar-circle">
            <icon-archive />
          </div>
        </div>
        <div class="warehouse-info">
          <h2 class="warehouse-name">{{ warehouseDetail.name }}</h2>
          <div class="warehouse-meta">
            <a-tag class="type-tag">
              {{ getTypeName(warehouseDetail.type) }}
            </a-tag>
            <div class="status-tag">
              <GiCellStatus :status="warehouseDetail.status"></GiCellStatus>
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
              <icon-archive />
            </div>
            <h3>基本信息</h3>
          </div>
          <div class="card-body">
            <div class="info-grid">
              <div class="info-item">
                <div class="info-label">仓库编码</div>
                <div class="info-value code">{{ warehouseDetail.code }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">仓库名称</div>
                <div class="info-value">{{ warehouseDetail.name }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">仓库类型</div>
                <div class="info-value">{{ getTypeName(warehouseDetail.type) }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">仓库管理员</div>
                <div class="info-value">{{ warehouseDetail.manager }}</div>
              </div>
              <div class="info-item full-width">
                <div class="info-label">仓库地址</div>
                <div class="info-value address">
                  <icon-location class="icon" />
                  {{ warehouseDetail.address || '-' }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 联系信息 -->
        <div class="detail-card fade-in" style="animation-delay: 0.2s">
          <div class="card-header">
            <div class="header-icon contact">
              <icon-phone />
            </div>
            <h3>联系信息</h3>
          </div>
          <div class="card-body">
            <div class="info-grid">
              <div class="info-item">
                <div class="info-label">联系电话</div>
                <div class="info-value">
                  <icon-phone class="icon" />
                  {{ warehouseDetail.phone }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 容量信息 -->
        <div class="detail-card fade-in" style="animation-delay: 0.3s">
          <div class="card-header">
            <div class="header-icon capacity">
              <icon-apps />
            </div>
            <h3>容量信息</h3>
          </div>
          <div class="card-body">
            <div class="info-grid">
              <div class="info-item">
                <div class="info-label">仓库面积</div>
                <div class="info-value amount">{{ warehouseDetail.area }} ㎡</div>
              </div>
              <div class="info-item">
                <div class="info-label">存储容量</div>
                <div class="info-value amount">{{ warehouseDetail.capacity }}</div>
              </div>
            </div>
          </div>
        </div>

        <!-- 系统信息 -->
        <div class="detail-card fade-in" style="animation-delay: 0.4s">
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
                <div class="info-value">{{ formatDate(warehouseDetail.createTime) }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">更新时间</div>
                <div class="info-value">{{ formatDate(warehouseDetail.updateTime) }}</div>
              </div>
              <div v-if="warehouseDetail.remark" class="info-item full-width">
                <div class="info-label">备注</div>
                <div class="info-value remark">
                  {{ warehouseDetail.remark }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>

    <div v-else class="error-container">
      <div class="error-content">
        <icon-exclamation-circle-fill class="error-icon" />
        <h3>仓库信息加载失败</h3>
        <p>请检查网络连接后重试</p>
        <a-button type="primary" @click="fetchWarehouseDetail">重新加载</a-button>
      </div>
    </div>
  </a-drawer>
</template>

<script setup lang="ts">
import { ref, watch, computed } from 'vue'
import { Message } from '@arco-design/web-vue'
import { warehouseAPI, type WarehouseItem } from '@/apis/warehouse'
import { useDict } from '@/hooks/app'
import {
  IconArchive,
  IconPhone,
  IconApps,
  IconFile,
  IconClockCircle,
  IconEdit,
  IconExclamationCircleFill,
  IconLocation
} from '@arco-design/web-vue/es/icon'

interface Props {
  visible: boolean
  warehouseId?: string
}

interface Emits {
  (e: 'update:visible', value: boolean): void
  (e: 'edit', warehouse: WarehouseItem): void
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  warehouseId: undefined
})

const emit = defineEmits<Emits>()

const { data: warehouseTypeOptions } = useDict({ code: 'warehouse' })

const loading = ref(false)
const warehouseDetail = ref<WarehouseItem | null>(null)

// 获取仓库类型名称
const getTypeName = (type: string | number) => {
  const option = warehouseTypeOptions.value?.find(opt => opt.value == type)
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

// 获取仓库详情
const fetchWarehouseDetail = async () => {
  if (!props.warehouseId) return

  try {
    loading.value = true
    const response = await warehouseAPI.getDetail({ id: props.warehouseId })
    warehouseDetail.value = response.data
  } catch (error) {
    console.error('获取仓库详情失败:', error)
    Message.error('获取仓库详情失败，请稍后重试')
    warehouseDetail.value = null
  } finally {
    loading.value = false
  }
}

// 监听弹窗状态和仓库ID变化
watch([() => props.visible, () => props.warehouseId], ([visible, warehouseId]) => {
  if (visible && warehouseId) {
    fetchWarehouseDetail()
  }
}, { immediate: true })

const handleClose = () => {
  emit('update:visible', false)
  warehouseDetail.value = null
}

const handleEdit = () => {
  if (warehouseDetail.value) {
    emit('edit', warehouseDetail.value)
    handleClose()
  }
}
</script>

<style lang="scss" scoped>
.warehouse-detail-drawer {
  :deep(.arco-drawer-header) {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
    background: linear-gradient(to bottom, #f8faff 0%, #ffffff 100%);
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

.warehouse-detail {
  padding: 16px;
}

.warehouse-header {
  display: flex;
  align-items: center;
  padding: 16px 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  margin-bottom: 16px;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.2);
  color: white;
  
  .warehouse-avatar {
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
  
  .warehouse-info {
    flex: 1;
    
    .warehouse-name {
      margin: 0 0 8px 0;
      font-size: 22px;
      font-weight: 700;
      color: white;
    }
    
    .warehouse-meta {
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
    background: linear-gradient(135deg, #f8faff 0%, #f0f2ff 100%);
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
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
      }
      
      &.contact {
        background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
        color: white;
      }
      
      &.capacity {
        background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
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
      
      &.amount {
        font-size: 14px;
        font-weight: 700;
        color: var(--color-success-6);
      }
      
      &.address {
        line-height: 1.3;
      }
      
      &.remark {
        background: var(--color-fill-1);
        padding: 8px 10px;
        border-radius: 6px;
        line-height: 1.4;
        margin-top: 2px;
        border-left: 2px solid var(--color-primary-6);
        font-size: 12px;
      }
    }
  }
}

.action-footer {
  margin-top: 16px;
  padding: 12px 16px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  border: 1px solid rgba(0, 0, 0, 0.05);
  display: flex;
  gap: 12px;
  justify-content: center;
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
  .warehouse-header {
    flex-direction: column;
    text-align: center;
    padding: 12px 16px;
    
    .warehouse-avatar {
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
  
  .warehouse-detail {
    padding: 12px;
  }
}
</style> 