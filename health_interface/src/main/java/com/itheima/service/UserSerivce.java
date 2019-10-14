package com.itheima.service;

import com.itheima.pojo.User;

/**
 * @atuthor JackLove
 * @date 2019-10-09 16:16
 * @Package com.itheima.service
 */
public interface UserSerivce {
    //验证认证与授权
    User findByUsername(String username);
}
