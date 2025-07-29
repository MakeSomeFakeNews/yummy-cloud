<template>
  <a-drawer
    :visible="props.visible"
    title="供应商详情"
    width="800px"
    :footer="false"
    unmount-on-close
    @cancel="handleClose"
    class="supplier-detail-drawer">
    
    <div v-if="loading" class="loading-container">
      <div class="loading-content">
        <a-spin :size="32" />
        <p class="loading-text">正在加载供应商信息...</p>
      </div>
    </div>
    
    <div v-else-if="supplierData" class="supplier-detail">
      <!-- 供应商头像和基本信息 -->
      <div class="supplier-header">
        <div class="supplier-avatar">
          <div class="avatar-circle">
            <icon-user />
          </div>
        </div>
        <div class="supplier-info">
          <h2 class="supplier-name">{{ supplierData.name }}</h2>
          <div class="supplier-meta">
            <a-tag :color="getTypeColor(supplierData.type)" class="type-tag">
              {{ supplierTypeOptions?.find(opt => opt.value == supplierData.type)?.label || '-' }}
            </a-tag>
            <a-tag 
              :color="getLevelColor(supplierData.level)"
              class="level-tag">
              <icon-star />
              {{ supplierLevelOptions?.find(opt => opt.value == supplierData.level)?.label || '-' }}
            </a-tag>
            <div class="status-tag">
              <GiCellStatus :status="supplierData.status"></GiCellStatus>
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
              <icon-user />
            </div>
            <h3>基本信息</h3>
          </div>
          <div class="card-body">
            <div class="info-grid">
              <div class="info-item">
                <div class="info-label">供应商编码</div>
                <div class="info-value code">{{ supplierData.code }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">供应商名称</div>
                <div class="info-value">{{ supplierData.name }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">简称</div>
                <div class="info-value">{{ supplierData.shortName || '-' }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">统一社会信用代码</div>
                <div class="info-value code">{{ supplierData.creditCode || '-' }}</div>
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
                <div class="info-label">联系人</div>
                <div class="info-value">{{ supplierData.contactPerson || '-' }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">联系电话</div>
                <div class="info-value">
                  <icon-phone class="icon" />
                  {{ supplierData.contactPhone || '-' }}
                </div>
              </div>
              <div class="info-item">
                <div class="info-label">联系邮箱</div>
                <div class="info-value">
                  <icon-settings class="icon" />
                  {{ supplierData.contactEmail || '-' }}
                </div>
              </div>
              <div class="info-item full-width">
                <div class="info-label">地址</div>
                <div class="info-value address">
                  <icon-star class="icon" />
                  {{ supplierData.address || '-' }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 商务信息 -->
        <div class="detail-card fade-in" style="animation-delay: 0.3s">
          <div class="card-header">
            <div class="header-icon finance">
              <icon-star />
            </div>
            <h3>商务信息</h3>
          </div>
          <div class="card-body">
            <div class="info-grid">
              <div class="info-item">
                <div class="info-label">开户银行</div>
                <div class="info-value">{{ supplierData.bankName || '-' }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">银行账户</div>
                <div class="info-value code">{{ supplierData.bankAccount || '-' }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">税率</div>
                <div class="info-value">
                  <span class="number">{{ formatTaxRate(supplierData.taxRate) }}</span>
                </div>
              </div>
              <div class="info-item">
                <div class="info-label">付款条件</div>
                <div class="info-value">{{ supplierData.paymentTerms || '-' }}</div>
              </div>
              <div class="info-item full-width">
                <div class="info-label">交货条件</div>
                <div class="info-value">{{ supplierData.deliveryTerms || '-' }}</div>
              </div>
            </div>
          </div>
        </div>

        <!-- 系统信息 -->
        <div class="detail-card fade-in" style="animation-delay: 0.4s">
          <div class="card-header">
            <div class="header-icon system">
              <icon-settings />
            </div>
            <h3>系统信息</h3>
          </div>
          <div class="card-body">
            <div class="info-grid">
              <div class="info-item">
                <div class="info-label">创建时间</div>
                <div class="info-value">{{ formatDate(supplierData.createTime) }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">更新时间</div>
                <div class="info-value">{{ formatDate(supplierData.updateTime) }}</div>
              </div>
              <div v-if="supplierData.remark" class="info-item full-width">
                <div class="info-label">备注</div>
                <div class="info-value remark">
                  {{ supplierData.remark }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-else class="empty-container">
      <div class="empty-content">
        <h3>暂无数据</h3>
        <p>无法获取供应商详细信息</p>
      </div>
    </div>
  </a-drawer>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { supplierAPI, type SupplierItem } from '@/apis/supplier'
import { useDict } from '@/hooks/app'
import { Message } from '@arco-design/web-vue'
import { 
  IconEdit,
  IconClose,
  IconUser,
  IconPhone,
  IconStar,
  IconSettings
} from '@arco-design/web-vue/es/icon'

interface Props {
  visible: boolean
  supplierId?: string | number
}

interface Emits {
  (e: 'update:visible', value: boolean): void
  (e: 'edit', supplier: SupplierItem): void
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  supplierId: undefined
})

const emit = defineEmits<Emits>()

const { data: statusOptions } = useDict({ code: 'status' })
const { data: supplierTypeOptions } = useDict({ code: 'supType' })
const { data: supplierLevelOptions } = useDict({ code: 'supLevel' })

const loading = ref(false)
const supplierData = ref<SupplierItem | null>(null)

// 监听弹窗显示状态和供应商ID变化
watch([() => props.visible, () => props.supplierId], ([newVisible, newSupplierId]) => {
  if (newVisible && newSupplierId) {
    fetchSupplierDetail()
  }
})

// 获取供应商详情
const fetchSupplierDetail = async () => {
  if (!props.supplierId) return
  
  try {
    loading.value = true
    const response = await supplierAPI.getDetail({ id: String(props.supplierId) })
    supplierData.value = response.data
  } catch (error) {
    console.error('获取供应商详情失败:', error)
    Message.error('获取供应商详情失败')
    supplierData.value = null
  } finally {
    loading.value = false
  }
}

// 格式化税率
const formatTaxRate = (rate: number): string => {
  if (!rate) return '-'
  return `${(rate * 100).toFixed(1)}%`
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

// 获取供应商类型颜色
const getTypeColor = (type: number): string => {
  const colorMap: Record<number, string> = {
    1: 'blue',    // 生产商
    2: 'green',   // 贸易商
    3: 'orange'   // 服务商
  }
  return colorMap[type] || 'gray'
}

// 获取供应商级别颜色
const getLevelColor = (level: number): string => {
  const colorMap: Record<number, string> = {
    1: 'red',     // A级
    2: 'orange',  // B级
    3: 'gray'     // C级
  }
  return colorMap[level] || 'gray'
}

// 关闭抽屉
const handleClose = () => {
  emit('update:visible', false)
}
</script>

<style lang="scss" scoped>
.supplier-detail-drawer {
  :deep(.arco-drawer-header) {
    background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
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
    background: linear-gradient(to bottom, #f0fff4 0%, #ffffff 100%);
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

.empty-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
  
  .empty-content {
    text-align: center;
    
    h3 {
      margin: 0 0 8px 0;
      color: var(--color-text-2);
      font-size: 16px;
    }
    
    p {
      margin: 0;
      color: var(--color-text-3);
      font-size: 14px;
    }
  }
}

.supplier-detail {
  padding: 16px;
}

.supplier-header {
  display: flex;
  align-items: center;
  padding: 16px 20px;
  background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
  border-radius: 12px;
  margin-bottom: 16px;
  box-shadow: 0 4px 20px rgba(17, 153, 142, 0.2);
  color: white;
  
  .supplier-avatar {
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
  
  .supplier-info {
    flex: 1;
    
    .supplier-name {
      margin: 0 0 8px 0;
      font-size: 22px;
      font-weight: 700;
      color: white;
    }
    
    .supplier-meta {
      display: flex;
      gap: 8px;
      flex-wrap: wrap;
      
      .type-tag,
      .level-tag,
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
    background: linear-gradient(135deg, #f0fff4 0%, #f0fdf4 100%);
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
        background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
        color: white;
      }
      
      &.contact {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
      }
      
      &.finance {
        background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        color: white;
      }
      
      &.system {
        background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%);
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
      
      &.address {
        line-height: 1.3;
      }
      
      &.remark {
        background: var(--color-fill-1);
        padding: 8px 10px;
        border-radius: 6px;
        line-height: 1.4;
        margin-top: 2px;
        border-left: 2px solid var(--color-success-6);
        font-size: 12px;
      }
      
      .number {
        font-weight: 700;
        color: var(--color-success-6);
      }
    }
  }
}

// 动画效果
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

.fade-in {
  animation: fadeIn 0.5s ease forwards;
}

// 响应式设计
@media (max-width: 768px) {
  .supplier-header {
    flex-direction: column;
    text-align: center;
    padding: 12px 16px;
    
    .supplier-avatar {
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
  
  .supplier-detail {
    padding: 12px;
  }
}
</style> 