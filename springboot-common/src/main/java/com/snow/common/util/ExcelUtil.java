package com.snow.common.util;


import com.snow.common.annotation.FieldDes;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author snow
 * */
public abstract class ExcelUtil {

    /**
     * 导出excel 返回Workbook对象
     * @param list 数据列表
     * @param fields 字段列表
     * @param titles 表头列表
     * */
    public static Workbook exportExcel(List<Map> list, List<String> fields, List<String> titles) {
        HSSFWorkbook sheets = new HSSFWorkbook();
        HSSFSheet sheet = sheets.createSheet("sheet");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        //创建标题
        for (int i = 0; i < titles.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(titles.get(i));
        }
        if (list.size() < 0) {
            return sheets;
        } else {
            for (int m = 0; m < list.size(); m++) {
                row = sheet.createRow(m + 1);
                for (int j = 0; j < fields.size(); j++) {
                    Object var1 = list.get(m).get(fields.get(j));
                    String str = var1 == null ? "" : String.valueOf(var1);
                    cell = row.createCell(j);
                    cell.setCellValue(str);
                }
            }
            return sheets;
        }
    }

    /**
     * 导入excel
     * @param is 字段列表
     * @param files 导入字段
     * */
    public static List<Map<String, Object>> importExcel(InputStream is, String files) {
        String[] split = files.split(",");
        List<String> strings = Arrays.asList(split);
        //获取要导入表格列数
        int length = files.split(",").length;
        Workbook hb = null;
        Sheet sheet = null;
        try {
            hb = new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = hb.getSheetAt(0);
        List<Map<String, Object>> result = new ArrayList<>();
        if (sheet != null) {
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Map<String, Object> map = new ConcurrentHashMap<>();
                try {
                    Row row = sheet.getRow(i);
                    if (isRowEmpty(row)) {
                        continue;
                    }
                    for (int j = 0; j < length; j++) {
                        String cellValue = getCellValue(row.getCell(j));
                        map.put(strings.get(j), cellValue);
                    }
                    result.add(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        return result;
    }

    /**
     * 导出excel
     * @param list 数据列表
     * @param fields 字段列表
     * @param titles 表头列表
     * @param response 响应对象
     * @param name 导出文件名称
     * */
    public static void exportExcel(List<Map> list, List<String> fields, List<String> titles, HttpServletResponse response,String name) {
        Workbook sheets = exportExcel(list, fields, titles);
        if(StringUtil.isBlank(name)){
            name = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        try {
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(name+".xls", "UTF-8"));
            response.setCharacterEncoding("UTF-8");
            sheets.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断读取Excel列是否为空
     */
    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据传入cell获取值
     */
    public static String getCellValue(Cell cell) {
        String strCell = "";
        CellType cellTypeEnum = cell.getCellTypeEnum();
        switch (cellTypeEnum) {
            case STRING:
                strCell = cell.getStringCellValue();
                break;
            case NUMERIC:
                Double value = cell.getNumericCellValue();
                strCell = String.valueOf(value.intValue());
                break;
            case BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK:
                strCell = "";
                break;
            case FORMULA:
                Double value2 = cell.getNumericCellValue();
                strCell = String.valueOf(value2.intValue());
                break;
            default:
                strCell = cell.toString();
                break;
        }

        if (strCell.equals("") || strCell == null) {
            return "";
        }
        return strCell;

    }

    public static void exportExcel(List<Map> list,Class clazz ,String name){
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        List<String> fields = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        getObjectFieldNameAndDes(clazz, fields, titles);
        exportExcel(list, fields, titles, response, name);
    }

    /**
     * 通过对象class获取字段名称
     *
     * @param clazz
     * @return
     */
    private static void getObjectFieldNameAndDes(Class clazz, List<String> fields, List<String> titles) {
        Arrays.asList(clazz.getDeclaredFields()).forEach(field -> {
            fields.add(field.getName());
            titles.add(field.getAnnotation(FieldDes.class).des());
        });
    }

}

