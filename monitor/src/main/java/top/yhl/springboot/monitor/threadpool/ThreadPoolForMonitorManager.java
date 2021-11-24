package top.yhl.springboot.monitor.threadpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yang_hl3
 */
@Component
public class ThreadPoolForMonitorManager {

    @Autowired
    ThreadPoolConfigurationProperties poolConfigurationProperties;
    private final ConcurrentHashMap<String, ThreadPoolExecutorForMonitor> threadPoolExecutorForMonitorConcurrentHashMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        poolConfigurationProperties.getExecutor().stream().filter(threadPoolProperties -> !threadPoolExecutorForMonitorConcurrentHashMap.containsKey(threadPoolProperties.getPoolName())).forEach(threadPoolProperties -> {
            ThreadPoolExecutorForMonitor executorForMonitor = new ThreadPoolExecutorForMonitor(
                    threadPoolProperties.getCorePoolSize(),
                    threadPoolProperties.getMaxmumPoolSize(),
                    threadPoolProperties.getKeepAliveTime(),
                    threadPoolProperties.getUnit(),
                    new ResizeLinkedBlockingQueue<>(threadPoolProperties.getQueueCapacity()),
                    threadPoolProperties.getPoolName()
            );
            threadPoolExecutorForMonitorConcurrentHashMap.put(threadPoolProperties.getPoolName(), executorForMonitor);
        });
    }

    public ThreadPoolExecutorForMonitor getThreadPoolExecutor(String poolName) {
        ThreadPoolExecutorForMonitor threadPoolExecutorForMonitor = threadPoolExecutorForMonitorConcurrentHashMap.get(poolName);
        if (threadPoolExecutorForMonitor == null) {
            throw new RuntimeException("找不到名称为" + poolName + "的线程池");
        }
        return threadPoolExecutorForMonitor;
    }

    public ConcurrentHashMap<String, ThreadPoolExecutorForMonitor> getThreadPoolExecutorForMonitorConcurrentHashMap() {
        return threadPoolExecutorForMonitorConcurrentHashMap;
    }

}
