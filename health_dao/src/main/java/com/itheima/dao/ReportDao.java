package com.itheima.dao;

import java.util.List;
import java.util.Map;

/**
 * @atuthor JackLove
 * @date 2019-10-11 10:55
 * @Package com.itheima.dao
 */
public interface ReportDao {
    //获取套餐的统计数据
    List<Map<String,Object>> getPackageReport();

}
