package top.yhl.cloud.log.aspectj;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import top.yhl.cloud.log.constants.LogConstants;
import top.yhl.cloud.log.entity.EnvProperties;
import top.yhl.cloud.log.entity.LogApiReq;
import top.yhl.cloud.log.entity.ServerInfo;
import top.yhl.cloud.log.service.LogService;
import top.yhl.cloud.log.util.JsonTool;
import top.yhl.cloud.log.util.LogTool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yang_hl3
 */
@Aspect
@Component
@Slf4j
public class LogRecordAspect {

    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    @Autowired
    LogService logService;

    private ObjectMapper objectMapper;

    private EnvProperties envProperties;
    private ServerInfo serverInfo;

    // 设置切点
    @Pointcut("(@within(org.springframework.web.bind.annotation.RestController)) ||" + "(@within(org.springframework.stereotype.Controller))" + " && !@annotation(top.yhl.cloud.log.anno.LogExclude)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object aroundMethod(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return point.proceed();
        }
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Object[] args = point.getArgs();
        // 参数处理
        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            // 获取方法参数
            MethodParameter methodParameter = getMethodParameter(method, i);

            // PathVariable 变量
            PathVariable pathVariable = methodParameter.getParameterAnnotation(PathVariable.class);
            if (pathVariable != null) {
                continue;
            }

            RequestBody requestBody = methodParameter.getParameterAnnotation(RequestBody.class);
            String parameterName = methodParameter.getParameterName();
            Object value = args[i];
            // body 是 json格式的对象
            if (requestBody != null) {
                if (value == null) {
                    paramMap.put(parameterName, null);
                } else if (ClassUtils.isPrimitiveOrWrapper(value.getClass())) {
                    paramMap.put(parameterName, value);
                } else {
                    try {
                        paramMap.putAll(BeanMap.create(value));
                    } catch (Exception e) {

                    }
                }
                continue;
            }
            if (value instanceof HttpServletRequest) {
                paramMap.putAll(((HttpServletRequest) value).getParameterMap());
                continue;
            } else if (value instanceof WebRequest) {
                paramMap.putAll(((WebRequest) value).getParameterMap());
                continue;
            } else if (value instanceof HttpServletResponse) {
                continue;
            } else if (value instanceof MultipartFile) {
                MultipartFile multipartFile = (MultipartFile) value;
                String name = multipartFile.getName();
                String fileName = multipartFile.getOriginalFilename();
                paramMap.put(name, fileName);
                continue;
            }

            // 参数名
            RequestParam requestParam = methodParameter.getParameterAnnotation(RequestParam.class);
            String param = parameterName;
            if (requestParam != null && StringUtils.hasText(requestParam.value())) {
                parameterName = requestParam.value();
            }
            if (value == null) {
                paramMap.put(parameterName, null);
            } else if (ClassUtils.isPrimitiveOrWrapper(value.getClass())) {
                paramMap.put(parameterName, value);
            } else if (value instanceof InputStream) {
                paramMap.put(parameterName, "InputStream");
            } else if (value instanceof InputStreamSource) {
                paramMap.put(parameterName, "InputStreamSource");
            } else if (canJsonSerialize(value)) {
                paramMap.put(parameterName, value);
            } else {
                paramMap.put(parameterName, "【注意】JSON不能序列化");
            }
        }
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();

        LogApiReq logApiReq = new LogApiReq();
        LogTool.addRequestInfoToLog(request, logApiReq);
        LogTool.addOtherInfoToLog(logApiReq, envProperties, serverInfo);
        logApiReq.setLogType(LogConstants.API_LOG);
        logApiReq.setTitle(getMethodOperation(method));
        logApiReq.setMethodClass(className);
        logApiReq.setMethodName(methodName);
        logApiReq.setParams(JsonTool.toJson(paramMap));


        Long startNS = System.nanoTime();
        try {
            Object result = point.proceed();
            return result;
        } finally {
            long tookMS = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNS);
            logApiReq.setTime(tookMS);
            log.info(JsonTool.toJson(logApiReq));
        }


    }

    private String getMethodOperation(Method method) {
        if (method == null) {
            return null;
        }
        Annotation[] annotations = method.getAnnotations();
        if (annotations == null || annotations.length == 0) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if (annotation != null) {
                if (annotation instanceof ApiOperation) {
                    return ((ApiOperation) annotation).value();
                }
            }
        }
        return null;
    }


    private boolean canJsonSerialize(Object value) {
        try {
            objectMapper.writeValueAsBytes(value);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private MethodParameter getMethodParameter(Method method, int index) {
        MethodParameter methodParameter = new SynthesizingMethodParameter(method, index);
        methodParameter.initParameterNameDiscovery(PARAMETER_NAME_DISCOVERER);
        return methodParameter;
    }

    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (requestAttributes == null) ? null : ((ServletRequestAttributes) requestAttributes).getRequest();
    }

}
