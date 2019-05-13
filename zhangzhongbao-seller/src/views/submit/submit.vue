<template>
  <el-form ref="form" :rules="dataRule" :model="form" label-width="80px" class="submit-form">
    <h2>充值提交</h2>
    <p style="text-align: center;">第一步：请先转账到平台官方账户</p>
      <div v-for="item in payBank">
        <p style="text-align: center;">开户名：{{item.holder}}</p>
        <p style="text-align: center;">开户行：{{item.bank}}</p>
        <p style="text-align: center;">账号：{{item.cardNumber}}</p>
        <p style="text-align: center;">— —— — —— — —— — —— — —— —
          —— — —— — ——</p>
      </div>
    <p style="text-align: center;">【财务工作时间：早上九点 -- 下午六点】</p>
    <p style="text-align: center;">第二步：请确认提交转账金额</p>
    <p style=" color: red ;text-align: center;">恶意提交，未打款后提交，将罚款100元/次，甚至封号</p>
    <el-form-item label="转账金额" prop="amount">
      <el-input v-model="form.amount" placeholder="转账金额"/>
    </el-form-item>
    <el-form-item label="中文大小写" >
      <el-input v-model="money" disabled></el-input>
    </el-form-item>
    <el-form-item label="打款人" prop="name">
      <el-input v-model="form.name" placeholder="打款人姓名"/>
    </el-form-item>
    <el-form-item label="汇款银行" prop="bank">
      <el-input v-model="form.bank" placeholder="汇款银行"/>
    </el-form-item>
    <h5 class="tit">其他汇款方式直接填写支付宝和微信</h5>
    <div :style="alipayBarCodeDiv"></div>
    <el-form-item label="汇款凭证" prop="img">
      <el-upload
        :action="url"
        :limit="1"
        list-type="picture-card"
        :file-list="form.fileList"
        :before-upload="beforeUploadHandle"
        :on-preview="handlePictureCardPreview"
        :on-success="successHandle"
        :on-remove="handleRemove">
        <i class="el-icon-plus"></i>
      </el-upload>
    </el-form-item>
    <el-form-item label="平台留言">
      <el-input v-model="form.note" placeholder="平台留言"/>
    </el-form-item>
     <el-form-item>
       <el-button type="primary" @click="onSubmit" :loading="submitLoading">提交</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data () {
    return {
      alipayBarCodeDiv:{
        width: '200px',
        height: '250px',
        textAlign: 'center',
        margin: 'auto',
        backgroundImage: 'url('+require('@/assets/barcode_alipay.jpg')+')',
        backgroundSize: '100% 100%',
        backgroundRepeat: 'no-repeat'
      },
      validate: false,
      dialogVisible: false,
      submitLoading: false,
      form: {
        amount: '',
        note: '',
        name: '',
        bank: '',
        img: '',
        delivery: false,
        fileList: []
      },
      payBank: [],
      isSeller: '1',
      agentId: '',
      url: '',
      dataRule: {
        amount: [
          {required: true, message: '打款金额不能为空', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '打款人姓名不能为空', trigger: 'blur'}
        ],
        bank: [
          {required: true, message: '汇款银行不能为空', trigger: 'blur'}
        ],
        img: [
          {required: true, message: '汇款凭证不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  computed: {
    money () {
      var n = this.form.amount
      if (!n) {
        return ''
      }
      var fraction = ['角', '分']
      var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖']
      var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟'] ]
      var head = n < 0 ? '欠' : ''
      n = Math.abs(n)
      var s = ''
      for (var i = 0; i < fraction.length; i++) {
        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '')
      }
      s = s || '整'
      n = Math.floor(n)

      for (var i = 0; i < unit[0].length && n > 0; i++) {
        var p = ''
        for (var j = 0; j < unit[1].length && n > 0; j++) {
          p = digit[n % 10] + unit[1][j] + p
          n = Math.floor(n / 10)
        }
        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s
      }
      return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整')
    }
  },
  created () {
    // this.isSeller = this.$cookie.get('isSeller')
    // if(this.isSeller == 3){
    //   this.agentId = this.$cookie.get('pid')
    // }
    this.init()
    this.getPayBank()
  },
  methods: {
    init (id) {
      this.url = this.$http.adornUrl(`/app/seller/file/upload`)
    },
    onSubmit () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.submitLoading = true
          this.$http({
            url: this.$http.adornUrl(`/app/seller/charge/save`),
            method: 'post',
            data: this.$http.adornData({
              'amount': this.form.amount,
              'note': this.form.note,
              'name': this.form.name,
              'img': this.form.img,
              'isSeller': 1,
              // 'agentId': this.agentId,
              'bank': this.form.bank
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.form = {}
                }
              })
              new Audio(
                'http://tts.baidu.com/text2audio?cuid=baiduid&lan=zh&ctp=1&pdt=311&tex=您已经成功充值金额：' + this.form.amount + '元！'
              ).play()
            } else {
              this.$message.error(data.msg)
            }
            this.submitLoading = false
          })
        }
      })
    },
    handleRemove (file, fileList) {
      console.log(file, fileList)
    },
    handlePictureCardPreview (file) {
      this.dialogVisible = true
      this.form.url = file.url
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
      file.url = response.url
      if (response && response.code === 0) {
        this.form.img = file.url
      } else {
        this.$message.error(response.msg)
      }
    },
    getPayBank () {
      this.$http({
        url: this.$http.adornUrl(`/app/agent/agentBank/getPayBank`),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.payBank = data.pay
        }
      })
      // if(this.isSeller == 3){
      //     this.$http({
      //       url: this.$http.adornUrl(`/app/agent/agentBank/list`),
      //       method: 'get',
      //       params: this.$http.adornParams()
      //     }).then(({data}) => {
      //       if (data && data.code === 0) {
      //         this.payBank = data.page.list
      //       }
      //     })
      // }else{
      //   this.$http({
      //     url: this.$http.adornUrl(`/app/agent/agentBank/getPayBank`),
      //     method: 'get',
      //     params: this.$http.adornParams()
      //   }).then(({data}) => {
      //     if (data && data.code === 0) {
      //       this.payBank = data.pay
      //     }
      //   })
      // }
    }
  }
}
</script>
<style>
  .submit-form .el-form-item {
    width: 70%;
    margin: auto;
    margin-top: 20px;
  }

  .el-form-item__label {
    font-size: 13px;
  }

  .el-form .tit {
    text-align: center !important;
  }
</style>
