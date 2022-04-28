package com.snow.easyexcel.mapper.file;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.snow.easyexcel.entity.file.FileImport;

/**
 * (FileImport)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-07 10:20:26
 */
public interface FileImportMapper extends BaseMapper<FileImport> {

/**
* 批量新增数据（MyBatis原生foreach方法）
*
* @param entities List<FileImport> 实例对象列表
* @return 影响行数
*/
int insertBatch(@Param("entities") List<FileImport> entities);

/**
* 批量新增或按主键更新数据（MyBatis原生foreach方法）
*
* @param entities List<FileImport> 实例对象列表
* @return 影响行数
* @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
*/
int insertOrUpdateBatch(@Param("entities") List<FileImport> entities);

}

