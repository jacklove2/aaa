package com.itheima.service;

import java.util.List;
import java.util.Map;

/**
 * @atuthor JackLove
 * @date 2019-10-11 10:54
 * @Package com.itheima.service
 */
public interface ReportService {
    //查询套餐的统计数据
    List<Map<String,Object>> getPackageReport();
    //获取运营数据
    Map<String,Object> getBusinessReportData();

}
