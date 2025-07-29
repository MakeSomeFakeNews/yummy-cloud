<template>
  <a-drawer
    :visible="props.visible"
    title="客户详情"
    width="900px"
    :footer="false"
    unmount-on-close
    @cancel="handleClose"
    class="customer-detail-drawer">
    
    <div v-if="loading" class="loading-container">
      <div class="loading-content">
        <a-spin size="large" />
        <p class="loading-text">正在加载客户信息...</p>
      </div>
    </div>
    
    <div v-else-if="customerDetail" class="customer-detail">
      <!-- 客户头像和基本信息 -->
      <div class="customer-header">
        <div class="customer-avatar">
          <div class="avatar-circle">
            <icon-user />
          </div>
        </div>
        <div class="customer-info">
          <h2 class="customer-name">{{ customerDetail.name }}</h2>
          <div class="customer-meta">
            <a-tag :color="customerDetail.type === 1 ? 'blue' : 'green'" class="type-tag">
              <icon-office v-if="customerDetail.type === 1" />
              <icon-user v-else />
              {{ customerDetail.type === 1 ? '企业客户' : '个人客户' }}
            </a-tag>
            <a-tag 
              :color="customerDetail.level === 1 ? 'red' : customerDetail.level === 2 ? 'orange' : 'gray'"
              class="level-tag">
              <icon-star-fill v-if="customerDetail.level === 1" />
              <icon-star v-else />
              {{ customerDetail.level === 1 ? 'VIP客户' : customerDetail.level === 2 ? '普通客户' : '潜在客户' }}
            </a-tag>
            <GiCellStatus :status="customerDetail.status" class="status-tag"></GiCellStatus>
          </div>
        </div>
      </div>

      <!-- 快速操作栏 -->
      <div class="quick-actions">
        <div class="action-item" @click="callPhone(customerDetail.contactPhone)">
          <div class="action-icon phone">
            <icon-phone />
          </div>
          <span>拨打电话</span>
        </div>
        <div 
          v-if="customerDetail.contactEmail"
          class="action-item" 
          @click="sendEmail(customerDetail.contactEmail)">
          <div class="action-icon email">
            <icon-email />
          </div>
          <span>发送邮件</span>
        </div>
        <div class="action-item" @click="handleEdit">
          <div class="action-icon edit">
            <icon-edit />
          </div>
          <span>编辑客户</span>
        </div>
      </div>

      <!-- 详细信息卡片 -->
      <div class="detail-cards">
        <!-- 基本信息 -->
        <div class="detail-card fade-in" style="animation-delay: 0.1s">
          <div class="card-header">
            <div class="header-icon basic">
              <icon-idcard />
            </div>
            <h3>基本信息</h3>
          </div>
          <div class="card-body">
            <div class="info-grid">
              <div class="info-item">
                <div class="info-label">客户编码</div>
                <div class="info-value code">{{ customerDetail.code }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">客户名称</div>
                <div class="info-value">{{ customerDetail.name }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">简称</div>
                <div class="info-value">{{ customerDetail.shortName || '-' }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">所属行业</div>
                <div class="info-value">{{ customerDetail.industry || '-' }}</div>
              </div>
              <div class="info-item full-width">
                <div class="info-label">统一社会信用代码</div>
                <div class="info-value code">{{ customerDetail.creditCode || '-' }}</div>
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
                <div class="info-value">{{ customerDetail.contactPerson }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">联系电话</div>
                <div class="info-value clickable" @click="callPhone(customerDetail.contactPhone)">
                  <icon-phone class="icon" />
                  {{ customerDetail.contactPhone }}
                </div>
              </div>
              <div class="info-item">
                <div class="info-label">联系邮箱</div>
                <div 
                  v-if="customerDetail.contactEmail"
                  class="info-value clickable" 
                  @click="sendEmail(customerDetail.contactEmail)">
                  <icon-email class="icon" />
                  {{ customerDetail.contactEmail }}
                </div>
                <div v-else class="info-value">-</div>
              </div>
              <div class="info-item full-width">
                <div class="info-label">地址</div>
                <div class="info-value address">
                  <icon-location class="icon" />
                  {{ customerDetail.address || '-' }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 财务信息 -->
                 <div class="detail-card fade-in" style="animation-delay: 0.3s">
           <div class="card-header">
             <div class="header-icon finance">
               <icon-gift />
             </div>
             <h3>财务信息</h3>
           </div>
          <div class="card-body">
            <div class="info-grid">
              <div class="info-item">
                <div class="info-label">信用额度</div>
                <div class="info-value amount">
                  <span class="currency">¥</span>
                  {{ formatMoney(customerDetail.creditLimit) }}
                </div>
              </div>
              <div class="info-item">
                <div class="info-label">信用期限</div>
                <div class="info-value">
                  <span class="number">{{ customerDetail.creditDays }}</span> 天
                </div>
              </div>
              <div class="info-item">
                <div class="info-label">税率</div>
                <div class="info-value">
                  <span class="number">{{ (customerDetail.taxRate * 100).toFixed(1) }}</span>%
                </div>
              </div>
              <div class="info-item">
                <div class="info-label">付款条件</div>
                <div class="info-value">{{ customerDetail.paymentTerms || '-' }}</div>
              </div>
              <div class="info-item full-width">
                <div class="info-label">交货条件</div>
                <div class="info-value">{{ customerDetail.deliveryTerms || '-' }}</div>
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
                <div class="info-value">{{ formatDate(customerDetail.createTime) }}</div>
              </div>
              <div class="info-item">
                <div class="info-label">更新时间</div>
                <div class="info-value">{{ formatDate(customerDetail.updateTime) }}</div>
              </div>
              <div v-if="customerDetail.remark" class="info-item full-width">
                <div class="info-label">备注</div>
                <div class="info-value remark">
                  <icon-file-text class="icon" />
                  {{ customerDetail.remark }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 操作按钮 -->
      <div class="detail-actions">
        <a-button type="primary" size="large" @click="handleEdit" class="action-btn primary">
          <template #icon><icon-edit /></template>
          编辑客户
        </a-button>
        <a-button size="large" @click="handleClose" class="action-btn">
          关闭
        </a-button>
      </div>
    </div>

    <div v-else class="empty-container">
      <div class="empty-content">
        <icon-exclamation-circle class="empty-icon" />
        <h3>暂无数据</h3>
        <p>无法获取客户详细信息</p>
      </div>
    </div>
  </a-drawer>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { Message } from '@arco-design/web-vue'
import { customerAPI, type CustomerItem } from '@/apis/customer'
import { 
  IconPhone, 
  IconEmail, 
  IconEdit, 
  IconUser,
  IconOffice,
  IconStar,
  IconStarFill,
  IconIdcard,
  IconGift,
  IconSettings,
  IconLocation,
  IconFileText,
  IconExclamationCircle
} from '@arco-design/web-vue/es/icon'

interface Props {
  visible: boolean
  customerId?: string
}

interface Emits {
  (e: 'update:visible', value: boolean): void
  (e: 'edit', customer: CustomerItem): void
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  customerId: undefined
})

const emit = defineEmits<Emits>()

const loading = ref(false)
const customerDetail = ref<CustomerItem | null>(null)

// 监听弹窗显示状态和客户ID变化
watch([() => props.visible, () => props.customerId], ([newVisible, newCustomerId]) => {
  if (newVisible && newCustomerId) {
    fetchCustomerDetail(newCustomerId)
  }
})

// 获取客户详情
const fetchCustomerDetail = async (customerId: string) => {
  try {
    loading.value = true
    const res = await customerAPI.getDetail({ id: customerId })
    customerDetail.value = res.data
  } catch (error) {
    console.error('获取客户详情失败:', error)
    Message.error('获取客户详情失败')
    customerDetail.value = null
  } finally {
    loading.value = false
  }
}

// 格式化金额
const formatMoney = (amount: number): string => {
  if (!amount) return '0.00'
  return amount.toLocaleString('zh-CN', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  })
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

// 拨打电话
const callPhone = (phone: string) => {
  if (phone) {
    window.open(`tel:${phone}`)
  }
}

// 发送邮件
const sendEmail = (email: string) => {
  if (email) {
    window.open(`mailto:${email}`)
  }
}

// 编辑客户
const handleEdit = () => {
  if (customerDetail.value) {
    emit('edit', customerDetail.value)
    handleClose()
  }
}

// 关闭抽屉
const handleClose = () => {
  emit('update:visible', false)
}
</script>

<style lang="scss" scoped>
.customer-detail-drawer {
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
  height: 400px;
  
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
  height: 400px;
  
  .empty-content {
    text-align: center;
    
    .empty-icon {
      font-size: 48px;
      color: var(--color-text-4);
      margin-bottom: 16px;
    }
    
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

.customer-detail {
  padding: 24px;
}

.customer-header {
  display: flex;
  align-items: center;
  padding: 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 16px;
  margin-bottom: 24px;
  box-shadow: 0 8px 32px rgba(102, 126, 234, 0.3);
  color: white;
  
  .customer-avatar {
    margin-right: 20px;
    
    .avatar-circle {
      width: 80px;
      height: 80px;
      border-radius: 50%;
      background: rgba(255, 255, 255, 0.2);
      backdrop-filter: blur(10px);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 32px;
      color: white;
      border: 3px solid rgba(255, 255, 255, 0.3);
    }
  }
  
  .customer-info {
    flex: 1;
    
    .customer-name {
      margin: 0 0 12px 0;
      font-size: 28px;
      font-weight: 700;
      color: white;
    }
    
    .customer-meta {
      display: flex;
      gap: 12px;
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
          display: flex;
          align-items: center;
          gap: 4px;
        }
      }
    }
  }
}

.quick-actions {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
  gap: 16px;
  margin-bottom: 24px;
  
  .action-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
    background: white;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
    cursor: pointer;
    transition: all 0.3s ease;
    border: 1px solid rgba(0, 0, 0, 0.05);
    
    &:hover {
      transform: translateY(-4px);
      box-shadow: 0 8px 32px rgba(0, 0, 0, 0.12);
      
      .action-icon {
        transform: scale(1.1);
      }
    }
    
    .action-icon {
      width: 48px;
      height: 48px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 20px;
      margin-bottom: 12px;
      transition: all 0.3s ease;
      
      &.phone {
        background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
        color: white;
      }
      
      &.email {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
      }
      
      &.edit {
        background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        color: white;
      }
    }
    
    span {
      font-size: 14px;
      font-weight: 500;
      color: var(--color-text-2);
    }
  }
}

.detail-cards {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 32px;
}

.detail-card {
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  border: 1px solid rgba(0, 0, 0, 0.05);
  overflow: hidden;
  transition: all 0.3s ease;
  
  &:hover {
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.12);
    transform: translateY(-2px);
  }
  
  .card-header {
    display: flex;
    align-items: center;
    padding: 20px 24px;
    background: linear-gradient(135deg, #f8faff 0%, #f0f2ff 100%);
    border-bottom: 1px solid rgba(0, 0, 0, 0.05);
    
    .header-icon {
      width: 40px;
      height: 40px;
      border-radius: 10px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 18px;
      margin-right: 12px;
      
      &.basic {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
      }
      
      &.contact {
        background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
        color: white;
      }
      
      &.finance {
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
      font-size: 16px;
      font-weight: 600;
      color: var(--color-text-1);
    }
  }
  
  .card-body {
    padding: 24px;
  }
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  
  .info-item {
    display: flex;
    flex-direction: column;
    
    &.full-width {
      grid-column: 1 / -1;
    }
    
    .info-label {
      font-size: 12px;
      font-weight: 500;
      color: var(--color-text-3);
      text-transform: uppercase;
      letter-spacing: 0.5px;
      margin-bottom: 6px;
    }
    
    .info-value {
      font-size: 14px;
      color: var(--color-text-1);
      font-weight: 500;
      display: flex;
      align-items: center;
      gap: 6px;
      
      .icon {
        color: var(--color-text-3);
      }
      
      &.clickable {
        color: var(--color-primary-6);
        cursor: pointer;
        transition: all 0.2s ease;
        
        &:hover {
          color: var(--color-primary-5);
          text-decoration: underline;
        }
      }
      
      &.code {
        font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
        background: var(--color-fill-1);
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 13px;
      }
      
      &.amount {
        font-size: 16px;
        font-weight: 700;
        color: var(--color-success-6);
        
        .currency {
          font-size: 14px;
          margin-right: 2px;
        }
      }
      
      &.address {
        line-height: 1.4;
      }
      
      &.remark {
        background: var(--color-fill-1);
        padding: 12px;
        border-radius: 8px;
        line-height: 1.5;
        margin-top: 4px;
        border-left: 3px solid var(--color-primary-6);
      }
      
      .number {
        font-weight: 700;
        color: var(--color-primary-6);
      }
    }
  }
}

.detail-actions {
  display: flex;
  justify-content: center;
  gap: 16px;
  padding-top: 24px;
  border-top: 1px solid var(--color-border-2);
  
  .action-btn {
    min-width: 120px;
    height: 40px;
    border-radius: 8px;
    font-weight: 500;
    transition: all 0.3s ease;
    
    &.primary {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border: none;
      
      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 20px rgba(102, 126, 234, 0.3);
      }
    }
    
    &:not(.primary) {
      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      }
    }
  }
}

// 动画效果
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.fade-in {
  animation: fadeIn 0.6s ease forwards;
}

// 响应式设计
@media (max-width: 768px) {
  .customer-header {
    flex-direction: column;
    text-align: center;
    
    .customer-avatar {
      margin-right: 0;
      margin-bottom: 16px;
    }
  }
  
  .quick-actions {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .info-grid {
    grid-template-columns: 1fr;
    
    .info-item.full-width {
      grid-column: 1;
    }
  }
  
  .detail-actions {
    flex-direction: column;
  }
}
</style>