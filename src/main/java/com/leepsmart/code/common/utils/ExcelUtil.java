package com.leepsmart.code.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.poi.hssf.record.cf.BorderFormatting.BORDER_THIN;
import static org.apache.poi.ss.usermodel.PatternFormatting.SOLID_FOREGROUND;

public class ExcelUtil {

    private static final String CHECK = "姓名手机号一级行业二级行业 公司或店铺名地区详细地址";

    /**
     * 导入表格解析为map返回
     */
    public static Map<Integer, List<String>> importExcel(File file) {
//        File file = new File("D:\\员工表.xlsx");
        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }
        FileInputStream fis = null;
        Workbook workBook = null;
        try {
            fis = new FileInputStream(file);
            // 使用XSSFWorkbook
            workBook = new XSSFWorkbook(fis);
            // 获取第一个sheet(表格头)
            Sheet sheet = workBook.getSheetAt(0);
            //第一个参数表示行数 第二个List保存该行的cell(单元格)数据
            Map<Integer, List<String>> map = new HashMap<>(8);
            int i = 0;
            //排除科学计数
            DecimalFormat df = new DecimalFormat("0");
            Row row1 = sheet.getRow(0);
            short lastCellNum = row1.getLastCellNum();
            StringBuilder sb = new StringBuilder();
            for (Cell cell : row1) {
                sb.append(cell.toString());
            }
            if (lastCellNum != 7 || !CHECK.equals(sb.toString())) {
                return null;
            }
            for (Row row : sheet) {
                map.put(i, new ArrayList<>());
                // 遍历当前行的所有cell
                for (Cell cell : row) {
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            // 如果是字符串则保存
                            map.get(i).add(cell.getRichStringCellValue().getString());
                            break;
                        case NUMERIC:
                            //将数值转换为字符串
                            map.get(i).add(df.format(cell.getNumericCellValue()));
                        default:
                            break;
                    }
                }
                i++;
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally { //关流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workBook != null) {
                try {
                    workBook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 根据参数产生excel表格
     */
    public static String createExcel(Map<String, List<String>> map, String[] strArray, String fileName) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("sheet1");
        sheet.setDefaultColumnWidth(20);// 默认列宽
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置表格边框
        style.setBorderBottom(BorderStyle.valueOf(BORDER_THIN)); //下边框
        style.setBorderLeft(BorderStyle.valueOf(BORDER_THIN));//左边框
        style.setBorderTop(BorderStyle.valueOf(BORDER_THIN));//上边框
        style.setBorderRight(BorderStyle.valueOf(BORDER_THIN));//右边框
        //设置背景色
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        style.setFillPattern(FillPatternType.forInt(SOLID_FOREGROUND));

        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        dataFont.setFontHeightInPoints((short) 13);
        dataFont.setColor(IndexedColors.BLACK.index);
        style.setFont(dataFont);

        // 添加excel title
        HSSFCell cell = null;
        for (int i = 0; i < strArray.length; i++) {
            cell = row.createCell((short) i);
            cell.setCellValue(strArray[i]);
            cell.setCellStyle(style);
        }

        HSSFCellStyle cellStyle = wb.createCellStyle();
        Font dataFont1 = wb.createFont();
        dataFont1.setFontName("simsun");
        dataFont1.setFontHeightInPoints((short) 14);
        dataFont1.setColor(IndexedColors.BLACK.index);
        cellStyle.setFont(dataFont1);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //设置表格边框
        cellStyle.setBorderBottom(BorderStyle.valueOf(BORDER_THIN)); //下边框
        cellStyle.setBorderLeft(BorderStyle.valueOf(BORDER_THIN));//左边框
        cellStyle.setBorderTop(BorderStyle.valueOf(BORDER_THIN));//上边框
        cellStyle.setBorderRight(BorderStyle.valueOf(BORDER_THIN));//右边框
        // 第五步，写入实体数据
        int i = 0;
        for (List<String> list : map.values()) {
            row = sheet.createRow(i + 1);
            // 第四步，创建单元格，并设置值和样式
            for (int j = 0; j < strArray.length; j++) {
                HSSFCell cell1 = row.createCell((short) j);
                cell1.setCellStyle(cellStyle);
                cell1.setCellValue(list.get(j));
            }
            i++;
        }

        autoSizeColumns(sheet, strArray.length + 1);

        try {
//                FileOutputStream fout = new FileOutputStream("/fruit_files/excel/"+fileName);
            // 第六步，将文件存到指定位置
            FileOutputStream fout = new FileOutputStream(fileName);
            wb.write(fout);
            fout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }


    /**
     * 自动调整列宽
     *
     * @param sheet
     * @param columnNumber
     */
    private static void autoSizeColumns(Sheet sheet, int columnNumber) {
        for (int i = 0; i < columnNumber; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = sheet.getColumnWidth(i) + 100;
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }
}