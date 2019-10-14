package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.PackageService;
import com.itheima.untils.SMSUtils;
import com.itheima.untils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @atuthor JackLove
 * @date 2019-10-01 19:07
 * @Package com.itheima.controller
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private PackageService packageService;

    //发送预约验证码
    @PostMapping("/send4Order")
    public Result send4Order(String telephone) {
        String key = "Order_"+RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        return sendValidateCode(key, telephone);
    }

    //快速登录
    @PostMapping("/send4Login")
    public Result send4Login(String telephone) {
        String key ="Login_"+ RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        return sendValidateCode(key, telephone);

    }

    private Result sendValidateCode(String key, String telephone) {
        //获取redis操作对象,用完关闭
        Jedis jedis = jedisPool.getResource();
        //判断是否发送过了
        if (jedis.get(key) != null) {
            return new Result(true, MessageConstant.SENT_VALIDATECODE);
        }
        //生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        //发送验证码
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code + "");
            //            //存入redis
            jedis.setex(key, 5 * 60, code + "");
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
    }
}
