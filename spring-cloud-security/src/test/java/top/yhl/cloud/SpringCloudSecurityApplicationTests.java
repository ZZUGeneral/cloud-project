package top.yhl.cloud;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = SpringCloudSecurityApplicationTests.class)
class SpringCloudSecurityApplicationTests {
    @Test
    public void testsestet(){
        // $2a$10$nTO5sl7DpQqPvDCYOBhL5eor3zvbdh3QCar6YUXR3Cdlw98JO8TQK
        //$2a$10$vfgbQzXQLi81u.uM6BfbD.DvsEVhVPOEco6nzUjYe5bRis9kGHt7y
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("$2a$10$vfgbQzXQLi81u.uM6BfbD.DvsEVhVPOEco6nzUjYe5bRis9kGHt7y");
        System.out.println(pwd);
    }

    @Test
    void contextLoads() {
        List<Map<String, String>> list = getAllUrl();
        for (Map<String, String> map : list) {
            System.out.println(map);
        }
    }

    @Autowired
    WebApplicationContext applicationContext;

    public List<Map<String, String>> getAllUrl() {
        List<Map<String, String>> resList = new ArrayList<>();
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> mappingInfoHandlerMethodEntry : map.entrySet()) {
            Map<String, String> resultMap = new LinkedHashMap<>();
            RequestMappingInfo requestMappingInfo = mappingInfoHandlerMethodEntry.getKey();
            HandlerMethod handlerMethod = mappingInfoHandlerMethodEntry.getValue();
            resultMap.put("className", handlerMethod.getMethod().getDeclaringClass().getName());
            Annotation[] parentAnnotations = handlerMethod.getBeanType().getAnnotations();
            for (Annotation annotation : parentAnnotations) {
                if (annotation instanceof Api) {
                    Api api = (Api) annotation;
                    resultMap.put("classDesc", api.value());
                } else if (annotation instanceof RequestMapping) {
                    RequestMapping requestMapping = (RequestMapping) annotation;
                    if (null != requestMapping.value() && requestMapping.value().length > 0) {
                        resultMap.put("classURL", requestMapping.value()[0]);
                    }
                }
            }
            resultMap.put("methodName", handlerMethod.getMethod().getName());
            Annotation[] annotations = handlerMethod.getMethod().getDeclaredAnnotations();
            if (annotations != null) {
                for (Annotation annotation : annotations) {
                    if (annotation instanceof ApiOperation) {
                        ApiOperation methodDesc = (ApiOperation) annotation;
                        String desc = methodDesc.value();
                        resultMap.put("methodDesc", desc);
                    }
                }
            }
            PatternsRequestCondition p = requestMappingInfo.getPatternsCondition();
            for (String url : p.getPatterns()) {
                resultMap.put("methodURL", url);
            }
            RequestMethodsRequestCondition methodsRequestCondition = requestMappingInfo.getMethodsCondition();
            for (RequestMethod requestMethod : methodsRequestCondition.getMethods()) {
                resultMap.put("requestType", requestMethod.toString());
            }
            resList.add(resultMap);
        }
        return resList;
    }
}
