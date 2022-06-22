package com.snow.es.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snow.es.dao.TbSyJzsjDao;
import com.snow.es.entity.TbSyJzsj;
import com.snow.es.service.TbSyJzsjService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 就诊事件索引(TbSyJzsj)表服务实现类
 *
 * @author makejava
 * @since 2022-05-13 14:32:41
 */
@Service("tbSyJzsjService")
public class TbSyJzsjServiceImpl extends ServiceImpl<TbSyJzsjDao, TbSyJzsj> implements TbSyJzsjService {



    public static void main(String[] args) {
        System.out.println(readZipFileName("D:/新建文本文档 (3).zip"));
    }

    //读取zip文件内的文件,返回文件名称列表
    public static List<Map<String, Object>> readZipFileName(String path){
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            // windows环境下，默认字符集为GBK，ZipFile默认使用UTF-8字符集，当文件名存在中文时，处理时就会报错
            ZipFile zipFile = new ZipFile(path, Charset.forName("GBK"));
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
//                System.out.println(entry.getName());
//                System.out.println(entry.getSize());
                Map<String, Object> map = new HashMap<>();
                map.put("name",entry.getName());
                map.put("size",entry.getSize());
                list.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}

