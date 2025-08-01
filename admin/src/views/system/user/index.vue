<template>
  <a-card title="用户管理" class="gi_page_card">
    <GiSplitPane>
      <template #left>
        <div class="gi_full_column">
          <a-scrollbar style="height: 100%; overflow: auto" outer-style="height: 100%">
            <a-tree ref="treeRef" block-node show-line default-expand-all :data="deptList" :field-names="{
              key: 'id',
              title: 'name',
              children: 'children',
            }" @select="onDeptSelect">
            </a-tree>
          </a-scrollbar>
        </div>
      </template>

      <a-row justify="space-between">
        <a-space wrap>
          <a-button type="primary" @click="onAdd">
            <template #icon><icon-plus /></template>
            <span>新增</span>
          </a-button>

          <a-button type="primary" status="danger" @click="onMulDelete">
            <template #icon><icon-delete /></template>
            <span>删除</span>
          </a-button>
        </a-space>

        <a-space wrap>
          <a-input-group>
            <a-select v-model="queryParams.status" :options="options" placeholder="用户状态" allow-clear
              style="width: 150px"></a-select>
            <a-input v-model="queryParams.username" placeholder="输入用户名搜索" allow-clear style="max-width: 250px">
            </a-input>
          </a-input-group>
          <a-button type="primary" @click="search">
            <template #icon><icon-search /></template>
            <span>查询</span>
          </a-button>
          <a-button @click="reset">
            <template #icon><icon-refresh /></template>
            <span>重置</span>
          </a-button>
        </a-space>
      </a-row>

      <a-table class="gi_table" row-key="id" :loading="loading" :data="userList" :bordered="{ cell: true }"
        :scroll="{ x: '100%', y: '100%', minWidth: 1700 }" :pagination="pagination"
        :row-selection="{ type: 'checkbox', showCheckedAll: true }" :selected-keys="selectedKeys" @select="select"
        @select-all="selectAll">
        <template #columns>
          <a-table-column title="序号" :width="64">
            <template #cell="cell">{{ cell.rowIndex + 1 }}</template>
          </a-table-column>
          <a-table-column title="用户名" data-index="username" :width="120">
            <template #cell="{ record }">
              <a-link @click="onDetail(record)">{{ record.username }}</a-link>
            </template>
          </a-table-column>
          <a-table-column title="昵称" data-index="nickname" :width="150">
            <template #cell="{ record }">
              <GiCellAvatar :avatar="record.avatar" :name="record.nickname"></GiCellAvatar>
            </template>
          </a-table-column>
          <a-table-column title="状态" :width="100" align="center">
            <template #cell="{ record }">
              <GiCellStatus :status="record.status"></GiCellStatus>
            </template>
          </a-table-column>
          <a-table-column title="性别" data-index="gender" :width="80" align="center">
            <template #cell="{ record }">
              <GiCellGender :gender="record.gender"></GiCellGender>
            </template>
          </a-table-column>
          <a-table-column title="联系方式" data-index="phone" :width="180"></a-table-column>
          <a-table-column title="部门" data-index="deptName" :width="180"></a-table-column>
          <a-table-column title="类型" :width="100" align="center">
            <template #cell="{ record }">
              <a-tag v-if="record.type === 1" color="red" size="small">系统内置</a-tag>
              <a-tag v-if="record.type === 2" color="orange" size="small">自定义</a-tag>
            </template>
          </a-table-column>
          <a-table-column title="描述" :width="200" data-index="description" :ellipsis="true"
            :tooltip="true"></a-table-column>
          <a-table-column title="创建时间" data-index="createTime" :width="200"></a-table-column>
          <a-table-column title="操作" :width="180" align="center" :fixed="fixed">
            <template #cell="{ record }">
              <a-space>
                <a-button type="primary" size="mini" @click="onEdit(record)">
                  <template #icon><icon-edit /></template>
                  <span>编辑</span>
                </a-button>
                <a-popconfirm type="warning" content="确定删除该用户吗?" @before-ok="onDelete(record)">
                  <a-button type="primary" status="danger" size="mini" :disabled="record.disabled">
                    <template #icon><icon-delete /></template>
                    <span>删除</span>
                  </a-button>
                </a-popconfirm>
              </a-space>
            </template>
          </a-table-column>
        </template>
      </a-table>
    </GiSplitPane>

    <AddUserModal ref="AddUserModalRef" @save-success="search"></AddUserModal>
    <UserDetailDrawer ref="UserDetailDrawerRef"></UserDetailDrawer>
  </a-card>
</template>

<script setup lang="ts">
import {Message} from '@arco-design/web-vue'
import AddUserModal from './AddUserModal.vue'
import UserDetailDrawer from './UserDetailDrawer.vue'
import {useTable} from '@/hooks'
import {useDept, useDict} from '@/hooks/app'
import type * as T from '@/apis/system/user'
import {baseAPI} from '@/apis/system/user'

defineOptions({ name: 'SystemUser' })

const { data: options } = useDict({ code: 'status' })
const treeRef = useTemplateRef('treeRef')
const AddUserModalRef = useTemplateRef('AddUserModalRef')
const UserDetailDrawerRef = useTemplateRef('UserDetailDrawerRef')
const treeInputValue = ref('')

const { deptList, getDeptList } = useDept({
  onSuccess: () => {
    nextTick(() => {
      treeRef.value?.expandAll(true)
    })
  }
})
getDeptList()

const queryParams = reactive({ status: '', username: '', deptId: null as string | null })

const {
  loading,
  tableData: userList,
  pagination,
  selectedKeys,
  search,
  select,
  selectAll,
  fixed,
  handleDelete
} = useTable((page) => baseAPI.getList({ ...page, ...queryParams }), { immediate: true })

const reset = () => {
  queryParams.status = ''
  queryParams.username = ''
  queryParams.deptId = null
  // 清空部门树选择
  treeRef.value?.selectNode([], false)
  search()
}

const onAdd = () => {
  AddUserModalRef.value?.add()
}

const onEdit = (item: T.ListItem) => {
  AddUserModalRef.value?.edit(item.id)
}

const onDelete = (item: T.ListItem) => {
  return handleDelete(() => baseAPI.delete({ ids: [item.id] }), { showModal: false })
}

// 批量删除
const onMulDelete = () => {
  if (!selectedKeys.value.length) {
    return Message.warning('请选择用户！')
  }
  return handleDelete(() => baseAPI.delete({ ids: selectedKeys.value as string[] }))
}

const onDetail = (item: T.ListItem) => {
  UserDetailDrawerRef.value?.open(item.id)
}

// 处理部门选择
const onDeptSelect = (selectedKeys: string[]) => {
  // 获取选中的第一个部门ID
  queryParams.deptId = selectedKeys.length > 0 ? selectedKeys[0] : null
  search()
}
</script>

<style lang="scss" scoped></style>
