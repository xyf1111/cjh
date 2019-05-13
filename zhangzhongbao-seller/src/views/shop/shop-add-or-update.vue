<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="110px">
      <el-form-item label="店铺名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="请输入店铺名称"></el-input>
      </el-form-item>
      <el-form-item label="店铺类目" prop="category">
         <el-cascader
          :options="options"
          v-model="value">
        </el-cascader>
      </el-form-item>
      <el-form-item label="店铺链接" prop="url">
        <el-input v-model="dataForm.url" placeholder="店铺链接"></el-input>
      </el-form-item>
      <el-form-item label="发货地址" prop="address">
        <el-input v-model="dataForm.address" placeholder="发货地址请按照：‘省,市,县(区),详细地址’的格式中间以英文逗号,隔开"></el-input>
      </el-form-item>
      <el-form-item label="平台" prop="platform">
        <el-radio label="1" v-model = "platform">淘宝</el-radio>
        <el-radio label="2" v-model = "platform">天猫</el-radio>
      </el-form-item>
      <el-form-item label="备注" prop="note">
        <el-input v-model="dataForm.note" placeholder="请输入备注"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import {isURL, isAddress} from '@/utils/validate'

export default {
  data () {
    return {
      visible: false,
      platform : '1',
      dataForm: {
        id: 0,
        name: '',
        category: '',
        address: '',
        url: '',
        note: '',
        platform: '1'
      },
      options: [],
      value: [],
      dataRule: {
        name: [
          {required: true, message: '店铺名称不能为空', trigger: 'blur'}
        ],
        categoryId: [
          {required: true, message: '店铺类目不能为空', trigger: 'blur'}
        ],
        url: [
          {required: true, message: '店铺链接不能为空', trigger: 'blur'}
        ],
        address: [
          {required: true, message: '发货地址不能为空', trigger: 'blur'}
        ],
        platform: [
          { required: true, message: '平台类型不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getCategory()
  },
  methods: {
    init (id) {
      this.dataForm.id = id || 0
      this.visible = true
    },
    // 表单提交
    dataFormSubmit () {
      this.dataForm.platform = this.platform
      if (!isURL(this.dataForm.url)) {
        this.$message.error('请输入正确的店铺链接!')
        return
      }
      if (!isAddress(this.dataForm.address)) {
        this.$message.error('地址格式输入有误！地址请按照：‘省,市,县(区),详细地址’的格式中间以英文逗号,隔开')
        return
      }
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/app/seller/shop/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'name': this.dataForm.name,
              'categoryIds': this.value,
              'url': this.dataForm.url,
              'address': this.dataForm.address,
              'note': this.dataForm.note,
              'platform': this.dataForm.platform
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
                  this.dataForm = {}
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
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
