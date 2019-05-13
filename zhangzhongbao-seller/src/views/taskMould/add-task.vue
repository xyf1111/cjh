<template>
  <el-dialog
    title="发布任务"
    :close-on-click-modal="false"
    :visible.sync="visible"
    class="task-submit">
    <el-steps :active="active" finish-status="success" align-center style="margin-bottom: 30px;">
      <el-step title="模板信息确认"></el-step>
      <el-step title="任务信息"></el-step>
      <el-step title="发布成功"></el-step>
    </el-steps>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="110px" v-if="this.step === 1">
      <el-row>
        <el-col :span="12">
          <el-form-item label="模板编号">
            <el-input v-model="dataForm.id"  disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
             <el-form-item label="宝贝备注">
              <el-input v-model="dataForm.style"></el-input>
            </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="付款方式">
            <el-input  placeholder="请先打款" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
             <el-form-item label="相关店铺">
              <el-input v-model="dataForm.shopName" disabled></el-input>
            </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="价格区间(从)">
            <el-input v-model = "dataForm.priceFrom" type="number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="价格区间(至)">
            <el-input v-model = "dataForm.priceTo" type="number"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="单价">
            <el-input v-model = "dataForm.price" type="number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="货重">
            <el-input v-model = "dataForm.weight" type="number"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="商品链接">
            <el-input v-model = "dataForm.url"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <!--<el-row>
        <el-col :span="24">
          <el-form-item label="搜素关键字">
            <el-input v-model = "dataForm.keyword"></el-input>
            <span style="color:red">销量低, 请用长尾词, 以便搜索, 请仔细确定搜索排名, 否则将导致任务失败。</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
        </el-col>
      </el-row>-->
      <el-row>
        <el-col :span="24">
          <el-form-item
            v-for="(ky, index) in dataForm.keywords"
            :label="'搜素关键字' + (index+1)"
            :key="ky.key"
            :prop="'keywords.' + index + '.value'"
            :rules="{required: true, message: '内容不能为空', trigger: 'blur'}"
          >
            <el-input v-model="ky.value" placeholder="商品关键字" style="width: 300px" ></el-input>
            <el-input v-model="ky.count" placeholder="关键字数量" type="number" style="width: 120px" ></el-input>
            <el-button type="primary" @click.prevent="addKeyWord()" v-if="index == 0">增加</el-button>
            <el-button type="primary" @click.prevent="removeKeyWord(ky)" v-if="index != 0">删除</el-button>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="排列方式" prop="order">
              <el-select v-model="order" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    <el-form-item label="商品主图（最多三张）" >
      <el-upload
        :action="url"
        :limit="3"
        list-type="picture-card"
        :file-list="dataForm.fileList"
        :before-upload="beforeUploadHandle"
        :on-preview="handlePictureCardPreview"
        :on-success="successHandle"
        :on-remove="handleRemove">
        <i class="el-icon-plus"></i>
      </el-upload>
    </el-form-item>
    </el-form>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="110px" v-if="this.step === 2" class='form1' >
     <el-row>
       <el-col :span="8">
          <div class="el-div">任务数量</div>
           <el-form-item  prop="amount">
              <el-input v-model="dataForm.amount" type="number" placeholder="请输入任务数量" style="width: 70%;"></el-input>
           </el-form-item>
       </el-col>
       <el-col :span="8">
          <div class="el-div">每单间隔时间(单位：分钟)</div>
           <el-form-item prop="interval">
              <el-select v-model="dataForm.interval" placeholder="请选择">
               <el-option
                 v-for="item in times"
                 :key="item.value"
                 :label="item.label"
                 :value="item.value">
               </el-option>
             </el-select>
           </el-form-item>
       </el-col>
       <el-col :span="8">
          <div class="el-div">首单任务执行时间(不选则默认当前时间)</div>
          <el-form-item  prop="beginTime">
             <el-date-picker
               :picker-options="pickerOptions"
               v-model="dataForm.beginTime"
               type="datetime"
               value-format="yyyy-MM-dd HH:mm:ss"
               placeholder="选择日期">
             </el-date-picker>
          </el-form-item>
       </el-col>
     </el-row>
    <!--<el-row>
      <el-col :span= "24">
       <el-form-item  prop="taskStyle">
          <el-radio-group v-model="taskStyle" @change="changeLabel(taskStyle)">
            <el-radio :label="0">精刷单任务</el-radio>
            <el-radio :label="1">标签二天</el-radio>
            <el-radio :label="2">标签三天</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
     </el-row>-->
     <div style="margin-top: 20px;">
       <el-row>
        <el-col :span= "24">
        <div>任务要求</div>
         <el-form-item prop="note" >
          <el-input type="textarea" v-model="dataForm.note" placeholder="" maxlength="400" :autosize="{ minRows: 2, maxRows: 6}"></el-input>
         </el-form-item>
        </el-col>
       </el-row>
     </div>
     <el-row>
       <el-col :span= "24">
        <div class="el-div">其他要求</div>
        <el-form-item prop="otherNote" >
          <el-input v-model="dataForm.otherNote" placeholder="其他要求" maxlength="300"></el-input>
        </el-form-item>
       </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="next1()" v-if="this.active > 1">上一步</el-button>
      <el-button type="primary" @click="next()" v-if="this.active < 3">下一步</el-button>
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
        img2: '',
        img3: '',
        shopName: '',
        url: '',
        style: '',
        price: '',
        priceFrom: '',
        priceTo: '',
        weight: '',
        keyword: '',
        keywords: [{value: '', count: 0}],
        orderStr: '',
        intervalBegin: '',
        intervalEnd: '',
        platForm: 0,
        fileList: [],
        amount: '',
        interval: '5',
        beginTime: '',
        taskStyle: 0,
        note: '',
        otherNote: ''
      },
      taskStyle: 0,
      label: '任务要求',
      step: 1,
      shopList: [],
      active: 1,
      shopId: '',
      keywordCounts: '',
      keywordAmount: 0,
      url: '',
      order: '',
      options: [{
        value: 1,
        label: '综合排序'
      }, {
        value: 2,
        label: '销量排序'
      }, {
        value: 3,
        label: '价格排序'
      }],
      times: [{
        value: 0,
        label: 0
      }, {
        value: 5,
        label: 5
      }, {
        value: 10,
        label: 10
      }, {
        value: 20,
        label: 20
      }, {
        value: 30,
        label: 30
      }, {
        value: 40,
        label: 40
      }, {
        value: 50,
        label: 50
      }, {
        value: 60,
        label: 60
      }],
      dataRule: {
        shopId: [
          { required: true, message: '店铺不能为空', trigger: 'blur' }
        ],
        img: [
          { required: true, message: '产品主图不能为空', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '商品链接不能为空', trigger: 'blur' }
        ],
        style: [
          { required: true, message: '商品款号不能为空', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '商品价格不能为空', trigger: 'blur' }
        ],
        amount: [
          { required: true, message: '任务数量不能为空', trigger: 'blur' }
        ],
        taskStyle: [
          { required: true, message: '任务类型不能为空', trigger: 'blur' }
        ]
      },
      pickerOptions: {
        disabledDate: (time) => {
          let nowData = new Date()
          nowData = new Date(nowData.setDate(nowData.getDate() - 1))
          return time < nowData
        }
      }
    }
  },
  methods: {
    init (id) {
      this.keywordAmount = 0
      this.dataForm.id = id || 0
      this.url = this.$http.adornUrl(`/app/seller/file/upload`)
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
              this.dataForm.fileList = [{name: '', url: this.dataForm.img}]
              if (data.taskMould.img2) {
                this.dataForm.img2 = data.taskMould.img2
                this.dataForm.fileList = [{name: '', url: this.dataForm.img}, {name: '', url: this.dataForm.img2}]
              }
              if (data.taskMould.img3) {
                this.dataForm.img3 = data.taskMould.img3
                this.dataForm.fileList = [{name: '', url: this.dataForm.img}, {name: '', url: this.dataForm.img2}, {name: '', url: this.dataForm.img3}]
              }
              this.dataForm.shopName = data.taskMould.shopName
              this.dataForm.url = data.taskMould.url
              this.dataForm.style = data.taskMould.style
              this.dataForm.price = data.taskMould.price
              this.dataForm.priceFrom = data.taskMould.priceFrom
              this.dataForm.priceTo = data.taskMould.priceTo
              this.dataForm.weight = data.taskMould.weight
              this.dataForm.keyword = data.taskMould.keyword
              // 回显处理
              this.dataForm.keywords = []
              var kys = this.dataForm.keyword.split(';')
              for (var i = 0; i < kys.length; i++) {
                var o = {value: kys[i], count: 0}
                this.dataForm.keywords.push(o)
              }
              this.order = data.taskMould.order
              this.dataForm.intervalBegin = data.taskMould.intervalBegin
              this.dataForm.intervalEnd = data.taskMould.intervalEnd
              this.dataForm.note = data.taskMould.note || this.dataForm.note
              this.dataForm.otherNote = data.taskMould.otherNote || this.dataForm.otherNote
              this.dataForm.createTime = data.taskMould.createTime
              this.dataForm.updateTime = data.taskMould.updateTime
            }
          })
        }
      })
    },
    /* 格式化表单数据 */
    formatKeywords (data) {
      var obj = []
      data.forEach((item, index) => {
        obj.push(item.value)
      })
      return obj.join(';')
    },
    /* 格式化表单数据 */
    formatKeywordCounts (data) {
      var obj = []
      data.forEach((item, index) => {
        obj.push(item.count)
        this.keywordAmount = this.keywordAmount + parseInt(item.count)
      })
      return obj.join(';')
    },
    // 表单提交
    dataFormSubmit () {
      if (this.dataForm.amount === null || this.dataForm.amount === '') {
        this.$message.error('任务数量不能为空')
        return
      }
      if (this.dataForm.priceFrom && this.dataForm.priceTo && parseFloat(this.dataForm.priceFrom) > parseFloat(this.dataForm.priceTo)) {
        this.$message.error('价格区间开始必须比结束小！')
        return
      }
      if (this.dataForm.price <= 0) {
        this.$message.error('本金输入有误!')
        return
      }
      if (this.dataForm.weight < 0.05 || this.dataForm.weight > 40) {
        this.$message.error('宝贝重量必须大于0.05kg,小于40kg!')
        return
      }
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.dataForm.keyword = this.formatKeywords(this.dataForm.keywords)
          // 校验关键词总和数量要等于任务数量
          this.keywordCounts = this.formatKeywordCounts(this.dataForm.keywords)
          /*console.log('this.keywordAmount = ')
          console.log(this.keywordAmount)
          console.log('this.dataForm.amount = ')
          console.log(this.dataForm.amount)*/
          if (this.keywordAmount != this.dataForm.amount) {
            this.keywordAmount = 0
            this.$message.error('关键词总和数量要必须等于任务数量！')
            return
          }
          this.$http({
            url: this.$http.adornUrl(`/app/seller/task/save`),
            method: 'post',
            data: this.$http.adornData({
              'sellerId': this.dataForm.sellerId,
              'shopId': this.dataForm.shopId,
              'img': this.dataForm.img,
              'img2': this.dataForm.img2,
              'img3': this.dataForm.img3,
              'shopName': this.dataForm.shopName,
              'url': this.dataForm.url,
              'style': this.dataForm.style,
              'price': this.dataForm.price,
              'priceFrom': this.dataForm.priceFrom,
              'priceTo': this.dataForm.priceTo,
              'weight': this.dataForm.weight,
              'keyword': this.dataForm.keyword,
              'keywordCounts': this.keywordCounts,
              'taskStyle': this.taskStyle,
              'order': this.order,
              'intervalBegin': this.dataForm.intervalBegin,
              'intervalEnd': this.dataForm.intervalEnd,
              'beginTime': this.dataForm.beginTime,
              'amount': this.dataForm.amount,
              'interval': this.dataForm.interval,
              'note': this.dataForm.note,
              'otherNote': this.dataForm.otherNote
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.visible = false
                  this.$emit('refreshDataList')
                  this.active = 1
                  this.step = 1
                }
              })
              new Audio(
                'http://tts.baidu.com/text2audio?cuid=baiduid&lan=zh&ctp=1&pdt=311&tex=您已经成功放置' + this.dataForm.amount + '单任务！'
              ).play()
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
    },
    next () {
      if (this.active === 1) {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.active++
            this.step = this.active
            this.changeLabel(0)
          }
        })
      } else if (this.active === 2) {
        this.dataFormSubmit()
      }
    },
    next1 () {
      this.active--
      this.step = this.active
    },
    addKeyWord () {
      // 关键词新增
      if (this.dataForm.keywords.length > 99) {
        this.$message.error('关键字最多支持100个！')
        return
      }
      this.dataForm.keywords.push({
        value: '',
        count: 0
      })
    },
    removeKeyWord (item) {
      // 关键词移除
      var index = this.dataForm.keywords.indexOf(item)
      if (index !== -1) {
        this.dataForm.keywords.splice(index, 1)
      }
    },
    handleRemove (file, fileList) {
      console.log(file, fileList)
      if(fileList.length == 1){
        this.dataForm.img = fileList[0].url
        this.dataForm.img2 = ''
        this.dataForm.img3 = ''
      } else if (fileList.length == 2) {
        this.dataForm.img1 = fileList[0].url
        this.dataForm.img2 = fileList[1].url
        this.dataForm.img3 = ''
      } else if (fileList.length == 3) {
        this.dataForm.img = fileList[0].url
        this.dataForm.img2 = fileList[1].url
        this.dataForm.img3 = fileList[2].url
      } else {
        this.dataForm.img = ''
        this.dataForm.img2 = ''
        this.dataForm.img3 = ''
      }
    },
    handlePictureCardPreview (file) {
      this.dialogVisible = true
      this.dataForm.url = file.url
    },
    // 上传之前
    beforeUploadHandle (file) {
      if (file.type !== 'image/jpg' && file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif') {
        this.$message.error('只支持jpg、png、gif格式的图片！')
        return false
      }
    },
    // 上传成功
    successHandle (response, file, fileList) {
      this.fileList = fileList
      if (response && response.code === 0) {
        if(fileList.length == 1){
          this.dataForm.img = fileList[0].response.url
          this.dataForm.img2 = ''
          this.dataForm.img3 = ''
        } else if (fileList.length == 2) {
          this.dataForm.img1 = fileList[0].response.url
          this.dataForm.img2 = fileList[1].response.url
          this.dataForm.img3 = ''
        } else if (fileList.length == 3) {
          this.dataForm.img = fileList[0].response.url
          this.dataForm.img2 = fileList[1].response.url
          this.dataForm.img3 = fileList[2].response.url
        }
      } else {
        this.$message.error(response.msg)
      }
    },
    changeLabel (val) {
      if (val === 0) {
        this.label = '精刷单任务要求'
        this.dataForm.note = `1. 按指定时间进去做任务，切记一定要按要求浏览，尽量用4G;
2. 用关键词货比一到四家(找${this.dataForm.price}元价格左右的店铺)----加购两家----提交订单(加购与提交订单是去别家店铺里做，不要在本店里做);
3. 进店禁止卡价和搜索店铺名进店----先浏览三个以上副宝贝-----再浏览主宝贝（主宝贝浏览时间要3分钟以上）-----看详情看评价---找客服聊天（聊天3句以上）---收藏店铺---加入购物车----从购物车下单---付款---（浏览时间必须在10分钟以上）;
4. 用真实姓名电话地址需签收包裹，一定要等到收到包裹以后再确认收货或等系统自动确认收货。
注意：用支付宝余额或银行卡付款，不要用花呗，淘宝客淘金币和信用卡付款。`
        this.dataForm.otherNote = this.dataForm.otherNote
      } else {
        this.label = '标签任务要求'
        this.dataForm.note = `切记一定要按要求浏览，用4G;
第一天：按指定时间进去做任务
1. 用第一天的关键词搜索货比一到四家(找${this.dataForm.price}元价格左右的店铺)----加购两家-----提交订单-----不进主店
第二天：以第一天指定的时间做任务（如果第一天任务时间是下午三点，那第二天也是下午三点
1. 用第二天的关键词货比三家-----进其他店浏览(找${this.dataForm.price}元价格左右的店铺)----看详情看评价
2. 进店禁止卡价和搜索店铺名进店，先浏览三个以上副宝贝-----再浏览主宝贝（主宝贝浏览时间要3分钟以上）-----看详情看评价---找客服聊天（聊天3句以上）---收藏店铺---加入购物车----从购物车下单---付款---（浏览时间必须在10分钟以上)
3. 用真实姓名电话地址需签收包裹，一定要等到收到包裹以后再确认收货或等系统自动确认收货
注意：用支付宝余额或者银行卡付款，不要用（花呗，淘宝客淘金币和信用卡）`
      }
    }
  }
}
</script>
<style>
.el-dialog__footer{
    text-align: center ;
    margin-top: -40px ;
  }
.el-div{
  margin-bottom: 5px;
}
.form1 .el-form-item__content{
  margin-left:0px !important
}

.task-submit .el-input.is-disabled .el-input__inner {
    text-align: center;
    color: #666;
    margin-right: 20px;
    width:100%;
}
</style>
