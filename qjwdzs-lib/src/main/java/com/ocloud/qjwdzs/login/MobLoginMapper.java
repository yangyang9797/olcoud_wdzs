package com.ocloud.qjwdzs.login;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MobLoginMapper {

    void updatePassword(@Param("userid") String userid,@Param("password") String password);
}
