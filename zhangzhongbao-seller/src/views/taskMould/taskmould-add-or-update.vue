<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-steps :active="active" finish-status="success" align-center style="margin-bottom: 30px;">
      <el-step title="店铺选择"></el-step>
      <el-step title="模板内容"></el-step>
      <el-step title="其他信息"></el-step>
      <el-step title="创建成功"></el-step>
    </el-steps>
    <el-form :model="dataForm" :rules="dataRule1" ref="dataForm" label-width="110px" v-if="this.step === 1">
      <el-form-item label="相关店铺" prop="shopId">
        <el-table
          :data="shopList"
          border
          style="width: 100%">
          <el-table-column
            prop="name"
            label="店铺名称"
            width="600">
          </el-table-column>
           <el-table-column
             fixed="right"
             header-align="center"
             align="center"
             width="100px"
             label="操作">
             <template slot-scope="scope">
               <el-radio v-model="dataForm.shopId" :label= scope.row.id> 选中</el-radio>
             </template>
           </el-table-column>
        </el-table>
        <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
      </el-form-item>
    <el-form-item label="商品主图（最多三张）" prop="img">
      <el-upload
        :action="url"
        :limit="3"
        list-type="picture-card"
        :file-list="dataForm.fileList"
        :before-upload="beforeUploadHandle"
        :on-preview="handlePictureCardPreview"
        :on-success="successHandle"
        :on-remove="handleRemove">
        <i class="el-icon-plus"></i>
      </el-upload>
    </el-form-item>

    </el-form>
    <el-form :model="dataForm" :rules="dataRule2" ref="dataForm" label-width="110px" v-if="this.step === 2">
      <el-form-item label="付款方式">
        <el-input placeholder="先打款" disabled></el-input>
      </el-form-item>
      <el-form-item label="商品链接" prop="url">
        <el-input v-model="dataForm.url" placeholder="商品链接"></el-input>
      </el-form-item>
      <el-form-item label="宝贝款号" prop="style">
        <el-input v-model="dataForm.style" placeholder="请输入宝贝款号"></el-input>
      </el-form-item>
      <el-form-item label="价格区间（从）" prop="priceFrom">
        <el-input v-model="dataForm.priceFrom" placeholder="价格区间（从）" type="number"></el-input>
      </el-form-item>
      <el-form-item label="价格区间（至）" prop="priceTo">
        <el-input v-model="dataForm.priceTo" placeholder="价格区间（至）" type="number"></el-input>
      </el-form-item>
      <el-form-item label="付款价格(元)" prop="price">
        <el-input v-model="dataForm.price" placeholder="请输入商品付款价格(元)" type="number"></el-input>
      </el-form-item>
      <el-form-item label="货重(千克)" prop="weight">
        <el-input v-model="dataForm.weight" placeholder="请输入商品重量(千克)" type="number"></el-input>
      </el-form-item>
    </el-form>

    <el-form :model="dataForm" :rules="dataRule3" ref="dataForm" label-width="110px" v-if="this.step === 3">
      <!--<el-form-item label="关键字" prop="keyword">
        <el-input v-model="dataForm.keyword" placeholder="商品关键字" style="width: 200px" ></el-input>
      </el-form-item>-->
      <el-form-item
        v-for="(ky, index) in dataForm.keywords"
        :label="'关键字' + (index+1)"
        :key="ky.key"
        :prop="'keywords.' + index + '.value'"
        :rules="{required: true, message: '内容不能为空', trigger: 'blur'}"
      >
        <el-input v-model="ky.value" placeholder="商品关键字" style="width: 250px" ></el-input>
        <el-button type="primary" @click.prevent="addKeyWord()" v-if="index == 0">增加</el-button>
        <el-button type="primary" @click.prevent="removeKeyWord(ky)" v-if="index != 0">删除</el-button>
      </el-form-item>
      <el-form-item label="排列方式" prop="order">
          <el-select v-model="order" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
      </el-form-item>
      <!--<el-form-item label="搜索排行页数起始" prop="intervalBegin">-->
        <!--<el-input v-model="dataForm.intervalBegin" placeholder="请输入搜索排行页数起始"></el-input>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="搜索排行页数终点" prop="intervalEnd">-->
        <!--<el-input v-model="dataForm.intervalEnd" placeholder="请输入搜索排行页数终点"></el-input>-->
      <!--</el-form-item>-->
      <el-form-item label="其他要求" prop="otherNote">
         <el-input v-model="dataForm.otherNote" type="textarea" maxlength="300" placeholder="其他要求" >
         </el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="next1()" v-if="this.active > 1">上一步</el-button>
      <el-button type="primary" @click="next()" v-if="this.active < 4">下一步</el-button>
    </span>
  </el-dialog>
