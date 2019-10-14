package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-28 21:05
 * @Package com.itheima.dao
 */
public interface OrderSettingDao {
    //查找预约日期
    OrderSetting findByOrderDate(Date orderDate);

    //更新预约日期
    void updateNumber(OrderSetting osIndb);

    //添加预约信息
    void add(OrderSetting orderSetting);

    //调用dao层进行范围查询
    List<OrderSetting> getOrderSettingBetweenDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

    //更新可预约人数
    void editNumberByOrderDate(OrderSetting orderSetting);

    //更新已预约人数
    void editReservationsByOrderDate(OrderSetting os);


}
