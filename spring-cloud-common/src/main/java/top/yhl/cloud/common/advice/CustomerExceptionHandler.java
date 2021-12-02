package top.yhl.cloud.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.yhl.cloud.common.entity.ApiResponseBody;

import javax.security.auth.message.AuthException;

/**
 * @author yang_hl3
 */
@RestControllerAdvice
@Slf4j
public class CustomerExceptionHandler {
    @ExceptionHandler(AuthException.class)
    public String errorHandler(AuthException e) {
        log.error("没有通过权限验证！", e);
        return "没有通过权限验证！";
    }

    @ExceptionHandler(Exception.class)
    public ApiResponseBody execption(Exception e) {
        log.error("未知异常！", e);
        return ApiResponseBody.failed(404, e.getMessage());
    }
}
