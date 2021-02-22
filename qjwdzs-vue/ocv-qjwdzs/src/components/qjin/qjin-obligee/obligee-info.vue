<template>
    <div class="drawer-content">
        <div class="top-title">权利人信息</div>
        <Row>
            <el-card class="box-card">
                <!--                <div slot="header" class="clearfix">-->
                <!--                    <span>权利人信息</span>-->
                <!--                    <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
                <!--                </div>-->
                <div>
                    <Row>
                        <Col span="10">
                            <span class="title1 justified"><span v-for="item in '户主'" :key="item">{{item}}</span></span>：
                            <span>{{sel_item.zzqlr}}</span>
                        </Col>
                        <Col span="14">
                            <span class="title1 justified"><span v-for="item in '电话号码'" :key="item">{{item}}</span></span>：
                            <span>{{sel_item.dhhm}}</span>
                        </Col>
                    </Row>
                    <Row class="mt-2">
                        <Col span="10">
                            <span class="title1 justified"><span v-for="item in '证件种类'" :key="item">{{item}}</span></span>：
                            <span>身份证</span>
                        </Col>
                        <Col span="14">
                            <span class="title1 justified"><span v-for="item in '证件号'" :key="item">{{item}}</span></span>：
                            <span>{{sel_item.zjh}}</span>
                        </Col>
                    </Row>

                    <span class="title2">共有情况</span>
                    <Row v-for="i in getGyqk(sel_item.publicstate)" :key="i">
                        <Col span="10">
                            <span class="title1 justified"><span v-for="item in '姓名'" :key="item">{{item}}</span></span>：
                            <span>{{i.zzqlr}}</span>
                        </Col>
                        <Col span="14">
                            <span class="title1 justified"><span v-for="item in '证件号'" :key="item">{{item}}</span></span>：
                            <span>{{i.zjh}}</span>
                        </Col>
                    </Row>
                    <span class="title2">证明材料</span>
                    <Row>
                        <div class="title1">身份证：</div>
                        <!--                            <Icon type="ios-arrow-back" size="25"/>-->
                        <div class="avatar-box">
                            <el-avatar
                                    v-for="file in fileList.filter(val=>val.reftype=='身份证')" :key="file.fileid"
                                    @click.native="showImage(file,fileUrl+file.fileid)"
                                    style="margin: 8px 16px 0 0;border-radius: 0;" shape="square" :size="60"
                                   :fit="fileUrl+file.fileid"
                                   :src="fileUrl+file.fileid">
                            </el-avatar>
                        <!--                            <Icon type="ios-arrow-forward" size="25"/>-->
                        </div>
                    </Row>
                    <Row>
                        <div class="title1">户口本：</div>
                        <div class="avatar-box">
                            <!--                            <Icon type="ios-arrow-back" size="25" class="icon-wz"/>-->
                            <i :class="['el-icon-arrow-left', {'disabled':curPage===0}]" v-if="residenceList&&residenceList.length>0" @click="curPage!==0&&turnPage('pre')"></i>
                            <el-avatar
                                    v-for="file in residenceList" :key="file.fileid"
                                    @click.native="showImage(file,fileUrl+file.fileid)"
                                    style="margin: 8px 16px 0 0;border-radius: 0;"
                                    :size="60"
                                    :fit="fileUrl+file.fileid"
                                    :src="fileUrl+file.fileid">
                            </el-avatar>
                            <i :class="['el-icon-arrow-right', {'disabled':(curPage+1)*6>=residenceListAll.length}]" v-if="residenceList&&residenceList.length>0" @click="(curPage+1)*6<residenceListAll.length&&turnPage('next')"></i>
                            <!--                            <Icon type="ios-arrow-forward" size="25"/>-->
                        </div>
                    </Row>
                </div>

            </el-card>
        </Row>
        <div class="top-title" style="margin-top: 20px">不动产信息</div>
        <Row>
            <el-card class="box-card">
                <!--                <div slot="header" class="clearfix">-->
                <!--                    <span>不动产信息</span>-->
                <!--                    <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
                <!--                </div>-->
                <Row>
                    <span class="title1 justified"><span v-for="item in '坐落'" :key="item">{{item}}</span></span>：
                    <span>{{sel_item.obladdress}}</span>
                </Row>
                <Row>
                    <span class="title1 justified"><span v-for="item in '备注信息'" :key="item">{{item}}</span></span>：
                    <span>{{sel_item.description}}</span>
                </Row>
                <v-divider></v-divider>
                <span class="title2">证明材料</span>
                <Row>
                    <div class="title1">土地证：</div>
                    <!--                        <Icon type="ios-arrow-back" size="25"/>-->
                    <div class="avatar-box">
                        <el-avatar
                                v-for="file in fileList.filter(val=>val.reftype=='土地证')" :key="file.fileid"
                                @click.native="showImage(file,fileUrl+file.fileid)"
                                style="margin: 8px 16px 0 0;border-radius: 0;" shape="square" :size="60"
                               :fit="fileUrl+file.fileid"
                               :src="fileUrl+file.fileid">
                        </el-avatar>
                    <!--                        <Icon type="ios-arrow-forward" size="25"/>-->
                    </div>
                </Row>
                <Row>
                    <div class="title1">房产证：</div>
                    <div class="avatar-box">
                    <!--                        <Icon type="ios-arrow-back" size="25"/>-->
                        <el-avatar
                                v-for="file in fileList.filter(val=>val.reftype=='房产证')" :key="file.fileid"
                                @click.native="showImage(file,fileUrl+file.fileid)"
                                style="margin: 8px 16px 0 0;border-radius: 0;" shape="square" :size="60"
                               :fit="fileUrl+file.fileid"
                               :src="fileUrl+file.fileid">
                        </el-avatar>
                    </div>
                    <!--                        <Icon type="ios-arrow-forward" size="25"/>-->
                </Row>
                <Row>
                    <div class="title1">房屋远景照片：</div>
                    <!--                        <Icon type="ios-arrow-back" size="25"/>-->
                    <div class="avatar-box">
                        <el-avatar
                                v-for="file in fileList.filter(val=>val.reftype=='房屋远景')" :key="file.fileid"
                                @click.native="showImage(file,fileUrl+file.fileid)"
                                style="margin: 8px 16px 0 0;border-radius: 0;" shape="square" :size="60"
                               :fit="fileUrl+fileid"
                               :src="fileUrl+file.fileid">
                        </el-avatar>
                    </div>
                    <!--                        <Icon type="ios-arrow-forward" size="25"/>-->
                </Row>
                <Row>
                    <div class="title1">门牌号照片：</div>
                    <!--                        <Icon type="ios-arrow-back" size="25"/>-->
                    <div class="avatar-box">
                        <el-avatar
                                v-for="file in fileList.filter(val=>val.reftype=='门牌号')" :key="file.fileid"
                                @click.native="showImage(file,fileUrl+file.fileid)"
                                style="margin: 8px 16px 0 0;border-radius: 0;" shape="square" :size="60"
                               :fit="fileUrl+file.fileid"
                               :src="fileUrl+file.fileid">
                        </el-avatar>
                    </div>
                    <!--                        <Icon type="ios-arrow-forward" size="25"/>-->
                </Row>
                <Row>
                    <div class="title1">其他：</div>
                    <!--                        <Icon type="ios-arrow-back" size="25"/>-->
                    <div class="avatar-box">
                        <el-avatar
                                v-for="file in fileList.filter(val=>val.reftype=='其他')" :key="file.fileid"
                                @click.native="showImage(file,fileUrl+file.fileid)"
                                style="margin: 8px 16px 0 0;border-radius: 0;" shape="square" :size="60"
                                   :fit="fileUrl+file.fileid"
                                   :src="fileUrl+file.fileid">
                        </el-avatar>
                    </div>
                    <!--                        <Icon type="ios-arrow-forward" size="25"/>-->
                </Row>
                <Modal v-model="showimage">
                    <p slot="header">
                        {{showImageInfo.filename}}
                    </p>
                    <div style="text-align:center">
                        <el-avatar shape="square" :size="400" :fit="showImageInfo" :src="showImageUrl"></el-avatar>
                    </div>
                    <div slot="footer">
                    </div>
                </Modal>
            </el-card>
        </Row>
    </div>
