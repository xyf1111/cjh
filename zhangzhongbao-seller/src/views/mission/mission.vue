<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.missionCode" placeholder="请输入订单编号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.userName" placeholder="请输入用户名称" clearable></el-input>
        </el-form-item>
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
        <el-input v-model="dataForm.taoBao" placeholder="请输入淘宝名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.taoBaoCode" placeholder="请输入淘宝订单号"></el-input>
      </el-form-item><br>
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
        <el-date-picker v-model="dataForm.startTime" placeholder="请选择开始时间" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="dataForm.endTime" placeholder="请选择结束时间"  value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.sort" placeholder="排序">
          <el-option key="0" label="倒序" value="0"></el-option>
          <el-option key="1" label="正序" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()" icon="el-icon-search" type="success">查询</el-button>
         <el-button type="primary" @click="reset()">重置</el-button>
        <el-form-item>
          <el-button @click="exportExcel" type="primary" :disabled="dataListSelections.length <= 0">任务列表导出
          </el-button>
        </el-form-item>
        <!--
         <el-button v-if="isAuth('background:mission:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
         <el-button v-if="isAuth('background:mission:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
         -->
      </el-form-item>
      <template>
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane label="已审核" name="1"/>
          <el-tab-pane label="已撤销" name="2"/>
          <el-tab-pane label="已报错" name="3"/>
        </el-tabs>
      </template>

    </el-form>

    <el-table
      :data="dataList"
      border
      stripe
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
        type="index"
        header-align="center"
        align="center"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="missionCode"
        header-align="center"
        align="center"
        width="200"
        label="订单编号">
        <template slot-scope="scope">
          {{scope.row.missionCode}}<br>
          {{scope.row.createTime}}
        </template>
      </el-table-column>
      <el-table-column
        prop="shopName"
        header-align="center"
        align="center"
        width="150"
        label="店铺名称">
      </el-table-column>
      <el-table-column
        prop="userName"
        header-align="center"
        align="center"
        width="150"
        label="刷手名称">
      </el-table-column>
      <el-table-column
        prop="price"
        header-align="center"
        align="center"
        label="宝贝信息">
        <template slot-scope="scope">
          订单号：{{scope.row.taobaoCode}}<br>
          价格{{scope.row.price.toFixed(2)}}
        </template>
      </el-table-column>
      <el-table-column
        prop="taobao"
        header-align="center"
        align="center"
        width="100"
        label="淘宝号">
      </el-table-column>
      <el-table-column
        prop="keyWord"
        header-align="center"
        align="center"
        width="100"
        label="关键字">
      </el-table-column>
      <el-table-column
        prop="platformName"
        header-align="center"
        align="center"
        label="下单时间">
        <template slot-scope="scope">
          {{scope.row.beginTime}}<br>
          {{scope.row.taskStyleStr}}<br>
          {{scope.row.isBadStr}}
        </template>
      </el-table-column>
      <el-table-column
        prop="statusStr"
        header-align="center"
        align="center"
        label="执行时间">
        <template slot-scope="scope">
          {{scope.row.missionTime}}<br>
          {{scope.row.statusStr}}
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="small" @click="missionDetailHandle(scope.row.id)">订单详情</el-button>
          <el-button type="danger" size="small" @click="setBadInfo(scope.row.id,scope.row.userId,scope.row.missionCode)" v-if="scope.row.isBad === 1" style="margin: 10px; !important">提交报错</el-button>
          <el-button type="success" size="small" @click="selBadInfo(scope.row.badInfo)" v-if="scope.row.isBad != 1" style="margin: 10px; !important">报错信息</el-button>
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
    <mission-detail v-if="addOrUpdateVisible" ref="missionDetail" @refreshDataList="getDataList"></mission-detail>
    <badInfo v-if="badInfoVisible" ref="badInfo" @refreshDataList="getDataList"></badInfo>
    <setBadInfo v-if="setBadInfoVisible" ref="setBadInfo" @refreshDataList="getDataList"></setBadInfo>
  </div>
</template>

<script>
import MissionDetail from './mission-detail'
import BadInfo from './badInfo'
import SetBadInfo from './setBadInfo'
import { getNowFormatDate } from '@/utils'

export default {
  name: 'mission',
  data () {
    return {
      dataForm: {
        key: '',
        sort: '0',
        startTime: '',
        endTime: '',
        role: '2'
      },
      shopId: '',
      dataList: [],
      shops: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      refuseInfoVisible: false,
      badInfoVisible: false,
      setBadInfoVisible: false,
      activeName: '1',
      role: '',
      isBad: '',
      isAnnul: '1',
      taskStyles: [{
        value: 0,
        label: '精刷单'
      }, {
        value: 1,
        label: '标签二天'
      }, {
        value: 2,
        label: '标签三天'
      }]
    }
  },
  components: {
    MissionDetail,
    BadInfo,
    SetBadInfo
  },
  activated () {
    this.init()
    this.getShopList()
    this.getDataList()
  },
  methods: {
    // 初始化数据
    init () {
      // 设置默认搜索时间为今天
      let nowDateString = getNowFormatDate()
      this.dataForm.startTime = this.dataForm.startTime || (nowDateString + ' 00:00:00')
      this.dataForm.endTime = this.dataForm.endTime || (nowDateString + ' 23:59:59')
    },
    // 获取数据列表
    getDataList () {
      this.dataListLoading = true
      var endTime = ''
      if (this.dataForm.endTime != null) {
        endTime = this.dataForm.endTime.replace('00:00:00', '23:59:59')
      }
      this.$http({
        url: this.$http.adornUrl('/app/seller/mission/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'missionCode': this.dataForm.missionCode,
          'userName': this.dataForm.userName,
          'shopName': this.dataForm.shopName,
          'taoBao': this.dataForm.taoBao,
          'taoBaoCode': this.dataForm.taoBaoCode,
          'taskStyle': this.dataForm.taskStyle,
          'status': this.dataForm.status,
          'isMark': this.dataForm.isMark,
          'startTime': this.dataForm.startTime,
          'endTime': endTime,
          'role': this.dataForm.role,
          'shopId': this.shopId,
          'isAnnul': this.isAnnul,
          'isBad': this.isBad,
          'sort': this.dataForm.sort
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
    // 查看订单详情
    missionDetailHandle (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.missionDetail.init(id)
      })
    },
    // 查看报错信息
    selBadInfo (badInfo) {
      this.badInfoVisible = true
      this.$nextTick(() => {
        this.$refs.badInfo.init(badInfo)
      })
    },
    // 提交报错信息
    setBadInfo (id, userId, missionCode) {
      this.setBadInfoVisible = true
      this.$nextTick(() => {
        this.$refs.setBadInfo.init(id, userId, missionCode)
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
      window.open(this.$http.adornUrl('/app/seller/mission/exportExcel?params=' + encodeURIComponent(JSON.stringify(params))) + '&token=' + token, '_blank')
    },
    handleClick (tab) {
      if (this.activeName === '1') {
        this.isAnnul = 1
        this.dataForm.role = 2
        this.isBad = 1
      } else if (this.activeName === '2') {
        this.isAnnul = 2
        this.isBad = 1
      } else if (this.activeName === '3') {
        this.isAnnul = 1
        this.isBad = 2
      }
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
