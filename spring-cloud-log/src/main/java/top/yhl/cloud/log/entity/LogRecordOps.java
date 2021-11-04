package top.yhl.cloud.log.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogRecordOps {
    //操作日志的文本模板
    String success;

    // 操作日志失败的文本版本
    String fail;

    //操作日志的执行人
    String operator;

    //操作日志绑定的业务对象标识
    String bizNo;

    //操作日志的种类
    String category;

    //	扩展参数，记录操作日志的修改详情
    String detail;

    //记录日志的条件
    String condition;
}
