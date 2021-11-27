package cn.myjszl.common.base.service;

import cn.myjszl.common.base.model.SecurityUser;

public interface LoginService {
    /**
     * 根据用户名查找
     */
    SecurityUser loadByUsername(String username);
}
