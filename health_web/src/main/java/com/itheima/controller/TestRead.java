package com.itheima.controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @atuthor JackLove
 * @date 2019-09-27 11:35
 * @Package com.itheima.controller
 */
public class TestRead {
    public static void main(String[] args) throws Exception {
        //1 创建工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\86189\\Desktop\\student.xlsx");
        //2 获得工作表对象
        XSSFSheet sheet = workbook.getSheetAt(0);
        //3.获得行对象
        //sht.getLastRowNum(); // 最后一行的下标
        for (Row row : sheet) {
            //遍历行对象,获取单元格对象
            for (Cell cell : row) {
                //cell.getCellType()获取单元格类型
                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    System.out.print(cell.getStringCellValue() + ",");
                } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    System.out.print(cell.getNumericCellValue() + ",");
                }
            }
            System.out.println();
        }
        workbook.close();
    }
}
