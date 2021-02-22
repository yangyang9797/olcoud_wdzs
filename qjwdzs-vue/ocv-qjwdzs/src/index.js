import './styles/index.less'
import plugins from './plugins'
import components from './components'
import views from './views'

const map = plugins.ocvAdmin.putViews("OcvQjydc", {...views, ...components})

// 定义 install 方法
const install = function (Vue, opts = {}) {
    if (install.installed) return
    install.installed = true
    // 遍历并注册全局组件
    console.info("遍历并注册全局组件", map)
    Object.keys(map).forEach(key => {
        Vue.component(key, map[key]);
    });

}
export default {
    // 导出的对象必须具备一个 install 方法
    install,
    // 组件列表
    ...map,
    opts: {
        ...plugins.opts
    }
}
