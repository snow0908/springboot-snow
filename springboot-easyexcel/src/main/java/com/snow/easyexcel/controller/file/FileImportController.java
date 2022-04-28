package com.snow.easyexcel.controller.file;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snow.common.pojo.PublicResult;
import com.snow.easyexcel.entity.file.FileImport;
import com.snow.easyexcel.service.file.FileImportService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (FileImport)表控制层
 *
 * @author makejava
 * @since 2022-04-07 10:20:26
 */
@RestController
@RequestMapping("fileImport")
public class FileImportController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private FileImportService fileImportService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param fileImport 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<FileImport> page, FileImport fileImport) {
        return success(this.fileImportService.page(page, new QueryWrapper<>(fileImport)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.fileImportService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param fileImport 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody FileImport fileImport) {
        return success(this.fileImportService.save(fileImport));
    }

    /**
     * 修改数据
     *
     * @param fileImport 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody FileImport fileImport) {
        return success(this.fileImportService.updateById(fileImport));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.fileImportService.removeByIds(idList));
    }


    //添加课程分类
    //获取上传的文件，把文件内容读出来
    @PostMapping("addSubject")
    public PublicResult addSubject(MultipartFile file){
        //上传excel文件
        fileImportService.saveSubject(file,fileImportService);
        return PublicResult.success();
    }
}

