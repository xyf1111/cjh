<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.name" placeholder="请输入店铺名称" ></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.category" placeholder="请输入店铺类目" ></el-input>
      </el-form-item>
      <el-form-item>
          <el-button @click="getDataList()" type="success" icon="el-icon-search">查询</el-button>
          <el-button type="primary" @click="reset()">重置</el-button>
      </el-form-item>
      <el-form-item>
          <el-button type="primary" @click="addOrUpdateHandle()">添加店铺</el-button><br>
      </el-form-item>
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
        prop="name"
        header-align="center"
        align="center"
        label="店铺名称">
      </el-table-column>
      <el-table-column
        prop="platform"
        header-align="center"
        align="center"
        label="平台类型">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.platform === 1">淘宝</el-tag>
          <el-tag type="danger" v-if="scope.row.platform === 2">天猫</el-tag>
          <el-tag type="warning" v-if="scope.row.platform === 3">京东</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="category"
        header-align="center"
        align="center"
        label="类目">
      </el-table-column>
      <el-table-column
        prop="url"
        header-align="center"
        align="center"
        label="店铺链接">
           <template slot-scope="scope">
              <el-button type="text" size="small" @click="toLink(scope.row.url)">跳转链接</el-button><br>
           </template>
      </el-table-column>
      <el-table-column
        prop="address"
        header-align="center"
        align="center"
        label="发货地址">
      </el-table-column>
      <el-table-column
        prop="note"
        header-align="center"
        align="center"
        label="备注">
      </el-table-column>
      <el-table-column
        prop="roleStr"
        header-align="center"
        align="center"
        label="创建时间（状态）">
         <template slot-scope="scope">
            <div>{{scope.row.createTime}}</div>
           <div> {{scope.row.roleStr}}</div>
          </template>
      </el-table-column>
      <!--<el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="200"
        label="操作">
        <template slot-scope="scope">
          <div>
            <el-button type="danger" size="small" @click="deleteHandle(scope.row.id)" v-if="scope.row.role === 1 || scope.row.role === 4">删除店铺</el-button>
          </div>
          <div style="margin-top: 5px;">
              <el-button type="primary" size="small" @click="updateRole(scope.row.id,2)"  v-if="scope.row.role === 1 || scope.row.role === 4">提交审核</el-button>
          </div>
        </template>
      </el-table-column>-->
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
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from './shop-add-or-update'

export default {
  data () {
    return {
      options: [{
        value: '1',
        label: '淘宝'
      }, {
        value: '2',
        label: '天猫'
      }],
      dataForm: {
        key: '',
        status: '',
        role: ''
      },
      dataList: [],
      ids: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      refuseVisible: false
    }
  },
  components: {
    AddOrUpdate
  },
  created: function () {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList () {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/app/seller/shop/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'status': this.dataForm.status,
          'name': this.dataForm.name,
          'category': this.dataForm.category
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list
          this.totalPage = data.data.totalCount
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
    // 拒绝
    refuse (id, type, note) {
      this.refuseVisible = true
      this.$nextTick(() => {
        this.$refs.refuse.init(id, type, note)
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
          url: this.$http.adornUrl('/app/seller/shop/delete'),
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
    },
    // 通过
    updateRole (id, role) {
      this.$http({
        url: this.$http.adornUrl('/app/seller/shop/update'),
        method: 'post',
        data: this.$http.adornData({
          id: id,
          role: role
        })
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
    },
    // 跳转外链接
    toLink (url) {
      window.open(url, '_blank')
    },
    reset(){
      this.dataForm = {}
    }
  }
}
</script>
