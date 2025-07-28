<template>
  <a-card title="操作日志" class="gi_page_card">
    <a-space wrap>
      <a-input v-model="queryParams.title" placeholder="操作模块" allow-clear style="width: 180px">
        <template #prefix><icon-search /></template>
      </a-input>
      <a-select v-model="queryParams.businessType" :options="businessTypeOptions" placeholder="业务类型" allow-clear
        style="width: 150px"></a-select>
      <a-input v-model="queryParams.operName" placeholder="操作人员" allow-clear style="width: 150px">
        <template #prefix><icon-user /></template>
      </a-input>
      <a-select v-model="queryParams.status" :options="statusOptions" placeholder="操作状态" allow-clear
        style="width: 120px"></a-select>
      <a-range-picker v-model="dateRange" style="width: 240px" />
      <a-button type="primary" @click="search">
        <template #icon><icon-search /></template>
        <span>查询</span>
      </a-button>
      <a-button @click="reset">
        <template #icon><icon-refresh /></template>
        <span>重置</span>
      </a-button>
    </a-space>

    <a-row style="margin-top: 16px;">
      <a-space wrap>
        <a-button type="primary" status="danger" :disabled="!hasPerm('sys:log:del')" @click="handleClean">
          <template #icon><icon-delete /></template>
          <span>清空</span>
        </a-button>
      </a-space>
    </a-row>

    <a-table class="gi_table" row-key="id" :data="tableData" :bordered="{ cell: true }" :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1300 }" :pagination="pagination">
      <template #columns>
        <a-table-column title="序号" :width="64">
          <template #cell="cell">{{ cell.rowIndex + 1 }}</template>
        </a-table-column>
        <a-table-column title="操作模块" data-index="title" :width="120"></a-table-column>
        <a-table-column title="业务类型" :width="120" align="center">
          <template #cell="{ record }">
            <a-tag :color="getBusinessTypeColor(record.businessType)">
              {{ getBusinessTypeName(record.businessType) }}
            </a-tag>
          </template>
        </a-table-column>
        <a-table-column title="请求方式" data-index="requestMethod" :width="100" align="center"></a-table-column>
        <a-table-column title="操作人员" data-index="operName" :width="120"></a-table-column>
        <a-table-column title="操作地址" data-index="operIp" :width="150"></a-table-column>
        <a-table-column title="操作地点" data-index="operLocation" :ellipsis="true" :tooltip="true" :width="200"></a-table-column>
        <a-table-column title="操作状态" :width="100" align="center">
          <template #cell="{ record }">
            <a-tag :color="record.status === 0 ? 'green' : 'red'">
              {{ record.status === 0 ? '正常' : '异常' }}
            </a-tag>
          </template>
        </a-table-column>
        <a-table-column title="操作日期" data-index="operTime" :width="180" :sortable="{ sortDirections: ['ascend', 'descend'] }"></a-table-column>
        <a-table-column title="操作" :width="120" align="center" :fixed="'right'">
          <template #cell="{ record }">
            <a-space>
              <a-button size="mini" type="primary" @click="handleDetail(record)">详情</a-button>
            </a-space>
          </template>
        </a-table-column>
      </template>
    </a-table>
    
    <!-- 详情弹窗 -->
    <a-modal v-model:visible="detailVisible" title="日志详情" width="1000px" :footer="false" class="log-detail-modal">
      <div class="detail-header">
        <a-descriptions :data="detailData" :column="{ xs: 1, sm: 2, md: 2 }" :label-style="{ 'font-weight': 'bold' }" bordered />
      </div>
      
      <div class="detail-section">
        <div class="section-title">
          <icon-link /> 请求信息
        </div>
        <a-descriptions :data="detailDataBasic" :column="1" :label-style="{ 'font-weight': 'bold' }" bordered />
      </div>
      
      <!-- 请求参数 -->
      <div class="detail-section" v-if="currentDetail?.operParam">
        <div class="section-title">
          <icon-code-block /> 请求参数
        </div>
        <div class="param-container">
          {{ formatRequestParam(currentDetail.operParam) }}
        </div>
      </div>
      
      <!-- 返回结果 -->
      <div class="detail-section" v-if="currentDetail?.jsonResult">
        <div class="section-title">
          <icon-code-square /> 返回结果
          <a-button class="expand-button" type="text" size="small" @click="expandCode('result')">
            <template #icon><icon-fullscreen /></template>全屏查看
          </a-button>
        </div>
        <div class="code-container">
          <GiCodeView :code-json="formatJson(currentDetail.jsonResult)" type="javascript"></GiCodeView>
        </div>
      </div>
      
      <!-- 错误消息 -->
      <div class="detail-section" v-if="currentDetail?.errorMsg">
        <div class="section-title">
          <icon-exclamation-circle /> 错误消息
        </div>
        <div class="code-container error-container">
          <GiCodeView :code-json="currentDetail.errorMsg" type="javascript"></GiCodeView>
        </div>
      </div>
      
      <template #title>
        <div class="flex items-center">
          <icon-file />
          <span class="ml-2">日志详情</span>
        </div>
      </template>
    </a-modal>
    
    <!-- 全屏代码查看弹窗 -->
    <a-modal v-model:visible="codeModalVisible" :title="codeModalTitle" fullscreen class="code-modal" :footer="false">
      <div class="fullscreen-code-container">
        <GiCodeView :code-json="fullscreenCode" type="javascript"></GiCodeView>
      </div>
    </a-modal>
  </a-card>
