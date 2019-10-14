package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.untils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @atuthor JackLove
 * @date 2019-09-28 21:03
 * @Package com.itheima.controller
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    //导入预约设置
    @PostMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) throws Exception {

        try {//1.读取文件的内容
            List<String[]> strings = POIUtils.readExcel(excelFile);
            // 2.把它转成OrderSetting实体List
            //2.1将日期格式转化为poi的格式
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            List<OrderSetting> list = new ArrayList<>();
            OrderSetting orderSetting = null;
            //2.2遍历拿到每一行的数据
            for (String[] arr : strings) {
                //2.3将日期转化为javabean中的数据类型,并且封装到javabean中
                orderSetting = new OrderSetting(sdf.parse(arr[0]), Integer.valueOf(arr[1]));
                //2.4将遍历的数据添加到list集合中去
                list.add(orderSetting);
            }
            //3.调用业务层服务导入预约设置
            if (list.size() > 0) {
                orderSettingService.doImport(list);
                return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS, list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }

    //展示日历的预约数据
    @PostMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month) {
        //1.调用业务层查询当前月份的预约信息
        List<OrderSetting> orderSettingList = orderSettingService.getOrderSettingByMonth(month);
        //2.封装当月的预约数据
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dayData = null;
        //2.1判断预约数据不为空
        if (orderSettingList != null && orderSettingList.size() > 0) {

            SimpleDateFormat sdf = new SimpleDateFormat("d");
            //2.2 遍历拿到每日的数据
            for (OrderSetting orderSetting : orderSettingList) {
                //2.3 将每日的预约数据封装到map里
                dayData = new HashMap<>();
                //2.4设置预约人数
                dayData.put("number", orderSetting.getNumber());
                //2.5设置可预约人数
                dayData.put("reservations", orderSetting.getReservations());
                //2.6设置预约中日期并转化成int
                dayData.put("date", sdf.format(orderSetting.getOrderDate()));
                //2.7将每日的数据封装到lsit集合里面去
                resultList.add(dayData);
            }
        }
        //3.返回结果集
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, resultList);
    }

    //设置预约人数
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        //调用业务设置预约信息
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }
}