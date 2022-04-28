package com.snow.easyexcel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.google.common.collect.Lists;
import com.snow.easyexcel.entity.excel.RptMerAccTotalListExcelVo;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyExcelMerge {
    public void getRptMerAccTotalListExcel(HttpServletResponse response) {
        // 制表人
        String username = "";
        String organcode = "";
        /**
         *  中间省略了一些逻辑代码。。。。
         *  meracctotalList  查询出的list集合
         */
        ArrayList<RptMerAccTotalListExcelVo> voArrayList = new ArrayList<>();
        // 收入总笔数合计
        Long intotalcountTotal =0L;
        //收入总金额合计
        Double intotalamtTotal = 0D;
        //支出总笔数合计
        Long outtotalcountTotal = 0L;
        // 支出总金额合计
        Double outtotalamtTotal = 0D;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 添加合计行数据
        RptMerAccTotalListExcelVo merAccTotalListExcelVo = new RptMerAccTotalListExcelVo();
        merAccTotalListExcelVo.setMername("合计：");
        merAccTotalListExcelVo.setIntotalcount(intotalcountTotal);
        merAccTotalListExcelVo.setIntotalamt(intotalamtTotal);
        merAccTotalListExcelVo.setOuttotalcount(outtotalcountTotal);
        merAccTotalListExcelVo.setOuttotalamt(outtotalamtTotal);
        voArrayList.add(merAccTotalListExcelVo);
        // 添加签字盖章行
        RptMerAccTotalListExcelVo rptMerAccTotalListExcelVo = new RptMerAccTotalListExcelVo();
        rptMerAccTotalListExcelVo.setMername("签字(盖章):");
        rptMerAccTotalListExcelVo.setOuttotalcount(null);
        rptMerAccTotalListExcelVo.setSettflag("_______年_____月_____日");
        voArrayList.add(rptMerAccTotalListExcelVo);
        // 合并单元格
        OnceAbsoluteMergeStrategy onceMerg1 = getOnceMerge(voArrayList.size() + 2, voArrayList.size() + 4, 0, 3);
        OnceAbsoluteMergeStrategy onceMerge2 = getOnceMerge(voArrayList.size() + 2, voArrayList.size() + 4, 4, 6);
        OnceAbsoluteMergeStrategy onceMerge3 = getOnceMerge(voArrayList.size() + 2, voArrayList.size() + 4, 7, 8);
        // 添加两个空的行(不然没有底部线条)
        RptMerAccTotalListExcelVo rvo = new RptMerAccTotalListExcelVo();
        rvo.setMername("");
        voArrayList.add(rvo);
        voArrayList.add(rvo);
        // 报表日期
        String reDate = sdf.format(new Date()) + "—" + sdf.format(new Date());;
        // 调用下面的动态表头
        List<List<String>> head = this.head(username, reDate);

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为白色
//        headWriteCellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);

        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle,contentWriteCellStyle);

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("公司销售额统计报表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), RptMerAccTotalListExcelVo.class)
                    .registerWriteHandler(onceMerg1)
                    .registerWriteHandler(onceMerge2)
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .registerWriteHandler(onceMerge3).head(head).sheet("统计报表").doWrite(voArrayList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 动态表头
    private List<List<String>> head(String username, String reDate) {

        List<List<String>> headTitles = Lists.newArrayList();
        String title = "统计报表", tabulator = "制表人：" + username, reportingUnit = "XX公司" , statementDate = "报表日期：" + reDate;

        headTitles.add(Lists.newArrayList(title, tabulator, "公司名称"));
        headTitles.add(Lists.newArrayList(title, tabulator, "公司模式"));
        headTitles.add(Lists.newArrayList(title, tabulator, "交易日期"));
        headTitles.add(Lists.newArrayList(title, reportingUnit, "收入总笔数"));
        headTitles.add(Lists.newArrayList(title, reportingUnit, "收入总金额(元)"));
        headTitles.add(Lists.newArrayList(title, reportingUnit, "支出总笔数"));
        headTitles.add(Lists.newArrayList(title, statementDate, "支出总金额(元)"));
        headTitles.add(Lists.newArrayList(title, statementDate, "结算状态"));
        headTitles.add(Lists.newArrayList(title, statementDate, "结算日期"));
        return headTitles;
    }



    /**
     * 合并单元格
     * 根据需要指定位置的单元格进行合并
     * @param firstRowIndex 起始行索引
     * @param lastRowIndex 结束行索引
     * @param firstColumnIndex 起始列索引
     * @param lastColumnIndex 结束列索引
     *          将第6-7行的2-3列合并成一个单元格
     * @return (firstRowIndex = 5, lastRowIndex = 6, firstColumnIndex = 1, lastColumnIndex = 2)
     */
    public static OnceAbsoluteMergeStrategy getOnceMerge(int firstRowIndex, int lastRowIndex, int firstColumnIndex, int lastColumnIndex) {
        OnceAbsoluteMergeStrategy once = new OnceAbsoluteMergeStrategy(firstRowIndex, lastRowIndex, firstColumnIndex, lastColumnIndex);
        return once;
    }

}
