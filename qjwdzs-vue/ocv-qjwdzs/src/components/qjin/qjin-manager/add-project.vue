<template>
    <section class="dialog-content">
        <el-form :model="f_qjin" label-position="top" :rules="rules" ref="form">
            <el-form-item v-for="item in model" :key="item.prop" :label="item.label" :prop="item.prop">
                <el-input v-model="f_qjin[item.prop]" placeholder="请输入" @blur="check"></el-input>
            </el-form-item>
        </el-form>
        <el-button type="primary" @click="save">保存</el-button>
    </section>
</template>

<script>
    // import qrcodeVue from 'qrcode-vue';
    import ApiQjydcInstance from "../../api/api-qjwdzs-instance";
    export default {
        // components: {qrcodeVue},
        props: ["qjin","visible"],
        watch: {
            visible:{
                handler() {
                    this.$refs.form.resetFields();
                },
                immediate: true
            }
        },
        data() {
            return {
                f_qjin: ApiQjydcInstance.getModel(),
                model: [
                    { label: '调查单位', prop: ''},
                    { label: '项目名称', prop: 'qjinname'},
                    { label: '项目编号', prop: 'qjincode'},
                ],
                rules: {
                    qjinname: [
                        { required: true, message: '项目名称必须填写', trigger: 'blur' }
                    ]
                },
            }
        },
        methods: {

            save() {
                if (this.f_qjin.qjinname) {
                    this.f_qjin.cuserid = this.$store.state.auth.user.userid;
                    console.log("this.f_qjin", JSON.stringify(this.f_qjin));
                    ApiQjydcInstance.save(this.f_qjin)
                        .then(response => {
                            try {
                                this.$message({message:"保存成功！",type: 'success'});
                                this.$emit('on-close');
                                this.$emit('on-save', this.f_qjin)
                            } catch (e) {
                                console.log("项目数据处理失败：", e);
                            }
                        })
                        .catch(error => {
                            this.$message.error(error.message);
                        });
                } else {
                    this.$message.error("项目名称必须填写！");
                }
            }
        }
    }
</script>

<style lang="less" scoped>
    .dialog-content {
        font-size: 14px;
        /deep/ .el-form-item {
            margin-bottom: 16px;
        }
        /deep/ .el-form-item__label {
            font-weight: 400;
            line-height: 0;
            &::before {
                display: none;
            }
        }
        /deep/ input {
            height: 36px;
            line-height: 36px;
            padding: 0 8px;
        }
        /deep/ .el-button {
            display: block;
            width: 76px;
            padding: 8px;
            margin: auto;
        }
    }
    .QRcode {
        display: inline-flex;
        align-items: center;
        justify-content: space-between;
        width: 100%;
        /deep/ .el-divider {
            width: 73px;
            margin: 0;
        }
        & + img {
            display: block;
            margin: 16px auto 24px auto;
        }
    }
</style>