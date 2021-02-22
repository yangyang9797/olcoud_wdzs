<template>
    <div class=" qjin-designer fill-height v-application">
        <div class=" ocv-card-fill">
            <div class="header">
                <div>
                    <div class="title">项目设计器</div>
                    <div class="opts">
                        <Button
                                icon="times"
                        >预览
                        </Button>
                        <Button
                                @click="save"
                                icon="md-checkmark"
                                type="primary"
                        >保存
                        </Button>

                    </div>
                </div>
            </div>
            <div class="content grow ocv-relative">
                <div class="ocv-fill">
                    <Split v-model="split.r">
                        <Split class="fill-height" slot="left" v-model="split.l">
                            <div class="fill-height panel-ctrl" slot="left">
                                <!-- 左边的控件列表 -->
                                <div class="conter">
                                    <span >节点列表</span>
                                </div>

                            </div>
                            <div class="fill-height panel-designer" slot="right">
                                <el-tabs class="fill-height ocv-tabs-fill" type="border-card"
                                         v-model="layout.left.activename">
                                    <el-tab-pane name="designer">
                                        <span slot="label"><icons type="gongdan"></icons> 设计</span>
                                        <el-scrollbar class="fill-height fill-width" style="overflow: hidden;"
                                                      view-class="fill-height" wrap-class="fill-height">
                                            <div class="fill-height">
                                                <Input class="input_fill" type="textarea" v-model="f_qjin.content"/>
                                            </div>
                                        </el-scrollbar>

                                    </el-tab-pane>
                                    <el-tab-pane name="code">
                                        <span slot="label"><icons type="zitidaima"></icons> 代码 </span>
                                        <el-scrollbar class="fill-height fill-width" view-class="fill-height"
                                                      wrap-class="fill-height">
                                            <json-viewer :expand-depth=50 :value="d" copyable></json-viewer>
                                        </el-scrollbar>
                                    </el-tab-pane>
                                </el-tabs>
                            </div>

                        </Split>
                        <div class="fill-height panel-prop" slot="right">
                            <el-tabs class="fill-height ocv-tabs-fill" type="border-card"
                                     v-model="layout.right.activename">
                                <el-tab-pane name="prop">
                                    <span slot="label"><icons type="zitidaima"></icons> 属性 </span>
                                    <el-scrollbar class="fill-height fill-width" view-class="fill-height"
                                                  wrap-class="fill-height">

                                    </el-scrollbar>
                                </el-tab-pane>
                            </el-tabs>
                        </div>
                    </Split>
                </div>
            </div>
        </div>

    </div>
</template>

<script>

    import JsonViewer from 'vue-json-viewer';
    import ApiQjydcInstance from '../../api/api-qjwdzs-instance'

    export default {
        name: "qjin-designer",
        components: {
            JsonViewer
        },
        props: {
            qjin: {
                type: Object,
                default: () => ({})
            },
            activename: {
                type: Number,
            }
        },
        watch: {
            qjin(val) {
                this.set(val);
            }
        }, computed: {},
        data: function () {
            return {
                f_qjin: {},
                split: {
                    r: 0.8,
                    l: 0.25
                },
                layout: {
                    left: {
                        activename: ""
                    },
                    right: {
                        activename: ""
                    }
                },
                d: {
                    // 设计器数据部分
                },
            };
        },
        mounted() {
            this.loadData();
        },
        methods: {
            //拿到项目center和节点规则等信息
            loadData() {
                ApiQjydcInstance.getById(this.qjin.qjinid)
                    .then(r => {
                        this.setData(r.data.data);
                    })
                    .catch(e => {
                        this.$Message.error(e.message);
                    });

            },
            set(qjin) {
                this.qjin = qjin;
                this.loadData()
            },
            setData(val) {
                this.f_qjin = val;
            },
            designer() {
                if (this.f_qjin && this.f_qjin.qjinid) {
                    const content = this.f_qjin.content;
                    if (content && content.length > 0) {
                        try {
                            this.d = JSON.parse(content);
                            return
                        } catch (e) {
                            console.info("内容解析失败", content, e);
                            this.$Message.error("内容解析失败：" + e.message);
                        }
                    }

                    this.d = {};
                    console.info("content", this.d);
                }
            },

            save() {
                if (this.f_qjin.qjinname) {
                    this.f_qjin.content = JSON.stringify(this.d);
                    ApiQjydcFlow.save(this.f_qjin)
                        .then(r => {
                            try {
                                this.$Message.success("保存成功！");

                                this.$emit('on-save', this.f_qjin)
                            } catch (e) {
                                console.log("数据处理失败：", e);
                            }
                        })
                        .catch(error => {
                            this.$Message.error(error.message);
                        });
                } else {
                    this.$Message.error("目录名称必须填写！");
                }
            }
        }}
</script>

<style lang="less">

    .qjin-designer {
        .ocv-tabs-fill {
            display: flex;
            flex-direction: column;

            > .el-tabs__header {
                flex: 0 0 auto;
            }

            > .el-tabs__content {
                flex: 1 1 auto;
                overflow: hidden;
                position: relative;

                > .el-tab-pane {
                    position: absolute;
                    top: 0px;
                    right: 0px;
                    left: 0px;
                    bottom: 0px;
                }
            }
        }

        .right-pane {
            padding: 0px 0px 0px 5px;
        }

        .el-tabs__content {
            padding: 5px;
        }

        .panel-ctrl, .panel-prop {
            .el-tabs__item {
                padding: 0px 10px;
            }


            .el-collapse-item__content {
                padding-bottom: 0px;
            }
        }
        .rules{
            height: 700px;
            width: 100%;
            background: #f0f2f5;
            -webkit-box-flex: 0;
            -ms-flex: 0 0 auto;
            flex: 0 0 auto;
            float: left;
            border-right: 1px solid #E9E9E9;
            border-bottom: 1px solid #E9E9E9;
            .rulesTile{
                text-align: left;
                height: 32px;
                padding-left: 12px;
                color: #000;
                line-height: 28px;
                background: #EBEEF2;
                border-bottom: 1px solid #DCE3E8;
            }

            .qjwdzs-input-suffix {
                width: 90%;
                margin: 10px;
            }
        }

        .panel-designer {


        }

        .conter {
            text-align: center;

            span {
                border-bottom: 1px solid #ececec;
                border-left: 1px solid #ececec;
                display: inline-block;
                padding: 10px;
                width: 100%;
            }
        }

        canvas {
            width: 100%;

        }

        .input_fill {
            height: 100%;

            & > textarea {

                height: 100%;
            }
        }

    }
</style>
