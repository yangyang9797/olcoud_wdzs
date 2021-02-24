<template>
  <div class="qjin-designer fill-height v-application">
    <div class="ocv-card-fill">
      <div style="margin-bottom: 10px">
        <Input
          style="width: 400px"
          search
          placeholder="请输入名称"
          v-model="searchValue"
          @change="search()"
        />
        <Button type="primary" style="float: right" @click="downloadFile()">
          <Icon type="ios-download-outline"></Icon>导出附件
        </Button>

        <Button
          type="primary"
          style="float: right;margin-right: 5px"
          @click="exportData()"
          :loading="disabledSj"
        >
          <Icon type="ios-download-outline"></Icon>导出清单
        </Button>

        <Button
          type="primary"
          style="float: right;margin-right: 5px;margin-left: 5px"
          :disabled="disabled"
          :title="disabled?`请${time}后操作`:''"
          :loading="disabledFj"
          @click="exportFile()"
        >
          <Icon type="ios-download-outline"></Icon>附件归档
        </Button>
      </div>
      <Table border :columns="columns" :data="obligeeData.records" ref="table"></Table>
      <div style="margin: 10px;overflow: hidden">
        <div style="float: right;">
          <Page
            :total="page.total"
            :page-size="page.size"
            :page-size-opts="page.opts"
            :current="1"
            @on-change="loadData"
            @on-page-size-change="changePage"
            show-sizer
            show-total
            show-elevator
          ></Page>
        </div>
      </div>
      <Drawer :closable="false" v-model="view.isshow" width="536" class="drawer">
        <obligee-info :sel_item="sel_item"></obligee-info>
      </Drawer>
    </div>
  </div>
</template>

<script>
import { ApiQjydcObligee } from "../../api/api-qjwdzs-obligee";
import ObligeeInfo from "./obligee-info";

