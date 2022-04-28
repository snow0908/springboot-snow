package com.snow.easyexcel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.snow.easyexcel.entity.excel.NoModelWriteData;
import com.snow.easyexcel.entity.excel.SimpleWriteData;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EasyExcelUtils {

    //不创建对象的导出
    public void noModleWrite(@RequestBody NoModelWriteData data, HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
//            response.setContentType("application/vnd.ms-excel");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode(data.getFileName(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream()).head(head(data.getHeadMap())).sheet(data.getFileName()).doWrite(dataList(data.getDataList(), data.getDataStrMap()));
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    //创建对象的导出
    public <T> void simpleWrite(@RequestBody SimpleWriteData data, Class<T> clazz, HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
//        response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(data.getFileName(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), clazz).sheet(data.getFileName()).doWrite(data.getDataList());
    }

    //设置表头
    private List<List<String>> head(String[] headMap) {
        List<List<String>> list = new ArrayList<List<String>>();

        for (String head : headMap) {
            List<String> headList = new ArrayList<String>();
            headList.add(head);
            list.add(headList);
        }
        return list;
    }


    //设置表头
    public List<List<String>> head2(List<String> headMap) {
        String basicInfo = "基础资料";
        List<List<String>> headTitles = Lists.newArrayList();
        headMap.forEach(title->{
            headTitles.add(Lists.newArrayList(basicInfo ,title) );
        });
        return headTitles;
    }

    //设置导出的数据内容
    public List<List<Object>> dataList(List<Map<String, Object>> dataList, String[] dataStrMap) {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (Map<String, Object> map : dataList) {
            List<Object> data = new ArrayList<Object>();
            for (int i = 0; i < dataStrMap.length; i++) {
                data.add(map.get(dataStrMap[i]));
            }
            list.add(data);
        }
        return list;
    }


    public List <List<String>> head(){
        List<List<String>> headTitles = Lists.newArrayList();
        String basicInfo = "基础资料",skuInfo = "商品扩展",orderInfo = "经营情况",empty = " ";
        //第一列，1/2/3行
        headTitles.add( Lists.newArrayList(basicInfo ,basicInfo,"类别") );
        //第二列，1/2/3行
        headTitles.add( Lists.newArrayList(basicInfo,basicInfo,"名称" ) );
        List<String>  skuTitles = Lists.newArrayList("组合商品", "上一次优惠时间", "销售次数", "库存", "价格");
        skuTitles.forEach(title->{
            headTitles.add( Lists.newArrayList(skuInfo ,skuInfo,title) );
        });
        /*List<Integer> monthList = Lists.newArrayList(5,6);
        //动态根据月份生成
        List<String> orderSpeaces = Lists.newArrayList("销售额", "客流", "利润");
        monthList.forEach(month->{
            orderSpeaces.forEach(title->{
                headTitles.add( Lists.newArrayList(orderInfo ,  month+"月" ,title ) );
            });
        });*/
        //无一、二行标题
        /*List<String> lastList = Lists.newArrayList("日均销售金额(元)", "月均销售金额(元)" );
        lastList.forEach(title->{
            headTitles.add( Lists.newArrayList(empty , empty ,title ) );
        });*/
        return headTitles;
    }

    public List <List<Object>> contentData(){
        List<List<Object>> contentList = Lists.newArrayList();
        //这里一个List<Object>才代表一行数据，需要映射成每行数据填充，横向填充（把实体数据的字段设置成一个List<Object>）
        contentList.add( Lists.newArrayList("测试", "商品A","苹果🍎") );
        contentList.add( Lists.newArrayList("测试", "商品B","橙子🍊") );
        return contentList;
    }

}

