<template>
  <el-dialog
    title="指定追评内容"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="100px" :rules="dataRule" @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="追评内容" prop="addCom">
        <el-input type="textarea" v-model="dataForm.addCom" rows = "5" maxlength = "250"></el-input>
      </el-form-item>
      <el-form-item label="图片上传" prop="imgList">
        <el-upload
          :action="url"
          :limit="5"
          list-type="picture-card"
          :file-list="dataForm.fileList"
          :before-upload="beforeUploadHandle"
          :on-preview="handlePictureCardPreview"
          :on-success="successHandle"
          :on-remove="handleRemove">
          <i class="el-icon-plus"></i>
          <div slot="tip" class="el-upload__tip" style="color: red">图片最多上传5张</div>
        </el-upload>
      </el-form-item>
      <!--<el-form-item label="小视频上传" prop="smallVideo">-->
        <!--<el-upload-->
          <!--:action="url"-->
          <!--:show-file-list="false"-->
          <!--:on-success="successVideoHandle"-->
          <!--:before-upload="beforeVideoUploadHandle">-->
          <!--<el-button size="small" type="primary" :loading="dataForm.isUploadVideo === 1">点击上传</el-button>-->
          <!--<div slot="tip" class="el-upload__tip" style="color: red">二手视频不可用，某宝会监控到（3分钟内，大小100M内）</div>-->
        <!--</el-upload>-->
        <!--<video width="320" height="240" controls :src="dataForm.smallVideo" v-if="dataForm.smallVideo"></video>-->
      <!--</el-form-item>-->
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      dataForm: {
        id: '',
        addCom: '',
        isUploadVideo: 0,
        smallVideo: '',
        fileList: [],
        imgList: [],
        missionId: ''
      },
      url: '',
      type: '1',
      dataRule: {
        addCom: [
          {required: true, message: '追评内容不能为空', trigger: 'blur'}
        ],
        imgList: [
          {required: true, message: '追评图片不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.addCom = ''
      this.dataForm.fileList = []
      this.dataForm.imgList = []
      this.dataForm.isUploadVideo = 0
      this.dataForm.smallVideo = ''
      this.dataForm.id = id
      this.visible = true
      this.url = this.$http.adornUrl(`/app/seller/file/upload`)
    },
    handleRemove (file, fileList) {
      this.imgList = fileList.url
      this.dataForm.imgList = []
      for (var i = 0; i < fileList.length; i++) {
        this.dataForm.imgList.push(fileList[i].url)
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
      file.url = response.url
      if (response && response.code === 0) {
        this.dataForm.imgList.push(response.url)
      } else {
        this.$message.error(response.msg)
      }
    },
    // 上传之前
    beforeVideoUploadHandle (file) {
      if (file.size > 100 * 1024 * 1024) {
        this.$message.error('只支持上传小于100M的视频文件！')
        return false
      }
      this.dataForm.isUploadVideo = 1
    },
    // 上传成功
    successVideoHandle (response, file, fileList) {
      file.url = response.url
      if (response && response.code === 0) {
        this.dataForm.smallVideo = response.url
        this.dataForm.isUploadVideo = 2
      } else {
        this.$message.error(response.msg)
      }
    },
    dataFormSubmit () {
      if (this.dataForm.isUploadVideo === 1) {
        this.$message.error('请等候，小视频上传中...')
        return false
      }

      var imgList = this.dataForm.imgList.join(',')
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/app/seller/missiondetail/isComment`),
            method: 'post',
            data: this.$http.adornData({
              'missionId': this.dataForm.id || undefined,
              'addImglist': imgList,
              'addSmallVideo': this.dataForm.smallVideo,
              'addcom': this.dataForm.addCom,
              'flag': 2
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
