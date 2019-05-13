import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/Login.vue'
import Home from '@/views/Home.vue'
import Header from '@/views/Header.vue'
import Index from '@/views/index.vue'
import Shop from '@/views/shop/shop.vue'
import TaskMould from '@/views/taskMould/taskmould.vue'
import Task from '@/views/task/task.vue'
import Mission from '@/views/mission/mission.vue'
import Review from '@/views/review/review.vue'
import Appoint from '@/views/appoint/appoint.vue'
import CourseStep from '@/views/courseStep/courseStep.vue'
import Inform from '@/views/inform/inform.vue'
import Paymoney from '@/views/paymoney/paymoney.vue'
import Address from '@/views/address/address.vue'
import Detail from '@/views/detail/detail.vue'
import LowerDetail from '@/views/detail/lowerDetail.vue'
import Record from '@/views/record/record.vue'
import Submit from '@/views/submit/submit.vue'
import Setpassword from '@/views/setpassword/setpassword.vue'
import Register from '@/views/seller/seller-register.vue'
import Forget from '@/views/forgetPassword/forgetPassword.vue'
import Seller from '@/views/seller/seller.vue'
import Invite from '@/views/invite/invite.vue'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/home',
      component: Home,
      name: 'home',
      iconCls: 'el-icon-message', // 图标样式class
      children: [
        {path: '/index', component: Index, name: 'Index', meta: {tab: 1, requiresAuth: true}},
        {path: '/header', component: Header, name: 'Header', meta: {tab: 1, requiresAuth: true}},
        {path: '/shop', component: Shop, name: 'Shop', meta: {tab: 1, requiresAuth: true}},
        {path: '/taskMould', component: TaskMould, name: 'TaskMould', meta: {tab: 1, requiresAuth: true}},
        {path: '/task', component: Task, name: 'Task', meta: {tab: 1, requiresAuth: true}},
        {path: '/mission', component: Mission, name: 'Mission', meta: {tab: 1, requiresAuth: true}},
        {path: '/review', component: Review, name: 'Review', meta: {tab: 1, requiresAuth: true}},
        {path: '/appoint', component: Appoint, name: 'Appoint', meta: {tab: 1, requiresAuth: true}},
        {path: '/courseStep', component: CourseStep, name: 'CourseStep', meta: {tab: 1, requiresAuth: true}},
        {path: '/inform', component: Inform, name: 'Inform', meta: {tab: 1, requiresAuth: true}},
        {path: '/paymoney', component: Paymoney, name: 'Paymoney', meta: {tab: 1, requiresAuth: true}},
        {path: '/address', component: Address, name: 'Address', meta: {tab: 1, requiresAuth: true}},
        {path: '/detail', component: Detail, name: 'Detail', meta: {tab: 1, requiresAuth: true}},
        {path: '/record', component: Record, name: 'Record', meta: {tab: 1, requiresAuth: true}},
        {path: '/submit', component: Submit, name: 'Submit', meta: {tab: 1, requiresAuth: true}},
        {path: '/setpassword', component: Setpassword, name: 'Setpassword', meta: {tab: 1, requiresAuth: true}},
        {path: '/seller', component: Seller, name: 'Seller', meta: {tab: 1, requiresAuth: true}},
        {path: '/lowerDetail', component: LowerDetail, name: 'LowerDetail', meta: {tab: 1, requiresAuth: true}},
        {path: '/invite', component: Invite, name: 'Invite', meta: {tab: 1, requiresAuth: true}}
      ]
      // beforeEnter(to, from, next) {
      // let token =  Vue.cookie.get('sellerToken')
      // if (to.matched.some(record => record.meta.requiresAuth) && (!token || token === null)) {
      //   next('/');
      // } else {
      //   next();
      // }
      // }
    },
    {
      path: '/',
      component: Login,
      alias: '/login',
      name: 'Login'
    },
    {
      path: '/forget',
      component: Forget,
      name: 'Forget'
    },
    {
      path: '/register',
      component: Register,
      name: 'Register'
    },
    {
      path: '/404',
      component: Error,
      name: 'Error'
    }]

})
