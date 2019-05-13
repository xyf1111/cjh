<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.shopName" placeholder="请输入店铺名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.style" placeholder="请输入宝贝款号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button  type="primary" @click="getDataList()" >查询</el-button>
        <el-button  type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
      <div style="display:inline-block;color:red;padding:10px 10px;vertical-align:top">
        声明：请各位老板发布任务时认真审查，出现错误请及时撤回或联系客服，<br/>如有错误订单交易成功后概不负责。
      </div>
      <div class="warningDiv" :style="warningDiv"></div>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="index"
        header-align="center"
        align="center"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="模版编号">
      </el-table-column>
      <el-table-column
        prop="shopName"
        header-align="center"
        align="center"
        label="店铺名称">
      </el-table-column>
      <el-table-column
        prop="style"
        header-align="center"
        align="center"
        label="宝贝款号">
      </el-table-column>
      <el-table-column
        prop="price"
        header-align="center"
        align="center"
        label="宝贝价格（元）">
      </el-table-column>
      <el-table-column
        prop="weight"
        header-align="center"
        align="center"
        label="宝贝货重（千克）">
      </el-table-column>
      <el-table-column
        prop="keyword"
        header-align="center"
        align="center"
        label="商品关键字">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="200"
        label="操作">
        <template slot-scope="scope">
          <div>
            <el-button type="success" size="small" @click="addTask(scope.row.id)">发布任务</el-button>
            <el-button type="primary" size="small" @click="addOrUpdateHandle(scope.row.id)">编辑模板</el-button>
          </div>
          <div style="margin-top: 5px;">
            <el-button type="primary" size="small" @click="toDetail(scope.row.id)">查看模板</el-button>
            <el-button type="danger" size="small" @click="deleteHandle(scope.row.id)">删除模板</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100, 500, 1000]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <Detail v-if="detailVisible" ref="detail" @refreshDataList="getDataList"></Detail>
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <add-task v-if="addTaskVisible" ref="addTask" @refreshDataList="getDataList"></add-task>
  </div>
</template>

<script>
import AddOrUpdate from './taskmould-add-or-update'
import AddTask from './add-task'
import Detail from './detail'
export default {
  data () {
    return {
      warningDiv: {
        display: 'inline-block',
        width: '70px',
        height: '70px',
        backgroundImage: 'url(' + require('@/assets/zhaocai.png') + ')',
        backgroundSize: '100% 100%',
        backgroundRepeat: ' no-repeat'
      },
      dataForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      detailVisible: false,
      addTaskVisible: false
    }
  },
  components: {
    AddOrUpdate,
    Detail,
    AddTask
  },
  activated () {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList () {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/app/seller/taskMould/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'shopName': this.dataForm.shopName,
          'style': this.dataForm.style
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list
          this.totalPage = data.page.totalCount
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle (val) {
      this.dataListSelections = val
    },
    // 新增 / 修改
    addOrUpdateHandle (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    // 发布任务
    addTask (id) {
      this.addTaskVisible = true
      this.$nextTick(() => {
        this.$refs.addTask.init(id)
      })
    },
    // 详情
    toDetail (id) {
      this.detailVisible = true
      this.$nextTick(() => {
        this.$refs.detail.init(id)
      })
    },
    // 删除
    deleteHandle (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/app/seller/taskMould/delete'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    }
  }
}
</script>

