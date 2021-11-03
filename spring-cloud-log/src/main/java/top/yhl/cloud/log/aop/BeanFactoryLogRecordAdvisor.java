package top.yhl.cloud.log.aop;

import top.yhl.cloud.log.interceptor.LogRecordInterceptor;

public class BeanFactoryLogRecordAdvisor {
    private LogRecordOperationSource logRecordOperationSource;

    private LogRecordInterceptor logRecordInterceptor;

    public void setLogRecordOperationSource(LogRecordOperationSource logRecordOperationSource) {
        this.logRecordOperationSource = logRecordOperationSource;
    }

    public void setAdvice(LogRecordInterceptor logRecordInterceptor) {
        this.logRecordInterceptor = logRecordInterceptor;
    }
}
