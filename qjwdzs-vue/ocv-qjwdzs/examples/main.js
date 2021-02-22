// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import ocv from '../src/index'

// 配置首页
// ocv.opts.RouterConfig.opt.routes.unshift({path: '/', redirect: '/login'})
// 取消认证不拦截
// ocv.opts.RouterConfig.authRequired = false;


/* eslint-disable no-new */
Vue.use(ocv);
console.info("ocv选项", ocv.opts)
const store = ocv.opts.StoreConfig.install();
const router = ocv.opts.RouterConfig.install(store);
const vue = new Vue({
    el: '#app',
    ...ocv.opts,
    store,
    router,
    render: h => h(App)
})
window.vue = vue;

// 设置用户信息
vue.$nextTick(() => {
     // console.info("$nextTick", datas);
     // vue.$store.dispatch('auth/SetToken', datas.userinfo);
    // vue.$store.commit('auth/setRoutersData', datas.funcs)
    // router.addRoutes(store.state.auth.permission.routers)
    // 动态添加可访问路由表
    // console.info("动态添加路由表", store.state.auth.permission.routers)
})

