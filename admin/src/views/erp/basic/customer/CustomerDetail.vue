<template>
  <a-drawer
    :visible="props.visible"
    title="客户详情"
    width="800px"
    :footer="false"
    unmount-on-close
    @cancel="handleClose"
    class="customer-detail-drawer">
    
    <div v-if="loading" class="loading-container">
      <div class="loading-content">
        <a-spin :size="32" />
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
               {{ customerDetail.type === 1 ? '企业客户' : '个人客户' }}
             </a-tag>
             <a-tag 
               :color="customerDetail.level === 1 ? 'red' : customerDetail.level === 2 ? 'orange' : 'gray'"
               class="level-tag">
               <icon-star />
               {{ customerDetail.level === 1 ? 'VIP客户' : customerDetail.level === 2 ? '普通客户' : '潜在客户' }}
             </a-tag>
             <div class="status-tag">
              <GiCellStatus :status="customerDetail.status"></GiCellStatus>
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
                <div class="info-value">
                  <icon-phone class="icon" />
                  {{ customerDetail.contactPhone }}
                </div>
              </div>
              <div class="info-item">
                <div class="info-label">联系邮箱</div>
                <div class="info-value">
                  <icon-email class="icon" />
                  {{ customerDetail.contactEmail || '-' }}
                </div>
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
               <icon-star />
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
                   {{ customerDetail.remark }}
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
  IconUser,
  IconStar,
  IconSettings,
  IconLocation
} from '@arco-design/web-vue/es/icon'

interface Props {
  visible: boolean
  customerId?: string | number
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
    fetchCustomerDetail(String(newCustomerId))
  }
})

// 获取客户详情
const fetchCustomerDetail = async (customerId: string | number) => {
  try {
    loading.value = true
    const res = await customerAPI.getDetail({ id: String(customerId) })
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

.customer-detail {
  padding: 16px;
}

.customer-header {
  display: flex;
  align-items: center;
  padding: 16px 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  margin-bottom: 16px;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.2);
  color: white;
  
  .customer-avatar {
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
  
  .customer-info {
    flex: 1;
    
    .customer-name {
      margin: 0 0 8px 0;
      font-size: 22px;
      font-weight: 700;
      color: white;
    }
    
    .customer-meta {
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
        
        .currency {
          font-size: 12px;
          margin-right: 1px;
        }
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
      
      .number {
        font-weight: 700;
        color: var(--color-primary-6);
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
  .customer-header {
    flex-direction: column;
    text-align: center;
    padding: 12px 16px;
    
    .customer-avatar {
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
  
  .customer-detail {
    padding: 12px;
  }
}
</style>