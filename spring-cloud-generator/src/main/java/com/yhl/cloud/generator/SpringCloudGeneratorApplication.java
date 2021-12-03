package com.yhl.cloud.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.yhl.cloud.generator.mapper")
public class SpringCloudGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGeneratorApplication.class, args);
    }

}
