package com.ocloud.qjwdzs.catalog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ocloud.anno.log.Log;
import com.ocloud.anno.web.WebController;
import com.ocloud.anno.web.WebMapping;
import com.ocloud.db.page.AiPage;
import com.ocloud.web.core.controller.BaseTreeController;
import com.ocloud.web.util.ContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = "8.5.2", description = "权籍调查项目目录[qjwdzs/catalog]")
@WebController("/qjwdzs/catalog")
public class QjwdzsCatalogController extends BaseTreeController<QjwdzsCatalogService, QjwdzsCatalogMapper, QjwdzsCatalog> {


    @ApiOperation(value = "获取子项树,包含权籍调查",
            notes = "获取数据，除支持字段匹配查询外，还支持模糊查询 ")
    @Log(action = "获取数据", tag = "/treeWithInstances")
    @WebMapping("/treeWithInstances")
    public List<Map<String, Object>> treeWithInstances(QjwdzsCatalog t, AiPage page) {
        return service.treeWithInstances(t, page);
    }

    @ApiOperation(value = "获取子项的权籍调查",
            notes = "获取数据，除支持字段匹配查询外，还支持模糊查询")
    @Log(action = "获取数据", tag = "/pageInstancesByTree")
    @WebMapping("/pageInstancesByTree")
    public IPage<Map<String, Object>> pageInstancesByTree(QjwdzsCatalog t, AiPage page) {
        return service.pageInstancesByTree(t, page);
    }

    @ApiOperation(value = "获取最高角色和所属公司",notes = "获取当前用户最高角色和角色所属公司")
    @Log(action = "获取最高角色和所属公司",tag = "/getTopRolesAndCompany")
    @WebMapping("/getTopRolesAndCompany")
    public Map getTopRolesAndCompany(HttpServletRequest request){
        String uid = ContextUtil.getUserid(request);
        return service.getTopRolesAndCompany(uid);
    }


}
