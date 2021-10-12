package top.yhl.cloud.data.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestCR {
    Lock lock = new ReentrantLock();

    void m1() {
        try {
            lock.lock(); // 加锁
            for (int i = 0; i < 4; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("m1() method " + i);
            }
            m2(); //在释放锁之前，调用m2方法
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); // 解锁
        }
    }

    void m2() {
        lock.lock();
        System.out.println("m2() method");
        lock.unlock();
    }

    public static void main(String[] args) {
        final TestCR t = new TestCR();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m2();
            }
        }).start();
    }
}
