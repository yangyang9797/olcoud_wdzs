const config = {
    app: {
        name: "权籍调查",
        desc: "qjwdzs management system",
        icon: "yunyingguanli",
        mainlayout: "portal",
    },
    models: {
        // dev: 'http://kjxx.cnovit.com/qjwdzs/',
        // dev: 'http://192.168.7.13:8172/qjwdzs/',
        dev: 'http://localhost:11002/qjwdzs/',
        pro: '../',
    },
    uris: {
        sso: 'sso/',
        auth: 'auth/',
        file: 'file/',
        qjwdzs: '',
    }
}

window.config = config;
console.info("原始配置", config);

