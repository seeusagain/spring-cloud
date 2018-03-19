package com.java.utils;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO:EXCEL导出
 * create by :lu.xu  20170808
 */
public class ExcelUtil {
    private static Logger logger = Logger.getLogger(ExcelUtil.class);

    private static final String dataFormart = "yyyy-MM-dd HH:mm:ss";

    /**
     * TODO:通用poi导出EXCEL
     * 注意：该方法导出的以".xls" office2003版的excel文档，若以".xlsx"后缀命名文件将不能正常打开。若要使用"xlsx"后缀，则
     * 该方法中的"HSSFWorkbook类"换成"XSSFWorkbook", 相应的单元格组件、样式都需要改正
     *
     * @param sheetName sheet名称
     * @param headers   列头
     *                  key:属性名称
     *                  value：属性名称对应的列头中文名
     * @param dataSets  数据集
     * @param pattern   日期格式化格式
     * @return 生成的HSSFWorkbook
     * @throws IOException
     */
    public static HSSFWorkbook exportExcel(String sheetName, LinkedHashMap<String, String> headers, Collection dataSets,
                                           String pattern) throws IOException {
        if (null == headers || headers.size() == 0) {
            throw new IllegalArgumentException("列头必须指明..");
        }
        if (EmptyUtils.isEmpty(pattern)) {
            pattern = dataFormart;
        }

        /**声明一个工作薄、一个sheet，并设置列宽15*/
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth((short) 15);

        /**表头样式、字体*/
        HSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        HSSFFont headFont = workbook.createFont();
        headFont.setFontHeightInPoints((short) 12);
        headFont.setBold(true);
        headStyle.setFont(headFont);

        /**内容样式、字体*/
        HSSFCellStyle contentStyle = workbook.createCellStyle();
        HSSFFont contentFont = workbook.createFont();
        contentStyle.setFont(contentFont);

        /**生成列头*/
        HSSFRow row = sheet.createRow(0);
        int i = 0;
        for (Map.Entry<String, String> header : headers.entrySet()) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            HSSFRichTextString text = new HSSFRichTextString(header.getValue());
            cell.setCellValue(text);
            i++;
        }

        /**生成数据*/
        Iterator it = dataSets.iterator();
        int index = 0;
        HSSFFont font3 = workbook.createFont();
        font3.setColor(HSSFColor.BLUE.index);
        try {
            while (it.hasNext()) {
                index++;
                Object t = it.next();
                row = sheet.createRow(index);

                /**反射获取数据并生成到EXCEL列中*/
                i = 0;
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    String props = header.getKey();
                    String getMethodName = "get" + props.substring(0, 1).toUpperCase() + props.substring(1);
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    /**Date类型使用格式化，其他按照字符串处理*/
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value != null) {
                        textValue = String.valueOf(value);
                    }

                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(contentStyle);
                    /**判断textValue是否全部由数字组成，如果是按照double处理*/
                    if (EmptyUtils.isNotEmpty(textValue)) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            if (EmptyUtils.isNotEmpty(textValue) && textValue.length() > 1000) {
                                textValue = textValue.substring(0, 1000) + "....";
                            }
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                    i++;
                } /**end of for*/
            } /**end of while*/
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return workbook;
    }
}