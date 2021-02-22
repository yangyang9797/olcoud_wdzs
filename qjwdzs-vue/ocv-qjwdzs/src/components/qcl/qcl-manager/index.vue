<template>
    <div class="qcl-manager">

        <el-tabs v-model="currentname">
            <el-tab-pane name="info">
                <span slot="label"><i class="el-icon-date"></i> 目录信息</span>
                <qcl-info :qcl="qcl" @on-del="ok" @on-save="ok"></qcl-info>
            </el-tab-pane>
        </el-tabs>


    </div>
</template>


<script>
    // import qclInfo from './qcl-info'
    import QclInfo from './qcl-info'

    export default {
        components: {QclInfo},
        props: ["qcl", "activename"],
        data: function () {
            return {
                currentname: this.activename || "info",

            };
        },

        mounted() {
        },
        methods: {
            ok(val) {
                this.$emit('on-ok', val)
            }

        }
    };
</script>
<style lang="scss" rel="stylesheet/scss">
    .qcl-manager {
        height: 100%;
    }
</style>

