package top.yhl.cloud.common.ed;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.yhl.cloud.common.entity.ApiResponseBody;

@EnableConfigurationProperties(EncryptProperties.class)
@ControllerAdvice
public class EncryptResponse implements ResponseBodyAdvice<ApiResponseBody> {
    private ObjectMapper om = new ObjectMapper();
    @Autowired
    EncryptProperties encryptProperties;


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public ApiResponseBody beforeBodyWrite(ApiResponseBody apiResponseBody, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        byte[] keyBytes = encryptProperties.getKey().getBytes();
        try {
            if (apiResponseBody.getMsg() != null) {
                apiResponseBody.setMsg(AESUtil.encrypt(apiResponseBody.getMsg().getBytes(), keyBytes));
            }
            if (apiResponseBody.getData() != null) {
                apiResponseBody.setData(AESUtil.encrypt(om.writeValueAsBytes(apiResponseBody.getData()), keyBytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponseBody;
    }
}
