package com.ocloud.qjwdzs.instance;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ocloud.anno.log.Log;
import com.ocloud.anno.web.WebController;
import com.ocloud.anno.web.WebMapping;
import com.ocloud.db.page.AiPage;
import com.ocloud.web.core.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = "8.5.1", description = "权籍调查项目实例[/qjwdzs/instance]")
@WebController("/qjwdzs/instance")
public class QjwdzsInstanceController extends BaseController<QjwdzsInstanceService, QjwdzsInstanceMapper, QjwdzsInstance> {

    @ApiOperation(value = "获取分页数据，包含权籍调查信息、目录信息",
            notes = "获取分页数据，除支持字段匹配查询外，还支持模糊查询。")
    @Log(action = "获取分页数据", tag = "/pageWithCatalog")
    @WebMapping("/pageWithCatalog")
    public IPage<Map<String, Object>> pageWithCatalog(QjwdzsInstance t, AiPage page) {
        return service.pageWithCatalog(t, q -> q.page(page));
    }

    @ApiOperation(value = "获取所有项目")
    @Log(action = "获取分页数据", tag = "/pageWithCatalog")
    @WebMapping("/pub/getInstance")
    public List getInstance () {
        return service.getInstance();
    }


}
