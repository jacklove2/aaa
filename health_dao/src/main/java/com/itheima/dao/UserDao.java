package com.itheima.dao;

import com.itheima.pojo.User;

/**
 * @atuthor JackLove
 * @date 2019-10-09 16:17
 * @Package com.itheima.dao
 */
public interface UserDao {
    //验证认证与授权
    User findByUsername(String username);
}
