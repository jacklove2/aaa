package com.itheima.controller;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-27 13:11
 * @Package com.itheima.controller
 */
public class TestWrite {
    public static void main(String[] args) throws Exception {
        //1.准备好要写入的表格的工作表,表头
        //创建工作簿对象, 此时不需要参数
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建工作表对象
        XSSFSheet sheet = workbook.createSheet();
        //创建行 表头 0
        XSSFRow row = sheet.createRow(0);
        //2.创建字段
        //创建单元格,
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("姓名");
        row.createCell(1).setCellValue("年龄");
        row.createCell(2).setCellValue("性别");
        //3.写入数据
        List<Student> students = getStudents();
        //从第一行开始
        //rowAt行号
        int rowAt = 1;
        for (Student student : students) {
            row = sheet.createRow(rowAt);
            row.createCell(0).setCellValue(student.name);
            row.createCell(1).setCellValue(student.age);
            row.createCell(2).setCellValue(student.gender);
            rowAt++;
        }
        //4.关流
        workbook.write(new FileOutputStream(new File("C:\\Users\\86189\\Desktop\\stu.xlsx")));
        workbook.close();
    }

    private static List<Student> getStudents() {
        List<Student> list = new ArrayList<>();
        list.add(new Student("张三", 18, "男"));
        list.add(new Student("李四", 19, "女"));
        return list;
    }


}
