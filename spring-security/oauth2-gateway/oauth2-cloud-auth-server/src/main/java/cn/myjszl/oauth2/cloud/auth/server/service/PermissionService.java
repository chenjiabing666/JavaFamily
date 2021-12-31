package cn.myjszl.oauth2.cloud.auth.server.service;

import cn.myjszl.oauth2.cloud.auth.server.model.vo.SysRolePermissionVO;

import java.util.List;

public interface PermissionService {
    /**
     * 获取所有的url->角色对应关系
     * @return
     */
    List<SysRolePermissionVO> listRolePermission();
}
