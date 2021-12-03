package top.yhl.cloud.log.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yang_hl3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodExecuteResult {
    private boolean success;
    public Throwable throwable;
    private String errorMsg;
}

