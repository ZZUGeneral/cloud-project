package top.yhl.cloud.common.util;

import java.util.concurrent.*;

public class ThreadPoolUtil {
    private static ThreadPoolExecutor threadPool;

    public static ThreadPoolExecutor getThreadPool() {
        if (threadPool != null) {
            return threadPool;
        } else {
            synchronized (ThreadPoolUtil.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(8, 16, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(32), new ThreadPoolExecutor.CallerRunsPolicy());
                }
                return threadPool;
            }
        }
    }

    public static void shutdown() {
        getThreadPool().shutdown();
    }

    public static void execute(Runnable runable) {
        getThreadPool().execute(runable);
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        return getThreadPool().submit(callable);
    }
}
