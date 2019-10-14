package com.itheima.job;

import com.itheima.constant.RedisConstant;
import com.itheima.untils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @atuthor JackLove
 * @date 2019-09-27 22:05
 * @Package com.itheima.job
 */
public class ClearPicture {
    @Autowired
    private JedisPool jedisPool;

    public void doClear() {
       
    }

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-job.xml");
    }
}
