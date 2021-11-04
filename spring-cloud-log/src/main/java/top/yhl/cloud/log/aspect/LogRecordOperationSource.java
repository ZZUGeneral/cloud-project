package top.yhl.cloud.log.aspect;

import top.yhl.cloud.log.anno.LogRecordAnnotaion;
import top.yhl.cloud.log.entity.LogRecordOps;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class LogRecordOperationSource {
    private Collection<LogRecordOps> operations;

    public LogRecordOperationSource() {
        operations = new ArrayList<>();
    }

    public Collection<LogRecordOps> computeLogRecordOperations(Method method, Class<?> targetClass) {
        if (method.getAnnotation(LogRecordAnnotaion.class) != null) {
            LogRecordAnnotaion anno = method.getAnnotation(LogRecordAnnotaion.class);
            LogRecordOps op = new LogRecordOps();
            op.setBizNo(anno.bizNo());
            op.setCategory(anno.category());
            op.setOperator(anno.operator());
            op.setCondition(anno.condition());
            op.setDetail(anno.detail());
            op.setSuccess(anno.success());
            op.setFail(anno.fail());
            operations.add(op);
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
        return null;
    }
}
