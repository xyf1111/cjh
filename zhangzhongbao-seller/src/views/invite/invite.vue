<template>
    <div>
          邀请商家链接：<el-input class="invite-url" v-model="url"></el-input>
      <!-- Trigger -->
      <el-button class="btn" :data-clipboard-text="url" @click="copy">点击复制</el-button>
    </div>
</template>

<script>
    import Clipboard from 'clipboard'

    export default {
        name: "invite",
        data(){
          return{
            url:'',
            mobile:'',
          }
        },
        activated(){
          this.mobile = this.$cookie.get("sellerMobile")
          // this.url = 'http://'+window.location.hostname+':8088/seller/#/register?type=1&mobile='+this.mobile
          let hostname = window.location.hostname
          let port = window.location.port
          this.url = `http://${port === '80' ? hostname : hostname + ':' + port}/seller/#/register?type=2&mobile=${this.mobile}`
        },
        methods: {
          //复制评价
          copy () {
            const clipboard = new Clipboard('.btn')
            clipboard.on('success', e => {
              console.log('复制成功')
              // 释放内存
              clipboard.destroy()
            })
            clipboard.on('error', e => {
              // 不支持复制
              console.log('该浏览器不支持自动复制')
              // 释放内存
              clipboard.destroy()
            })
          }
        }
    }
</script>

<style>
     .invite-url{
        width: 50%;
        margin-top: 5px;
     }
</style>
