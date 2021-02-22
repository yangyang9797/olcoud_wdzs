package com.ocloud.qjwdzs.wxuser;

import com.ocloud.auth.user.User;
import com.ocloud.web.core.mapper.BaseMapper;

public interface QjwdzsWxuserMapper extends BaseMapper<QjwdzsWxuser> {

    public User selectUserByOpenid(String openid);
}