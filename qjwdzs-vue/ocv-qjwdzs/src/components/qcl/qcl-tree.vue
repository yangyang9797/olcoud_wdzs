<template>
    <div class="qcl-tree fill-height ">

        <v-card class=" ocv-card-fill bg-content" slot="left">

            <div class="header ">
                <div class="funcs">
                    <el-input
                            class="search"
                            placeholder="搜索"
                            size="small"
                            style="width: 100%;"
                            v-model="search_value">
                        <template slot="prepend">
                            <Button

                                    class="opts button"
                                    size="small" type="text"
                            >
                                <icons :size="12" @click="saveSeq()" type="search"></icons>
                            </Button>
                        </template>
                        <template slot="append">
                            <Button
                                    @click="showView()"
                                    class="opts button"
                                    size="small" type="text"
                            >
                                <icons :size="12" type="plus"></icons>
                            </Button>
                        </template>
                    </el-input>
                </div>
                <div class="confirm" v-if="showConfirm">
                    <div class="msg">
                        <div>排序已经修改，请点击保存</div>
                    </div>
                    <v-btn @click="saveSeq()" class="mr-1" color="primary" small>
                        <icons :size="12" type="check"></icons>
                        保存
                    </v-btn>
                    <v-btn @click="cancelSeq()" small>
                        <icons :size="12" type="times"></icons>
                        取消
                    </v-btn>
                </div>

            </div>

            <div class="grow   overflow-hidden relative">
                <div class="ocv-fill">
                    <el-scrollbar class="fill-height" view="fill-height " wrap-class="fill-height">
                        <!--<Scroll>-->

                        <el-tree
                                :allow-drag="allowDrag"
                                :allow-drop="allowDrop"
                                :data="tree"
                                :default-expanded-keys="expandedKeys"
                                :filter-node-method="filterNode"
                                :props="props"
                                @node-drop="nodeDrop"
                                class="bg-content"
                                draggable
                                node-key="id"
                                ref="tree"

                        >

                            <div :class="{isqjin:data.qjinid}"
                                 @click="onNodeClick(node, data )"
                                 @dblclick.native="onNodeDblClick(node, data )"
                                 class="tree-node"
                                 slot-scope="{ node, data }">
                                <div class="text">
                                    <icons :size="12" :type="data.qjinid?'gcjk':'folder-open'"></icons>
                                    {{ node.label||'默认' }}
                                </div>
                                <div class="opts">
                                    <el-button
                                            @click.stop="() => onEdit(node,data)"
                                            size="mini"
                                            tilte="编辑"
                                            type="text">
                                        <icons :size="12" type="edit"></icons>
                                    </el-button>
                                    <el-button
                                            @click.stop="() => onAddChild(node,data)"
                                            size="mini"
                                            tilte="添加"
                                            type="text"
                                            v-if="!data.qjinid">
                                        <icons :size="12" type="plus"></icons>
                                    </el-button>

                                    <el-button
                                            @click.stop="() => onDesigner(node,data)"
                                            size="mini"
                                            tilte="设计"
                                            type="text"
                                            v-if="data.qjinid">
                                        <icons :size="12" type="moshubang"></icons>
                                    </el-button>
                                </div>
                            </div>

                        </el-tree>
                    </el-scrollbar>
                </div>
            </div>
            <!--</Scroll>-->
        </v-card>


        <Drawer :title="view.item_sel.qclid?('编辑['+view.item_sel.qclname||''+']'):'添加'"
                scrollable v-model="view.isshow" width="70">
            <qcl-manager :qcl="view.item_sel"
                         @on-ok="ok"
            ></qcl-manager>
        </Drawer>
    </div>
</template>