</template>


<script>

    import ApiFile from "../../api/api-file";

    export default {
        name: "obligee-info",
        components: {},
        props: ["sel_item"],
        watch: {
            sel_item() {
                this.getFileList();
            }
        },
        computed: {
            residenceListAll() {
                return this.fileList.filter(val=>val.reftype=='户口本')
            }
        },
        data: function () {
            return {
                fileList: [],
                fileUrl: ApiFile.apiUrl + 'view?fileid=',
                showimage: false,
                showImageInfo: {},
                showImageUrl: "",
                residenceList: [],
                curPage: 0,
            };
        },
        created() {
            this.getFileList();
        },
        mounted() {

        },
        methods: {
            getFileList() {
                ApiFile.list({
                    refid: this.sel_item.qlrid
                }).then(res => {
                    try {
                        console.log(res);
                        this.fileList = res.data.data
                        this.residenceList = this.residenceListAll.slice(0,6);
                    } catch (e) {

                    }
                })
            },
            getGyqk(data) {
                return data ? JSON.parse(data) : [];
            },
            showImage(file, url) {
                this.showimage = true;
                this.showImageInfo = file;
                this.showImageUrl = url;
            },
            /**
             * 翻页
             * type 类型（pre上一页，next下一页）
             * */
            turnPage(type) {
                this.curPage = type==='pre'?this.curPage-1:this.curPage+1;
                this.residenceList = this.residenceListAll.slice(this.curPage*6,this.curPage*6+6)
                console.log(this.residenceListAll,this.curPage)
            },
        }
    };
</script>
<style lang="scss" rel="stylesheet/scss">
    .drawer-content {
        width: 488px;
        font-family:PingFangSC-Regular,PingFang SC;
    }

    .obligee-info {

    }

    .title1 {
        &.justified {
            display: inline-flex;
            width: 60px;
            justify-content: space-between;
        }
        & + span {
            color: #333;
        }
    }

    .title2 {
        display: inline-block;
        margin: 16px 0;
        font-size: 16px;
        font-weight: 500;
        color: #333;
        font-family:PingFangSC-Medium,PingFang SC;
    }

    .top-title {
        margin-bottom: 8px;
        font-size: 18px;
        font-weight: 500;
        color: #333;
    }

    .icon-wz {

    }

    .box-card {
        font-size: 14px;
        /deep/ .el-card__body {
            padding: 24px;
        }
        /deep/ .ivu-row {
            margin-bottom: 16px;
            color: #999;
        }
    }

    .avatar-box {
        position: relative;
        white-space: nowrap;
        > i {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            color: #333;
            cursor: pointer;
            &.disabled {
                color: #b2b2b2;
                cursor: not-allowed;
            }
            &.el-icon-arrow-left {
                left: -15px;
            }
            &.el-icon-arrow-right {
                right: -15px;
            }
        }
        /deep/ img {
            margin: auto;
        }
    }
</style>

