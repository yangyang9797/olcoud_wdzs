package com.ocloud.qjwdzs.obligee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
@TableName("qjwdzs_obligee")
public class QjwdzsObligeeInfo {

    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "权利人ID或者为相关人员ID")
    private String qlrid;
    @ApiModelProperty(value = "相关人员的权利人ID（若为空，此人为权利人）")
    private String qlrpid;
    @ApiModelProperty(value = "权利人姓名")
    private String zzqlr;
    @ApiModelProperty(value = "身份证号")
    private String zjh;
    @ApiModelProperty(value = "手机号")
    private String dhhm;
    @ApiModelProperty(value = "是否为代理人")
    private String dlr;
    @ApiModelProperty(value = "与代理人关系")
    private String ydlrgx;
    @ApiModelProperty(value = "代理人身份证")
    private String dlrzjh;
    @TableLogic
    @ApiModelProperty(value = "是否删除")
    private String isdel;
    @ApiModelProperty(value = "更新时间")
    private Date ctime;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "创建人")
    private String cuserid;
    @ApiModelProperty(value = "项目编号")
    private String qjinid;

    @ApiModelProperty(value = "相关人员")
    private String publicstate;
}