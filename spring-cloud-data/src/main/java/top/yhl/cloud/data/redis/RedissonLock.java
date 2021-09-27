package top.yhl.cloud.data.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonLock {
    public static void main(String[] args) {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        config.useSingleServer().setPassword("redis1234");

        final RedissonClient client = Redisson.create(config);
        RLock lock = client.getLock("lock1");

        try {
            lock.lock();
        } finally {
            lock.unlock();
        }
    }
}
