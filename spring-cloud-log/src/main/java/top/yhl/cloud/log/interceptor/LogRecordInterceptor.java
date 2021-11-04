package top.yhl.cloud.log.interceptor;

import top.yhl.cloud.log.aspect.LogRecordOperationSource;
import top.yhl.cloud.log.service.IFunctionService;

public class LogRecordInterceptor {
    private LogRecordOperationSource logRecordOperationSource;

    private String tenant;

    private IFunctionService functionService;

    public void setLogRecordOperationSource(LogRecordOperationSource logRecordOperationSource) {
        this.logRecordOperationSource = logRecordOperationSource;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public void setFunctionService(IFunctionService functionService) {
        this.functionService = functionService;
    }
}
