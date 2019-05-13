<template>
  <div class="mod-config">
    <el-form  :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="name" placeholder="请输入打款人姓名"></el-input>
      </el-form-item>
      <el-form-item>
         <el-button @click="getDataList()" icon="el-icon-search" type="success">查询</el-button>
      </el-form-item>
      <br>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="待审核" name="1"></el-tab-pane>
        <el-tab-pane label="已审核" name="2"></el-tab-pane>
        <el-tab-pane label="已拒绝" name="3"></el-tab-pane>
      </el-tabs>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="充值编号">
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="打款人姓名">
      </el-table-column>
      <el-table-column
        prop="img"
        header-align="center"
        align="center"
        label="打款截图">
        <template slot-scope="scope">
          <img :src=scope.row.img style="width:70px;height:70px;"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="bank"
        header-align="center"
        align="center"
        label="汇款银行">
      </el-table-column>
      <el-table-column
        prop="note"
        header-align="center"
        align="center"
        label="平台留言">
      </el-table-column>
      <el-table-column
        prop="mistakeNote"
        header-align="center"
        align="center"
        label="拒绝备注"
        v-if="status === 3">
      </el-table-column>
      <el-table-column
        prop="amount"
        header-align="center"
        align="center"
        label="金额">
         <template slot-scope="scope">
          {{scope.row.amount.toFixed(2)}}
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="创建时间">
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
  </div>
</template>

<script>
  export default {
    name: 'detail',
    data() {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        status:'',
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        activeName: '1',
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        name: ''
      }
    },
    activated() {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList() {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/app/seller/charge/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'name': this.name,
            'status': this.status
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
      sizeChangeHandle(val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle(val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle(val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle(id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      handleClick() {
        this.pageIndex = 1
        this.pageSize = 10
        if(this.activeName === '1'){
            this.status = 1
        }else if(this.activeName === '2'){
          this.status = 2
        }else if(this.activeName === '3'){
          this.status = 3
        }
        this.getDataList()
      }
    }
  }
</script>
