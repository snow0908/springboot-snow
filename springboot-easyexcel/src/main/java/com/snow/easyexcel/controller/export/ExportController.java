package com.snow.easyexcel.controller.export;

import com.snow.easyexcel.service.user.TbUserService;
import com.snow.easyexcel.util.ZipUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("export")
public class ExportController {
    @Resource
    private TbUserService service;

    //ZIP文件包压缩下载
    @GetMapping("/downloadZip")
    public ResponseEntity downloadZip(){
        Map<String,String> map = new HashMap<>();
        Map<String,Map<String,String>> dataMap = getData();
        for (Map.Entry<String, Map<String, String>> m : dataMap.entrySet()) {
            for (Map.Entry<String, String> tt : m.getValue().entrySet()) {
                map.put(m.getKey()+"/"+tt.getKey(),tt.getValue());
            }
        }
        byte[] data = ZipUtils.getDatas(map);
        // 设置http响应头
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "attachment;filename=" + "zipFile.zip" );
        return new ResponseEntity<byte[]>(data, header, HttpStatus.CREATED);
//        return PublicResult.success(data);
    }

    public static Map<String,Map<String,String>> getData(){
        Map<String,Map<String,String>> map = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            Map<String,String> data = new HashMap<>();
            for (int j = 0; j < 3; j++) {
                String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><YHYL><USERCODE>zhangsan---"+j+"</USERCODE><USERNAME>管理员---"+j+"</USERNAME></YHYL>";
                data.put("EMR-SD"+j+".xml",xml);
            }
            map.put("姓名"+i,data);
        }
        return map;
    }

}
