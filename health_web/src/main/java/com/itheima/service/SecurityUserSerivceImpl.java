package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @atuthor JackLove
 * @date 2019-10-09 12:16
 * @Package com.itheima.service
 */
@Service("securityUserSerivceImpl")
public class SecurityUserSerivceImpl implements UserDetailsService {
    @Reference
    private UserSerivce userSerivce;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userSerivce.findByUsername(username);
        if (user == null) {
            return null;
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        Set<Role> userRoles = user.getRoles();
        //用户拥有的角色
        if (userRoles != null) {
            GrantedAuthority authority = null;
            for (Role role : userRoles) {
                //创建角色
                authority = new SimpleGrantedAuthority(role.getKeyword());
                //添加用户的角色
                authorityList.add(authority);
                //判断角色下是否有权限
                if (role.getPermissions() != null) {
                    for (Permission p : role.getPermissions()) {
                        authority = new SimpleGrantedAuthority(p.getKeyword());
                        authorityList.add(authority);
                    }
                }
            }
        }
        // user.getUsername(),user.getPassword() 让security帮我们校验，调用encoder的matches方法 encoder-> <bean id=encoder/>
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorityList);
    }
}
