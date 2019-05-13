// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Vuex from 'vuex'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import httpRequest from '@/utils/httpRequest'
import '@/element-ui'
import '@/element-ui-theme'
import VueCookie from 'vue-cookie'
import Viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'

Vue.use(Viewer, { defaultOptions: { zIndex: 9999 } })
Vue.use(VueCookie)
Vue.use(ElementUI)
Vue.use(Vuex)
Vue.config.productionTip = false

// 挂载全局
Vue.prototype.$http = httpRequest // ajax请求方法

router.beforeEach((to, from, next) => {
  let token = Vue.cookie.get('sellerToken')
  if (to.matched.some(record => record.meta.requiresAuth) && (!token || token === null)) {
    next('/')
  } else {
    next()
  }
})

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
