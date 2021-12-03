package top.yhl.cloud.log.anno;

import java.lang.annotation.*;

/**
 * @author yang_hl3
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogRecordAnnotaion {
    //操作日志的文本模板
    String success();

    // 操作日志失败的文本版本
    String fail() default "";

    //操作日志的执行人
    String operator() default "";

    //操作日志绑定的业务对象标识
    String bizNo();

    //操作日志的种类
    String category() default "";

    //	扩展参数，记录操作日志的修改详情
    String detail() default "";

    //记录日志的条件
    String condition() default "";
}
