package top.yhl.springboot.monitor.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadPoolExecutorForMonitor extends ThreadPoolExecutor {

    public static final RejectedExecutionHandler defaultHandler = new AbortPolicy();
    public static final String defaultPoolName = "Default-Task";
    public static ThreadFactory threadFactory = new MonitorThreadFactory(defaultPoolName);

    public ThreadPoolExecutorForMonitor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, defaultHandler);
    }

    public ThreadPoolExecutorForMonitor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, String poolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new MonitorThreadFactory(poolName), defaultHandler);
    }

    public ThreadPoolExecutorForMonitor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler, String poolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    //最短执行时间
    private long minCostTime;
    //最长执行时间
    private long maxCostTime;
    //总的耗时
    private AtomicLong totalCostTime = new AtomicLong();

    private ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        startTimeThreadLocal.set(System.currentTimeMillis());
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        long costTime = System.currentTimeMillis() - startTimeThreadLocal.get();
        startTimeThreadLocal.remove();
        maxCostTime = Math.max(maxCostTime, costTime);
        if (getCompletedTaskCount() == 0) {
            minCostTime = costTime;
        }
        minCostTime = Math.min(minCostTime, costTime);
        totalCostTime.addAndGet(costTime);
        super.afterExecute(r, t);
    }

    public long getMinCostTime() {
        return minCostTime;
    }

    public long getMaxCostTime() {
        return maxCostTime;
    }

    public long getAverageCostTime() {//平均耗时
        if (getCompletedTaskCount() == 0 || totalCostTime.get() == 0) {
            return 0;
        }
        return totalCostTime.get() / getCompletedTaskCount();
    }

    @Override
    protected void terminated() {
        super.terminated();
    }

    static class MonitorThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        MonitorThreadFactory(String poolName) {
            SecurityManager sm = System.getSecurityManager();
            group = (sm != null) ? sm.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = poolName + "-pool-" + poolNumber.getAndIncrement() + "-thread- ";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
