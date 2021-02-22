package com.ocloud.qjwdzs.homestead;

import com.ocloud.web.core.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QjwdzsHomesteadService extends BaseService<QjwdzsHomesteadMapper,QjwdzsHomestead> {
    public void init() {
        super.init();
        setEntiyType(QjwdzsHomestead.class);
        this.setIdAndName("zjdid", "");
        this.setCol_searchs("qlrid,dicname,dicalias,description");
        this.setCol_orderbys("seqno, ctime");
    }
}
