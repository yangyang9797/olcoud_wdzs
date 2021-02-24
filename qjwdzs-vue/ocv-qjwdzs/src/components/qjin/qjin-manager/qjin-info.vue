<template>
    <v-card class="qjin-info ocv-card-fill">
        <div class="header">
            <div>
                <div class="title">不动产调查系统</div>
                <div class="opts">
                    <Button
                            @click="save()"
                            icon="md-checkmark"
                            type="primary"
                    >保存
                    </Button>
                    <Button
                            @click="del()"
                            icon="md-close"
                    >删除
                    </Button>
                </div>
            </div>
        </div>
        <div class="content">
            <el-scrollbar wrap-class="scrollbar-wrapper">

                <Form
                        :model="this.f_qjin"
                        :rules="formrule"
                        class="ai-v-form"
                        ref="viewform"
                >

                    <FormItem
                            class="item-w50 label-x40"
                            label="项目名称"
                            prop="qjinname"
                    >
                        <Input v-model="f_qjin.qjinname"/>
                    </FormItem>
                    <FormItem
                            class="item-w50 label-x40"
                            label="地级子区代码"
                            prop=" qjincode"
                    >
                        <Input v-model="f_qjin.qjincode"/>
                    </FormItem>

                    <FormItem
                            class="item-h-l-2"
                            label="项目区域"
                            prop="qjinxzqh"
                    >
                        <el-cascader
                                style="width: 100%;"
                                size="large"
                                :options="options"
                                v-model="selectedOptions"
                                @change="handleChange">
                        </el-cascader>

                    </FormItem>

                    <FormItem
                            class="item-w50 label-x40"
                            label="项目内容"
                            prop="description"
                    >
                        <Input v-model="f_qjin.content"/>
                    </FormItem>

                    <FormItem

                            class="item-w50 label-x40"
                            label="项目类型"
                            prop="enable"
                    >
                        <Input v-model="f_qjin.qjintype"/>
                    </FormItem>

                    <FormItem
                            class="item-h-l-2"
                            label="描述"
                            prop="description"
                    >
                        <Input type="textarea" v-model="f_qjin.description"/>
                    </FormItem>

                </Form>
            </el-scrollbar>

        </div>
    </v-card>
</template>


<script>
    import ApiQjydcInstance from "../../api/api-qjwdzs-instance";
    import QRCode from "qrcodejs2"
    import VueQr from 'vue-qr'
    import qrcodeVue from 'qrcode-vue'
    import {provinceAndCityData, CodeToText } from 'element-china-area-data'

    export default {
        name: "qjin-info",
        components: {QRCode, VueQr,qrcodeVue},
        props: ["qjin"],
        watch: {
            qjin() {
                this.set(this.qjin);

            }
        },
        data: function () {

            return {
                size: 200,
                bgColor: '#fff',
                fgColor: '#000',
                logo: require('../../../assets/ewm_logo.png'),
                f_qjin: ApiQjydcInstance.getModel(),
                qjintypes: [],
                formunits: [],
                options: provinceAndCityData,
                selectedOptions: [],
                formrule: {
                    qjinname: [{required: true, message: "项目名称必须填写", trigger: "blur"}],
                    qjinxzqh: [{required: true, message: "项目区域必须填写", trigger: "blur"}]
                },
                link: '',
                // link: 'http://192.168.13.62:8080/#/login?id=' + this.qjin.qjinid + '&&qjinname=' + encodeURI(this.qjin.qjinname),
                // link: 'http://192.168.13.43:8080/#/login?'+this.qjin.qjinid,

            };
        },
        mounted() {

            this.set(this.qjin);
            // this.creatQrCode();

        },
        methods: {

            set(qjin) {

                this.f_qjin = ApiQjydcInstance.getModel(this.qjin)
                this.f_qjin.children = null;
                this.f_qjin._parent = null;
                this.loadData();
            },
            //保存图片
            savePic(){
                ApiQjydcInstance.saveQRcodePic.call(this);
            },

            loadData() {
//                debugger
                if (this.f_qjin.qjinid) {
                    ApiQjydcInstance.getById(this.f_qjin.qjinid)
                        .then(r => {
                            try {
                              this.f_qjin = r.data.data;
                                let qjs = this.f_qjin.qjinxzqh.split(',')
                                this.selectedOptions = qjs;  //数据回显
                                this.qjin.qjinxzqh = CodeToText[this.selectedOptions[1]]  //二维码参数
                                this.link= 'http://kjxx.cnovit.com/qjwdzs/mob/index.html#/login?id='+this.qjin.qjinid+'&&qjinname='+encodeURI(this.qjin.qjinname)+'&&qjinxzqh='+encodeURI(this.qjin.qjinxzqh);

                            } catch (e) {
                                console.log("数据处理失败：", e);
                            }

                    })
                }
            },

            handleChange (value) {
                this.f_qjin.qjinxzqh = value.join(',');
            },
            save() {
                if (this.f_qjin.qjinname) {
                    this.f_qjin.cuserid = this.$store.state.auth.user.userid;
                    console.log("this.f_qjin", JSON.stringify(this.f_qjin));
                    ApiQjydcInstance.save(this.f_qjin)
                        .then(response => {
                            try {
                                this.$Message.success("保存成功！");

                                this.$emit('on-save', this.f_qjin)
                            } catch (e) {
                                console.log("项目数据处理失败：", e);
                            }
                        })
                        .catch(error => {
                            this.$Message.error(error.message);
                        });
                } else {
                    this.$Message.error("项目名称必须填写！");
                }
            },
            del() {
                if (this.qjin.qjinid) {
                    ApiQjydcInstance.deleteById(this.qjin.qjinid)
                        .then(response => {
                            try {
                                this.$Message.success("删除成功！");
                                this.$emit('on-del', this.qjin)
                            } catch (e) {
                                console.log("项目数据处理失败：", e);
                            }
                        })
                        .catch(error => {
                            this.$Message.error(error.message);
                        });
                } else {
                    this.$Message.error("目录不存在！");
                }
            }
        }
    };
</script>
<style lang="scss" rel="stylesheet/scss">
    .qjin-info {

    }

</style>

