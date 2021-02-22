package com.ocloud.qjwdzs.wx;


import com.ocloud.anno.log.Log;
import com.ocloud.anno.web.WebController;
import com.ocloud.anno.web.WebMapping;
import com.ocloud.common.tool.gson.GsonUtil;
import com.ocloud.qjwdzs.utils.HttpUtil;
import com.ocloud.qjwdzs.utils.WechatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Api(tags = "11.5.5", description = "微信服务[pub/wx]")
@WebController("/pub/wx")
public class WxController{

    private static String appId = "wx6203ae32b1626f34";
    private static String appSecret  = "711d5bf856620488f6ee34c1eee2895b";

    @ApiOperation(value = "获取微信签名接口", notes = "获取微信签名接口")
    @Log(action = "获取微信签名接口", tag = "/config")
    @WebMapping("/config")
    public Map<String, String> config(String signUrl) throws UnsupportedEncodingException {
        Map<String, String> ret = new HashMap<>();
        TokenSingleton tokenSingleton = TokenSingleton.getInstance();
        Map<String, String> map = tokenSingleton.getMap();
        String jsapi_ticket = map.get("jsapi_ticket");
        ret = WechatUtil.generateWxTicket(jsapi_ticket, signUrl);
        return ret;
    }

    @ApiOperation(value = "验证Token接口", notes = "验证Token接口")
    @Log(action = "验证Token接口", tag = "/checkWxToken")
    @WebMapping("/checkWxToken")
    public Object checkWxToken(String signature, String timestamp, String nonce, String echostr) {
        //验证:
        //1）将token、timestamp、nonce三个参数进行字典序排序 2）将三个参数字符串拼接成一个字符串进行sha1加密
        // 3）开发者获得加密后的字符串可与signature对比
        //得到的signature与接收到的signature相等则标识该请求来源于微信,
        //若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功
        //这里省略验证过程
        return echostr;
    }
    /**
     * 路径带pub 则不需要凭证
     * @param code
     * @return
     */
    @ApiOperation(value = "获取微信用户信息", notes = "获取微信用户信息")
    @Log(action = "获取微信用户信息", tag = "getWxUserInfo")
    @WebMapping("/getWxUserInfo")
    /**
     * 1.三参数都不为空 则为前端已登陆,更新用户信息
     * 2.只有code 获取微信信息后返还用户
     */
    public Object getWxInfo(String code) {
//		https://api.weixin.qq.com/sns/oauth2/access_token?
//		appid=wx7c163c33192e8680&
//		secret=7d5c9fe5ef311c52258b1af150913628&code=081r35Tw0mkdQi1idkVw0HoLSw0r35T7&grant_type=authorization_code
        WxEntity wxEntity = getAccessToken(code);
        if (! StringUtils.isEmpty(wxEntity.getAccess_token())) {  //获取到了accesscode
            return getWxUserInfoFromWx(wxEntity);
        } else {
            return "获取Access_Token失败";
        }
    }

    /**
     * 从微信获取用户信息
     * @param wxEntity
     * @return
     */
    private Object getWxUserInfoFromWx(WxEntity wxEntity) {
        String wxUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?";
        Map<String, String> wxUserParams = new HashMap<>();
        wxUserParams.put("access_token", wxEntity.getAccess_token());
        wxUserParams.put("openid", wxEntity.getOpenid());
        wxUserParams.put("lang", "zh_CN");
        Object wxUserObject = HttpUtil.doPost(wxUserInfoUrl, wxUserParams);
        WxEntity wxUserInfo = GsonUtil.fromJson(wxUserObject,WxEntity.class);
        if (! StringUtils.isEmpty(wxUserInfo.getOpenid())){
//            return  wxUserObject.toString().replace(",\"privilege\":[]","");
            return  wxUserInfo;
        } else {
            return "获取用户信息失败";
        }
    }


    /**
     * 获取微信openId
     * 和Access_token
     * @param code
     * @return
     */
    private WxEntity getAccessToken(String code) {
        String access_tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?";
        Map<String, String> accParams = new HashMap<>();
        accParams.put("appid", appId);
        accParams.put("secret", appSecret);
        accParams.put("code", code);
        accParams.put("grant_type", "authorization_code");
        Object accObject = HttpUtil.doPost(access_tokenUrl, accParams);
        return GsonUtil.fromJson(accObject, WxEntity.class);
    }


    @ApiOperation(value = "获取CardInfo", notes = "获取CardInfo")
    @Log(action = "获取CardInfo", tag = "/getCardInfo")
    @WebMapping("/getCardInfo")
    public Object getCardInfo(String img_url){
        String access_token = TokenSingleton.getInstance().getMap().get("access_token");
        log.info("redisGetToken=====",access_token);
        Object cardInfo = getOrcIdcard(img_url,access_token);
        return cardInfo;
    }


    public Object getOrcIdcard(String img_url, String access_token){
        String accessTokenUrl = "https://api.weixin.qq.com/cv/ocr/idcard?";
        Map<String, String> accParams = new HashMap<>();
        accParams.put("img_url",img_url);
        accParams.put("access_token",access_token);
        Object cardObject = HttpUtil.doPost1(accessTokenUrl,accParams);
        return  cardObject;
    }

}
