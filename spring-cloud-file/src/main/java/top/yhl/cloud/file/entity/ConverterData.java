package top.yhl.cloud.file.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ConverterData {

    @ExcelProperty(value = "字符串标题")
    private String string;

    @DateTimeFormat("yyyy年mm月dd日HH时mm分ss秒")
    @ExcelProperty(value = "日期标题")
    private Date data;

    @NumberFormat("#.##%")
    @ExcelProperty(value = "数字标题")
    private Double doubleData;
}
