package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.UserDao;
import com.itheima.pojo.User;
import com.itheima.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @atuthor JackLove
 * @date 2019-10-09 16:16
 * @Package com.itheima.service.impl
 */
@Service(interfaceClass = UserSerivce.class)
public class UserSerivceImpl implements UserSerivce {
    @Autowired
    private UserDao userDao;
    ////验证认证与授权
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
