package com.snow.easyexcel.service.file;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snow.easyexcel.entity.file.FileImport;
import org.springframework.web.multipart.MultipartFile;

/**
 * (FileImport)表服务接口
 *
 * @author makejava
 * @since 2022-04-07 10:20:29
 */
public interface FileImportService extends IService<FileImport> {
    //添加课程分类
    void saveSubject(MultipartFile file,FileImportService fileImportService);

}

