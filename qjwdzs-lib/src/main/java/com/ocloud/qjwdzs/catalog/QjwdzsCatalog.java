package com.ocloud.qjwdzs.catalog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ocloud.common.tool.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("qjwdzs_catalog")
public class QjwdzsCatalog {
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "目录编号")
    private String qclid;
    @ApiModelProperty(value = "目录上级编号")
    private String qclpid;
    @ApiModelProperty(value = "目录编码")
    private String qclcode;
    @ApiModelProperty(value = "目录名称")
    private String qclname;
    @ApiModelProperty(value = "目录类型")
    private String qcltype;
    @ApiModelProperty(value = "说明")
    private String description;
    @ApiModelProperty(value = "创建者")
    private String cuserid;
    @ApiModelProperty(value = "开始时间")
    private Date starttime;
    @ApiModelProperty(value = "结束时间")
    private Date endtime;
    @ApiModelProperty(value = "创建时间")
    private Date ctime;
    @ApiModelProperty(value = "排序")
    private Double seqno;
    @ApiModelProperty(value = "是否启用")
    private String enable;
    @TableLogic
    @ApiModelProperty(value = "是否删除")
    private String isdel;

    @JsonIgnore
    public boolean isEnabled() {
        return StringUtil.IsEmpty(enable) || (true + "").equals(enable);
    }
}
