<template>
    <v-card class=" ocv-card-fill">
            <div class="header">
                <div>
                <div class="title">目录信息表</div>
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
                        :model="f_qcl"
                        :rules="fromrule"
                        class="ai-v-form"
                        ref="viewform"
                >

                    <FormItem
                            label="目录名称"
                            prop="qclname"
                    >
                        <Input v-model="f_qcl.qclname"/>
                    </FormItem>

                    <FormItem
                            class="item-w50 label-x40"
                            label="是否启用"
                            prop="enable"
                    >
                        <RadioGroup v-model="f_qcl.enable">
                            <Radio label="1">是</Radio>
                            <Radio label="0">否</Radio>
                        </RadioGroup>
                    </FormItem>
                    <FormItem
                            class="item-w50 label-x40"
                            label="排序"
                            prop="seqno"
                    >
                        <Input v-model="f_qcl.seqno"/>
                    </FormItem>
                    <FormItem
                            class="item-h-l-2"
                            label="描述"
                            prop="description"
                    >
                        <Input type="textarea" v-model="f_qcl.description"/>
                    </FormItem>
                </Form>
            </el-scrollbar>
        </div>

  </v-card>
</template>


<script>
    import ApiQjydcCatalog from "../../api/api-qjwdzs-catalog";


    export default {
        components: {},
        props: ["qcl"],
        watch: {
            qcl() {
                this.set(this.qcl);
            }
        },
        data: function () {
            return {
                qcltypes: [],
                qclunits: [],
                f_qcl: ApiQjydcCatalog.getModel(),
                fromrule: {
                    qclname: [{required: true, message: "目录名称必须填写", trigger: "blur"}],
                }
            };
        },
        mounted() {
            this.set(this.qcl);
        },
        methods: {
            set(qcl) {
                this.f_qcl = ApiQjydcCatalog.getModel(this.qcl)
                this.f_qcl.children = null;
                this.f_qcl._parent = null;

            },

            save() {
                if (this.f_qcl.qclname) {
                    this.f_qcl.cuserid =this.$store.state.auth.user.userid;
                    ApiQjydcCatalog.save(this.f_qcl)
                        .then(response => {
                            try {
                                this.$Message.success("保存成功！");

                                this.$emit('on-save', this.f_qcl)
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
            },
            del() {
                if (this.qcl.qclid) {
                    ApiQjydcCatalog.deleteById(this.qcl.qclid)
                        .then(response => {
                            try {
                                this.$Message.success("删除成功！");
                                this.$emit('on-del', this.qcl)
                            } catch (e) {
                                console.log("数据处理失败：", e);
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

</style>

