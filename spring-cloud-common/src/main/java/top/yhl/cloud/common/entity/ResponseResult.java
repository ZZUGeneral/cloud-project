package top.yhl.cloud.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yang_hl3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {
    //  "HTTP 响应码"
    private Integer code;

    // 提示信息
    private String msg;

    // 返回数据
    private T data;

    public static ResponseResult success() {
        return success(null);
    }

    public static <T> ResponseResult success(T data) {
        return new ResponseResult(200, null, data);
    }

    public static ResponseResult failed(Integer code, String messsage) {
        return new ResponseResult(code, messsage, null);
    }
}
