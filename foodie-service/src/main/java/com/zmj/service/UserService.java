package com.zmj.service;

import com.zmj.pojo.Users;
import com.zmj.pojo.bo.UserBO;

public interface UserService {

    /**
     * 查询用户名是否已存在
     * @param username
     * @return
     */
    public boolean queryUserNameIsExist(String username);

    /**
     * 创建用户
     * @param userBO
     * @return
     */
    public Users createUser(UserBO userBO);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public Users queryUserForLogin(String username, String password);
}
