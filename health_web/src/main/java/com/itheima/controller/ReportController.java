package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.PackageService;
import com.itheima.service.ReportService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @atuthor JackLove
 * @date 2019-10-10 10:06
 * @Package com.itheima.controller
 */
@RestController
@RequestMapping("/report")

public class ReportController {
    @Reference
    private MemberService memberService;

    @Reference
    private PackageService packageService;

    @Reference
    private ReportService reportService;

    //获取过去一年的会员数据
    @GetMapping("/getMemberReport")
    public Result getMemberReport() {
        Map<String, Object> map = memberService.getMemberReport();
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }

    //预约套餐占比
    @GetMapping("/getPackageReport")
    public Result getPackageReport() {
        //存放套餐名称集合和套餐数据的集合
        Map<String, Object> map = new HashMap<>();
        //存放套餐名称集合
        List<String> packageNames = new ArrayList<>();
        //存放套餐数据的集合
        List<Map<String, Object>> packagelist = reportService.getPackageReport();

        if (packagelist != null) {
            for (Map<String, Object> pkgMap : packagelist) {
                //每一个套餐的数据
                packageNames.add((String) pkgMap.get("name"));
            }
        }
        map.put("packageNames", packageNames);
        map.put("packageCount", packagelist);

        return new Result(true, MessageConstant.GET_PACKAGE_COUNT_REPORT_SUCCESS, map);
    }

    //获取运用数据
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        Map<String, Object> map = reportService.getBusinessReportData();
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
    }

    //运营数据的导出
    @GetMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> map = reportService.getBusinessReportData();
        String template = req.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
        try {
            Workbook wb = new XSSFWorkbook(template);
            Sheet sht = wb.getSheetAt(0);
            // 4. 获取行、单元格、在对应的位置写入内容
            sht.getRow(2).getCell(5).setCellValue(((String) map.get("reportDate")));
            // 会员数据
            sht.getRow(4).getCell(5).setCellValue(((Integer) map.get("todayNewMember")));
            sht.getRow(4).getCell(7).setCellValue(((Integer) map.get("totalMember")));
            sht.getRow(5).getCell(5).setCellValue(((Integer) map.get("thisWeekNewMember")));
            sht.getRow(5).getCell(7).setCellValue(((Integer) map.get("thisMonthNewMember")));
            // 预约数量
            sht.getRow(7).getCell(5).setCellValue(((Integer) map.get("todayOrderNumber")));
            sht.getRow(7).getCell(7).setCellValue(((Integer) map.get("todayVisitsNumber")));
            sht.getRow(8).getCell(5).setCellValue(((Integer) map.get("thisWeekOrderNumber")));
            sht.getRow(8).getCell(7).setCellValue(((Integer) map.get("thisWeekVisitsNumber")));
            sht.getRow(9).getCell(5).setCellValue(((Integer) map.get("thisMonthOrderNumber")));
            sht.getRow(9).getCell(7).setCellValue(((Integer) map.get("thisMonthVisitsNumber")));
            // 热门套餐
            int rowCnt = 12;
            List<Map<String, Object>> hotPackageList = (List<Map<String, Object>>) map.get("hotPackage");
            if (hotPackageList != null) {
                for (Map<String, Object> pkgMap : hotPackageList) {
                    sht.getRow(rowCnt).getCell(4).setCellValue((String) pkgMap.get("name"));
                    sht.getRow(rowCnt).getCell(5).setCellValue((long) pkgMap.get("count"));
                    sht.getRow(rowCnt).getCell(6).setCellValue(((BigDecimal) pkgMap.get("proportion")).toString());
                    sht.getRow(rowCnt).getCell(7).setCellValue((String) pkgMap.get("remark"));
                    rowCnt++;
                }
                // 告诉浏览器接收的是文件 Content-Type,这个内容的是excel文件
                resp.setContentType("application/vnd.ms-excel");
                String filename = "运营数据.xlsx";
                //解决乱码
                filename = new String(filename.getBytes(), "ISO-8859-1");
                // 下载文件,
                resp.setHeader("Content-Disposition", "attachment;filename=" + filename);
                // 调用Response的输出流实现
                ServletOutputStream outputStream = resp.getOutputStream();
                wb.write(outputStream);
                outputStream.flush();
                outputStream.close();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
    }

}
