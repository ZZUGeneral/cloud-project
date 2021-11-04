package top.yhl.cloud.log.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import top.yhl.cloud.log.service.IFunctionService;

import java.lang.reflect.Method;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogRecordValueParser extends SpelExpressionParser {
    private static LogRecordExpressionEvaluator expressionEvaluator;
    private IFunctionService functionService;

    public EvaluationContext parse(Method method, Object[] args, Class targetClass, Object ret, String errorMsg, BeanFactory beanFactory) {
        EvaluationContext evaluationContext = expressionEvaluator.createEvaluationContext(method, args, targetClass, ret, errorMsg, beanFactory);
        return evaluationContext;
    }

}
