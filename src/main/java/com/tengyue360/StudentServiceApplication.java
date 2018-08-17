package com.tengyue360;

import com.tengyue360.web.requestModel.LoginRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@SpringBootApplication(scanBasePackages = "com.tengyue360")
@EnableDiscoveryClient
@MapperScan("com.tengyue360.dao")
@RestController
@ServletComponentScan
//@ImportResource(locations = { "classpath:druid-bean.xml" })
public class StudentServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApplication.class, args);
    }

}
