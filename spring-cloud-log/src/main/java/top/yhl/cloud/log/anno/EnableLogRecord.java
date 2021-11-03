package top.yhl.cloud.log.anno;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import top.yhl.cloud.log.aop.LogRecordConfigureSelector;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogRecordConfigureSelector.class)
public @interface EnableLogRecord {

    String tenant();

    AdviceMode mode() default AdviceMode.PROXY;
}