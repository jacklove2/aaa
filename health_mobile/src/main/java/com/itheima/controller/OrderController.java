package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.MemberService;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @atuthor JackLove
 * @date 2019-10-02 0:13
 * @Package com.itheima.controller
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    //提交预约
    @RequestMapping("/submit")
    public Result submitOrder(@RequestBody Map<String, String> map) {
        Jedis jedis = jedisPool.getResource();
        //获取手机号
        String telephone = map.get("telephone");
        String key = "Order_" + RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        //redis中的验证码
        String codeInRedis = jedis.get(key);
        //先判断验证码是否为空
        if (StringUtils.isEmpty(codeInRedis)) {
            //验证为空,验证码过时或者没有点击获取验证码
            return new Result(false, MessageConstant.SENT_VALIDATECODE_ISNULL);
        }
        //再判断验证是否匹配
        if (!codeInRedis.equals(map.get("validateCode"))) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //验证码正确,则允许预约操作,清除redis验证码
        jedis.del(key);
        map.put("orderType", "微信预约");
        Order order = orderService.submitOrder(map);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order);

    }

    //预约成功信息展示
    @GetMapping("/findById")
    public Result findById(Integer id) {
        Map<String, Object> map = orderService.findById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
    }
}
