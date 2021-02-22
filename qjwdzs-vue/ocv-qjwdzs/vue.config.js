
module.exports = {
    publicPath: "./",
    outputDir: 'dist',
    assetsDir: 'static',
    lintOnSave: false,
    productionSourceMap: process.env.NODE_ENV === 'production' ? false : true,

    pages: {
        index: {
            // page 的入口
            entry: 'examples/main.js',
            // 模板来源
            template: 'public/index.html',
            // 输出文件名
            filename: 'index.html'
        }
    },

    transpileDependencies: [],
    configureWebpack: () => {
        // if (process.env.NODE_ENV === 'production') {
        const CompressionPlugin = require("compression-webpack-plugin")

        return {
            externals: {
                'vue': 'Vue',
                'vue-router': 'VueRouter',
                'vuex': 'Vuex',
                'axios': 'axios',
                'element-ui': 'ELEMENT',
                'vuetify': 'Vuetify',  // vuetify 不能这样 ie不会兼容
                'iview': 'iview',
                'vant': 'vant'
            },
            plugins: [

                new CompressionPlugin({
                    test: /\.js$|\.html$|.\css/, //匹配文件名
                    threshold: 10240,//对超过10k的数据压缩
                    deleteOriginalAssets: false //不删除源文件
                })
            ]
            // }
        }
    },
    chainWebpack: config => {
        if (process.env.NODE_ENV === 'production') {
            // 解决低版本浏览器 es567错误问题
            // 默认情况下 babel-loader 会忽略所有 node_modules 中的文件。
            // 如果你想要通过 Babel 显式转译一个依赖，可以在这个选项中列出来

            const path = require('path');

            function resolve(dir) {
                return path.join(__dirname, '.', dir);
            }

            config.module.rule('compile')
                .test(/\.js$/)
                .include
                .add(resolve('src'))
                .add(resolve('test'))
                .add(resolve('node_modules'))
                .end()
                .use('babel')
                .loader('babel-loader')
                .options({
                    presets: [
                        ['@babel/preset-env', {
                            modules: false
                        }]
                    ]
                });
        }
    }
}
