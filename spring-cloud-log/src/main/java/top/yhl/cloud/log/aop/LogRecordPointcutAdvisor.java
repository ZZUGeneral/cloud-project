package top.yhl.cloud.log.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.util.CollectionUtils;
import top.yhl.cloud.log.entity.LogRecordContext;
import top.yhl.cloud.log.entity.LogRecordOps;
import top.yhl.cloud.log.entity.MethodExecuteResult;

import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class LogRecordPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor implements MethodInterceptor {
    private LogRecordOperationSource logRecordOperationSource;

    public void setLogRecordOperationSource(LogRecordOperationSource logRecordOperationSource) {
        this.logRecordOperationSource = logRecordOperationSource;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        // 记录日志
        return execute(methodInvocation, methodInvocation.getThis(), method, methodInvocation.getArguments());
    }

    private Object execute(MethodInvocation invoker, Object target, Method method, Object[] args) throws Throwable {
        Class<?> targetClass = getTargetClass(target);
        Object ret = null;
        MethodExecuteResult methodExecuteResult = new MethodExecuteResult();
        LogRecordContext.putEmptySpan();
        Collection<LogRecordOps> operations = new ArrayList<>();
        Map<String, String> functionNameAndReturnMap = new HashMap<>();
        try {
            operations = logRecordOperationSource.computeLogRecordOperations(method, targetClass);
            List<String> spElTemplates = getBeforeExecuteFunctionTemplate(operations);
            //业务逻辑执行前的自定义函数解析
            functionNameAndReturnMap = processBeforeExecuteFunctionTemplate(spElTemplates, targetClass, method, args);
        } catch (Exception e) {
            log.error("log record parse before function exception", e);
        }
        try {
            ret = invoker.proceed();
        } catch (Exception e) {
            methodExecuteResult = new MethodExecuteResult(false, e, e.getMessage());
        }
        try {
            if (!CollectionUtils.isEmpty(operations)) {
                recordExecute(ret, method, args, operations, targetClass,
                        methodExecuteResult.isSuccess(), methodExecuteResult.getErrorMsg(), functionNameAndReturnMap);
            }
        } catch (Exception t) {
            //记录日志错误不要影响业务
            log.error("log record parse exception", t);
        } finally {
            LogRecordContext.clear();
        }
        if (methodExecuteResult.throwable != null) {
            throw methodExecuteResult.throwable;
        }

        return ret;
    }

    private void recordExecute(Object ret, Method method, Object[] args, Collection<LogRecordOps> operations, Class<?> targetClass, Object success, Object errorMsg, Map<String, String> functionNameAndReturnMap) {
    }

    private Map<String, String> processBeforeExecuteFunctionTemplate(List<String> spElTemplates, Class<?> targetClass, Method method, Object[] args) {
    }

    private List<String> getBeforeExecuteFunctionTemplate(Collection<LogRecordOps> operations) {

    }

    private Class<?> getTargetClass(Object target) {
        return target.getClass();
    }

    @Override
    public Pointcut getPointcut() {
        return null;
    }
}
