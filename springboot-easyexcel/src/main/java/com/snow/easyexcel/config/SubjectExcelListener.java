package com.snow.easyexcel.config;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snow.easyexcel.entity.file.FileImport;
import com.snow.easyexcel.entity.file.SubjectData;
import com.snow.easyexcel.service.file.FileImportService;
import org.hibernate.service.spi.ServiceException;

/**
 * @author ZhangTao
 * @date 2021/4/22 15:42
 * @note:监听器
 *
 * /因为SubjectExcelListener不能交给spring管理，需要 自己new，不能注入其他对象
 * 不能实现数据库的操作
 *
 *前面service中已经在listener中注入了service，
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public FileImportService subjectService;
    //创建有参无参构造器为了后面能做添加操作，
    public SubjectExcelListener(FileImportService subjectService) {
        this.subjectService = subjectService;
    }
    public SubjectExcelListener(){

    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new ServiceException("20001:文件数据为空");
        }
        //从第二行开始读
        //一行一行的读取，每次读取的两个值，第一个值是一级分类，第二个是二级分类
        //判断一级分类是否为空
        FileImport existOneSubject = this.existOneSubject(subjectService,subjectData.getOneSubjectName());
        //为空就是没有相同的一级分类，则进行添加
        if(existOneSubject==null){//没有相同的一级分类
            existOneSubject=new FileImport();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());//刚传进去的一级分类名
            subjectService.save(existOneSubject);//前面做了很多就是为了能使用subjectService调用save方法，
        }

        //获取一节分类的id值
        //二级分类的pareat——id就是一级分类的id值
        String pid=existOneSubject.getId();
        //添加二级分类
        //判断二级分类是否重复
        FileImport existTwoSubject = this.existTwoSubject(subjectService,subjectData.getTwoSubjectName(),pid);
        if(existTwoSubject==null){//没有相同的一级分类
            existTwoSubject=new FileImport();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());//一级分类名
            subjectService.save(existTwoSubject);
        }
    }
    //判断一级分类不能重复添加
    //使根据传进去的name和0（代表着使一级分类）//判断表中是否有值
    //没有值的话返回为null
    private FileImport existOneSubject(FileImportService subjectService, String name){
        QueryWrapper<FileImport> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        FileImport oneSubject=subjectService.getOne(wrapper);//getOne根据 Wrapper，查询一条记录
        return oneSubject;
    }
    //    //判断二级分类不能重复添加

    private FileImport existTwoSubject(FileImportService subjectService,String name,String pid){
        QueryWrapper<FileImport> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        FileImport twoSubject=subjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
