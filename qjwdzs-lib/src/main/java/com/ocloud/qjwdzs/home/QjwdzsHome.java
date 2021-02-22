package com.ocloud.qjwdzs.home;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("qjwdzs_home")
public class QjwdzsHome {
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "房屋ID")
    private String fwid;
    @ApiModelProperty(value = "房屋预编码")
    private String fwybm;
    @ApiModelProperty(value = "房屋层数")
    private String fwcs;
    @ApiModelProperty(value = "建成年份")
    private String jcnf;
    @ApiModelProperty(value = "房屋类型")
    private String fwlx;
    @ApiModelProperty(value = "创建时间")
    private Date ctime;
    @ApiModelProperty(value = "创建人")
    private String cuserid;
    @ApiModelProperty(value = "权利人ID")
    private String qlrid;


}