<script>
    import ApiQjydcCatalog from "../api/api-qjwdzs-catalog";
    import ApiQjydcInstance from "../api/api-qjwdzs-instance";
    import QclManager from "../qcl/qcl-manager";

    export default {
        // name:"qcl-tree",
        components: {QclManager},
        watch: {
            search_value(val) {
                this.$refs.tree.filter(val);
            }
        },
        computed: {
            showConfirm() {
                return this.seq_list_cl.length + this.seq_list_qjin.length > 0;
            }
        },
        data: function () {
            return {
                split: 0.2,
                tree: [],
                seq_list_cl: [],
                seq_list_qjin: [],
                page: {
                    records: []
                },
                props: {label: 'name'},
                expandedKeys: [],
                search_value: "",
                view: {
                    isshow: false,
                    item_sel: ApiQjydcCatalog.getModel()
                },
                ismounted: true
            };
        },
        mounted() {
            this.ismounted = true;
            this.loadData();
            //  this.loadPage();

        },
        methods: {
            // 加载数据
            loadData() {
                ApiQjydcCatalog.treeWithInstances({
                    search: this.search_value,
                    // cuserid:this.$store.state.auth.user.userid
            })
                    .then(r => {
                        this.setTreeData(r.data.data);
                    })
                    .catch(e => {
                        this.$Message.error(e.message);
                    });
            },
            // 数据预处理
            setTreeData(tree) {
                this.getExpandedKeys(this.tree);
                this.setExpandedKeys(tree);
                this.tree = tree;
                this.ismounted = false;
            },
            // 获取展开的节点
            getExpandedKeys(tree) {
                const ks = [];
                this.forEachTree(null, tree, (n) => {
                    const node = this.$refs.tree.getNode(n);
                    //console.info("node", node)
                    if (node.expanded) {
                        ks.push(n.id);
                    }
                })
                this.expandedKeys = ks;
            },
            // 设置上次展开的节点
            setExpandedKeys(tree) {

                // 设置默认展开内容
                if (this.ismounted && this.expandedKeys.length < 1) {
                    tree.forEach(n => {
                        this.expandedKeys.push(n.id);
                    })
                }

                this.forEachTree(null, tree, (n) => {
                    n.expand = this.expandedKeys.indexOf(n.id)
                });
            },
            // 节点点击后触发
            onNodeClick(node, data) {
                this.$emit('onNodeClick', node, data);
            },
            // 节点双击后触发
            onNodeDblClick(node, data) {
                this.$emit('onNodeDblClick', node, data);
            },
            onEdit(node, data) {
                this.$emit('onEdit', node, data);
                if (!data.qjinid) {
                    // 树的编辑
                    this.showView(data);
                }
            },
            onAddChild(node, data) {
                this.$emit('onAdd', node, data);
                if (!data.qjinid) {
                    // 树添加子节点
                    const i = ApiQjydcCatalog.getModel({qclpid: data.qclid, _parent: data});
                    this.showView(i);
                }
            },
            onDesigner(node, data) {
                this.$emit('onDesigner', node, data);
            },

            forEachTree(p, tree, func) {
                if (tree && func) {
                    tree.forEach((n, i, arr) => {
                        func(n, i, arr, p, tree);
                        if (n.children && n.children.length > 0) {
                            this.forEachTree(n, n.children, func);
                        }
                    });
                }
            },


            handleSearch(value) {
                this.search_value = value;
                this.loadData();
            },

            showView(item) {
                if (!item) {
                    item = ApiQjydcCatalog.getModel();
                }
                if (item && item != this.view.item_sel) {
                    this.view.item_sel = item;
                }
                this.view.isshow = true;
            },
            ok(val) {
                this.view.isshow = false;
                this.loadData();
            },
            cancelSeq() {
                this.loadData();
            },
            saveSeq() {
                this.checkSeqChange();
                // console.info("saveSeq", this.seq_list_cl, this.seq_list_qjin)
                //return;
                ApiQjydcCatalog.saveAll(this.seq_list_cl)
                    .then(() => ApiQjydcInstance.saveAll(this.seq_list_qjin))
                    .then(() => {
                        this.seq_list_cl = []
                        this.seq_list_qjin = []
                        this.loadData();
                        this.$Message.success("保存成功");
                    })
                    .catch(e => {
                        console.error(e);
                        this.$Message.error(e.message);
                    });
                if ((this.seq_list_cl.length + this.seq_list_qjin.length) < 1) {
                    this.$Message.info("还没有要保存的内容");
                }
            },
            findSeqChange(p, treelists) {
                if (treelists && treelists.length > 0) {
                    const p_id = (p ? p.id : '') || '';
                    treelists.forEach((i, n) => {
                        if (
                            (i.qjinid && i.qclid !== p_id) // 流程拖动了
                            || i.qclpid !== p_id  // 目录拖动了
                            || i.seqno !== n  // 排序发生变化
                        ) {
                            if (i.qjinid) {
                                this.seq_list_qjin.push({
                                    qjinid: i.qjinid,
                                    qclid: p_id,
                                    seqno: n
                                });
                            } else if (i.qclid) {
                                this.seq_list_cl.push({
                                    qclid: i.qclid,
                                    qclpid: p_id,
                                    seqno: n
                                });
                            }
                        }
                        this.findSeqChange(i, i.children);
                    });
                }
            },
            checkSeqChange() {
                this.findSeqChange(null, this.tree);
            },
            allowDrop(draggingNode, dropNode, type) {
                // console.info(draggingNode, dropNode, type);
                return dropNode.data.qclid && (!(dropNode.data.qjinid && 'inner' === type));
                // console.info(dropNode);
                //return true;

            },
            allowDrag(draggingNode) {
                return true;
            },
            nodeDrop(draggingNode, dropNode, type, e) {
                this.checkSeqChange()
                dropNode.expanded = true;
                // console.info("nodeDrop", draggingNode, dropNode, type, e, this.seq_list_cl, this.seq_list_qjin)
            },

            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            }

        }
    };
