package com.ocloud.qjwdzs.wxuser;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("qjwdzs_wxuser")
public class QjwdzsWxuser {

    @ApiModelProperty("userid")
    private String userid;

    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty("providerUserId")
    private String provideruserid;

    @ApiModelProperty("displayName")
    private String displayname;

    @ApiModelProperty("profileUrl")
    private String profileurl;

    @ApiModelProperty("imageUrl")
    private String imageurl;

    @ApiModelProperty("accessToken")
    private String accesstoken;

    @ApiModelProperty("refreshToken")
    private String refreshtoken;

}