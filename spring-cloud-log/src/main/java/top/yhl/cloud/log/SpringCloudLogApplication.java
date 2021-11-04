package top.yhl.cloud.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import top.yhl.cloud.log.anno.EnableLogRecord;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableTransactionManagement
@EnableLogRecord(tenant = "com.mzt.test")
public class SpringCloudLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudLogApplication.class, args);
    }

}
