package com.snow.es.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 就诊事件索引(TbSyJzsj)表实体类
 *
 * @author makejava
 * @since 2022-05-13 14:32:35
 */
@Data
public class TbSyJzsj extends Model<TbSyJzsj> {
    //唯一编码，（医疗机构编码_就诊号）
    @TableId
    private String cbm;
    //就诊号（门诊/住院号)依据患者类型判断
    private String cjzh;
    //患者标识(业务患者唯一标识)
    private String chzbs;
    //患者类型编码
    private String chzjzlxbm;
    //患者类型名称
    private String chzjzlxmc;
    //就诊开始时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date djzkssj;
    //就诊结束时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date djzjssj;
    //医疗机构代码
    private String cyljgdm;
    //医疗机构名称
    private String cyljgmc;
    //就诊科室代码
    private String cjzksdm;
    //就诊科室名称
    private String cjzksmc;
    //主治医师代码
    private String czzysdm;
    //主治医师姓名
    private String czzysmc;
    //疾病诊断编码（第一顺位诊断）
    private String czdbm;
    //疾病诊断名称
    private String czdmc;
    //分区标识
    private String cfq;
    //患者姓名
    private String chzxm;
    //性别代码
    private String cxbbm;
    //性别名称
    private String cxbmc;
    //身份证件号码
    private String csfzjhm;
    //归档标识
    private Integer igdbs;
    //转科标识
    private Integer izkbs;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.cbm;
    }
    }

