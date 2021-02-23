package com.ocloud.qjwdzs.instance;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ocloud.db.wrapper.Query;
import com.ocloud.web.core.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Service
public class QjwdzsInstanceService extends BaseService<QjwdzsInstanceMapper, QjwdzsInstance> {

    @Override
    public void init() {
        super.init();
        setEntiyType(QjwdzsInstance.class);
        setIdAndName("qjinid", "qjinname");
        setCol_searchs("qjinname,qjincode,qjintype,description");
        setCol_orderbys("seqno,ctime ");
        setCol_excludes("content,configs");
    }

    public IPage<Map<String, Object>> pageWithCatalog(QjwdzsInstance t, Consumer<Query<QjwdzsInstance>> consumer) {
        Query q = getQuery(t);
        consumer.accept(q);
        return baseMapper.pageWithCatalog(q.getPage(), q);
    }


    public Map<String,String> getYumByQjinid(String qjinid) {
        return baseMapper.getYumByQjinid(qjinid);
    }


    public List getInstance() {
        return baseMapper.selectList(new QueryWrapper<QjwdzsInstance>().ne("isdel","1"));
    }
}

