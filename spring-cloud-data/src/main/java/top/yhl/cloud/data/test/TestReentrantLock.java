package top.yhl.cloud.data.test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {
    /**
     * ReentrantLock的构造方法
     * public ReentrantLock(boolean fair) {sync = fair ? new FairSync() : new NonfairSync();}
     */
    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unFairLock = new ReentrantLock2(false);

    public static void testFair() throws InterruptedException {
        testLock(fairLock); //测试公平锁
    }

    public static void testUnFair() throws InterruptedException {
        testLock(unFairLock); //测试非公平锁
    }

    private static void testLock(Lock lock) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Job(lock)) {
                @Override
                public String toString() {
                    return getName();
                }
            };
            thread.setName(i + "");
            thread.start();
        }
        Thread.sleep(0);
    }

    private static class Job extends Thread {
        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            //两次打印当前线程和等待队列中的Threads
            for (int i = 0; i < 2; i++) {
                lock.lock(); //获取锁
                try {
                    Thread.sleep(1000);
                    System.out.println("当前线程=>" + Thread.currentThread().getName() + " " +
                            "等待队列中的线程=>" + ((ReentrantLock2) lock).getQueuedThreads());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock(); //释放锁
                }
            }
        }

    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        @Override
        public Collection<Thread> getQueuedThreads() { //逆序打印等待队列中的线程
            List<Thread> list = new ArrayList<Thread>(super.getQueuedThreads());
            Collections.reverse(list);
            return list;
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        testFair();
    testUnFair();
    }


}
