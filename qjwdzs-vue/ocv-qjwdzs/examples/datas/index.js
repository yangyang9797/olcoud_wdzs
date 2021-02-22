// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.

export const userinfo = {
    "userid": "1216642351568310274",
    "username": "lichun_420300",
    "personname": "李纯",
    "seqno": null,
    "enable": "1",
    "icon": "",
    "credential":"b2ec7911564a9a39ddb82b063ac8e870",
    "session":"e182ee34-f4a8-4b55-b9c4-d13a5f6db777",
    "info": {
        "org": {"orgid": "1207913457064218626", "orgcode": "st", "orgname": "省厅"},
        "xzq": {"xzqid": "1211828621924032514", "xzqcode": "420300", "xzqname": "十堰市"},
        "admin": {"adminid": "1197401645409898497", "admincode": "admin", "adminname": "管理员"}
    }
}
export const funcs = [{
    "funcid": "DWH000000",
    "funcpath": "",
    "funccode": "qjwdzs",
    "funcname": "权籍云调查",
    "functype": "application",
    "description": "Qjydc management system",
    "icon": "yunyingguanli"
}
    ,
    {
        "funcid": "DWH010000",
        "funcpid": "DWH000000",
        "funccode": "index",
        "funcpath": "qjwdzs/list",
        "funcname": "项目",
        "functype": "vue",
        "url": "OcvQjydcViewIndex",
        "icon": "gcjk"
    }
]

export default {
    userinfo, funcs
}


