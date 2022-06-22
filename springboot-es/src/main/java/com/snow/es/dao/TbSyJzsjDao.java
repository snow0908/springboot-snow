package com.snow.es.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.snow.es.entity.TbSyJzsj;

/**
 * 就诊事件索引(TbSyJzsj)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-13 14:32:30
 */
public interface TbSyJzsjDao extends BaseMapper<TbSyJzsj> {

/**
* 批量新增数据（MyBatis原生foreach方法）
*
* @param entities List<TbSyJzsj> 实例对象列表
* @return 影响行数
*/
int insertBatch(@Param("entities") List<TbSyJzsj> entities);

/**
* 批量新增或按主键更新数据（MyBatis原生foreach方法）
*
* @param entities List<TbSyJzsj> 实例对象列表
* @return 影响行数
* @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
*/
int insertOrUpdateBatch(@Param("entities") List<TbSyJzsj> entities);

}

