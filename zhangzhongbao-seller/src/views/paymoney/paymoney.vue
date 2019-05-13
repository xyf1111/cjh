<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.shopName" placeholder="请输入店铺名称" clearable></el-input>
      </el-form-item>
     <el-form-item>
         <el-select v-model="shopId" clearable placeholder="请选择店铺名称">
           <el-option
             v-for="item in shops"
             :key="item.id"
             :label="item.name"
             :value="item.id">
           </el-option>
         </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.taskStyle" placeholder="请选择任务类型" clearable>
          <el-option
            v-for="item in taskStyles"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.missionCode" placeholder="请输入任务编号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="dataForm.startTime"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd HH:mm:ss">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="dataForm.endTime"
          type="date"
          placeholder="选择日期"
         value-format="yyyy-MM-dd HH:mm:ss">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()" icon="el-icon-search" type="success">查询</el-button>
        <el-button type="primary" @click="reset()">重置</el-button>
        <el-button type="danger" @click="updateStatus()"
           :disabled="dataListSelections.length <= 0">批量打款
        </el-button>
        <el-button type="danger" @click="exportExcel(1)"
           :disabled="dataListSelections.length <= 0">招商银行导出
        </el-button>
        <el-button type="danger" @click="exportExcel(2)"
           :disabled="dataListSelections.length <= 0">浦发银行导出
        </el-button>
        <el-button v-if="isPay == 2" type="danger" @click="exportExcel(4)"
           :disabled="dataListSelections.length <= 0">任务列表导出
        </el-button>
      </el-form-item>
    </el-form>
    <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-left: 10px;">
      <el-tab-pane label="未打款" name="1"></el-tab-pane>
      <el-tab-pane label="已打款" name="2"></el-tab-pane>
    </el-tabs>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="60">
      </el-table-column>
      <el-table-column
        prop="missionCode"
        header-align="center"
        align="center"
        label="订单编号">
      </el-table-column>
      <el-table-column
        prop="holder"
        header-align="center"
        align="center"
        label="开户人">
      </el-table-column>
      <el-table-column
        prop="taobao"
        header-align="center"
        align="center"
        label="淘宝信息/淘宝订单号">
        <template slot-scope="scope">
          <div>{{scope.row.taobao}}</div>
          <div>{{scope.row.taobaoCode}}</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="bankInfo"
        header-align="center"
        align="center"
        label="银行信息">
      </el-table-column>
      <el-table-column
        prop="cardNumber"
        header-align="center"
        align="center"
        label="银行卡号">
      </el-table-column>
      <el-table-column
        prop="price"
        header-align="center"
        align="center"
        label="金额">
       <template slot-scope="scope">
          {{scope.row.price.toFixed(2)}}
        </template>
      </el-table-column>
      <el-table-column
        prop="isPay"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.isPay === 1">未打款</el-tag>
          <el-tag type="success" v-if="scope.row.isPay === 2">已打款</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作"
        v-if="this.activeName === '1'">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updateStatus(scope.row.id,scope.row.price)">确认打款</el-button>
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
  name: 'paymoney',
  data () {
    return {
      dataForm: {
        key: '',
        startTime: '',
        endTime: '',
        shopName: '',
        missionCode: '',
        taskStyle: ''
      },
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
      dataList: [],
      shops: [],
      shopId: '',
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      activeName: '1',
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      isPay: '',
      status: '',
      row: ''
    }
  },
  activated () {
    this.init()
    this.getShopList()
    this.getDataList()
  },
  methods: {
    // 初始化数据
    init () {
      let nowDateString = getNowFormatDate()
      this.dataForm.startTime = this.dataForm.startTime || (nowDateString + ' 00:00:00')
      this.dataForm.endTime = this.dataForm.endTime || (nowDateString + ' 23:59:59')
    },
    // 获取数据列表
    getDataList () {
      if (this.activeName === '1') {
        this.isPay = 1
        this.status = 8
      } else if (this.activeName === '2') {
        this.isPay = 2
        this.status = ''
      }
      var endTime = ''
      if (this.dataForm.endTime != null) {
        endTime = this.dataForm.endTime.replace('00:00:00', '23:59:59')
      }
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/app/seller/mission/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'startTime': this.dataForm.startTime,
          'endTime': endTime,
          'isPay': this.isPay,
          'role': 2,
          'status': this.status,
          'isAnnul': 1,
          'shopId': this.shopId,
          'shopName': this.dataForm.shopName,
          'missionCode': this.dataForm.missionCode,
          'taskStyle': this.dataForm.taskStyle
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
    // 修改打款状态
    updateStatus (id, price) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })

      let totalPrice = 0
      this.dataListSelections.forEach(item => {
        totalPrice = item.price + totalPrice
      })

      let msg = id ? `该笔订单金额${price}元，确定进行打款？` : `您已选中${this.dataListSelections.length}笔订单，共计${totalPrice}元，确定进行批量打款？`

      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/app/seller/mission/updates'),
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
      window.open(this.$http.adornUrl('/app/seller/mission/exportExcel1?params=' + encodeURIComponent(JSON.stringify(params))) + '&token=' + token, '_blank')
    },
    handleClick (tab) {
      this.activeName = tab.name
      this.pageIndex = 1
      this.pageSize = 10
      this.getDataList()
    },
    reset () {
      this.dataForm = {}
      this.shopId = ''
    },
    getShopList () {
      this.$http({
        url: this.$http.adornUrl('/app/seller/shop/getShopList'),
        method: 'get',
        data: this.$http.adornData({
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.shops = data.list
        }
        this.dataListLoading = false
      })
    }
  }
}
</script>
