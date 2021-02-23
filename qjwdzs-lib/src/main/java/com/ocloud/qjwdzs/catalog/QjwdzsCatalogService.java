package com.ocloud.qjwdzs.catalog;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ocloud.auth.role.Role;
import com.ocloud.auth.role.RoleService;
import com.ocloud.common.tool.AiValue;
import com.ocloud.common.tool.MapUtil;
import com.ocloud.db.page.AiPage;
import com.ocloud.qjwdzs.instance.QjwdzsInstance;
import com.ocloud.qjwdzs.instance.QjwdzsInstanceService;
import com.ocloud.web.core.service.BaseTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class QjwdzsCatalogService extends BaseTreeService<QjwdzsCatalogMapper, QjwdzsCatalog> {

    @Autowired
    QjwdzsInstanceService instanceService;

    @Autowired
    private RoleService roleService;

    public void init() {
        super.init();
        set("qclid", "qclname", "qclpid", "parent", "children");
        setCol_searchs("qclname,qclcode,qcltype,description");
        setCol_orderbys("seqno,ctime");
    }

    public List<Map<String, Object>> treeWithInstances(QjwdzsCatalog t, AiPage page) {
//        // 组织架构数据权限查询
//        String cuserid = t.getCuserid();
//        List<Role> roles = (List<Role>) getTopRolesAndCompany(cuserid).get("topRoles");
//        // 查询所有用户(去除同级用户)
//        List<Map<String, Object>> maps = Lists.newArrayList();
//        for (int i = 0;i<roles.size();i++){
//            // 获取最高一级的架构roleid
//            String roleid = roles.get(i).getRoleid();
//            // 最高的rolecode
//            String rolecode = roles.get(i).getRolecode();
//            // 通过roleid查询用户
//            Role role = new Role();
//            role.setRoleid(roleid);
//            // 查询所有用户(去除同级用户)
//            maps.addAll(roleService.listWithUsers(role, "", "", "").stream().filter(m -> !rolecode.equals(m.get("rolecode").toString())).collect(Collectors.toList()));
//        }
//        // 所有下级用户
//        List<String> userList = Lists.newArrayList();
//        // 添加自己
//        userList.add(cuserid);
//        maps.forEach(map -> {
//            List<Map<Object, Object>> children = MapUtil.fromObjects(map.get("children"));
//            for (Map<Object, Object> child : children) {
//                userList.add(child.get("userid").toString());
//            }
//        });
//        // 相同用户去重
//        List<String> list = new ArrayList<>(new TreeSet<>(userList));
//        // 所有目录
//        List<Map<String, Object>> catalogList = Lists.newArrayList();
//        // 所有项目
//        List<Map<String, Object>> items = Lists.newArrayList();
//        QjwdzsInstance QjwdzsInstance = new QjwdzsInstance();
//        for (String userid : list) {
//            t.setCuserid(userid);
//            catalogList.addAll(listMaps(t, page));
//            QjwdzsInstance.setCuserid(userid);
//            items.addAll(instanceService.listMaps(QjwdzsInstance, page));
//        }
//
//        // 关联树(目录+项目)
//        List<Map<String, Object>> tree = treeWithRefs(catalogList, items);
//        // 最终结果
//        List<Map<String, Object>> res = treeOrderBySeq(tree, instanceService);
//
//        System.out.println("最终结果:" + JSON.toJSONString(res));
//        return res;
        return treeWithRefs(t, instanceService, page);
    }

    public IPage<Map<String, Object>> pageInstancesByTree(com.ocloud.qjwdzs.catalog.QjwdzsCatalog t, AiPage page) {
        page.set(this);
        page.setSelectcols(AiValue.get(page.getSelectcols(), instanceService.getTableInfo().getAllSqlSelect() + ",qclname,qclseqno"));
        page.setExcludecols(AiValue.get(page.getExcludecols(), instanceService.getCol_excludes()));
        page.setOrderbys(AiValue.get(page.getOrderbys(), " qclseqno, " + instanceService.getCol_orderbys()));

        List<Map<String, Object>> ls = listMaps(q -> q.setEntity(t));
        List<String> ids = getMapIds(ls);
        if (ids.size() > 0) {
            ls.addAll(allChildrenList(ids, new ArrayList<String>(), 1, 0));
        }
        List<Object> ids_all = getMapIds(ls);
        return instanceService.pageWithCatalog(null, q -> q.page(page).in("qclid", ids_all, true));
    }

    /**
     * 获取用户最高角色和所属公司
     *
     * @param uid
     * @return
     */

    public Map getTopRolesAndCompany(String uid) {
        Assert.notEmpty(uid, "用户ID为空");
        //获取uid的所有角色
        List<Role> roles = baseMapper.getRolesByUid(uid);
        Assert.notEmpty(roles, "当前用户角色信息为空");
        //获取roles的所有子角色
        List<Map<String, Object>> allRoles = Lists.newArrayList();
        for (int i = 0; i < roles.size(); i++) {
            //直接传入roles.get【i】返回结果为空
            Role role = new Role();
            role.setRoleid(roles.get(i).getRoleid());
            allRoles.addAll(roleService.listWithUsers(role, "", "", ""));
        }
        //选出重复角色
        List<Map<String, Object>> duplicate = allRoles.stream().collect(Collectors.toMap(e -> e, e -> 1, Integer::sum)).entrySet().stream().filter(e -> e.getValue() > 1).map(Map.Entry::getKey).collect(toList());
        //删除maps里所有重复的角色（全部删除）
        allRoles = allRoles.stream().distinct().collect(toList());
        allRoles.removeAll(duplicate);
        //allRole转化为Role
        List<Role> allRole = Lists.newArrayList();
        for (int i = 0; i < allRoles.size(); i++) {
            allRole.add(JSON.parseObject(JSON.toJSONString(allRoles.get(i)), Role.class));
        }
        //求roles和allRoles的交集，结果为用户最高角色
        roles.retainAll(allRole);
        Map map = Maps.newHashMap();
        map.put("topRoles", roles);
        return map;
    }

}
