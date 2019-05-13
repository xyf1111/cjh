<template >
  <el-dialog title="查看"  :visible.sync="visible" width="40%">
        <el-input type="textarea" v-model="this.dataForm.note" :rows="2"></el-input>
   </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          note: ''
        }
      }
    },
    methods: {
      init (note) {
        this.visible = true
        this.dataForm.note = note
      }
    }
  }
</script>
