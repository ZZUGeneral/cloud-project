package top.yhl.cloud.log.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import top.yhl.cloud.log.anno.LogRecordAnnotaion;
import top.yhl.cloud.log.service.IFunctionService;

import java.lang.reflect.Method;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogRecordValueParser extends SpelExpressionParser {
    private static LogRecordExpressionEvaluator expressionEvaluator;
    private IFunctionService functionService;

    public Expression parse(Method method, Object[] args, Class targetClass, Object ret, String errorMsg, BeanFactory beanFactory) {
        EvaluationContext evaluationContext = expressionEvaluator.createEvaluationContext(method, args, targetClass, ret, errorMsg, beanFactory);
        if (method.getAnnotation(LogRecordAnnotaion.class) == null) {
            throw new NullPointerException();
        }
        String success = method.getAnnotation(LogRecordAnnotaion.class).success();
        SpelExpression expression = doParseExpression(success, new TemplateParserContext());
        return expression;
    }

}
