package com.ocloud.qjwdzs.wxuser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocloud.auth.security.AuthService;
import com.ocloud.auth.user.User;
import com.ocloud.auth.user.UserService;
import com.ocloud.web.core.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * describe:
 *
 * @author liuli
 * @date 2020/08/27
 */
@Slf4j
@Service
public class QjwdzsWxuserService extends BaseService<QjwdzsWxuserMapper, QjwdzsWxuser> {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;


    public String selectUserId(String openid) {
        String useid = "";
        QjwdzsWxuser userconnectio = new QjwdzsWxuser();
        userconnectio.setProvideruserid(openid);
        QueryWrapper queryWrapper = new QueryWrapper(userconnectio);
        QjwdzsWxuser userconnection = baseMapper.selectOne(queryWrapper);
        if (null != userconnection) {
            useid = userconnection.getUserid();
        }
        return useid;
    }

    public User selectUserByOpenid(String openid) {
        return baseMapper.selectUserByOpenid(openid);
    }

    public void userBinding(String openid, String phoneNum) {
        User user = userService.getBaseMapper().getByUserName(phoneNum);
        //用户未注册过
        if (user == null) {
            user = authService.register("", phoneNum, phoneNum, "888888", "", "", "", "", "", "");
        }
        //进行绑定
        QjwdzsWxuser qjwdzsWxuser = new QjwdzsWxuser();
        qjwdzsWxuser.setUserid(user.getUserid());
        qjwdzsWxuser.setProvideruserid(openid);
        baseMapper.updateById(qjwdzsWxuser);
    }

    //    public QjwdzsWxuser selectOneByOpenId(String openid){
//        QjwdzsWxuser userconnectio = new QjwdzsWxuser();
//        userconnectio.setProvideruserid( openid );
//        QueryWrapper queryWrapper = new QueryWrapper(userconnectio);
//        QjwdzsWxuser userconnection = baseMapper.selectOne( queryWrapper);
//        return userconnection;
//    }

}
