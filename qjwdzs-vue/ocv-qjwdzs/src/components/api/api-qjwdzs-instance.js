import request from '@ocv/web/src/http/request'
import config from '@ocv/web/src/config'

export const ApiQjydcInstance = {
    apiUrl: config.urls.qjwdzs + 'qjwdzs/instance/',
    model_templet: {
        qjinid: '',
        qclid: '',
        qjincode: '',
        qjinname: '',
        qjinxzqh:'',
        qjintype: '',
        content: '',
        configs: '',
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
    getById(id) {
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
    pageWithCatalog(opt) {
        return request({
            url: this.apiUrl + 'pageWithCatalog',
            method: 'post',
            data: opt // params
        })
    },
    /**
     * 保存二维码为图片
     * pWidth 保存出来的图片宽度
     * pHeight 保存出来的图片高度
     * bgSrc 背景图地址
     * textTop 标题与上边框距离
     * font 标题字体
     * color 标题色值
     * text 标题文本
     * imgTop 二维码与上边框距离
     * */
    saveQRcodePic(e,pWidth=502,pHeight=794,bgSrc='./static/imgs/QRcode_bg.png',textTop=180,font='30px bolder serif',color='#0060b0',text=`${this.qjin.qjinxzqh}自然资源和规划局`,imgTop=258) {
        //找到canvas标签
        let myCanvas = document.getElementById('picture').getElementsByTagName('canvas')[0];
        myCanvas.width = pWidth;
        myCanvas.height = pHeight;
        let myQRcode = document.getElementById('picture').getElementsByTagName('img')[0];
        let ctx = myCanvas.getContext('2d');
        let img = new Image();
        img.src = bgSrc;
        img.onload = () => {
            ctx.drawImage(img, 0, 0, pWidth, pHeight);
            ctx.drawImage(myQRcode, pWidth / 2 - (myQRcode.width / 2), imgTop);
            ctx.font = font;
            ctx.textAlign = 'center';
            ctx.fillStyle = '#333';
            ctx.fillText(text, pWidth / 2, textTop);
            ctx.fillStyle = color;
            ctx.font = '28px bolder serif';
            ctx.fillText(this.qjin.qjinname, pWidth / 2, textTop+40);
            // myCanvas.style.display='block';
            // return;
            //创建一个a标签节点
            let a = document.createElement("a");
            //设置a标签的href属性（将canvas变成png图片）
            a.href = myCanvas.toDataURL('image/png').replace('image/png', 'image/octet-stream')
            //设置下载文件的名字
            a.download = this.qjin.qjinname + ("（二维码）.png");
            //点击
            a.click()
        }
    }
}

export default ApiQjydcInstance
