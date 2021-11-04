package top.yhl.cloud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import top.yhl.cloud.common.SpringCloudCommonApplication;

@SpringBootTest(classes = SpringCloudCommonApplication.class)
class SpringCloudCommon2ApplicationTests {

    @Test
    void contextLoads() {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("#request.getDeliveryOrderNo()");
        System.out.println(expression.getExpressionString());
    }

}
