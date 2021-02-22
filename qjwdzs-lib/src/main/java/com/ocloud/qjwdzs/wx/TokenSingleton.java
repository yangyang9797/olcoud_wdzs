package com.ocloud.qjwdzs.wx;
import com.alibaba.fastjson.JSONObject;
import com.ocloud.qjwdzs.utils.WebUtil;
import com.ocloud.qjwdzs.utils.WechatUtil;
import lombok.extern.slf4j.Slf4j;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TokenSingleton {

    private Map<String, String> map = new HashMap<>(); // 缓存accessToken的Map, map中包含一个accessToken和缓存的时间戳

    private TokenSingleton() {
    }

    private static TokenSingleton single = null;

    public static TokenSingleton getInstance() {
        if (single == null) {
            single = new TokenSingleton();
        }
        return single;
    }


    public Map<String, String> getMap() {
        String time = map.get("time");
        String accessToken = map.get("access_token");
        long nowDate = new Date().getTime();
        if (accessToken != null && time != null && nowDate - Long.parseLong(time) < 6000 * 1000) {
            log.info("accessToken存在，尚未超时，返回单例！");
        } else {
            log.info("access_token超时或者不存在，重新获取！");
            JSONObject jsonObject = WechatUtil.getAccessToken();
            String newAccessToken = jsonObject.getString("access_token");
            log.info("new access_token = " + newAccessToken);
            String jsApiTicket = getJsApiTicket(newAccessToken);
            map.put("time", nowDate + "");
            map.put("access_token", newAccessToken);
            map.put("jsapi_ticket", jsApiTicket);
        }

        return map;
    }

    public String getJsApiTicket(String accessToken) {
        String apiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
        String requestUrl = apiTicketUrl.replace("ACCESS_TOKEN", accessToken);
        JSONObject result = WebUtil.doGet(requestUrl);
        String jsApiTicket = null;
        if (null != result) {
            jsApiTicket = result.getString("ticket");
        }
        return jsApiTicket;
    }

}
