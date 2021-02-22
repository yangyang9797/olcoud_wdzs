import request from '@ocv/web/src/http/request'
import config from '@ocv/web/src/config'

export const ApiFile = {

    apiUrl: config.urls.file,
    model_templet: {
        fileid: '',
        refid: '',
        filecode: '',
        filename: '',
        filetype: '',
        filesize: '',
        fileext: '',
        reftype: '',
        md5: '',
        url: '',
        filepath: '',
        contenttype: '',
        description: '',
        cuserid: '',
        cusername: '',
        seqno: '',
        enable: '',
        state: '',
        ctime: '',
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
            // contentType: 'application/json; charset=utf-8',
            // dataType: 'json',
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
    upload(upfiles) {
        return request({
            url: this.apiUrl + 'image/upload',
            method: 'post',
            data: upfiles,// params
            processData: false, //必须false才会自动加上正确的Content-Type
            contentType: false,//必须false才会避开jQuery对 formdata 的默认处理
        })
    },
    fileupload(upfiles) {
        return request({
            url: this.apiUrl + 'upload',
            method: 'post',
            data: upfiles,// params
            processData: false, //必须false才会自动加上正确的Content-Type
            contentType: false,//必须false才会避开jQuery对 formdata 的默认处理
        })
    },
    download(opt) {
        return request({
            url: this.apiUrl + 'download',
            method: 'post',
            params: opt,// params
        })
    },
};
export default ApiFile
