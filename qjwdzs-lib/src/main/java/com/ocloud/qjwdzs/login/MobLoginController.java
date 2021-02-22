package com.ocloud.qjwdzs.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.google.common.collect.Maps;
import com.ocloud.anno.log.Log;
import com.ocloud.anno.web.WebController;
import com.ocloud.anno.web.WebMapping;
import com.ocloud.auth.security.AuthService;
import com.ocloud.auth.user.User;
import com.ocloud.auth.user.UserService;
import com.ocloud.common.result.Result;
import com.ocloud.common.tool.MD5Util;
import com.ocloud.qjwdzs.instance.QjwdzsInstanceService;
import com.ocloud.qjwdzs.wxuser.QjwdzsWxuserController;
import com.ocloud.qjwdzs.wxuser.QjwdzsWxuserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Slf4j
@Api(tags = "8.5.6", description = "移动端登录服务[/pub/mob]")
@WebController("/mob")
public class MobLoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private QjwdzsInstanceService qiService;
    @Autowired
    private AuthService authService;
    @Autowired
    private MobLoginService mobLoginService;

    private static final String CODE_TYPE = "1"; //验证码类型
    //    private static final String YUM_URL = "http://192.168.7.13:8100"; //短信地址
    private static final String YUM_URL = "http://127.0.0.1:8100"; //短信地址
    private static final String YUM_URL_SENDCODE = YUM_URL +
            "/pub/yummas/sendAuthCode?username={username}&passwd={passwd}&signno={signno}&phoneNumber={phoneNumber}"; //接口地址

    @ApiOperation(value = "移动端登录,登录校验注册", notes = "移动端登录,登录校验注册")
    @Log(action = "移动端登录,登录校验注册", tag = "/login")
    @WebMapping("/pub/login")
    public Object login(String username, String password, String isupCode, HttpServletRequest request, HttpServletResponse response) {

        Assert.notEmpty(username, "用户名为空");
        //查询该手机号用户是否存在
        List<User> list_user = userService.list(new QueryWrapper<User>().eq("username", username));
        //用户存在
        if (list_user.size() > 0) {
            if (isupCode.equals(CODE_TYPE)) {
                // 1 验证码
                return authService.login(request, response, username, null, list_user.get(0).getPassword());
            }
        }
        //用户不存在，注册后登录
        User user = authService.register(null, username, username, "888888", null, null, null, null, null, null);
        return authService.login(request, response, username, null, list_user.get(0).getPassword());
    }


    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    @Log(action = "发送验证码", tag = "/sendCode")
    @WebMapping("/pub/sendCode")
    public Result sendCode(String qjinid, String phoneNumber) {
        Assert.notEmpty(qjinid, "缺少房屋编号");
        //生成验证码
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        Map map = Maps.newHashMap();
        map.put("code", sb.toString());

        return Result.success(Result.success(map));
    }

    @ApiOperation(value = "根据用户名获取用户", notes = "根据用户名获取用户")
    @Log(action = "根据用户名获取用户", tag = "/selectByUsername")
    @WebMapping("/pub/selectByUsername")
    public User selectByUsername(String username, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getBaseMapper().getByUserName(username);
        Assert.notNull(user, "用户不存在");
        return user;
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @Log(action = "修改密码", tag = "/updatePassword")
    @WebMapping("/pub/updatePassword")
    public void updatePassword(String userid, String password) {
        mobLoginService.updatePassword(userid, password);
    }

}
