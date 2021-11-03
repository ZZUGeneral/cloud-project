package top.yhl.cloud.log.service;

import java.util.logging.LogRecord;

public interface ILogRecordService {
    /**
     * 保存 log
     *
     * @param logRecord 日志实体
     */
    void record(LogRecord logRecord);

}
