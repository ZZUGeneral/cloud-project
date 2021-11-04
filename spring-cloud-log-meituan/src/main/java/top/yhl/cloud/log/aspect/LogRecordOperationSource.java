package top.yhl.cloud.log.aspect;

import top.yhl.cloud.log.anno.LogRecordAnnotaion;
import top.yhl.cloud.log.entity.LogRecordOps;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;

public class LogRecordOperationSource {
    private Collection<LogRecordOps> operations;

    public LogRecordOperationSource() {
        operations = new ArrayList<>();
    }

    public Collection<LogRecordOps> computeLogRecordOperations(Method method, Class<?> targetClass) {
        if (method.getAnnotation(LogRecordAnnotaion.class) == null) {
            return null;
        }
        LogRecordAnnotaion anno = method.getAnnotation(LogRecordAnnotaion.class);
        LogRecordOps op = new LogRecordOps();
        String template = anno.success();
        // 提取出 spel 表达式

        op.setParameters(method.getParameters());
        operations.add(op);
        Parameter[] param = method.getParameters();
        return operations;

//            String realOperatorId = "";
//            if (StringUtils.isEmpty(operatorId)) {
//                if (operatorGetService.getUser() == null || StringUtils.isEmpty(operatorGetService.getUser().getOperatorId())) {
//                    throw new IllegalArgumentException("user is null");
//                }
//                realOperatorId = operatorGetService.getUser().getOperatorId();
//            } else {
//                spElTemplates = Lists.newArrayList(bizKey, bizNo, action, operatorId, detail);
//            }


    }
}