</template>

<script>
import {isURL} from '@/utils/validate'
export default {
  data () {
    return {
      visible: false,
      dataForm: {
        id: 0,
        sellerId: '',
        shopId: '',
        img: '',
        shopName: '',
        url: '',
        style: '',
        price: '',
        priceFrom: '',
        priceTo: '',
        weight: '',
        keyword: '',
        keywords: [{value: '' }],
        orderStr: '',
        order: '',
        intervalBegin: '',
        intervalEnd: '',
        note: '',
        otherNote: '',
        fileList: []
      },
      sellerId: '',
      step: 1,
      shopList: [],
      active: 1,
      shopId: '',
      url: '',
      style: '',
      otherNote: '',
      order: '',
      options: [{
        value: 1,
        label: '综合排序'
      }, {
        value: 2,
        label: '销量排序'
      }, {
        value: 3,
        label: '价格排序'
      }],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      dataRule1: {
        shopId: [
          { required: true, message: '店铺不能为空', trigger: 'blur' }
        ],
        img: [
          { required: true, message: '产品主图不能为空', trigger: 'blur' }
        ]
      },
      dataRule2: {
        url: [
          { required: true, message: '商品链接不能为空', trigger: 'blur' },
          { max: 500, message: '链接长度不能超过500个字符', trigger: 'blur'}
        ],
        price: [
          { required: true, message: '商品价格不能为空', trigger: 'blur' }
        ],
        weight: [
          { required: true, message: '商品货重不能为空', trigger: 'blur' }
        ]},
      dataRule3: {
        /* keyword: [
          { required: true, message: '商品关键字不能为空', trigger: 'blur' }
        ], */
        order: [
          { required: true, message: '商品排列顺序不能为空', trigger: 'blur' }
        ]
        // intervalBegin: [
        //   { required: true, message: '商品排序方式起始排名不能为空', trigger: 'blur' }
        // ],
        // intervalEnd: [
        //   { required: true, message: '商品排列方式终止排名不能为空', trigger: 'blur' }
        // ],
        // note: [
        //     { required: true, message: '备注不能为空', trigger: 'blur' }
        //   ]
      }
    }
  },
  created () {
    this.getShopList()
  },
  methods: {
    init (id) {
      this.step = 1
      this.active = 1
      if (id === undefined) {
        this.dataForm.id = 0
        this.dataForm.img = ''
        this.dataForm.img2 = ''
        this.dataForm.img3 = ''
        this.dataForm.fileList = []
        this.dataForm.shopName = ''
        this.dataForm.url = ''
        this.dataForm.style = ''
        this.dataForm.price = ''
        this.dataForm.priceFrom = ''
        this.dataForm.priceTo = ''
        this.dataForm.weight = ''
        this.dataForm.keyword = ''
        this.dataForm.keywords = [{value: '' }],
        this.order = ''
        this.dataForm.intervalBegin = ''
        this.dataForm.intervalEnd = ''
        this.dataForm.otherNote = ''
      }
      this.dataForm.id = id || 0
      this.url = this.$http.adornUrl(`/app/seller/file/upload`)
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/app/seller/taskMould/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.sellerId = data.taskMould.sellerId
              this.dataForm.shopId = data.taskMould.shopId
              this.dataForm.img = data.taskMould.img
              this.dataForm.fileList = [{name: '', url: this.dataForm.img}]
              if (data.taskMould.img2) {
                this.dataForm.img2 = data.taskMould.img2
                this.dataForm.fileList = [{name: '', url: this.dataForm.img}, {name: '', url: this.dataForm.img2}]
              }
              if (data.taskMould.img3) {
                this.dataForm.img3 = data.taskMould.img3
                this.dataForm.fileList = [{name: '', url: this.dataForm.img}, {name: '', url: this.dataForm.img2}, {name: '', url: this.dataForm.img3}]
              }
              this.dataForm.shopName = data.taskMould.shopName
              this.dataForm.url = data.taskMould.url
              this.dataForm.style = data.taskMould.style
              this.dataForm.price = data.taskMould.price
              this.dataForm.priceFrom = data.taskMould.priceFrom
              this.dataForm.priceTo = data.taskMould.priceTo
              this.dataForm.weight = data.taskMould.weight
              this.dataForm.keyword = data.taskMould.keyword
              // 回显处理
              this.dataForm.keywords = []
              var kys = this.dataForm.keyword.split(';')
              for (var k in kys) {
                var o = {value: kys[k]}
                this.dataForm.keywords.push(o)
              }
              this.order = data.taskMould.order
              this.dataForm.intervalBegin = data.taskMould.intervalBegin
              this.dataForm.intervalEnd = data.taskMould.intervalEnd
              this.dataForm.otherNote = data.taskMould.otherNote
              this.dataForm.note = data.taskMould.note
              this.dataForm.createTime = data.taskMould.createTime
              this.dataForm.updateTime = data.taskMould.updateTime
            }
          })
        }
      })
    },
    /* 格式化表单数据 */
    formatData (data) { // 动态表单生成的是一个对象数组，需要把对象数组转成一个对象字符串，|分隔
      var obj = []
      data.forEach((item, index) => {
        obj.push(item.value)
      })
      return obj.join(';')
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.dataForm.keyword = this.formatData(this.dataForm.keywords)
          this.$http({
            url: this.$http.adornUrl(`/app/seller/taskMould/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'sellerId': this.dataForm.sellerId,
              'shopId': this.dataForm.shopId,
              'img': this.dataForm.img,
              'img2': this.dataForm.img2,
              'img3': this.dataForm.img3,
              'shopName': this.dataForm.shopName,
              'url': this.dataForm.url,
              'style': this.dataForm.style,
              'price': this.dataForm.price,
              'priceFrom': this.dataForm.priceFrom,
              'priceTo': this.dataForm.priceTo,
              'weight': this.dataForm.weight,
              'keyword': this.dataForm.keyword,
              'order': this.dataForm.order,
              // 'intervalBegin': this.dataForm.intervalBegin,
              // 'intervalEnd': this.dataForm.intervalEnd,
              'note': this.dataForm.note,
              'otherNote': this.dataForm.otherNote
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
                  this.active = 1
                  this.step = 1
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
    },
    next () {
      if (this.active === 1) {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.active++
            this.step = this.active
          }
        })
      } else if (this.active === 2) {
        if (!isURL(this.dataForm.url)) {
          this.$message.error('请输入正确的商品链接!')
          return
        }
        if (this.dataForm.priceFrom && this.dataForm.priceTo && parseFloat(this.dataForm.priceFrom) > parseFloat(this.dataForm.priceTo)) {
          this.$message.error('价格区间开始必须比结束小！')
          return
        }
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.active++
            this.step = this.active
          }
        })
      } else if (this.active === 3) {
        this.dataForm.order = this.order
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.active++
            this.dataFormSubmit()
          }
        })
      }
    },
    addKeyWord () {
      // 关键词新增
      if (this.dataForm.keywords.length > 99) {
        this.$message.error('关键字最多支持100个！')
        return
      }
      this.dataForm.keywords.push({
        value: ''
      })
    },
    removeKeyWord (item) {
      // 关键词移除
      var index = this.dataForm.keywords.indexOf(item)
      if (index !== -1) {
        this.dataForm.keywords.splice(index, 1)
      }
    },
    next1 () {
      this.active--
      this.step = this.active
    },
    getShopList () {
      this.$http({
        url: this.$http.adornUrl('/app/seller/shop/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'status': 1,
          'role': 3
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list
          this.totalPage = data.data.totalCount
          this.shopList = data.data.list
        }
      })
    },
    handleRemove (file, fileList) {
      console.log(file, fileList)
      if (fileList.length == 1) {
        this.dataForm.img = fileList[0].url
        this.dataForm.img2 = ''
        this.dataForm.img3 = ''
      } else if (fileList.length == 2) {
        this.dataForm.img1 = fileList[0].url
        this.dataForm.img2 = fileList[1].url
        this.dataForm.img3 = ''
      } else if (fileList.length == 3) {
        this.dataForm.img = fileList[0].url
        this.dataForm.img2 = fileList[1].url
        this.dataForm.img3 = fileList[2].url
      } else {
        this.dataForm.img = ''
        this.dataForm.img2 = ''
        this.dataForm.img3 = ''
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
      // console.log(file)
      // file.url = response.url
      if (response && response.code === 0) {
        if (fileList.length == 1) {
          this.dataForm.img = fileList[0].response.url
          this.dataForm.img2 = ''
          this.dataForm.img3 = ''
        } else if (fileList.length == 2) {
          this.dataForm.img1 = fileList[0].response.url
          this.dataForm.img2 = fileList[1].response.url
          this.dataForm.img3 = ''
        } else if (fileList.length == 3) {
          this.dataForm.img = fileList[0].response.url
          this.dataForm.img2 = fileList[1].response.url
          this.dataForm.img3 = fileList[2].response.url
        }
      } else {
        this.$message.error(response.msg)
      }
    },
    // 每页数
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getShopList()
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getShopList()
    }
  }
}
</script>
<style>
.el-dialog__footer{
    text-align: center ;
    margin-top: -40px ;
  }
</style>
