package com.ocloud.qjwdzs.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.HttpClients;
import lombok.extern.slf4j.Slf4j;
import com.ocloud.common.tool.StringUtil;

import java.io.IOException;

/**
 * describe:根据通讯地址进行网络请求
 *
 * @author liuli
 * @date 2020/08/27
 */
@Slf4j
public class AuthUtil {
    public static final String APPID = "wx6203ae32b1626f34";
    public static final String APPSECRET = "711d5bf856620488f6ee34c1eee2895b";

    public static JSONObject doGetJson(String url) throws IOException {
        log.info("访问URL=" + url);
        JSONObject jsonObject = null;
        //String result = HttpClientUtil.doGet(url);
        String result =  HttpUtil.doGet(url);
        if (StringUtil.IsNotEmpty(result)) {
            jsonObject = JSON.parseObject(result);
            log.info("返回结果=" + result);
        } else {
            log.info("返回结果为空 =" + result);
        }
        return jsonObject;
    }

}
