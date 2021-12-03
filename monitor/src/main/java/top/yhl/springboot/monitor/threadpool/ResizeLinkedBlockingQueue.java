package top.yhl.springboot.monitor.threadpool;

import java.util.concurrent.LinkedBlockingQueue;

public class ResizeLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {

    private final int capacity;

    public ResizeLinkedBlockingQueue(int queueCapacity) {
        this.capacity = queueCapacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
