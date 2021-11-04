package top.yhl.cloud.log.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Parameter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogRecordOps {
    //操作日志文本模板
    String template;
    // 参数名称
    Parameter[] parameters;
}
