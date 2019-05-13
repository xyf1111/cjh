<template>
  <el-form ref="form" class='aaa' :rules="dataRule" :model="form" label-width="80px" style="width: 60%; margin: auto;">
    <div class="password-div">
      <h2>修改密码</h2>
      <el-form-item label="旧密码" prop="password">
        <el-input type="password" v-model="form.password" placeholder="旧密码"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword" style="margin-top: 20px;">
        <el-input type="password" v-model="form.newPassword" placeholder="新密码"/>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword" style="margin-top: 20px;">
        <el-input type="password" v-model="form.confirmPassword" placeholder="确认新密码"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </el-form-item>
    </div>
    <br/>
    <br/>
    <br/>
    <div class="password-div">
      <h2>我的商家邀请链接：</h2>
      <el-input v-model="url"/>
      <br/>
      复制您的商家专用邀请链接，邀请好友成功注册掌中宝平台商家账号之后，
      <br/>你邀请的商家每放一单，并且任务全部完成了系统自动提成1元自动到账。
      <br/>
    </div>
    <div class="password-div">
      <h2>请注意：</h2>
      <span style="color:red">
        1：邀请链接只能发布于聊天工具中，如微信，朋友圈，QQ，QQ动态等等，禁止推广于外部网站，如微博，博客，贴吧等等的各大网站，平台会定期核实，如有发现一律邀请链接作废！<br/>
        2：任何会员不得利用此活动，自己邀请自己获得返利；一旦平台排查出这种情况，违规获得的邀请返利将不予以提现。
      </span>
    </div>
  </el-form>
</template>

<script>
export default {
  data () {
    var validateConfirmPassword = (rule, value, callback) => {
      if (this.form.newPassword !== value) {
        callback(new Error('确认密码与新密码不一致'))
      } else {
        callback()
      }
    }
    return {
      url: '',
      mobile: '',
      validate: false,
      visible: false,
      dialogVisible: false,
      form: {
        password: '',
        newPassword: '',
        confirmPassword: '',
        delivery: false
      },
      dataRule: {
        password: [
          {required: true, message: '旧密码不能为空', trigger: 'blur'}
        ],
        newPassword: [
          {required: true, message: '新密码不能为空', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: '新密码不能为空', trigger: 'blur'},
          {validator: validateConfirmPassword, trigger: 'blur'}
        ]
      }
    }
  },
  created () {
    this.init()
  },
  methods: {
    init (id) {
      this.mobile = this.$cookie.get('sellerMobile')
      // this.url = 'http://'+window.location.hostname+':8088/seller/#/register?type=1&mobile='+this.mobile
      let hostname = window.location.hostname
      let port = window.location.port
      this.url = `http://${port === '80' ? hostname : hostname + ':' + port}/seller/#/register?type=2&mobile=${this.mobile}`
      this.visible = true
      this.$nextTick(() => {
        this.$refs['form'].resetFields()
      })
    },
    onSubmit () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/app/seller/user/update`),
            method: 'post',
            data: this.$http.adornData({
              'password': this.form.password,
              'newPassword': this.form.newPassword,
              'confirmPassword': this.form.confirmPassword
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
    }
  }
}
</script>
<style>
  .aaa .el-form-item {
    width: 70%;
    margin: auto;
    margin-top: 12px;
  }

  .el-form-item__label {
    font-size: 13px;
  }

  .el-form .tit {
    text-align: center !important;
  }
  .password-div{
    text-align: center;
    margin-top: 20px;
  }
</style>
