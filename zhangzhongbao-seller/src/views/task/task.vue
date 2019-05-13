<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-date-picker v-model="dataForm.startTime" placeholder="请选择开始时间" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" @change="searchTimeChange"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="dataForm.endTime" placeholder="请选择结束时间" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" @change="searchTimeChange"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.id" placeholder="请输入任务编号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.shopName" placeholder="请输入店铺名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.keyWord" placeholder="请输入关键词" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()" icon="el-icon-search" type="success">查询</el-button>
        <el-button type="primary" @click="reset()">重置</el-button>
      </el-form-item>
      <br>
      <el-form-item>
        <el-button @click="exportExcel(1)" type="primary" :disabled="dataListSelections.length <= 0">任务列表导出</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="exportExcel(2)" type="primary">表格模板下载</el-button>
      </el-form-item>
      <el-form-item>
       <el-upload
         :action="action"
         ref="upload"
         :auto-upload="false"
         :file-list="fileList"
         :before-remove="beforeRemove"
         :on-success = "excelSuccess"
         :limit="1">
         <el-button slot="trigger" type="primary">点击上传</el-button>
         <el-button style="margin-left: 10px;" type="success" @click="submitUpload" :loading="uploadLoading">确认上传</el-button>
       </el-upload>
      </el-form-item>
    </el-form>
    <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-left:20px">
      <el-tab-pane label="待审核" name="1"></el-tab-pane>
      <el-tab-pane label="已审核" name="2"></el-tab-pane>
      <el-tab-pane label="已拒绝" name="3"></el-tab-pane>
    </el-tabs>
    <el-table
      :data="dataList"
      border
      :summary-method="getSummaries"
      show-summary
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="55">
      </el-table-column>
      <el-table-column
        type="index"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="任务编号">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="发布时间"
        width="200">
      </el-table-column>
      <el-table-column
        prop="shopName"
        header-align="center"
        align="center"
        label="店铺名称">
      </el-table-column>
      <el-table-column
        prop="img"
        header-align="center"
        align="center"
        label="商品主图">
        <template slot-scope="scope">
          <viewer >
            <img :src=scope.row.img style="width:70px;height:70px;"/>
          </viewer>
        </template>
      </el-table-column>
      <el-table-column
        prop="taskStyleStr"
        header-align="center"
        align="center"
        label="任务类型">
      </el-table-column>
      <el-table-column
        prop="price"
        header-align="center"
        align="center"
        label="价格(本金)">
      </el-table-column>
      <el-table-column
        prop="weight"
        header-align="center"
        align="center"
        label="货重">
        <template slot-scope="scope">
          <div>{{scope.row.weight}}kg</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="price"
        header-align="center"
        align="center"
        label="佣金">
        <template slot-scope="scope">
          <div>接单：{{scope.row.sellerPay}}元</div>
          <div>标签：{{scope.row.viewPay}}元</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="price"
        header-align="center"
        align="center"
        label="宝贝信息">
        <template slot-scope="scope">
          宝贝备注:{{scope.row.style}}<br>
          类目：{{scope.row.shopCategory}}<br>
        </template>
      </el-table-column>
      <el-table-column
        prop="remain"
        header-align="center"
        align="center"
        label="任务信息">
        <template slot-scope="scope">
          总数:{{scope.row.amount}}<br>
          (已接：{{scope.row.accept}})<br>
          (已撤回：<span style="color: red">{{scope.row.remove}}</span>)
          <el-button v-if="scope.row.accept == '0' && scope.row.remove != scope.row.amount && activeName == '2' " type="primary" size="small" @click="revoke(scope.row.id)" style="margin-bottom: 10px;margin-left: 8px;">撤回</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="beginTime"
        header-align="center"
        align="center"
        label="下单时间"
        width="200">
        <template slot-scope="scope">
          {{scope.row.beginTime}}<br>
          每单间隔时间：{{scope.row.interval}} 分钟
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
  </div>
</template>

<script>
import { getNowFormatDate } from '@/utils'

