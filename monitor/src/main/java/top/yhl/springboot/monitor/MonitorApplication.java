package top.yhl.springboot.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import top.yhl.springboot.monitor.threadpool.ThreadPoolConfigurationProperties;

@EnableConfigurationProperties(ThreadPoolConfigurationProperties.class)
@SpringBootApplication
public class MonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }

}
