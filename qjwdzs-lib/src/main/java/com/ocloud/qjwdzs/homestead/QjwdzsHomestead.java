package com.ocloud.qjwdzs.homestead;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("qjwdzs_homestead")
public class QjwdzsHomestead {
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "宅基地ID")
    private String zjdid;
    @ApiModelProperty(value = "关联权利人和房屋信息")
    private String zddm;
    @ApiModelProperty(value = "宗地地址")
    private String zddz;
    @ApiModelProperty(value = "宗地面积")
    private String zdmj;
    @ApiModelProperty(value = "不登记原因")
    private String zbdjyy;
    @ApiModelProperty(value = "宅基地来源")
    private String zjdly;
    @ApiModelProperty(value = "登记类型")
    private String djlx;
    @ApiModelProperty(value = "是否委托指界")
    private String wtzj;
    @ApiModelProperty(value = "权利人是否外出")
    private String qlrwc;
    @ApiModelProperty(value = "创建时间")
    private Date ctime;
    @ApiModelProperty(value = "创建人")
    private String cuserid;
    @ApiModelProperty(value = "权利人ID")
    private String qlrid;
    @ApiModelProperty(value = "土地证号")
    private String tdzh;
    @ApiModelProperty(value = "经度")
    private String lng;
    @ApiModelProperty(value = "纬度")
    private String lat;

}
