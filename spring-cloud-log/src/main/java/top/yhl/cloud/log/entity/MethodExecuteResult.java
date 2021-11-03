package top.yhl.cloud.log.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodExecuteResult {
    private boolean success;
    public Throwable throwable;
    private String errorMsg;
}

