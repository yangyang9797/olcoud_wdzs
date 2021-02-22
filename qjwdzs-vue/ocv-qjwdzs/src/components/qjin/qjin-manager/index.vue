<template>
    <div class="qjin-manager">

        <el-tabs v-model="currentname">
            <el-tab-pane name="info">
                <span slot="label"><i class="el-icon-date"></i> 项目信息</span>
                <qjin-info :qjin="qjin" @on-del="ok" @on-save="ok"></qjin-info>
            </el-tab-pane>
        </el-tabs>


    </div>
</template>


<script>
    import QjinInfo from './qjin-info'

    export default {
        name: "index",
        components: {QjinInfo},
        props: ["qjin", "activename"],
        data: function () {
            return {
//                currentname: this.activename || "info"
                currentname:"info"
            };
        },
        mounted() {
        },
        methods: {
            ok(val) {
                console.log("val", val);
                this.$emit('on-ok', val)
            }
        }
    };
</script>
<style lang="scss" rel="stylesheet/scss">
    .qjin-manager {
        height: 100%;
    }
</style>

