package com.ocloud.qjwdzs.instance;

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
@TableName("qjwdzs_instance")
public class QjwdzsInstance {
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "示例编号")
    private String qjinid;
    @ApiModelProperty(value = "目录编号")
    private String qclid;
    @ApiModelProperty(value = "示例名称")
    public String qjinname;
    @ApiModelProperty(value = "示例代码")
    private String qjincode;
    @ApiModelProperty(value = "示例类型")
    private String qjinxzqh;
    @ApiModelProperty(value = "行政区域")
    private String qjintype;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "配置")
    private String configs;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "创建者")
    private String cuserid;
    @ApiModelProperty(value = "创建时间")
    private Date ctime;
    @ApiModelProperty(value = "排序")
    private Double seqno;
    @ApiModelProperty(value = "是否启用")
    private String enable;
    @ApiModelProperty(value = "归档完成时间")
    private Date gdtime;
    @TableLogic
    @ApiModelProperty(value = "是否删除")
    private String isdel;
    @JsonIgnore
    public boolean isEnabled() {
        return StringUtil.IsEmpty(enable) || (true + "").equals(enable);
    }
}
