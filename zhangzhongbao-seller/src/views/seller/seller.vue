<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.mobile" placeholder="请输入商家手机号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.contact" placeholder="请输入商家名称"></el-input>
      </el-form-item>
      <el-form-item>
         <el-select v-model="dataForm.isVip" placeholder="请选择发单状态">
          <el-option
            v-for="item in isVip"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
         <el-button @click="getDataList()" icon="el-icon-search" type="success">查询</el-button>
          <el-button type="primary" @click="reset()">重置</el-button>
      </el-form-item><br>
    </el-form>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="待审核" name="1"></el-tab-pane>
      <el-tab-pane label="已审核" name="2"></el-tab-pane>
      <el-tab-pane label="已驳回" name="3"></el-tab-pane>
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
        width="50">
      </el-table-column>
      <el-table-column
        type="index"
        header-align="center"
        align="center"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="contact"
        header-align="center"
        align="center"
        label="联系人">
        <template slot-scope="scope">
          {{scope.row.contact}}<br>
        </template>
      </el-table-column>
      <el-table-column
        prop="url"
        header-align="center"
        align="center"
        label="店铺网址">
        <template slot-scope="scope">
          <el-button size="mini" @click="open(scope.row.url)" type="primary">跳转店铺</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="mobile"
        header-align="center"
        align="center"
        label="手机号">
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="店铺名称"
        v-if="this.activeName != '2'">
      </el-table-column>
      <el-table-column
        prop="category"
        header-align="center"
        align="center"
        label="店铺类目"
        v-if="this.activeName != '2'">
      </el-table-column>
      <el-table-column
        prop="balance"
        header-align="center"
        align="center"
        label="余额"
        v-if="this.activeName === '2'">
          <template slot-scope="scope">
            {{scope.row.balance.toFixed(2)}}
         </template>
      </el-table-column>
      <el-table-column
        prop="limit"
        header-align="center"
        align="center"
        label="金额限制"
        v-if="this.activeName === '2'">
      </el-table-column>
      <el-table-column
        prop="benefit"
        header-align="center"
        align="center"
        label="佣金浮动"
        v-if="this.activeName === '2'">
         <template slot-scope="scope">
            {{scope.row.benefit.toFixed(2)}}
         </template>
      </el-table-column>
      <el-table-column
        prop="publishableStr"
        header-align="center"
        align="center"
        label="发单状态"
        v-if="this.activeName === '2'">
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
        width="250"
        label="操作">
        <template slot-scope="scope">
           <el-button type="info" size="small" @click="sellerDetail(scope.row.id)">来往明细</el-button>
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
     <seller-detail v-if="sellerDetailVisible" ref="sellerDetail" @refreshDataList="getDataList"></seller-detail>
  </div>
</template>

<script>
  import SellerDetail from './seller-detail'

  export default {
    data () {
      return {
        dataForm: {
          mobile: '',
          publishable: '',
          isVip: '',
          contact: ''
        },
        ids: [],
        activeName: '1',
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        sellerUpdateVisible: false,
        sellerUpdateMoneyVisible: false,
        sellerDetailVisible: false,
        sellerLimitOrBenefitVisible: false,
        publishs: [{
          label: '请选择发单状态',
          value: ''
        }, {
          label: '可发单',
          value: '1'
        }, {
          label: '禁止发单',
          value: '2'
        }],
        isVip: [{
          label: '请选择是否为VIP',
          value: ''
        }, {
          label: '是',
          value: '2'
        }, {
          label: '否',
          value: '1'
        }]
      }
    },
    activated () {
      this.pageIndex = 1
      this.pageSize = 10
      this.getDataList()
    },
    components: {
      SellerDetail
    },
    methods: {
      // 获取数据列表
      getDataList () {
        if (this.activeName === '1') {
          this.dataForm.status = 1
        }
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/app/seller/sellerLower/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'status': this.dataForm.status,
            'isVip': this.dataForm.isVip,
            'mobile': this.dataForm.mobile,
            'contact': this.dataForm.contact
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
      // 往来明细
      sellerDetail (id) {
        this.sellerDetailVisible = true
        this.$nextTick(() => {
          this.$refs.sellerDetail.init(id)
        })
      },
      handleClick (tab) {
        this.activeName = tab.name
        if (tab.name === '1') {
          this.dataForm.status = 1
        } else if (tab.name === '2') {
          this.dataForm.status = 2
        } else if (tab.name === '3') {
          this.dataForm.status = 3
        }
        this.pageIndex = 1
        this.pageSize = 10
        this.getDataList()
     },
      //店铺跳转
      open(url){
        window.open(url, '_blank')
      },
      reset(){
        this.dataForm = {}
      }
    }
  }
</script>

