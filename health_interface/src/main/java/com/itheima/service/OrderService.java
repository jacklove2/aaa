package com.itheima.service;

import com.itheima.exception.MyException;
import com.itheima.pojo.Order;

import java.util.Map;

/**
 * @atuthor JackLove
 * @date 2019-10-02 0:16
 * @Package com.itheima.service
 */
public interface OrderService {
    //处理预约
    Order submitOrder(Map<String, String> map) throws MyException;
    //预约成功
    Map<String,Object> findById(Integer id);
}
