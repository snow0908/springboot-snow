package com.snow.easyexcel.entity.user;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (TbUser)实体类
 *
 * @author makejava
 * @since 2022-03-26 16:28:39
 */
@Data
public class TbUser implements Serializable {
    private static final long serialVersionUID = 989548188838659935L;

    @ExcelIgnore
    private Integer id;

    @ExcelProperty(value = "用户名",index = 1)
    private String username;

    @ExcelProperty(value = "密码",index = 2)
    private String password;

    @ExcelProperty(value = "真名",index = 3)
    private String realname;

    @ExcelProperty(value = "文档信息",index = 4)
    private String xml;



}

