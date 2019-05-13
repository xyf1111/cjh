<template>
  <el-container class='home' style="height: 100vh; border: 1px solid #eee">
    <el-aside width="201px" style="background-color: #fff; border-right: solid 1px #f1f1f1;">
      <el-menu  style="border-right: none" router>
        <el-menu-item index="/index">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span slot="title">系统首页</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/inform">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span slot="title">系统公告</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/shop">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span slot="title">店铺管理</span>
          </template>
        </el-menu-item>
        <el-submenu index="2" >
          <template slot="title"><i class="el-icon-location"></i>任务管理</template>
          <el-menu-item-group>
            <el-menu-item index="/taskMould">任务模板</el-menu-item>
            <el-menu-item index="/task">任务列表</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-menu-item index="/mission">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span slot="title">订单管理</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/address">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span slot="title">发货管理</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/review">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span slot="title">指定评价</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/appoint">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span slot="title">追加评价</span>
          </template>
        </el-menu-item>
        <!--<el-menu-item index="/paymoney">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span slot="title">打款管理</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/courseStep">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span slot="title">打款教程</span>
          </template>
        </el-menu-item>-->
        <el-menu-item index="/record">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span slot="title">充值记录</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/submit">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span slot="title">充值提交</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/detail">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span slot="title">往来明细</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/setpassword">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span slot="title">我的资料</span>
          </template>
        </el-menu-item>
<!--        <el-submenu index="3" >
          <template slot="title"><i class="el-icon-message"></i>下级商家管理</template>
          <el-menu-item-group>
            <el-menu-item index="/seller">商家管理</el-menu-item>
            <el-menu-item index="/lowerDetail">奖励流水</el-menu-item>
          </el-menu-item-group>
        </el-submenu>-->
      </el-menu>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px">
      <span style="margin-right: 50px;font-size: 20px;">元宝：{{this.seller.balance}}</span>
        <el-dropdown>
          <i class="el-icon-setting" style="margin-right: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <!--<el-dropdown-item @click.native="activated()">链接</el-dropdown-item>-->
            <el-dropdown-item @click.native="logoutHandle()">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <span>{{this.seller.contact}}</span>
      </el-header>

      <el-main style="background: #f5f5f5;">
        <div  style="background: #fff;height: 89vh;overflow: auto">
          <keep-alive>
            <router-view />
          </keep-alive>
        </div>
      </el-main>
    </el-container>
  </el-container>
  </template>

<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }
  .home .el-form{
     text-align: left !important;
     margin-top: 10px;
     margin-left: 25px;
  }
  .el-pagination{
    text-align: right;
    margin-top: 20px;
    margin-right: 30px;
  }
</style>

<script>
export default {
  data () {
    return {
      name: '',
      seller: {},
      mobile: '',
      url: ''
    }
  },
  created () {
    this.getBalance()
  },
  methods: {
    logoutHandle () {
      this.$cookie.delete('sellerToken')
      this.$router.push({name: 'Login'})
    },
    activated () {
      this.mobile = this.$cookie.get('sellerMobile')
      // this.url = 'http://'+window.location.hostname+':8088/seller/#/register?type=1&mobile='+this.mobile
      let hostname = window.location.hostname
      let port = window.location.port
      this.url = `http://${port === '80' ? hostname : hostname + ':' + port}/seller/#/register?type=2&mobile=${this.mobile}`
      this.$confirm('我的商家邀请链接：' + this.url, '提示', {
        type: 'warning'
      })
    },
    getBalance () {
      this.$http({
        url: this.$http.adornUrl(`/app/seller/user/getBalance`),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.seller = data.seller
          this.seller.balance = data.seller.balance.toFixed(2)
        }
      })
    }
  }
}
</script>
