<template >
  <el-dialog title="报错提交"  :visible.sync="visible" width="40%">
    <el-form :model="dataForm" ref="dataForm" label-width="80px" :rules="dataRule" @keyup.enter.native="submit()">
      <el-form-item label="报错备注" prop="badInfo">
          <el-input type="textarea" v-model="dataForm.badInfo"></el-input>
      </el-form-item>
     </el-form>
   <span slot="footer" class="dialog-footer">
     <el-button @click="visible = false">取消</el-button>
     <el-button type="primary" @click="submit()">确定</el-button>
   </span>
   </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          userId: '',
          missionCode: '',
        },
        dataRule: {
            badInfo: [
              {required: true, message: '报错内容不能为空', trigger: 'blur'}
            ]
          }
      }
    },
    methods: {
      init (id,userId,missionCode) {
        this.dataForm.id = id
        this.dataForm.userId = userId
        this.dataForm.missionCode = missionCode
        this.visible = true
      },
      submit(){
         this.$refs['dataForm'].validate((valid) => {
            if (valid) {
         this.$http({
              url: this.$http.adornUrl(`/app/seller/missiondetail/updateBad`),
              method: 'post',
              data: this.$http.adornData({
                'missionId': this.dataForm.id || undefined,
                'badInfo':this.dataForm.badInfo,
                'isBad':2,
                'userId':this.dataForm.userId,
                'missionCode':this.dataForm.missionCode
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
      }
    }
  }
</script>
