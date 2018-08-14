package com.tengyue360;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.tengyue360.dao")
public class StudentServiceApplication {



    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApplication.class, args);
    }


}
