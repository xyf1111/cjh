<template>
  <!--背景图-->
  <div class="note" :style="note">

    <!--login框，表单+tab标签页的组合-->
    <div class="loginFrame">
      <!--表单组件放在外面，标签栏在里面-->
      <el-form ref="dataForm" :model="dataForm" :rules="rules" class="demo-ruleForm login-container">
        <div class="formGroup">
          <el-form-item label="账号" prop="mobile">
            <el-input v-model="dataForm.mobile" type="text" auto-complete="off" placeholder="请输入您的账号"
                      class="form-control"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" class="form-inline">
            <el-input v-model="dataForm.password" type="password" auto-complete="off" placeholder="请输入密码"
                      class="form-control"></el-input>
          </el-form-item>
        </div>
        <div class="remFor">
          <label   class="remember"  @click = "toRegister()">商家注册</label>
          <label  class="forget" @click = "toLink()">忘记密码？</label>
        </div>
        <div class="formButton">
          <el-form-item style="width:100%;">
            <el-button type="primary" style="width:100%;" @click.native.prevent="login" :loading="logining">登录
            </el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>

</template>

<script>
export default {
  data () {
    return {
      logining: false,
      note: {
        position: 'absolute',
        top: '0px',
        left: '0px',
        width: '100%',
        height: '100%',
        backgroundImage: 'url(' + require('@/assets/login_bg.jpg') + ')',
        backgroundSize: '100% 100%',
        backgroundRepeat: 'no-repeat'
      },
      dataForm: {
        mobile: '',
        password: ''
      },
      rules: {
        mobile: [
          {required: true, message: '请输入账号', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ]
      },
      checked: false
    }
  },
  methods: {
    login () {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl('/app/seller/login'),
            method: 'post',
            data: this.$http.adornData({
              'mobile': this.dataForm.mobile,
              'password': this.dataForm.password
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$cookie.set('sellerToken', data.data.token)
              this.$cookie.set('isSeller', data.data.isSeller)
              this.$cookie.set('pid', data.data.pid)
              this.$cookie.set('sellerId', data.data.sellerId)
              this.$cookie.set('sellerMobile', data.data.mobile)
              this.$router.push({name: 'Index'})
            } else {
              this.$message.error(data.msg)
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    toLink () {
      this.$router.push({name: 'Forget'})
    },
    toRegister () {
      this.$router.push({path: '/register'})
    }
  }
}
</script>

<style>
  .login-container {
    -webkit-border-radius: 5px;
    border-radius: 15px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: rgba(255, 255, 255, 0.7);
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  label {
    width: 70px;
    text-align: left;
  }

  .form-control {
    width: 270px;
    flex: 1;
    -webkit-flex: 1;
    -ms-flex: 1;
  }

  .remember {
    width: 250px;
    text-align: left;
    margin-right: 170px;
  }

  .forget {
    width: 500px;
    text-align: right;
    font-size: 14px;
    font-family: PingFang SC;
  }

  .remFor {
    margin-bottom: 10px;
    padding-bottom: 10px;
  }

  .tabsUser {
    display: inline-block;
    margin-left: 0px;
    margin-right: 0px;
    text-align: center;
    font-size: 25px;
  }
</style>
