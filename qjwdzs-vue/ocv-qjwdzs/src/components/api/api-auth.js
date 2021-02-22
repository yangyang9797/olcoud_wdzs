import request from '@ocv/web/src/http/request'
import config from '@ocv/web/src/config'

export const ApiAuth = {
    apiUrl: config.urls.auth,
    getCurentUser(opt) {
        return request({
            url: this.apiUrl + 'getCurentUser',
            method: 'post',
            params: opt // params
        })
    },
    login(opt) {
        return request({
            url: this.apiUrl + 'login',
            method: 'post',
            params: opt // params
        })
    },
    modified(opt) {
        return request({
            url: this.apiUrl + "user/modifyPassword",
            method: 'post',
            params: opt // params
        })
    },
}
export default ApiAuth
