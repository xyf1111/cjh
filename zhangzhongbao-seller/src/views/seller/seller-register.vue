<template>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px" class="reg-el-form">
    <h1>{{isPermitBind === 0 ? '商家注册': '绑定代理商'}}</h1>
    <el-form-item label="上级手机号" >
      <el-input v-model="dataForm.pidMobile" disabled style="text-align: center;margin-right: 20px"></el-input>
    </el-form-item>
      <el-form-item label="注册手机号" prop="mobile">
        <!--<el-input v-model="dataForm.mobile" placeholder="商家手机号" @change="bindAgentCheck"></el-input>-->
        <el-input v-model="dataForm.mobile" placeholder="商家手机号"></el-input>
      </el-form-item>
    <el-form-item label="商家联系人姓名" prop="contact" v-if="isPermitBind === 0">
      <el-input v-model="dataForm.contact" placeholder="商家联系人姓名"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input type="password" v-model="dataForm.password" placeholder="商家账号密码"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="passwordAgain" v-if="isPermitBind === 0">
      <el-input type="password" v-model="dataForm.passwordAgain" placeholder="商家账号确认密码"></el-input>
    </el-form-item>
    <el-form-item label="QQ" prop="qq" v-if="isPermitBind === 0">
      <el-input v-model="dataForm.qq" placeholder="请输入QQ"></el-input>
    </el-form-item>
    <el-form-item label="店铺名称" prop="shopName" v-if="isPermitBind === 0">
      <el-input v-model="dataForm.shopName" placeholder="店铺名称"></el-input>
    </el-form-item>
    <el-form-item label="店铺类目" prop="categoryId" style="width: 50%;" v-if="isPermitBind === 0">
      <el-cascader :options="options" v-model="value"></el-cascader>
    </el-form-item>
    <el-form-item label="店铺链接" prop="url" v-if="isPermitBind === 0">
      <el-input v-model="dataForm.url" placeholder="店铺链接"></el-input>
    </el-form-item>
      <el-form-item label="发货地址" prop="address" v-if="isPermitBind === 0">
        <el-input v-model="dataForm.address" placeholder="发货地址请按照：‘省,市,县(区),详细地址’的格式中间以英文逗号,隔开"></el-input>
      </el-form-item>
    <el-form-item label="平台" prop="platform" v-if="isPermitBind === 0">
      <el-radio label="1" v-model = "platform">淘宝</el-radio>
      <el-radio label="2" v-model = "platform">天猫</el-radio>
      <!--<el-radio label="3" v-model = "platform">京东</el-radio>-->
    </el-form-item>
    <el-form-item label="备注" prop="note" v-if="isPermitBind === 0">
      <el-input v-model="dataForm.note" placeholder="请输入备注"></el-input>
    </el-form-item>
      <el-button type="primary" @click="dataFormSubmit()" :loading="submitLoading">确定</el-button>
    </el-form>
</template>

<script>
import {isURL, isAddress} from '@/utils/validate'
export default {
  name: 'Register',
  data () {
    return {
      platform: '1',
      isPermitBind: 0,
      submitLoading: false,
      dataForm: {
        id: 0,
        contact: '',
        mobile: '',
        password: '',
        wechat: '',
        note: '',
        qq: '',
        balance: '',
        limit: '',
        benefit: '',
        amount: '',
        status: '',
        type: '',
        pidMobile: ''
      },
      options: [],
      value: [],
      dataRule: {
        contact: [
          { required: true, message: '商家联系人姓名不能为空', trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '商家手机号不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '商家账号密码不能为空', trigger: 'blur' }
        ],
        passwordAgain: [
          { required: true, message: '商家账号确认密码不能为空', trigger: 'blur' }
        ],
        qq: [
          { required: true, message: 'QQ不能为空', trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: '商家类目不能为空', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '商家链接不能为空', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '发货地址不能为空，请按照：‘省,市,县(区),详细地址’的格式中间以英文逗号,隔开', trigger: 'blur' }
        ],
        platform: [
          { required: true, message: '平台不能为空', trigger: 'blur' }
        ],
        shopName: [
          { required: true, message: '店铺名称不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.dataForm.type = this.$route.query.type
    this.dataForm.pidMobile = this.$route.query.mobile
    this.getCategory()
  },
  methods: {
    bindAgentCheck (value) {
      if (this.dataForm.type === '3') {
        this.$http({
          url: this.$http.adornUrl(`/app/seller/user/bindAgentCheck/${value}`),
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            if (data.isPermitBind === 1) {
              this.$confirm('您的手机号码已注册商家，是否进行绑定代理商？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                this.isPermitBind = data.isPermitBind
              })
            }
          } else {
            this.$message.error(data.msg)
          }
        })
      }
    },
    // 表单提交
    dataFormSubmit () {
      if (this.isPermitBind === 1) {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.submitLoading = true
            this.$http({
              url: this.$http.adornUrl(`/app/seller/user/bindAgent`),
              method: 'post',
              data: this.$http.adornData({
                'mobile': this.dataForm.mobile,
                'password': this.dataForm.password,
                'agentMobile': this.dataForm.pidMobile
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功!',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.submitLoading = false
                    this.$router.push({name: 'Login'})
                  }
                })
              } else {
                this.$message.error(data.msg)
                this.submitLoading = false
              }
            })
          }
        })
      } else {
        this.dataForm.isVip = this.isVip
        this.dataForm.publishable = this.publishable
        this.dataForm.platform = this.platform
        if (this.dataForm.password !== this.dataForm.passwordAgain) {
          this.$message.error('前后密码不一致!')
          return
        }
        if (!isURL(this.dataForm.url)) {
          this.$message.error('请输入正确的店铺链接!')
          return
        }
        if (!isAddress(this.dataForm.address)) {
          this.$message.error('地址格式输入有误！请按照：‘省,市,县(区),详细地址’的格式中间以英文逗号,隔开')
          return
        }
        this.dataForm.categoryId = this.value.join(',')
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.submitLoading = true
            this.$http({
              url: this.$http.adornUrl(`/app/seller/user/save`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'contact': this.dataForm.contact,
                'mobile': this.dataForm.mobile,
                'password': this.dataForm.password,
                'passwordAgain': this.dataForm.passwordAgain,
                'qq': this.dataForm.qq,
                'note': this.dataForm.note,
                'limit': this.dataForm.limit,
                'platform': this.dataForm.platform,
                'url': this.dataForm.url,
                'address': this.dataForm.address,
                'shopName': this.dataForm.shopName,
                'pidMobile': this.dataForm.pidMobile,
                'type': this.dataForm.type,
                'flag': 2,
                'categoryId': this.dataForm.categoryId
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功,等待平台审核!',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.submitLoading = false
                    this.$router.push({name: 'Login'})
                  }
                })
              } else {
                this.submitLoading = false
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    },
    getCategory () {
      this.$http({
        url: this.$http.adornUrl(`/app/seller/category/getList`),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        this.options = data
      })
    }
  }
}
</script>
<style>
  .reg-el-form{
    width: 50%;
    margin: auto;
  }
</style>