</script>
<style lang="scss" rel="stylesheet/scss">

    .qcl-tree {
        width: 100%;
        padding: 5px;

        .header {

            background: transparent !important;

            .funcs {
                background: #ebeef5;
            }

            .confirm {
                text-align: center;
                background: rgba(255, 0, 5, 0.1);
                display: block;

                .msg {
                    /*text-align: left;*/
                    font-size: 14px;
                    margin-bottom: 10px;
                    color: #E65100;
                }
            }
        }

        .search {
            .el-input-group__append, .el-input-group__prepend {
                padding: 0px 5px;
                cursor: pointer;
            }

            .el-input-group__append,
            .el-input-group__append .button {
                border-top-right-radius: 20px;
                border-bottom-right-radius: 20px;
            }

            .el-input-group__prepend,
            .el-input-group__prepend .button {
                border-top-left-radius: 20px;
                border-bottom-left-radius: 20px;
            }
        }

        .el-tree-node {
            .el-tree-node__content {
                padding: 15px 0px;
            }

            .tree-node {
                font-size: 14px;
                font-weight: 400;
            }

            .el-tree-node {
                .el-tree-node__content {
                    padding: 14px 0px;
                }

                .tree-node {
                    font-size: 14px;
                }


                .el-tree-node {
                    .el-tree-node__content {
                        padding: 14px 0px;
                    }

                    .tree-node {
                        font-size: 13px;
                    }
                }
            }
        }

        .el-tree-node:focus > .el-tree-node__content {
            background-color: #e2eaf5;
        }

        .el-tree-node > .el-tree-node__content:hover {
            background-color: #eef3f6;
        }


        .tree-node {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: space-between;
            font-size: 14px;
            padding-right: 8px;


            .text {
                flex: 1 0 auto;
                display: block;
                overflow: hidden;
                text-overflow: ellipsis;
                width: 100px;
            }

            .opts {
                flex: 0 1 auto;
                display: none;
            }

            &:hover {
                .opts {
                    display: block;
                }
            }

        }

        .el-tree .el-tree-node.is-current {
            & > .el-tree-node__content {
                border-left: 3px solid #409EFF;
                background: rgba(64, 158, 255, 0.1);
            }
        }

        .isqjin.tree-node .text i {
            color: #409EFF;
        }

        .isqjin.tree-node .text {
            color: #409EFF;
        }

        .tree-node .text {
            color: #444444;
        }

        .tree-node .text i {
            color: #E6A23C;
        }

    }


</style>

