package top.yhl.cloud.ed;

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
import top.yhl.cloud.entity.ResponseResult;

@EnableConfigurationProperties(EncryptProperties.class)
@ControllerAdvice
public class EncryptResponse implements ResponseBodyAdvice<ResponseResult> {
    private ObjectMapper om = new ObjectMapper();
    @Autowired
    EncryptProperties encryptProperties;


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public ResponseResult beforeBodyWrite(ResponseResult responseResult, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        byte[] keyBytes = encryptProperties.getKey().getBytes();
        try {
            if (responseResult.getMsg() != null) {
                responseResult.setMsg(AESUtil.encrypt(responseResult.getMsg().getBytes(), keyBytes));
            }
            if (responseResult.getData() != null) {
                responseResult.setData(AESUtil.encrypt(om.writeValueAsBytes(responseResult.getData()), keyBytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseResult;
    }
}
