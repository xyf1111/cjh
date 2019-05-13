<template>
  <el-dialog
    title="追评内容"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="80px">
      <el-form-item label="追评内容">
        <el-input type="textarea" v-model="dataForm.addCom" rows = "5" maxlength = "250"></el-input>
      </el-form-item>
      <el-form-item label="详情">
        <img :src = img  v-for="img in dataForm.imgList" style="width:200px;height:200px;margin: 10px 10px">
      </el-form-item>
      <el-form-item label="小视频" prop="smallVideo" v-if="dataForm.smallVideo">
        <video width="320" height="240" controls :src="dataForm.smallVideo"></video>
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
        comment: '',
        smallVideo: '',
        imgList: []
      },
      type: '1'
    }
  },
  methods: {
    init (addCom, imgList, smallVideo) {
      this.visible = true
      this.dataForm.addCom = addCom
      this.dataForm.imgList = imgList
      this.dataForm.smallVideo = smallVideo
    }
  }
}
</script>