export default {
  name: 'task',
  data () {
    return {
      dataForm: {
        startTime: '',
        endTime: '',
        isSearchTimeChange: false,
        key: ''
      },
      roles: [{
        value: 1,
        label: '已完成'
      }, {
        value: 2,
        label: '未完成'
      }],
      taskStyles: [{
        value: 0,
        label: '精刷单'
      }, {
        value: 1,
        label: '标签二天'
      }, {
        value: 2,
        label: '标签三天'
      }],
      activeName: '1',
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      number: 0,
      sellerPay: 0,
      price: 0,
      action: '',
      dataListLoading: false,
      dataListSelections: [],
      taskDetailVisible: false,
      refuseNoteVisible: false,
      fileList: [],
      uploadLoading: false,
      sellerId: ''
    }
  },
  components: {},
  activated () {
    this.sellerId = this.$cookie.get('sellerId')
    this.dataForm.isSearchTimeChange = false
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList () {
      this.number = ''
      this.sellerPay = ''
      this.price = ''
      this.action = this.$http.adornUrl(`/app/seller/task/upload/${this.sellerId}`)
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/app/seller/task/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': +this.pageSize,
          'id': this.dataForm.id,
          'role': this.activeName,
          'shopName': this.dataForm.shopName,
          'keyWord': this.dataForm.keyWord,
          'startTime': this.dataForm.startTime,
          'endTime': this.dataForm.endTime,
          'order1': 3
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list
          this.totalPage = data.page.totalCount
          for (var i = 0; i < this.dataList.length; i++) {
            this.number = Number(this.number) + Number(this.dataList[i].amount - this.dataList[i].remove)
            this.sellerPay = Number(this.sellerPay) + Number(this.dataList[i].sellerPay / this.dataList[i].amount * (this.dataList[i].amount - this.dataList[i].remove)) +
              Number(this.dataList[i].viewPay / (this.dataList[i].amount) * (this.dataList[i].amount - this.dataList[i].remove))
            this.price = Number(this.price) + Number(this.dataList[i].price) * Number(this.dataList[i].amount - this.dataList[i].remove)
          }
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
    handleClick (tab) {
      this.activeName = tab.name
      this.price = 0
      this.number = 0
      this.sellerPay = 0
      this.pageIndex = 1
      this.pageSize = 10
      if (this.activeName !== '1') {
        //let nowDateString = getNowFormatDate()
        //this.dataForm.startTime = this.dataForm.isSearchTimeChange ? this.dataForm.startTime : (nowDateString + ' 00:00:00')
        //this.dataForm.endTime = this.dataForm.isSearchTimeChange ? this.dataForm.endTime : (nowDateString + ' 23:59:59')
      } else {
        //this.dataForm.startTime = this.dataForm.isSearchTimeChange ? this.dataForm.startTime : ''
        //this.dataForm.endTime = this.dataForm.isSearchTimeChange ? this.dataForm.endTime : ''
      }
      this.getDataList()
    },
    searchTimeChange () {
      this.dataForm.isSearchTimeChange = true
    },
    submitUpload () {
      // if (this.fileList.length === 0) {
      //   return this.$message.error('请先选择需要上传的文件！')
      // }
      this.uploadLoading = true
      this.$refs.upload.submit()
    },
    getSummaries (param) {
      const {columns, data} = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '总计'
          return
        }
        if (index == 7) {
          sums[index] = this.price + '元'
          return
        }
        if (index == 9) {
          sums[index] = this.sellerPay + '元'
          return
        }
        if (index == 11) {
          sums[index] = this.number
        }
      })
      return sums
    },
    // 导出excel表格
    exportExcel (excelType) {
      var ids = []
      for (var i = 0; i < this.dataListSelections.length; i++) {
        ids.push(this.dataListSelections[i].id)
      }
      var params = {
        ids: ids,
        excelType: excelType
      }
      var token = this.$cookie.get('token')
      window.open(this.$http.adornUrl('/app/seller/task/exportExcel?params=' + encodeURIComponent(JSON.stringify(params))) + '&token=' + token, '_blank')
    },
    revoke (id) {
      //撤回任务
      this.$confirm(`确定对[id=${id}]进行[撤回任务]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl(`/app/seller/task/revoke/${id}`),
          method: 'get'
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
      }).catch(action => {
        console.log(action)
      })

    },
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    excelSuccess (response, file, fileList) {
      this.uploadLoading = false
      this.fileList = []
      this.getDataList()
      if (response.code == 0) {
        return this.$message.success(response.msg)
      } else {
        return this.$message.error(response.msg)
      }
    },
    reset () {
      this.dataForm = {}
    }
  }
}
</script>
