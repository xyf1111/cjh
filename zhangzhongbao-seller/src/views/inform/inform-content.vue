<template>
  <el-dialog
    title="查看"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width = "80%">
    <el-form :model="dataForm" label-width="80px">
      <el-form-item label="公告标题" prop="title">
        <el-input v-model="dataForm.title" placeholder="标题"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content">
      <div v-html="dataForm.content" style="border: solid 1px #cccc; padding: 10px;"></div>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
    </span>
  </el-dialog>
</template>

<script>

  export default {
    data() {
      return {
        visible: false,
        dataForm: {
          id: 0,
          content: '',
          title: ''
        },
      }
    },
    methods: {
      init(id, title, content) {
        this.visible = true
        this.dataForm.title = title
        this.dataForm.content = content
      }
    }
  }
</script>

<style>
  .components-container {
    line-height: 20px;
  }
</style>
