package com.ocloud.qjwdzs.login;

import com.ocloud.common.tool.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MobLoginService {
    @Autowired
    private MobLoginMapper mobLoginMapper;
    public void updatePassword(String userid, String password) {
        mobLoginMapper.updatePassword(userid, MD5Util.encode(password));
    }
}
