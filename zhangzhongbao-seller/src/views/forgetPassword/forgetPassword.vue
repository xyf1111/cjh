<template>
  <el-form ref="form" :rules="dataRule" :model="form" label-width="80px" class="forget-form">
    <h2>忘记密码</h2>
    <el-form-item label="手机号" prop="mobile" style="text-align: left;">
      <el-input  v-model="form.mobile" placeholder="请输入手机号"></el-input>
      <el-button type="primary" @click="getSmsCode()" style="margin-top: 10px;">获取验证码</el-button>
    </el-form-item>
    <el-form-item label="验证码" prop="smsCode">
      <el-input  v-model="form.smsCode" placeholder="请输入验证码  "></el-input>
    </el-form-item>
    <el-form-item label="新密码" prop="newPassword">
      <el-input type="password" v-model="form.newPassword" placeholder="新密码"/>
    </el-form-item>
    <el-form-item label="确认密码" prop="confirmPassword">
      <el-input type="password" v-model="form.confirmPassword" placeholder="确认密码"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">提交</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {isMobile} from '@/utils/validate'
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
      form: {
        mobile:'',
        smsCode:'',
        newPassword: '',
        confirmPassword: '',
        delivery: false
      },
      dataRule: {
        mobile: [
          {required: true, message: '手机号不能为空', trigger: 'blur'}
        ],
        smsCode: [
          {required: true, message: '短信验证码不能为空', trigger: 'blur'}
        ],
        newPassword: [
          {required: true, message: '新密码不能为空', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: '确认密码不能为空', trigger: 'blur'},
        ]
      }
    }
  },
  methods: {
    onSubmit () {
      this.confirm()
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/app/seller/user/forgetPassword`),
            method: 'post',
            data: this.$http.adornData({
              'mobile':this.form.mobile,
              'smsCode':this.form.smsCode,
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
    },
    getSmsCode(){
      this.confirm()
       this.$http({
        url: this.$http.adornUrl(`/app/smsCode/get/`+this.form.mobile),
        method: 'get'
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message({
            message: '验证码已发送',
            type: 'success',
            duration: 1500,
            onClose: () => {
            }
          })
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    confirm(){
       if(this.form.mobile == null || this.form.mobile == ''){
            return this.$message.error("手机号码不能为空!")
          }
         if(!isMobile(this.form.mobile)){
             return this.$message.error("手机格式错误!")
          }
    }
  }
}
</script>
<style>
  .forget-form .el-form-item {
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
