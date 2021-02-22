package com.ocloud.qjwdzs.catalog;

import com.ocloud.auth.role.Role;
import com.ocloud.web.core.mapper.BaseMapper;

import java.util.List;

public interface QjwdzsCatalogMapper extends BaseMapper<QjwdzsCatalog> {

    List<Role> getRolesByUid(String uid);

    List<Role> getAllRoles();

    List<Role> getCompany(String level);
}
