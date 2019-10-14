package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.ReportDao;
import com.itheima.entity.DateUtils;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @atuthor JackLove
 * @date 2019-10-11 10:54
 * @Package com.itheima.service.impl
 */
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Map<String, Object>> getPackageReport() {
        return reportDao.getPackageReport();
    }

    @Override
    public Map<String, Object> getBusinessReportData() {
        //当前日期的年月日
        Date now = new Date();
        String reportDate = DateUtils.date2String(now, DateUtils.YMD);
        //星期一的日期
        String monday = DateUtils.date2String(DateUtils.getThisWeekMonday(), DateUtils.YMD);
        //星期天的日期
        String sunday = DateUtils.date2String(DateUtils.getSundayOfThisWeek(), DateUtils.YMD);
        // 1号的日期
        String firstDayOfThisMonth = DateUtils.date2String(DateUtils.getFirstDayOfThisMonth(), DateUtils.YMD);
        // 最后一天
        String lastDayOfThisMonth = DateUtils.date2String(DateUtils.getLastDayOfThisMonth(), DateUtils.YMD);

        //--------------------会员统计数据-------------------------
        //今日新增会员数
        Integer todayNewMember = memberDao.findMemberCountByDate(reportDate);
        //总会员数
        Integer totalMember = memberDao.findMemberTotalCount();
        //本周新增会员数
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(monday);
        //本月新增会员数
        Integer thisMonthNewMember = memberDao.findMemberCountBeforeDate(firstDayOfThisMonth);

        //--------------------预约到诊数据统计-------------------------
        // 今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(reportDate);
        // 今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(reportDate);
        // 本周预约数
        Integer thisWeekOrderNumber = orderDao.findOrderCountBetweenDate(monday, sunday);
        // 本周到诊数
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(monday);
        // 本月预约数
        Integer thisMonthOrderNumber = orderDao.findOrderCountBetweenDate(firstDayOfThisMonth, lastDayOfThisMonth);
        // 本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDayOfThisMonth);

        //--------------------热门套餐,取前4个-------------------------
        List<Map<String, Object>> hotPackages = orderDao.findHotPackage();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportDate",reportDate);
        map.put("todayNewMember",todayNewMember);
        map.put("totalMember",totalMember);
        map.put("thisWeekNewMember",thisWeekNewMember);
        map.put("thisMonthNewMember",thisMonthNewMember);
        map.put("todayOrderNumber",todayOrderNumber);
        map.put("todayVisitsNumber",todayVisitsNumber);
        map.put("thisWeekOrderNumber",thisWeekOrderNumber);
        map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        map.put("thisMonthOrderNumber",thisMonthOrderNumber);
        map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        map.put("hotPackage",hotPackages);
        return map;

    }
}
