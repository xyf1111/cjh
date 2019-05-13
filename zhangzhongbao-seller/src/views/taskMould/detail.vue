<template>
  <el-dialog
    title="详情"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm"  ref="dataForm" label-width="100px">
    <el-form-item label="店铺名称" prop="shopName">
      <el-input v-model="dataForm.shopName" ></el-input>
    </el-form-item>
    <el-form-item label="商品链接" prop="url">
      <el-input v-model="dataForm.url" ></el-input>
    </el-form-item>
    <el-form-item label="宝贝款号" prop="style">
      <el-input v-model="dataForm.style" ></el-input>
    </el-form-item>
    <el-form-item label="价格区间（从）" prop="priceFrom">
      <el-input v-model="dataForm.priceFrom"></el-input>
    </el-form-item>
    <el-form-item label="价格区间（至）" prop="priceTo">
      <el-input v-model="dataForm.priceTo"></el-input>
    </el-form-item>
    <el-form-item label="价格" prop="price">
      <el-input v-model="dataForm.price"></el-input>
    </el-form-item>
      <el-form-item label="货重" prop="weight">
        <el-input v-model="dataForm.weight"></el-input>
      </el-form-item>
    <el-form-item label="关键字" prop="keyword">
      <el-input v-model="dataForm.keyword"></el-input>
    </el-form-item>
    <el-form-item label="排列顺序" prop="order">
      <el-input v-model="dataForm.orderStr"></el-input>
    </el-form-item>
    <!--<el-form-item label="排名区间起点" prop="intervalBegin">
      <el-input v-model="dataForm.intervalBegin"></el-input>
    </el-form-item>
    <el-form-item label="排名区间终点" prop="intervalEnd">
      <el-input v-model="dataForm.intervalEnd"></el-input>
    </el-form-item>-->
    <el-form-item label="其他要求" prop="otherNote">
      <el-input v-model="dataForm.otherNote"></el-input>
    </el-form-item>
    </el-form>
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
      dataForm: {
        id: 0,
        sellerId: '',
        shopId: '',
        img: '',
        shopName: '',
        url: '',
        style: '',
        price: '',
        priceFrom: '',
        priceTo: '',
        weight: '',
        keyword: '',
        orderStr: '',
        intervalBegin: '',
        intervalEnd: '',
        note: '',
        otherNote: '',
        createTime: '',
        updateTime: ''
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/app/seller/taskMould/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.sellerId = data.taskMould.sellerId
              this.dataForm.shopId = data.taskMould.shopId
              this.dataForm.img = data.taskMould.img
              this.dataForm.shopName = data.taskMould.shopName
              this.dataForm.url = data.taskMould.url
              this.dataForm.style = data.taskMould.style
              this.dataForm.price = data.taskMould.price
              this.dataForm.priceFrom = data.taskMould.priceFrom
              this.dataForm.priceTo = data.taskMould.priceTo
              this.dataForm.weight = data.taskMould.weight
              this.dataForm.keyword = data.taskMould.keyword
              this.dataForm.orderStr = data.taskMould.orderStr
              this.dataForm.intervalBegin = data.taskMould.intervalBegin
              this.dataForm.intervalEnd = data.taskMould.intervalEnd
              this.dataForm.note = data.taskMould.note
              this.dataForm.otherNote = data.taskMould.otherNote
            }
          })
        }
      })
    }
  }
}
</script>
