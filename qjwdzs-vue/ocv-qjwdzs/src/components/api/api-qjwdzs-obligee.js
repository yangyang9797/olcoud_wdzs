import request from '@ocv/web/src/http/request'
import config from '@ocv/web/src/config'

export const ApiQjydcObligee = {
    apiUrl: config.urls.qjwdzs + 'qjwdzs/obligee/',
    model_templet: {
        qlrid: '',
        qlrpid: '',
        zzqlr: '',
        zjh: '',
        dhhm: '',
        dlr:'',
        ydlrgx: '',
        dlrzjh: '',
        isdel: '',
        ctime: '',
        cuserid: '',
        qjinid:'',
    },
    getModel(opt) {
        return Object.assign({}, this.model_templet, opt)
    },
    save(opt) {
        return request({
            url: this.apiUrl + 'save',
            method: 'post',
            params: opt // params
        })
    },
    saveAll(opt) {
        return request({
            url: this.apiUrl + 'saveAll',
            method: 'post',
            data: opt // params
        })
    },
    deleteById(id) {
        return request({
            url: this.apiUrl + 'deleteById',
            method: 'post',
            params: {id} // params
        })
    },
    getById({id}) {
        return request({
            url: this.apiUrl + 'getById',
            method: 'post',
            params: {id} // params
        })
    },
    list(opt) {
        return request({
            url: this.apiUrl + 'list',
            method: 'post',
            params: opt // params
        })
    },
    page(opt) {
        return request({
            url: this.apiUrl + 'page',
            method: 'post',
            params: opt // params
        })
    },
    output(opt) {
        return request({
            url: this.apiUrl + 'output',
            method: 'post',
            params: opt // params
        })
    },
    download(opt) {
        return request({
            url: config.urls.qjwdzs + 'web/file/download',
            method: 'post',
            params: opt // params
        })
    },
    getListByQjinid(qjinid) {
        return request({
            url: this.apiUrl + 'getListByQjinid',
            method: 'post',
            params: {qjinid} // params
        })
    },
    selectByQjinid(qjinid) {
        return request({
            url: this.apiUrl + 'selectByQjinid',
            method: 'post',
            params: {qjinid} // params
        })
    },


}
export default ApiQjydcObligee
