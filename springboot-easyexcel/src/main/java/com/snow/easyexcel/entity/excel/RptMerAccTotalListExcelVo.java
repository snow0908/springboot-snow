package com.snow.easyexcel.entity.excel;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
@ColumnWidth(20)
public class RptMerAccTotalListExcelVo {

    private String mername;
    private String txcode;
    private String rptdate;
    private Long intotalcount;
    private Double intotalamt;
    private Long outtotalcount;
    private Double outtotalamt;
    private String settflag;
    private String settdate;


}

