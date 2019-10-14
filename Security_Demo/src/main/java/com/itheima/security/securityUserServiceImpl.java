package com.itheima.security;

import com.itheima.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-10-08 21:53
 * @Package com.itheima.security
 */
public class securityUserServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        //String username, 用户名
        //String password, 密码
        //Collection<? extends GrantedAuthority> authorities 集合，List,set
        //authorities 登陆用户所拥有的权限集合 授权
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // String role 角色名/权限名
        SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_ADMIN");
        authorities.add(sga);

        // 登陆用户信息
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authorities);
        //return null; // 认证失败
        return userDetails;
    }


    private User findByUsername(String username){
        User user = null;
        if("admin".equals(username)) {
            user = new User();
            user.setUsername("admin");
            user.setPassword("$2a$10$eVv1MOayZz76kzsXXfqbquoRJDJ9tlSG1yMBRvvGBa6dVdpm0grGe");
            user.setId(9527);
        }
        return user;
    }
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin"));

        //$2a$10$0LnwGI3TM9jBdRhnwujtkun1Mlr5BHgB/vIno8xn.GHGQcgKeCaYS
        //$2a$10$lxb0uYgUWKSpMbTbbPGdL.xFasE79CIhYc99oJF.OeCsx6YOycA9.

        // 校验
        //rawPassword    没有加密 明文
        //encodedPassword  加密后的密文
        System.out.println(encoder.matches("1234", "$2a$10$0LnwGI3TM9jBdRhnwujtkun1Mlr5BHgB/vIno8xn.GHGQcgKeCaYS"));
        System.out.println(encoder.matches("1234", "$2a$10$lxb0uYgUWKSpMbTbbPGdL.xFasE79CIhYc99oJF.OeCsx6YOycA9."));
        Calendar calendar = Calendar.getInstance();

    }

}
