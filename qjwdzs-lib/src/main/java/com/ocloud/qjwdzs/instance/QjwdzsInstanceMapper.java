package com.ocloud.qjwdzs.instance;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ocloud.web.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


public interface QjwdzsInstanceMapper extends BaseMapper<QjwdzsInstance> {

    IPage<Map<String, Object>> pageWithCatalog(IPage<Map<String, Object>> page, @Param("ew") QueryWrapper<QjwdzsInstance> queryWrapper);


    Map<String,String> getYumByQjinid(String qjinid);
}

