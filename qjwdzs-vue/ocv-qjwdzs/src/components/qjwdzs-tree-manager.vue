<template>
    <div class="qjin-tree-manager fill-height ">
        <v-card class="fill-height layout " outlined>
            <Split v-model="split">
                <qcl-tree @onDesigner="onDesigner"
                          @onEdit="onEdit"
                          @onNodeClick="onNodeClick"
                          ref="leftTree" slot="left"></qcl-tree>

                <qjin-list @on-ok="ok" class="ml-1"
                           ref="rightList" slot="right"></qjin-list>
            </Split>
        </v-card>
    </div>
</template>


<script>
    import QclTree from './qcl/qcl-tree';
    import QjinList from './qjin/qjin-list';

    export default {
        components: {QjinList, QclTree},
        data: function () {
            return {
                split: 0.2,
            };
        },
        methods: {
            onNodeClick(node, data) {
                this.$refs.rightList.loadData(data);
            },
            onEdit(node, data) {
                if (data.qjinid) {
                    this.$refs.rightList.showView(data);
                }
            },
            onDesigner(node, data) {
                if (data.qjinid) {
                    this.$refs.rightList.showDesigner(data);
                }
            },
            ok(val) {
                console.info("ok", val);
                this.$emit('on-ok', val)
                this.$refs.leftTree.loadData();
            },
        }
    };
</script>
<style lang="scss" rel="stylesheet/scss">

    .qjin-tree-manager {
        width: 100%;
        padding: 10px;

    }


</style>

