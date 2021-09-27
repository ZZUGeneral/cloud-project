package top.yhl.cloud.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.yhl.cloud.mapper")
public class SpringCloudDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDataApplication.class, args);
    }

}
