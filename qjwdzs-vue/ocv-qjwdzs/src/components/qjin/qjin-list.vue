<template>
    <div class="qjin-list fill-height ">
        <v-card class=" ocv-card-fill" slot="right">

            <div class="header ">
                <div>
                    <div class="title">
                        <icons type="gongdan"></icons>
                        权籍调查
                    </div>

                    <div class="opts ">
                        <Input
                                @on-search="handleSearch"
                                clearable
                                enter-button
                                placeholder="输入项目名称"
                                search
                                style="width: 200px;"
                                v-model="search_value"
                        />
                        <Button
                                @click="showView()"
                                class="opts button"
                                icon="md-add"
                                type="primary"
                        >新增项目
                        </Button>
                    </div>
                </div>
            </div>
            <div class="grow overflow-hidden relative">
                <div class="ocv-fill">
                    <el-scrollbar class="fill-height" view="fill-height" wrap-class="fill-height">
                        <el-table :data="pagelist.records"
                                  border size="mini"
                                  style="width:100%"
                                  @row-click="showObligees"
                        >
                            <el-table-column
                                    align="center"
                                    label="序号"
                                    type="index"
                                    width="60">
                            </el-table-column>
                            <el-table-column
                                    label="项目名称"
                                    min-width="150"
                                    width="240"
                                    prop="qjinname">
                                <template slot-scope="scope">
                                    <div class="blue--text">
                                        <icons :size="12" type="gcjk"></icons>
                                        {{ scope.row.qjinname}}
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    label="分类"
                                    min-width="100"
                                    prop="qclname">
                            </el-table-column>
                            <el-table-column
                                    align="left"
                                    label="说明"
                                    prop="description">
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    label="日期"
                                    prop="ctime"
                                    width="240">
                            </el-table-column>
                            <el-table-column
                                    align="center"
                                    label="操作"
                                    width="150">
                                <template slot-scope="scope">
                                    <el-button
                                            @click="qjinEdit( scope.row)"
                                            size="mini">
                                        <icons :size="12" type="edit"></icons>
                                        编辑
                                    </el-button>
                                    <!--<el-button @click="qjinDesigner( scope.row)"
                                               size="mini"
                                               type="primary">
                                        <icons :size="12" type="moshubang"></icons>
                                        设计
                                    </el-button>-->
                                </template>
                            </el-table-column>
                        </el-table>


                    </el-scrollbar>
                </div>
            </div>

            <el-pagination
                    :current-page="pagelist.current"
                    :page-size="pagelist.size"
                    :page-sizes="[10, 15,30, 50,100]"
                    :total="pagelist.total"
                    @current-change="(val)=>{pagelist.current=val; this.loadData()}"
                    @size-change="(val)=>{pagelist.size=val;pagelist.current = 1; this.loadData()}"
                    class="pa-1"
                    layout="total, sizes, prev, pager, next, jumper"
            ></el-pagination>
        </v-card>

        <Drawer :title="view.item_sel.qjinid ?('编辑['+view.item_sel.qjinname+']'):'添加'"
                v-show="view.item_sel.qjinid"
                scrollable v-model="view.isshow" width="70%">
            <qjin-manager :qjin="view.item_sel"
                          @on-ok="ok"
                          v-if="view.isshow"
            ></qjin-manager>
        </Drawer>

        <el-dialog title="新增项目" v-show="!view.item_sel.qjinid" :visible.sync="showAddDialog" width="314px" class="add-project-dialog">
            <add-project :qjin="view.item_sel" :visible="showAddDialog" @on-close="showAddDialog=false" @on-save="onSaveProject"></add-project>
        </el-dialog>

        <Drawer :title="oblititle"
                scrollable v-model="view.isshow_obligee" width="100%">
            <qjin-obligee :qjin="view.item_sel"
                          @on-ok="ok"
                          v-if="view.isshow_obligee"
            ></qjin-obligee>
        </Drawer>
    </div>
</template>


<script>
    import ApiQjydcCatalog from "../api/api-qjwdzs-catalog";
    import ApiQjydcInstance from "../api/api-qjwdzs-instance";
    import QjinDesigner from "./qjin-designer/index";
    import QjinManager from "./qjin-manager";
    import QjinObligee from "./qjin-obligee";
    import addProject from'./qjin-manager/add-project';

    export default {
        name: "qjin-list",
        components: {QjinManager, QjinDesigner, QjinObligee, addProject},
        data: function () {
            return {
                pagelist: {
                    records: [],
                    current: 1,
                    size: 15,
                    total: 0,
                    pages: 0
                },
                oblititle: "湖北省/武汉市/洪山区",
                search_value: "",
                view: {
                    isshow: false,
                    isshow_designer: false,
                    isshow_obligee: false,
                    item_sel: ApiQjydcInstance.getModel()
                },
                opt: {},
                showAddDialog: false
            };
        },
        mounted() {
            this.loadData();
        },
        methods: {
            onSaveProject(){
                this.$parent.$parent.$parent.$refs.leftTree.loadData()
            },
            loadData(opt) {
                if (opt.qclid){
                    this.opt = opt || {};
                    // 根据目录编号加载子项的所有流程
                    ApiQjydcCatalog.pageInstancesByTree({
                        pageindex: this.pagelist.current,
                        pagesize: this.pagelist.size,
                        search: this.search_value,
                        searchcols: 'qjinname',
//                    qclid: opt ? opt.qclid : null
                        qclid:  opt.qclid
                    })
                        .then(r => {
                            this.pagelist = r.data.data;
                        })
                        .catch(e => {
                            this.$Message.error(e.message);
                        });
                }

            },
            handleSearch(value) {
                this.search_value = value;
                this.s
                this.loadData();
            },
            showView(item) {
                this.setView(item);
                if(item){
                    this.$set(this.view,'isshow', true);
                }else {
                    this.showAddDialog = true;
                }
            },
            showDesigner(item) {
                this.setView(item);
                this.view.isshow_designer = true;
                console.info("设计", item);

            },
            setView(item) {
                if (!item) {
                    item = ApiQjydcInstance.getModel({qclid: this.opt.qclid});
                }
                if (item && item != this.view.item_sel) {
                    this.view.item_sel = item;
                }
            },
            ok(val) {
                this.view.isshow = false;
                console.info("ok", val);
                this.$emit('on-ok', val)
                this.loadData();
            },
            qjinEdit(item) {
                 if (this.view.isshow_obligee == true) {
                    this.view.isshow_obligee = false;
                 }
                this.showView(item);
            },
            qjinDesigner(item) {
                this.showDesigner(item);
            },
            showObligees(row) {
                this.setView(row);
                if (!this.view.isshow) {
                    this.view.isshow_obligee = true;
                }
            }
        }
    };
</script>
<style lang="scss" rel="stylesheet/scss">

    .qjin-list {

        width: 100%;
        padding: 5px 10px 5px 8px;

        .ivu-input-icon-clear {
            position: absolute;
            right: 48px;
            z-index: 3;
        }
    }

    .drawer-designer {
        .ivu-drawer-body {
            padding: 4px;
        }

    }

    .add-project-dialog {
        /deep/ .el-dialog__header {
            text-align: center;
        }
        /deep/ .el-dialog__body {
            padding: 10px 20px 20px 20px;
        }
    }

</style>

