<template>
  <div class="mod-config" v-loading="loading">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.shopName" placeholder="请输入店铺名称" value-format="yyyy-MM-dd HH:mm:ss"></el-input>
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
        <el-input v-model="dataForm.missionCode" placeholder="请输入任务编号" value-format="yyyy-MM-dd HH:mm:ss"></el-input>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="dataForm.startTime"
          type="date"
          placeholder="选择日期"
         value-format="yyyy-MM-dd HH:mm:ss" >
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
        <el-button type="danger" @click="updateStatus()" :disabled="isBtnDisabled">批量发货</el-button>
      <el-button type="primary" @click="reset()">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="exportExcel" type="primary" :disabled="isBtnDisabled">任务列表导出</el-button>
      </el-form-item>
      <div style="display:inline-block;font-size:20px;color:red;padding:10px 10px;vertical-align:top">
        老板们，手下留情，确认发货后，不要重复提交，物流最多1分钟就会出来
      </div>
    </el-form>
    <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-left: 10px;">
      <el-tab-pane label="未发货" name="1"></el-tab-pane>
      <el-tab-pane label="已发货" name="2"></el-tab-pane>
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
        prop="userName"
        header-align="center"
        align="center"
        label="开户人">
      </el-table-column>
      <el-table-column
        prop="taobao"
        header-align="center"
        align="center"
        label="淘宝信息"
        width="300px">
          <template slot-scope="scope">
           淘宝号：{{scope.row.taobao}}<br>
           淘宝订单号：{{scope.row.taobaoCode}}
          </template>
      </el-table-column>
      <el-table-column
        prop="isLogistics"
        header-align="center"
        align="center"
        label="是否发送物流">
        <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.isLogistics === 0">否</el-tag>
          <el-tag type="success" v-if="scope.row.isLogistics === 1">是</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="logisticsComp"
        header-align="center"
        align="center"
        label="物流公司">
      </el-table-column>
      <el-table-column
        prop="logisticsNo"
        header-align="center"
        align="center"
        label="物流单号">
      </el-table-column>
      <el-table-column
        prop="logisticsName"
        header-align="center"
        align="center"
        label="收货人">
      </el-table-column>
      <!--<el-table-column
        prop="logisticsAddress"
        header-align="center"
        align="center"
        label="收货地址">
      </el-table-column>-->
      <el-table-column
        prop="logisticsPhone"
        header-align="center"
        align="center"
        label="收货电话">
      </el-table-column>
      <!--<el-table-column
        prop="bankInfo"
        header-align="center"
        align="center"
        label="银行信息">
      </el-table-column>-->
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
        prop="weight"
        header-align="center"
        align="center"
        label="货重">
      </el-table-column>
      <!--<el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.status === 5">未发货</el-tag>
          <el-tag type="success" v-if="scope.row.status === 6">已发货</el-tag>
        </template>
      </el-table-column>-->
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作"
        v-if="this.activeName === '1'">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="missionDetailHandle(scope.row.id)" style="margin-bottom: 10px;margin-left: 8px;">订单详情</el-button>
          <el-button type="success" size="small" @click="updateStatus(scope.row.id)">确认发货</el-button>
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
     <mission-detail v-if="addOrUpdateVisible" ref="missionDetail" @refreshDataList="getDataList"></mission-detail>
  </div>
</template>

<script>
import MissionDetail from '../mission/mission-detail'

export default {
  data () {
    return {
      dataForm: {
        key: '',
        startTime: '',
        endTime: '',
        taskStyle: '',
        shopName: ''
      },
      shops: [],
      isBtnDisabled: true,
      shopId: '',
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      activeName: '1',
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      status: '',
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
      loading: true
    }
  },
  components: {
    MissionDetail
  },
  activated () {
    this.getShopList()
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList () {
      if (this.activeName === '1') {
        this.status = 8
      } else if (this.activeName === '2') {
        this.status = 6
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
          'status': this.status,
          'shopId': this.shopId,
          'taskStyle': this.dataForm.taskStyle,
          'shopName': this.dataForm.shopName,
          'missionCode': this.dataForm.missionCode
        })
      }).then(({data}) => {
        this.loading = false
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
      if (this.dataListSelections.length > 0) {
        this.isBtnDisabled = false
      } else {
        this.isBtnDisabled = true
      }
    },
    // 新增 / 修改
    addOrUpdateHandle (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    // 查看订单详情
    missionDetailHandle (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.missionDetail.init(id)
      })
    },
    // 修改发货状态
    updateStatus (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '发货' : '批量发货'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$confirm('是否需要发送物流?', '提示', {
          distinguishCancelAndClose: true,
          confirmButtonText: '需要',
          cancelButtonText: '不需要',
          type: 'info',
          center: true
        }).then(() => {
          this.loading = true
          this.isBtnDisabled = true
          ids[ids.length] = "1";
          this.$http({
            url: this.$http.adornUrl('/app/seller/mission/status'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            this.loading = false
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
              // 清空选项
              if (this.dataListSelections.length > 0) {
                this.isBtnDisabled = false
              } else {
                this.isBtnDisabled = true
              }
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(action => {
          if(action === 'cancel') {
            this.loading = true
            this.isBtnDisabled = true
            ids[ids.length] = "0";
            this.$http({
              url: this.$http.adornUrl('/app/seller/mission/status'),
              method: 'post',
              data: this.$http.adornData(ids, false)
            }).then(({data}) => {
              this.loading = false
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.getDataList()
                  }
                })
                // 清空选项
                if (this.dataListSelections.length > 0) {
                  this.isBtnDisabled = false
                } else {
                  this.isBtnDisabled = true
                }
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      })
    },
    // 导出excel表格
    exportExcel () {
      var ids = []
      for (var i = 0; i < this.dataListSelections.length; i++) {
        ids.push(this.dataListSelections[i].id)
      }
      var params = {
        ids: ids
      }
      var token = this.$cookie.get('token')
      window.open(this.$http.adornUrl('/app/seller/mission/exportExcel2?params=' + encodeURIComponent(JSON.stringify(params))) + '&token=' + token, '_blank')
    },
    handleClick () {
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
