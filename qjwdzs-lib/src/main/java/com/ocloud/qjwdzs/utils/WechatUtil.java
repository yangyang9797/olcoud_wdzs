package com.ocloud.qjwdzs.utils;

import com.alibaba.fastjson.JSONObject;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WechatUtil {


    private static String appId = "wx6203ae32b1626f34";
    private static String appSecret  = "711d5bf856620488f6ee34c1eee2895b";


    public static JSONObject getAccessToken() {
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appId=APPID&secret=APPSECRET";
        String requestUrl = accessTokenUrl.replace("APPID", appId).replace("APPSECRET", appSecret);
        return WebUtil.doGet(requestUrl);
    }

    public static Map<String, String> generateWxTicket(String jsApiTicket, String url) {
        Map<String, String> ret = new HashMap<>();
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";

        string1 = "jsapi_ticket=" + jsApiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url ;
//                "&accessToken=" + accessToken;

        System.out.println("string1 ====> " + string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            System.out.println("signature ====> " + signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsApiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appid", appId);
//        ret.put("accessToken", accessToken);
        return ret;
    }

    /**
     * 字节数组转换为十六进制字符串
     *
     * @param hash 字节数组
     * @return 十六进制字符串
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 生成随机字符串
     *
     * @return 随机字符串
     */
    private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成时间戳
     *
     * @return 时间戳
     */
    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
