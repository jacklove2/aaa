package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @atuthor JackLove
 * @date 2019-10-06 23:55
 * @Package com.itheima.controller
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    @PostMapping("/check")
    public Result loginCheck(@RequestBody Map<String, String> map, HttpServletResponse res) {
        String telephone = map.get("telephone");
        //获取验证码
        String code = map.get("validateCode");
        Jedis jedis = jedisPool.getResource();
        //获取手机号
        String key = "Login_" + RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        //redis中的验证码
        String codeInRedis = jedis.get(key);
        //先判断验证码是否为空
        if (StringUtils.isEmpty(codeInRedis)) {
            //验证为空,验证码过时或者没有点击获取验证码
            return new Result(false, MessageConstant.SENT_VALIDATECODE_ISNULL);
        }
        //再判断验证是否匹配--
        if (!codeInRedis.equals(map.get("validateCode"))) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        Member member = memberService.findByTelephone(telephone);
        if (member == null) {
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);
        }
        // 跟踪用户, 采用cookie
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        cookie.setMaxAge(30 * 24 * 60 * 60);// 一个星期
        cookie.setPath("/");// 只要用户访问了网站
        res.addCookie(cookie); // 只要用户访问了网站，就会在请求头的Header中带上这个cookie参数
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
