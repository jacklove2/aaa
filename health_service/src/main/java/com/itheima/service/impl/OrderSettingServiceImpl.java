package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-28 21:04
 * @Package com.itheima.service.impl
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    //导入预约数据
    @Override
    public void doImport(List<OrderSetting> list) {
        //遍历所有的lsit
        for (OrderSetting orderSetting : list) {
            //调用dao查询预约日期是否存在
            OrderSetting osIndb = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
            if (osIndb != null) {
                //如果存在则更新数量
                osIndb.setNumber(orderSetting.getNumber());
                orderSettingDao.updateNumber(osIndb);
            } else {//不存在则插入
                orderSettingDao.add(orderSetting);
            }
        }
    }

    //展示预约数据
    @Override
    public List<OrderSetting> getOrderSettingByMonth(String month) {
        String startDate = month + "-01";
        String endDate = month + "-31";
        return orderSettingDao.getOrderSettingBetweenDate(startDate, endDate);
    }

    //通过日期设置预约人数
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //调用dao查询预约日期
        OrderSetting os = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        //判断预约人数是否存在
        if (os == null) {
            //不存在则插入
            orderSettingDao.add(orderSetting);
        } else {
            //存在则更新数据
            os.setNumber(orderSetting.getNumber());
            orderSettingDao.updateNumber(os);
        }
    }
}
