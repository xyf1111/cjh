<template>
  <el-dialog
    title="明细"
    :close-on-click-modal="false"
    :visible.sync="visible">
   <el-table
         :data="dataList"
         border
         stripe
         v-loading="dataListLoading"
         style="width: 100%;">
         <el-table-column
           type="index"
           header-align="center"
           align="center"
           label="序号">
         </el-table-column>
         <el-table-column
           prop="code"
           header-align="center"
           align="center"
           width="200"
           label="任务编号">
         </el-table-column>
         <el-table-column
             prop="amount"
             header-align="center"
             align="center"
             width = "150"
             label="操作金额">
         </el-table-column>
         <el-table-column
           prop="typeStr"
           header-align="center"
           align="center"
           width="150"
           label="操作类型">
         </el-table-column>
         <el-table-column
           prop="createTime"
           header-align="center"
           align="center"
           label="操作时间">
         </el-table-column>
         <el-table-column
           prop="note"
           header-align="center"
           align="center"
           label="备注">
         </el-table-column>
       </el-table>
       <el-pagination
         @size-change="sizeChangeHandle"
         @current-change="currentChangeHandle"
         :current-page="pageIndex"
         :page-sizes="[10, 20, 50, 100]"
         :page-size="pageSize"
         :total="totalPage"
         layout="total, sizes, prev, pager, next, jumper">
       </el-pagination>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        activeName: 'first',
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataForm: {
        }
      }
    },
    methods: {
      init (id) {
        this.visible = true
        this.dataForm.id = id || 0
        this.getDataList()
      },
      getDataList () {
        this.$nextTick(() => {
          this.$http({
            url: this.$http.adornUrl(`/app/seller/detail/list`),
            method: 'get',
            params: this.$http.adornParams({
              'page': this.pageIndex,
              'limit': this.pageSize,
              'userId': this.dataForm.id,
              'isSeller': 1
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
      }
    }
  }
</script>
