package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-28 21:04
 * @Package com.itheima.service
 */
public interface OrderSettingService {
    //导入预约设置
    void doImport(List<OrderSetting> list);
    //根据预约查询预约数据
    List<OrderSetting> getOrderSettingByMonth(String month);
    //设置预约人数
    void editNumberByDate(OrderSetting orderSetting);


}
