<template>
  <el-dialog
    title="评价内容"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="80px">
      <el-form-item label="评价内容" prop="refuseNote">
        <el-input type="textarea" v-model="dataForm.comment" rows = "5"></el-input>
      </el-form-item>
      <el-form-item label="详情" prop="removeNote">
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
          imgList: [],
          smallVideo: ''
        },
        type: '1'
      }
    },
    methods: {
      init (comment, imgList, smallVideo) {
        this.visible = true
        this.dataForm.comment = comment
        this.dataForm.imgList = imgList
        this.dataForm.smallVideo = smallVideo
      }
    }
  }
</script>
