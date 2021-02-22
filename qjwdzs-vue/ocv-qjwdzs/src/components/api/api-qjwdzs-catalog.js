import request from '@ocv/web/src/http/request'
import config from '@ocv/web/src/config'

export const ApiQjydcCatalog = {
    apiUrl: config.urls.qjwdzs + 'qjwdzs/catalog/',
    model_templet: {
        qclid: '',
        qclpid: '',
        qclcode: '',
        qclname: '',
        qcltype: '',
        description: '',
        cuserid: '',
        seqno: '',
        enable: '1'
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
    tree(opt) {
        return request({
            url: this.apiUrl + 'tree',
            method: 'post',
            params: opt // params
        })
    },
    treeWithInstances(opt) {
        return request({
            url: this.apiUrl + 'treeWithInstances',
            method: 'post',
            params: opt // params
        })
    },
    pageInstancesByTree(opt) {
        return request({
            url: this.apiUrl + 'pageInstancesByTree',
            method: 'post',
            params: opt // params
        })
    }

}
export default ApiQjydcCatalog
