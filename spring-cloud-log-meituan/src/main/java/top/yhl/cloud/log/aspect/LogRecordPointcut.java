package top.yhl.cloud.log.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author yang_hl3
 */
@Aspect
public class LogRecordPointcut extends StaticMethodMatcherPointcut implements Serializable {
    // LogRecord 的解析类
    private LogRecordOperationSource logRecordOperationSource;

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        // 解析 这个 method 上有没有 @LogRecordAnnotation 注解，有的话会解析出来注解上的各个参数
        return !CollectionUtils.isEmpty(logRecordOperationSource.computeLogRecordOperations(method, targetClass));
    }

    public void setLogRecordOperationSource(LogRecordOperationSource logRecordOperationSource) {
        this.logRecordOperationSource = logRecordOperationSource;
    }
}
