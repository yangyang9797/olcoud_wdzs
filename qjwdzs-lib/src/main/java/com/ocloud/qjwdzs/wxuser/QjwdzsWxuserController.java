package com.ocloud.qjwdzs.wxuser;

import com.alibaba.fastjson.JSONObject;
import com.ocloud.anno.log.Log;
import com.ocloud.anno.web.WebController;
import com.ocloud.anno.web.WebMapping;
import com.ocloud.auth.security.AuthService;
import com.ocloud.auth.user.User;
import com.ocloud.common.result.Result;
import com.ocloud.common.result.ResultEnum;
import com.ocloud.common.tool.StringUtil;
import com.ocloud.qjwdzs.utils.AuthUtil;
import com.ocloud.web.core.controller.BaseController;
import com.ocloud.web.util.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;


@Slf4j
@Api(tags = "11.5.6", description = "微信服务[pub/wxlogin]")
@WebController("/pub/auth/weixin/overt")
public class QjwdzsWxuserController extends BaseController<QjwdzsWxuserService, QjwdzsWxuserMapper, QjwdzsWxuser> {

    @Autowired
    private AuthService authService;

    @ApiOperation(
            value = "手机端微信登录",
            notes = "手机端微信登录"
    )
    @Log(
            module = "手机端微信登录",
            action = "手机端微信登录",
            tag = "/login"
    )
    @GetMapping({"/login"})
    public void weixinLogin(HttpServletResponse response) throws IOException {
        String backUrl = "http://kjxx.cnovit.com/qjwdzs/pub/auth/weixin/overt/callBack";
        //引导用户到微信登录地址
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID" +
                "&redirect_uri=REDIRECT_URI" +
                "&response_type=code" +
                "&scope=SCOPE" +
                "&state=123#wechat_redirect";
        url = url.replace("APPID", AuthUtil.APPID);
        url = url.replace("REDIRECT_URI", URLEncoder.encode(backUrl));
        url = url.replace("SCOPE", "snsapi_userinfo");
        response.sendRedirect(url);
    }


    @ApiOperation(
            value = "手机端微信登录",
            notes = "手机端微信登录"
    )
    @Log(
            module = "手机端微信登录",
            action = "手机端微信登录",
            tag = "/callBack"
    )
    @GetMapping({"/callBack"})
    public void weixinCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //处理回调后的地址
        String code = request.getParameter("code");
        log.info("code = " + code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=" + AuthUtil.APPID +
                "&secret=" + AuthUtil.APPSECRET +
                "&code=" + code +
                "&grant_type=authorization_code";
        JSONObject jsonObject = AuthUtil.doGetJson(url);

        if (StringUtil.IsEmpty(jsonObject.get("access_token"))) {
            throw new RuntimeException("没有获取到access_token");
        }
        String accessToken = jsonObject.get("access_token").toString();
        String openId = jsonObject.get("openid").toString();
        String refreshToken = jsonObject.getString("refresh_token");

        //拉取用户信息
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=ACCESS_TOKEN" +
                "&openid=OPENID" +
                "&lang=zh_CN";
        infoUrl = infoUrl.replace("ACCESS_TOKEN", accessToken);
        infoUrl = infoUrl.replace("OPENID", openId);

        JSONObject weixinUserinfo = AuthUtil.doGetJson(infoUrl);

        log.info("获取到的用户信息= " + JSONObject.toJSONString(weixinUserinfo));
//        //情况1.使用微信息直接登录，无需绑定
//        return new ModelAndView("personal","userInfo",jsonObject1);
//情况2.绑定现有用户

        //更新微信用户信息
        QjwdzsWxuser qjwdzsWxuser = new QjwdzsWxuser();
        qjwdzsWxuser.setProvideruserid(weixinUserinfo.getString("openid"));
        //qjwdzsWxuser.setAccesstoken(weixinUserinfo.getString(""));
        qjwdzsWxuser.setImageurl(weixinUserinfo.getString("headimgurl"));
        qjwdzsWxuser.setDisplayname(weixinUserinfo.getString("nickname"));
        save(qjwdzsWxuser);

        String userId = service.selectUserId(openId);
        User user = new User();
        //判断当前微信用户是否绑定了系统用户
        String indexUrl = "";
        if (StringUtil.IsNotEmpty(userId)) {
            //已绑定,获取用户
            user = service.selectUserByOpenid(openId);
            indexUrl = "http://kjxx.cnovit.com/qjwdzs/mob/index.html#/grsb?username="+user.getUsername();
        } else {
            //未绑定
            //注册用户，进行绑定
            indexUrl = "http://kjxx.cnovit.com/qjwdzs/mob/index.html#/wxlogin?provideruserid="+openId;
        }

        //方式一：重定向
        //authService.login(request, response, user.getUsername(), null, user.getPassword());
        response.sendRedirect(indexUrl);

        //方式二：
        //return usernamePasworLogin(request, response, user.getUsername(), null, user.getPassword());

    }

    @ApiOperation(value = "微信用户绑定系统用户", notes = "微信用户绑定系统用户")
    @Log(action = "微信用户绑定系统用户", tag = "/userBinding")
    @WebMapping("/userBinding")
    public void userBinding(String openid, String phoneNum) {
        service.userBinding(openid, phoneNum);
    }


    /**
     * 最终需要调用的登录方法
     *
     * @param request
     * @param response
     * @param username
     * @param password
     * @param password_md5
     * @return
     */
    public Object usernamePasworLogin(HttpServletRequest request, HttpServletResponse response,
                                      String username, String password, String password_md5) {
        try {
            username = WebUtil.nvlRequest(username, request, "username");
            password = WebUtil.nvlRequest(password, request, "password");
            password_md5 = WebUtil.nvlRequest(password_md5, request, "password_md5");
            return this.authService.login(request, response, username, password, password_md5);
        } catch (Exception var7) {
            return Result.error(ResultEnum.UNAUTH, var7.getMessage());
        }
    }
}
