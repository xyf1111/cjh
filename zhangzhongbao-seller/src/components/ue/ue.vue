<template>
  <div>
    <script :id="this.id" type="text/plain"></script>
  </div>
</template>
<script>
  export default {
    name: 'UE',
    data () {
      return {
        editor: null
      }
    },
    props: {
      id: {
        type: String
      },
      defaultMsg: {
        type: String
      },
      config: {
        type: Object
      }
    },
    mounted () {
      const _this = this
      this.editor = window.UE.getEditor(this.id, this.config)  // 初始化UE
      this.editor.addListener('ready', function () {
        _this.editor.setContent(_this.defaultMsg)   // 确保UE加载完成后，放入内容。
        _this.editor.container.style.zIndex = 2222
      })
    },
    methods: {
      getUEContent () {
        // 获取内容方法
        return this.editor.getContent()
      },
      setUEContent (content) {
        // 设置内容
        this.editor.setContent(content)
      }
    },
    destroyed () {
      this.editor.destroy()
    }
  }
</script>
