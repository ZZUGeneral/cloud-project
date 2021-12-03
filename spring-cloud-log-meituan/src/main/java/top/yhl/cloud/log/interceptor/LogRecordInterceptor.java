package top.yhl.cloud.log.interceptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yhl.cloud.log.aspect.LogRecordOperationSource;
import top.yhl.cloud.log.service.IFunctionService;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogRecordInterceptor {
    private IFunctionService functionService;
    private String tenant;
    private LogRecordOperationSource logRecordOperationSource;
}