export default {
  oblname: "qjin-obligee",
  components: { ObligeeInfo },
  props: {
    qjin: {
      type: Object,
      default: () => ({})
    },
    activename: {
      type: Number
    }
  },
  watch: {
    qjin(val) {
      this.set(val);
    }
  },
  data: function() {
    return {
      obltitle: "权利人信息",
      view: {
        isshow: false
      },
      columns: [
        {
          type: "selection",
          width: 60,
          align: "center"
        },
        {
          title: "姓名",
          key: "zzqlr"
        },
        {
          title: "证件号",
          key: "zjh"
        },
       /* {
          title: "房屋地址",
          key: "fwdz"
        },*/
        {
          title: "操作",
          key: "action",
          width: 150,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h(
                "a",
                {
                  style: {
                    marginRight: "8px"
                  },
                  on: {
                    click: () => {
                      this.view.isshow = true;
                      this.viewOblnfo(params.row);
                    }
                  }
                },
                "预览"
              ),
              h(
                "a",
                {
                  on: {
                    click: () => {
                      this.remove(params.row);
                    }
                  }
                },
                "删除"
              )
            ]);
          }
        }
      ],
      //导出列名
      DCcolumns: [
        {
          title: "姓名",
          key: "zzqlr",
          width: 200
        },
        {
          title: "证件号",
          key: "zjh",
          width: 500
        },
        {
          title: "手机号",
          key: "dhhm",
          width: 200
        },
        {
          title: "是否为代理人",
          key: "dlr",
          width: 200
        },
        {
          title: "与代理人关系",
          key: "ydlrgx",
          width: 200
        },
        {
          title: "代理人身份证",
          key: "dlrzjh",
          width: 200
        },
        {
            title: '身份证',
            key: 'sfz',
            width: 500
        },
        {
            title: '户口本',
            key: 'hkb',
            width: 500
        },

        {
            title: '土地证',
            key: 'tdz',
            width: 500
        },
        {
            title: '房产证',
            key: 'fcz',
            width: 500
        },
        {
            title: '房屋远景',
            key: 'fwyj',
            width: 500
        },
        {
            title: '门牌号',
            key: 'mph',
            width: 500
        },
        {
            title: '调查草图',
            key: 'dcct',
            width: 500
        },
        {
            title: '调查表格',
            key: 'dcbg',
            width: 500
        },
        {
            title: '其他',
            key: 'qt',
            width: 500
        },
      ],
      DCObligeeData: [], //导出列表数据
      obligeeData: [],
      sel_item: ApiQjydcObligee.getModel(),
      page: {
        opts: [10, 20, 40, 80],
        size: 10,
        current: 1,
        total: 0
      },
      searchValue: "",
      disabledSj: false,
      disabledFj: false,
      disabled: false,
      timer: null // 定时器
    };
  },
  computed: {
    time() {
      return localStorage.getItem("time");
    }
  },
  watch: {
    searchValue() {
      this.loadData();
    }
  },
  mounted() {
    this.loadData();
    this.getTimes();
  },
  beforeDestroy() {
    clearInterval(this.timer);
  },
  methods: {
    getTimes() {
      this.timer = setInterval(() => {
        const endTime = localStorage.getItem("endTime");
        const qjinid = localStorage.getItem('qjinid');
        if (!endTime || new Date(endTime).getTime() < new Date().getTime() || this.qjin.qjinid !== qjinid) {
          this.disabled = false;
        } else {
          this.disabled = true;
        }
      }, 1000);
    },
    //拿到项目center和节点规则等信息
    loadData(current) {
      if (current) {
        this.page.current = current;
      }
      ApiQjydcObligee.page({
        qjinid: this.qjin.qjinid,
        search: this.searchValue,
        pagesize: this.page.size,
        pageindex: this.page.current
      })
        .then(r => {
          this.setData(r.data.data);
          this.page.total = r.data.data.total;
          this.page.size = r.data.data.size;
        })
        .catch(e => {
          this.$Message.error(e.message);
        });
    },
    search() {
      alert("s");
    },
    setData(val) {
      this.obligeeData = val;
    },
    viewOblnfo(row) {
      this.sel_item = row;
    },
    remove(row) {
      ApiQjydcObligee.deleteById(row.qlrid)
        .then(r => {
          this.loadData();
        })
        .catch(e => {
          this.$Message.error(e.message);
        });
    },
    //导出excel
    exportData() {
      this.disabledSj = true;
      //获取导出数据信息
      ApiQjydcObligee.selectByQjinid(this.qjin.qjinid).then(res => {
        try {
          this.DCObligeeData = res.data.data;
          console.log("this.DCObligeeData",this.DCObligeeData)
          this.$refs.table.exportCsv({
            filename: this.qjin.zzqlr,
            columns: this.DCcolumns,
            data:this.DCObligeeData
     /*       data: this.DCObligeeData.map(k => {
              k.zjh = "\t" + k.zjh;
              if (k.sfwtzj == "1") {
                k.sfwtzj = "是";
              } else if (k.sfwtzj == "0") {
                k.sfwtzj = "否";
              }

              if (k.sfwc == "1") {
                k.sfwc = "是";
              } else if (k.sfwc == "0") {
                k.sfwc = "否";
              }
              return k;
            })*/
          });
          this.$Message.success("导出数据成功");
          this.disabledSj = false;
        } catch (e) {
          this.$Message.error(e.message);
          this.disabledFj = false;
        }
      });
    },
    exportFile() {
      //   this.disabledFj = true;
      this.disabled = true;
      const endTime = new Date(new Date().getTime() + 600000);
      localStorage.setItem('qjinid', this.qjin.qjinid);
      localStorage.setItem("endTime", endTime);
      localStorage.setItem(
        "time",
        endTime.getHours() + ":" + endTime.getMinutes()
      );
      ApiQjydcObligee.output({ qjinid: this.qjin.qjinid }).then(res => {
        const state = res.data.data.state;
        if (state == 2) {
          const gdtime = new Date(res.data.data.gdtime);
          this.$confirm(
            `归档完成时间：${gdtime.toLocaleDateString()} ${gdtime.toLocaleTimeString()},确认是否重新归档？`,
            "提示",
            {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }
          )
            .then(() => {
              ApiQjydcObligee.output({
                qjinid: this.qjin.qjinid,
                isUpdate: true
              }).then(respones => {
                this.$Message.success({
                  content: "附件归档成功.",
                  duration: 10,
                  closable: true
                });
                localStorage.removeItem('qjinid');
                localStorage.removeItem('endTime');
                localStorage.removeItem('time');
                this.disabled = false;
              });
            })
            .catch(() => {
                localStorage.removeItem('qjinid');
                localStorage.removeItem('endTime');
                localStorage.removeItem('time');
                this.disabled = false;
            });
        } else if(state == 1) {
            this.$Message.error({
                content: "归档中",
                duration: 10,
                closable: true
            });
        } else {
          //附件已保存在C:\ovit\ovit-qjwdzs\project\
          this.$Message.success({
            content: "附件归档成功.",
            duration: 10,
            closable: true
          });
        }
      });
    },
    downloadFile() {
      window.location.href =
        "http://localhost:11002/qjwdzs/pub/file/download?qjinid=" +
        this.qjin.qjinid;
      //                ApiQjydcObligee.download({qjinid: this.qjin.qjinid}).then(res=>{
      //                    try {
      //                        //附件已保存在C:\ovit\ovit-qjwdzs\project\
      //                        this.$Message.success({
      //                            content: '附件下载成功.',
      //                            duration: 10,
      //                            closable: true
      //                        });
      //                    }catch (e) {
      //                        this.$Message.error(e.message);
      //                    }
      //                });
    },
    changePage(pagesize) {
      if (pagesize) {
        this.page.size = pagesize;
        this.loadData();
      }
    }
  }
};
</script>

<style lang="less" scoped>
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

  .panel-ctrl,
  .panel-prop {
    .el-tabs__item {
      padding: 0px 10px;
    }

    .el-collapse-item__content {
      padding-bottom: 0px;
    }
  }

  .rules {
    height: 700px;
    width: 100%;
    background: #f0f2f5;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 auto;
    flex: 0 0 auto;
    float: left;
    border-right: 1px solid #e9e9e9;
    border-bottom: 1px solid #e9e9e9;

    .rulesTile {
      text-align: left;
      height: 32px;
      padding-left: 12px;
      color: #000;
      line-height: 28px;
      background: #ebeef2;
      border-bottom: 1px solid #dce3e8;
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

  .ivu-page-item-active {
    border-color: #2d8cf0;
    background-color: #2d8cf0;
  }

  .v-application a {
    color: #000000;
  }

  .ivu-page-item-active a,
  .ivu-page-item-active:hover a {
    color: #000000;
  }

  .ivu-page-item-active a,
  .ivu-page-item-active:active a {
    color: #ffffff !important;
  }

  .ivu-page-item a {
    font-family: "Monospaced Number";
    margin: 0 6px;
    text-decoration: none;
    color: #040d18;
  }
}

.drawer /deep/ .ivu-drawer-body {
  background: #f8f8fa;
  padding: 24px;
}
</style>