</template>

<script lang="ts" setup>
import { Message, Modal } from '@arco-design/web-vue'
import type { ListItem } from '@/apis/system/operLog'
import { baseAPI } from '@/apis/system/operLog'
import { hasPerm } from '@/utils/has'
import { usePagination } from '@/hooks'
import GiCodeView from '@/components/GiCodeView/index.vue'

defineOptions({ name: 'OperationLog' })

// 查询参数
const queryParams = reactive({
  title: '',
  businessType: undefined,
  operName: '',
  status: undefined
})

const dateRange = ref<[Date, Date] | undefined>()

// 业务类型选项
const businessTypeOptions = [
  { label: '其他', value: 0 },
  { label: '新增', value: 1 },
  { label: '修改', value: 2 },
  { label: '删除', value: 3 },
  { label: '授权', value: 4 },
  { label: '导出', value: 5 },
  { label: '导入', value: 6 },
  { label: '强退', value: 7 },
  { label: '生成代码', value: 8 },
  { label: '清空数据', value: 9 },
  { label: '查询', value: 10 }
]

// 状态选项
const statusOptions = [
  { label: '成功', value: 0 },
  { label: '失败', value: 1 }
]

// 表格数据和分页
const loading = ref(false)
const tableData = ref<ListItem[]>([])
const { pagination, setTotal } = usePagination(() => {
  getTableData()
})

