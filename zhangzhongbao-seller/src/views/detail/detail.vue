<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.missionCode" placeholder="请输入任务编号"></el-input>
      </el-form-item>
      <el-form-item>
          <el-select v-model="value" placeholder="请选择变动类型">
            <el-option
              v-for="item in options"
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
        <el-date-picker v-model="dataForm.endTime" placeholder="请选择结束时间" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()" icon="el-icon-search" type="success">查询</el-button>
        <el-button type="primary" @click="reset()">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        prop="type"
        header-align="center"
        align="center"
        label="变动类型">
        <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.type === 1">商家充值</el-tag>
          <el-tag type="success" v-if="scope.row.type === 2">商家发布任务扣除本佣金</el-tag>
          <el-tag type="success" v-if="scope.row.type === 3">商家发布任务被拒绝返回的本佣金</el-tag>
          <el-tag type="success" v-if="scope.row.type === 4">指定评价的佣金</el-tag>
          <el-tag type="success" v-if="scope.row.type === 5">刷手订单被审核通过获得的本佣金</el-tag>
          <el-tag type="success" v-if="scope.row.type === 6">刷手提现的本佣金</el-tag>
          <el-tag type="success" v-if="scope.row.type === 7">订单被撤销，返回给商家的本佣金或是扣除刷手的本佣金</el-tag>
          <el-tag type="success" v-if="scope.row.type === 8">下家完成任务的本佣金</el-tag>
          <el-tag type="success" v-if="scope.row.type === 9">后台修改</el-tag>
          <el-tag type="success" v-if="scope.row.type === 16">商家发货未选择物流返还金额</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="amount"
        header-align="center"
        align="center"
        label="往来金额">
       <template slot-scope="scope">
          {{scope.row.amount.toFixed(2)}}
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="发生时间">
      </el-table-column>
      <el-table-column
        prop="note"
        header-align="center"
        align="center"
        label="往来备注">
      </el-table-column>
      <el-table-column
        prop="balance"
        header-align="center"
        align="center"
        label="金额">
         <template slot-scope="scope">
          {{scope.row.balance.toFixed(2)}}
        </template>
      </el-table-column>
      <el-table-column
        prop="code"
        header-align="center"
        align="center"
        label="编号">
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
    name: 'detail',
    data() {
      return {
        dataForm: {
          key: '',
          missionCode: '',
          type: '',
          startTime: '',
          endTime: ''
        },
        options:[{
          value: '1',
          label: '商家充值'
        }, {
          value: '2',
          label: '商家发布任务扣除佣金'
        }, {
          value: '3',
          label: '商家任务被拒绝返回佣金'
        }, {
          value: '4',
          label: '发布评价任务扣除佣金'
        }, {
          value: '7',
          label: '订单被撤销退回佣金'
        }, {
          value: '12',
          label: '下级发单获取的佣金'
        }, {
          value: '9',
          label: '后台修改'
        }],
        value:'',
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        activeName: '1',
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    activated() {
      this.init()
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
      getDataList() {
        this.dataListLoading = true
         var endTime = ''
          if(this.dataForm.endTime != null){
             endTime = this.dataForm.endTime.replace('00:00:00','23:59:59')
          }
        this.$http({
          url: this.$http.adornUrl('/app/seller/detail/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'code': this.dataForm.missionCode,
            'type': this.value,
            'startTime': this.dataForm.startTime,
            'endTime': endTime
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
        this.getDataList()
      },
      reset(){
        this.dataForm = {}
      }
    }
  }
</script>
