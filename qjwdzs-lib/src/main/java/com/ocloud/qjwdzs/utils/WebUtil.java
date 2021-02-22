package com.ocloud.qjwdzs.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WebUtil {

    /**
     * 发起http get请求
     */
    public static JSONObject doGet(String requestUrl) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String responseContent = null;
        JSONObject result = null;

        try {
            HttpGet httpGet = new HttpGet(requestUrl);
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            result = JSON.parseObject(responseContent);
        } catch (IOException e) {
            System.out.println("HTTP请求异常：" + e.getMessage());
        }

        return result;
    }
}
