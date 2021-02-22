package com.ocloud.qjwdzs.home;

import com.ocloud.web.core.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class QjwdzsHomeService extends BaseService<QjwdzsHomeMapper, QjwdzsHome> {
    public void init() {
        super.init();
        setEntiyType(QjwdzsHome.class);
        this.setIdAndName("fwid", "");
        this.setCol_searchs("qlrid,dicname,dicalias,description");
        this.setCol_orderbys("seqno, ctime");
    }

}
