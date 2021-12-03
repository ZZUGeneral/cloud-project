package top.yhl.cloud.log.service.impl;

import lombok.extern.slf4j.Slf4j;
import top.yhl.cloud.log.service.ILogRecordService;

import java.util.logging.LogRecord;

@Slf4j
public class DefaultLogRecordServiceImpl implements ILogRecordService {

    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void record(LogRecord logRecord) {
        log.info("【logRecord】log={}", logRecord);
    }
}
