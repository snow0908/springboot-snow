package com.snow.easyexcel.entity.excel;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class SimpleWriteData implements Serializable {
    private String fileName;//文件名
    private List<?> dataList;//数据列表
}

