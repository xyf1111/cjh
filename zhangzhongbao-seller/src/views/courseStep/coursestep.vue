<template>
  <div v-html="content">
      {{content}}
  </div>
</template>

<script>
  export default {
    name: 'courseStep',
    data () {
      return {
        content : ''
      }
    },
    activated () {
      this.init()
    },
    methods: {
      init () {
        this.$http({
          url: this.$http.adornUrl(`/app/seller/coursestep/info/1`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.content = data.courseStep.content
          }
        })
      }
    }
  }
</script>

<style >
  .mod-demo-ueditor {
    position: relative;
    z-index: 510;
    > .el-alert {
      margin-bottom: 10px;
    }
  }
</style>
