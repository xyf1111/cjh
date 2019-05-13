<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-date-picker v-model="startTime" type="datetime" placeholder="请选择开始时间" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="endTime" type="datetime" placeholder="请选择结束时间" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.missionCode" placeholder="请输入任务订单编号" clearable></el-input>
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
        <el-input v-model="dataForm.taobao" placeholder="请输入淘宝号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()" icon="el-icon-search" type="success">查询</el-button>
        <el-button type="primary" @click="reset()">重置</el-button>
      </el-form-item>
      <br>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="未指定" name="1"></el-tab-pane>
        <el-tab-pane label="已指定" name="2"></el-tab-pane>
        <el-tab-pane label="已完成" name="3"></el-tab-pane>
      </el-tabs>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        header-align="center"
        align="center"
        type="index"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="missionCode"
        header-align="center"
        align="center"
        label="订单编号">
      </el-table-column>
      <el-table-column
        prop="shopName"
        header-align="center"
        align="center"
        label="店铺信息">
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        label="宝贝信息">
        <template slot-scope="scope">
          价格：{{scope.row.price.toFixed(2)}}
        </template>
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        label="淘宝号"
        prop="taobao">
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        label="评价截图"
        prop="commentImg"
        key="commentImg"
        v-if="this.activeName === '3'">
        <template slot-scope="scope">
          <viewer :image="scope.row.commentImg" v-if="scope.row.commentImg">
            <img :src=scope.row.commentImg style=" width: 90px; height: 90px;"/>
          </viewer>
        </template>
      </el-table-column>
      <el-table-column
        prop="keyWord"
        header-align="center"
        align="center"
        label="关键字">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="创建时间">
        <template slot-scope="scope">
          {{scope.row.createTime}}<br>
          {{scope.row.statusStr}}
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="操作">
        <template slot-scope="scope">
          <el-button type="primary" style="margin-bottom: 7px;"
               @click="appointAdd(scope.row.id,scope.row.missionId,scope.row.userId,scope.row.missionCode)" v-if="scope.row.isComment === 1">指定评价
          </el-button>
          <el-button type="primary" style="margin-bottom: 7px;"
               @click="appointRevoke(scope.row.comment,scope.row.imgListStr,scope.row.smallVideo)" v-if="scope.row.isComment !== 1">查看评价
          </el-button>
          <el-button type="danger" style="margin-left: -1px;" @click="revoke(scope.row)"  v-if="scope.row.isComment === 2">撤销指评</el-button>
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
    <appoint-revoke v-if="appointRevokeVisible" ref="appointRevoke" @refreshDataList="getDataList"></appoint-revoke>
    <AppointAdd v-if="appointAddVisible" ref="appointAdd" @refreshDataList="getDataList"></AppointAdd>
  </div>
</template>

<script>
  import AppointRevoke from './review-revoke'
  import AppointAdd from './review-add'
  import { getNowFormatDate } from '@/utils'

  export default {
    name: 'appoint',
    data() {
      return {
        dataForm: {
          key: '',
          imgListStr: ''
        },
        options: [],
        shops:[],
        shopId:'',
        activeName: '1',
        startTime: '',
        endTime: '',
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        appointRevokeVisible: false,
        appointAddVisible: false,
        dataListSelections: []
      }
    },
    activated() {
      this.init()
      this.getShopList()
      this.getDataList()
    },
    components: {
      AppointRevoke,
      AppointAdd
    },
    methods: {
      // 初始化数据
      init () {
        // 设置默认搜索时间为今天
        let nowDateString = getNowFormatDate()
        this.startTime = this.startTime || (nowDateString + ' 00:00:00')
        this.endTime = this.endTime || (nowDateString + ' 23:59:59')
      },
      // 获取数据列表
      getDataList() {
      if(this.activeName === '1'){
        this.dataForm.status = 6
      }else{
        this.dataForm.status = ''
      }
        this.dataListLoading = true
          var endTime = ''
          if(this.endTime != null){
            endTime  = this.endTime.replace('00:00:00','23:59:59')
          }
        this.$http({
          url: this.$http.adornUrl('/app/seller/mission/getAppointList'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'missionCode': this.dataForm.missionCode,
            'shopName': this.dataForm.shopName,
            'taobao': this.dataForm.taobao,
            'isComment': this.activeName,
            'shopId': this.shopId,
            'role': 2,
            'isAnnul':1,
            'status': this.dataForm.status,
            'startTime': this.startTime,
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
      // 查看评价
      appointRevoke(comment, imgList, smallVideo) {
        this.appointRevokeVisible = true
        this.$nextTick(() => {
          this.$refs.appointRevoke.init(comment, imgList, smallVideo)
        })
      },
      // 指定评价
      appointAdd(id,missionId,userId,missionCode) {
        this.appointAddVisible = true
        this.$nextTick(() => {
          this.$refs.appointAdd.init(id,missionId,userId,missionCode)
        })
      },
      // 撤销评价
      revoke(row) {
        this.$confirm(`确定对撤销评价操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/app/seller/mission/revokeComment'),
            method: 'post',
            data: this.$http.adornData({
              'missionCode': row.missionCode,
              'userId': row.userId,
              'sellerId': row.sellerId,
              'id': row.id,
              'commentUser': row.commentUser,
              'commentPay': row.commentPay,
              'flag': 2
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
            }
            this.dataListLoading = false
          })
        })
      },
      handleClick(tab) {
        this.pageIndex = 1
        this.pageSize = 10
        this.getDataList()
      },
      reset(){
         this.dataForm = {}
         this.shopId = ''
         this.startTime=''
         this.endTime=''
      },
      getShopList(){
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