// 获取表格数据
const getTableData = async () => {
  loading.value = true
  try {
    const params = {
      ...queryParams,
      page: pagination.current,
      size: pagination.pageSize,
      startTime: dateRange.value?.[0],
      endTime: dateRange.value?.[1]
    }
    const res = await baseAPI.getList(params)
    tableData.value = res.data.records || []
    setTotal(res.data.total || 0)
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

// 查询
const search = () => {
  pagination.onChange(1)
}

// 重置
const reset = () => {
  queryParams.title = ''
  queryParams.businessType = undefined
  queryParams.operName = ''
  queryParams.status = undefined
  dateRange.value = undefined
  search()
}

// 业务类型映射
const getBusinessTypeName = (type: number) => {
  const types = {
    0: '其他',
    1: '新增',
    2: '修改',
    3: '删除',
    4: '授权',
    5: '导出',
    6: '导入',
    7: '强退',
    8: '生成代码',
    9: '清空数据',
    10: '查询'
  }
  return types[type as keyof typeof types] || '未知'
}

// 业务类型颜色
const getBusinessTypeColor = (type: number) => {
  const colors = {
    0: 'gray',
    1: 'green',
    2: 'blue',
    3: 'red',
    4: 'orange',
    5: 'purple',
    6: 'pink',
    7: 'magenta',
    8: 'lime',
    9: 'gold',
    10: 'arcoblue'
  }
  return colors[type as keyof typeof colors] || 'gray'
}

// 清空日志
const handleClean = async () => {
  Modal.warning({
    title: '确认清空',
    content: '确定要清空所有操作日志吗？此操作不可恢复！',
    hideCancel: false,
    onOk: async () => {
      try {
        await baseAPI.clean()
        Message.success('日志清空成功')
        search()
      } catch (error) {
        Message.error('日志清空失败')
      }
    }
  })
}

// 详情相关
const detailVisible = ref(false)
const currentDetail = ref<ListItem | null>(null)
const detailData = computed(() => {
  if (!currentDetail.value) return []
  
  return [
    { label: '日志序号', value: currentDetail.value.id },
    { label: '操作模块', value: currentDetail.value.title },
    { label: '业务类型', value: getBusinessTypeName(currentDetail.value.businessType) },
    { label: '方法名称', value: currentDetail.value.method },
    { label: '请求方式', value: currentDetail.value.requestMethod },
    { label: '操作人员', value: currentDetail.value.operName },
    { label: '操作地址', value: currentDetail.value.operIp },
    { label: '操作地点', value: currentDetail.value.operLocation },
    { label: '操作状态', value: currentDetail.value.status === 0 ? '成功' : '失败' },
    { label: '操作时间', value: currentDetail.value.operTime }
  ]
})

const detailDataBasic = computed(() => {
  if (!currentDetail.value) return []
  
  return [
    { label: '操作URL', value: currentDetail.value.operUrl }
  ]
})

// 查看详情
const handleDetail = async (record: ListItem) => {
  try {
    // 因为后端直接返回了日志数据，无需再次请求详情
    // 直接使用表格行中的记录数据
    currentDetail.value = record
    detailVisible.value = true
  } catch (error) {
    Message.error('获取详情失败')
  }
}

// 全屏代码查看
const codeModalVisible = ref(false)
const codeModalTitle = ref('')
const fullscreenCode = ref('')

const expandCode = (type: 'params' | 'result') => {
  if (!currentDetail.value) return
  
  if (type === 'params') {
    codeModalTitle.value = '请求参数'
    fullscreenCode.value = formatJson(currentDetail.value.operParam)
  } else {
    codeModalTitle.value = '返回结果'
    fullscreenCode.value = formatJson(currentDetail.value.jsonResult)
  }
  
  codeModalVisible.value = true
}

// 格式化JSON字符串
const formatJson = (jsonString: string) => {
  if (!jsonString) return ''
  try {
    // 尝试解析JSON字符串
    const jsonObj = JSON.parse(jsonString)
    // 将对象格式化为漂亮的JSON字符串
    return JSON.stringify(jsonObj, null, 2)
  } catch (error) {
    console.error('JSON格式化失败:', error)
    // 如果解析失败，尝试转义字符串中的转义字符后再解析
    try {
      // 有时JSON字符串中的引号被转义了两次
      const fixedString = jsonString.replace(/\\"/g, '"').replace(/\\\\"/g, '\\"')
      const jsonObj = JSON.parse(fixedString)
      return JSON.stringify(jsonObj, null, 2)
    } catch (e) {
      // 如果仍然失败，返回原始字符串
      return jsonString
    }
  }
}

// 格式化请求参数
const formatRequestParam = (param: string) => {
  if (!param) return '无请求参数'
  try {
    // 尝试解析为JSON
    const jsonObj = JSON.parse(param)
    // 简单展示键值对
    let result = ''
    for (const [key, value] of Object.entries(jsonObj)) {
      result += `${key}: ${Array.isArray(value) ? value.join(', ') : value}\n`
    }
    return result || '无请求参数'
  } catch (error) {
    // 如果不是JSON，直接返回
    return param || '无请求参数'
  }
}

// 首次加载数据
onMounted(() => {
  getTableData()
})
</script>

<style lang="less" scoped>
.ml-2 {
  margin-left: 8px;
}
.flex {
  display: flex;
}
.items-center {
  align-items: center;
}
.code-container {
  background-color: #f5f5f5;
  padding: 10px;
  border-radius: 4px;
  overflow-x: auto;
}
.log-detail-modal {
  .arco-modal-body {
    padding: 20px;
  }
  .detail-header {
    margin-bottom: 20px;
  }
  .detail-section {
    margin-bottom: 20px;
    border: 1px solid #e9e9e9;
    border-radius: 4px;
    overflow: hidden;
    
    .section-title {
      display: flex;
      align-items: center;
      padding: 12px 16px;
      background-color: #fafafa;
      border-bottom: 1px solid #e9e9e9;
      font-weight: 500;
      
      .arco-icon {
        margin-right: 8px;
        font-size: 16px;
      }
      .expand-button {
        margin-left: auto;
        color: #2080f0;
      }
    }
    .code-container {
      height: 400px; /* 增加高度 */
      max-height: 70vh; /* 限制最大高度 */
      overflow-y: auto; /* 允许滚动 */
      background-color: #f5f7fa;
      padding: 16px;
    }
    .error-container {
      background-color: #fff1f0; /* 浅红色背景 */
      border: 1px solid #ffccc7; /* 浅红色边框 */
    }
    .param-container {
      white-space: pre-wrap; /* 保留换行和空格 */
      word-break: break-all; /* 允许在单词内换行 */
      font-family: monospace; /* 设置等宽字体 */
      font-size: 14px;
      line-height: 1.5;
      padding: 16px;
      background-color: #f5f5f5;
      height: auto;
      min-height: 100px;
      max-height: 300px;
      overflow-y: auto;
    }
    
    .arco-descriptions {
      padding: 16px;
    }
  }
}
.code-modal {
  .arco-modal-body {
    padding: 0; /* 移除内边距，因为全屏 */
  }
  .fullscreen-code-container {
    height: 100%;
    padding: 20px;
    overflow-y: auto;
  }
}
</style>
