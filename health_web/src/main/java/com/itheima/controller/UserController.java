package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin2.message.Message;

/**
 * @atuthor JackLove
 * @date 2019-10-09 20:22
 * @Package com.itheima.controller
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/getUsername")
    public Result getUsername() {
        //获取认证信息的用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, username);

    }
}
