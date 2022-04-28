package com.snow.easyexcel.controller.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.fastjson.JSON;
import com.snow.common.pojo.PublicResult;
import com.snow.easyexcel.entity.excel.NoModelWriteData;
import com.snow.easyexcel.entity.excel.SimpleWriteData;
import com.snow.easyexcel.entity.user.TbUser;
import com.snow.easyexcel.service.user.TbUserService;
import com.snow.easyexcel.util.EasyExcelUtils;
import com.snow.easyexcel.util.MyExcelMerge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("excel")
public class ExcelController {
    /**
     * 服务对象
     */
    @Resource
    private TbUserService service;


    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        SimpleWriteData d = new SimpleWriteData();
        d.setFileName("账单流水");
        d.setDataList(service.getList());
        EasyExcelUtils easyExcelUtils = new EasyExcelUtils();
        easyExcelUtils.simpleWrite(d, TbUser.class,response);
    }
    @GetMapping("/export2")
    public void export2(HttpServletResponse response) throws IOException {
        // 文件输出位置
        OutputStream out = new FileOutputStream("d://test.xlsx");
        ExcelWriter writer = EasyExcelFactory.write(out).build();
        EasyExcelUtils easyExcelUtils = new EasyExcelUtils();
        // 动态添加表头，适用一些表头动态变化的场景
        WriteSheet sheet1 = new WriteSheet();
        sheet1.setSheetName("商品明细");
        sheet1.setSheetNo(0);
        // 创建一个表格，用于 Sheet 中使用
        WriteTable table = new WriteTable( );
        table.setTableNo(1);
        table.setHead(easyExcelUtils.head());
        // 写数据
        writer.write(easyExcelUtils.contentData(), sheet1, table);
        writer.finish();
        out.close();
    }

    @GetMapping("/export3")
    public void export3(HttpServletResponse response) throws IOException {
        String[] headMap = {"编码", "用户", "密码", "真名"};
        List<String> headList = Arrays.asList(headMap);
        String[] dataStrMap = {"id", "username", "password", "realname"};
        List<TbUser> list = service.list();
        List<Map<String, Object>> relist = new ArrayList<>();
        for (TbUser u:list) {
            Map<String, Object> map = JSON.parseObject(JSON.toJSONString(u),Map.class);
            relist.add(map);
        }

        // 文件输出位置
        OutputStream out = new FileOutputStream("d://test2.xlsx");
        ExcelWriter writer = EasyExcelFactory.write(out).build();
        EasyExcelUtils easyExcelUtils = new EasyExcelUtils();
        // 动态添加表头，适用一些表头动态变化的场景
        WriteSheet sheet1 = new WriteSheet();
        sheet1.setSheetName("商品明细");
        sheet1.setSheetNo(0);
        // 创建一个表格，用于 Sheet 中使用
        WriteTable table = new WriteTable( );
        table.setTableNo(1);
        table.setHead(easyExcelUtils.head2(headList));
        // 写数据
        writer.write(easyExcelUtils.dataList(relist,dataStrMap), sheet1, table);
        writer.finish();
        out.close();


    }


    @GetMapping("/export4")
    public void export4(HttpServletResponse response) {
        MyExcelMerge myExcelMerge= new MyExcelMerge();
        myExcelMerge.getRptMerAccTotalListExcel(response);
    }
    @GetMapping("/export5")
    public void export5(HttpServletResponse response) throws IOException {
        String[] headMap = {"编码", "用户", "密码", "真名"};
        List<String> headList = Arrays.asList(headMap);
        String[] dataStrMap = {"id", "username", "password", "realname"};
        List<TbUser> list = service.list();
        List<Map<String, Object>> relist = new ArrayList<>();
        for (TbUser u:list) {
            Map<String, Object> map = JSON.parseObject(JSON.toJSONString(u),Map.class);
            relist.add(map);
        }

        EasyExcelUtils easyExcelUtils = new EasyExcelUtils();
        NoModelWriteData data = new NoModelWriteData();
        data.setDataList(relist);
        data.setDataStrMap(dataStrMap);
        data.setHeadMap(headMap);
        data.setFileName("testttttt");
        easyExcelUtils.noModleWrite(data,response);

    }
    @GetMapping("/export6")
    public PublicResult export6() throws IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("date",new Date());
        map.put("person","测试人员");
        List<TbUser> list = service.list();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/"+"tempExcel.xlsx");
        String fileName = UUID.randomUUID() + "_" + "tempExcel.xlsx";
//        String downloadPath = RuoYiConfig.getDownloadPath() + fileName;
        String downloadPath ="d://" + fileName;
        OutputStream out=new FileOutputStream(downloadPath);
        ExcelWriter excelWriter = EasyExcel.write(out).withTemplate(inputStream).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        excelWriter.fill(list, writeSheet);
        excelWriter.fill(map, writeSheet);
        excelWriter.finish();
        return PublicResult.success(fileName);
    }

}
