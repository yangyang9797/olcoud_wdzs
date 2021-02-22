var cdns = {
    polyfill: "static/cdn/polyfill/polyfill.min.js",
    vue: "static/cdn/vue/prod/vue.min.js",
    vuex: "static/cdn/vuex/vuex.min.js",
    axios: "static/cdn/axios/axios.min.js",
    "vue-router": "static/cdn/vue-router/vue-router.js",
    jquery: "static/cdn/jquery/jquery.min.js",
    // <!-- import element-ui -->
    "element-ui-css": "static/cdn/element-ui/index.css",
    "element-ui-js": "static/cdn/element-ui/index.js",
    // <!-- import iView -->
    "iview-css": "static/cdn/iview/iview.css",
    "iview-js": "static/cdn/iview/iview.min.js",
    // <!-- import iView -->
    "vant-css": "static/cdn/vant/index.css",
    "vant-js": "static/cdn/vant/vant.min.js",
    // <!-- import vuetify -->
    "vuetify-css": "static/cdn/vuetify/vuetify.min.css",
    "vuetify-js": "static/cdn/vuetify/vuetify.min.js",

    "ocv-icon-iconfont": "static/cdn/ocv-icon/iconfont.css",
    "ocv-icon-fa": "static/cdn/ocv-icon/font-awesome.min.css",
    "material-design-icons": "static/cdn/ocv-icon/material-design-icons.css"

}

function loadHead(url) {
    if ((url + '?').toLowerCase().indexOf('.css') > -1) {
        document.write('<link href="' + url + '" type="text/css" rel="stylesheet"><\/link>');
    } else {
        document.write('<script src="' + url + '" type="text/javascript"><\/script>');
    }
    return;
}

function loadCdns(cdns_) {
    if (!cdns_) {
        cdns_ = assign({}, cdns)
        if (config && config.http && config.http.cdns) {
            cdns_ = assign(cdns_, config.http.cdns)
        }
    }
    for (key in cdns_) {
        loadHead(cdns_[key]);
    }
}

function assign(target, source) {
    if (!target) {
        target = {}
    }
    if (source) {
        for (key in source) {
            target[key] = source[key];
        }
    }
    return target;
}
loadCdns();
