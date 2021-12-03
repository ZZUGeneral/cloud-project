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
public class ApiResponseBody<T> {
    //  "HTTP 响应码"
    private Integer code;

    // 提示信息
    private String msg;

    // 返回数据
    private T data;

    // 全局调用链 id
    private String txId;

    public static ApiResponseBody defaultSuccess() {
        return success(null);
    }

    public static <T> ApiResponseBody success(T data) {
        return createApiResponseBody(200, "请求成功", data, null);
    }

    public static ApiResponseBody failed(Integer code, String messsage) {
        return createApiResponseBody(code, messsage, null, null);
    }

    public static <T> ApiResponseBody<T> createApiResponseBody(int code, String messsage, T data, String txId) {
        ApiResponseBody apiResponseBody = new ApiResponseBody();
        apiResponseBody.setCode(code);
        apiResponseBody.setMsg(messsage);
        apiResponseBody.setData(data);
        apiResponseBody.setTxId(txId);
        return apiResponseBody;
    }
}
