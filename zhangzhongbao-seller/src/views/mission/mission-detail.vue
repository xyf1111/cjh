<template>
  <el-dialog
    title="订单详情"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width='915px'
    >
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane label="订单信息" name="info"></el-tab-pane>
          <el-tab-pane label="订单图片" name="image"></el-tab-pane>
          <el-tab-pane label="浏览1天" name="view1" v-if="this.dataForm.taskStyle === 1 || this.dataForm.taskStyle === 2"></el-tab-pane>
          <el-tab-pane label="浏览2天" name="view2"  v-if="this.dataForm.taskStyle === 2"></el-tab-pane>
        </el-tabs>
        <table v-if="this.activeName === 'info'">
            <tr>
              <th>订单编号</th>
              <th>淘宝号</th>
              <th>淘宝订单</th>
            </tr>
            <tr>
              <td><el-input v-model = "this.dataForm.missionCode" disabled></el-input></td>
              <td><el-input v-model = "this.dataForm.taobao" disabled></el-input></td>
              <td><el-input v-model = "this.dataForm.taobaoCode" disabled></el-input></td>
            </tr>
        </table>
        <table v-if="this.activeName === 'info'">
          <tr>
            <th>商品价格</th>
            <th>店铺名称</th>
            <th>执行时间</th>
          </tr>
          <tr>
            <td><el-input v-model = "this.dataForm.price" disabled></el-input></td>
            <td><el-input v-model = "this.dataForm.shopName" disabled></el-input></td>
            <td><el-input v-model = "this.dataForm.missionTime" disabled></el-input></td>
          </tr>
        </table>
        <table v-if="this.activeName === 'info'">
          <tr>
            <th>收货人</th>
            <th>收货人手机号</th>
            <th>是否发送物流</th>
          </tr>
          <tr>
            <td><el-input v-model = "logisticsName" disabled></el-input></td>
            <td><el-input v-model = "logisticsPhone" disabled></el-input></td>
            <el-input v-model = "isLogisticsValue" disabled ></el-input>
          </tr>
        </table>
        <table v-if="this.activeName === 'info'">
          <tr>
            <th>收货地址</th>
          </tr>
          <tr>
            <td>
              <el-input v-model = "logisticsAddress" class="my-el-input" style="width: 800px"></el-input>
            </td>
            <!--<td><el-button type="success" size="medium" @click="confirmAddress">确认收货地址</el-button></td>-->
          </tr>
        </table>
        <el-row :gutter="30" v-if="this.activeName === 'info'">
          <el-col :span="12">
            <div class="grid-content bg-purple">
              任务要求：
              <el-input type="textarea" v-model="this.dataForm1.note" ></el-input>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="grid-content bg-purple">
              商品链接： <el-button type="primary" size="small"><a :href="this.dataForm1.url" target="_blank">点击跳转</a></el-button>
              <el-input type="text" v-model="this.dataForm1.url" style="margin-top: 9px;width:100%;" disabled></el-input>
            </div>
            <el-row>
              <el-col :span="8">
                <div class="grid-content bg-purple">
                  <div>产品主图</div>
                  <div>
                    <viewer :image="dataForm1.img" v-if="dataForm1.img">
                      <img :src="dataForm1.img" class="img1">
                    </viewer>
                   </div>
                </div>
              </el-col>
              <el-col :span="8">
                <div class="grid-content bg-purple">
                  <div>宝贝聊天图</div>
                  <div>
                    <viewer :image="dataForm1.chat" v-if="dataForm1.chat">
                      <img :src="dataForm1.chat" class="img1">
                    </viewer>
                  </div>
                </div>
              </el-col>
              <el-col :span="8">
              <div class="grid-content bg-purple">
                <div>已付款图</div>
                  <div>
                    <viewer :image="dataForm1.paied" v-if="dataForm1.paied">
                      <img :src="dataForm1.paied" class="img1">
                    </viewer>
                  </div>
              </div>
              </el-col>
            </el-row>
            <div>
              其他要求：<el-input type="textarea" v-model="this.dataForm1.otherNote" :autosize="{minRows: 2, maxRows: 4}"></el-input>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="40" v-if="this.activeName === 'image'">
          <el-col :span="4">
            <div class="grid-content bg-purple">
              <div>货比三家1 </div>
                <div>
                   <viewer :image="dataForm1.compareOne" v-if="dataForm1.compareOne">
                      <img :src="this.dataForm1.compareOne" class="img1">
                   </viewer>
                </div>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">
              <div>货比三家2</div>
                <div>
                  <viewer :image="dataForm1.compareTwo" v-if="dataForm1.compareTwo">
                     <img :src=" this.dataForm1.compareTwo" class="img1">
                  </viewer>
                </div>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">
              <div>货比三家3</div>
                <div>
                   <viewer :image="dataForm1.compareThree" v-if="dataForm1.compareThree">
                      <img :src=" this.dataForm1.compareThree" class="img1">
                   </viewer>
                </div>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">
              <div>关注店铺</div>
                <div>
                 <viewer :image="dataForm1.collectionCom" v-if="dataForm1.collectionCom">
                    <img :src=" this.dataForm1.collectionCom" class="img1">
                 </viewer>
                </div>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">
              <div>足迹图 </div>
                <div>
                 <viewer :image="dataForm1.footprint" v-if="dataForm1.footprint">
                    <img :src=" this.dataForm1.footprint" class="img1">
                 </viewer>
                </div>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">
              <div>店内宝贝1 </div>
              <div>
                <viewer :image="dataForm1.addCart" v-if="dataForm1.addCart">
                  <img :src=" this.dataForm1.addCart" class="img1">
                </viewer>
              </div>
            </div>
          </el-col>
        </el-row>
        <hr>
         <el-row :gutter="40" v-if="this.activeName === 'image'">
           <el-col :span="4">
             <div class="grid-content bg-purple">
               <div>店内宝贝2 </div>
               <div>
                 <viewer :image="dataForm1.addCart2" v-if="dataForm1.addCart2">
                   <img :src="this.dataForm1.addCart2" class="img1">
                 </viewer>
               </div>
             </div>
           </el-col>
           <el-col :span="4">
            <div class="grid-content bg-purple">
              <div>假聊截图  </div>
                <div>
                 <viewer :image="dataForm1.shopOne" v-if="dataForm1.shopOne">
                   <img :src=" this.dataForm1.shopOne" class="img1">
                 </viewer>
                </div>
            </div>
           </el-col>
           <el-col :span="4">
            <div class="grid-content bg-purple">
              <div>关键字搜索 </div>
              <div>
               <viewer :image="dataForm1.keySearch" v-if="dataForm1.keySearch">
                  <img :src=" this.dataForm1.keySearch" class="img1">
               </viewer>
              </div>
            </div>
           </el-col>
           <el-col :span="4">
            <div class="grid-content bg-purple">
              <div>确认收货 </div>
              <div>
              <viewer :image="dataForm1.receive" v-if="dataForm1.receive">
                  <img :src="this.dataForm1.receive" class="img1">
              </viewer>
              </div>
            </div>
           </el-col>
           <el-col :span="4">
            <div class="grid-content bg-purple">
              <div>指定评价 </div>
              <div>
                <viewer :image="dataForm1.commentImg" v-if="dataForm1.commentImg">
                   <img :src="this.dataForm1.commentImg" class="img1">
                </viewer>
              </div>
            </div>
           </el-col>
           <el-col :span="4">
            <div class="grid-content bg-purple">
              <div>指定追价</div>
              <div>
                <viewer :image="dataForm1.addComImg" v-if="dataForm1.addComImg">
                  <img :src="this.dataForm1.addComImg" class="img1">
                </viewer>
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="40" v-if="this.activeName === 'view1'">
          <el-col :span="4">
            <div class="grid-content bg-purple">
                <viewer :images="dataForm2" >
                  <div v-for="item in this.dataForm2"><img :src="item" class="img1"></div>
                </viewer>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="40" v-if="this.activeName === 'view2'">
          <el-col :span="4">
            <div class="grid-content bg-purple">
              <viewer :images="dataForm3">
                <div v-for="item in this.dataForm3"><img :src="item" class="img1"></div>
              </viewer>
            </div>
          </el-col>
        </el-row>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      isLogistics: '0',
      isLogisticsValue: '否',
      userName: '',
      logisticsName: '',
      logisticsAddress: '',
      logisticsPhone: '',
      isLogisticsSelect: [{
        value: 0,
        label: '否'
      }, {
        value: 1,
        label: '是'
      }],
      activeName: 'info',
      dataForm: {
      },
      dataForm1: {
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/app/seller/mission/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.isLogistics = data.mission.mission.isLogistics
              this.isLogisticsValue = (data.mission.mission.isLogistics === 0 ? '否' : '是')
              this.userName = data.mission.mission.userName
              this.logisticsAddress = data.mission.mission.logisticsAddress
              this.logisticsName = data.mission.mission.logisticsName
              this.logisticsPhone = data.mission.mission.logisticsPhone
              this.dataForm = data.mission.mission
              this.dataForm1 = data.mission.missionDetail
              this.dataForm2 = data.mission.view1
              this.dataForm3 = data.mission.view2
            }
          })
        }
      })
    },
    confirmAddress () {
      this.$http({
        url: this.$http.adornUrl(`/app/seller/mission/confirmAddress`),
        method: 'post',
        data: {
          'id': this.dataForm.id,
          'userName': this.userName,
          'logisticsName': this.logisticsName,
          'isLogistics': this.isLogistics,
          'logisticsPhone': this.logisticsPhone,
          'logisticsAddress': this.logisticsAddress
        }
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message.success('操作成功')
          this.visible = false
          this.$emit('refreshDataList')
          this.dataForm = {}
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    handleClick (tab) {
      this.activeName = tab.name
    }
  }
}
</script>
<style >
  .el-input.is-disabled .el-input__inner {
    text-align:center;
    color:#666;
    width:250px;
    margin-right:20px
  }
  .my-el-input {
    text-align:center;
    color:#666;
    width:800px;
    margin-right:20px
  }
  .el-textarea__inner{
    height: 300px;
  }
  a{
    text-decoration: none;
    color: #fff;
  }
  .img1{
    width:100px;
    height:100px;
  }
  .a{

  }
</style>
