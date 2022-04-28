package com.snow.easyexcel.service.impl.file;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snow.easyexcel.config.SubjectExcelListener;
import com.snow.easyexcel.entity.file.SubjectData;
import com.snow.easyexcel.mapper.file.FileImportMapper;
import com.snow.easyexcel.entity.file.FileImport;
import com.snow.easyexcel.service.file.FileImportService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * (FileImport)表服务实现类
 *
 * @author makejava
 * @since 2022-04-07 10:20:29
 */
@Service("fileImportService")
public class FileImportServiceImpl extends ServiceImpl<FileImportMapper, FileImport> implements FileImportService {

    @Override
    public void saveSubject(MultipartFile file,FileImportService fileImportService) {
        try {
            //文件输入流
            InputStream in=file.getInputStream();
            //调用方法进行读取
            //吧service直接注入进来为了后面能使用

            //因为在listener中不能注入service所以在这个serviceiimpl中，通过listener使service注入进去，为了在listener中能够使用service中的发方法save/
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(fileImportService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

