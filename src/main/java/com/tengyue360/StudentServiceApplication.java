package com.tengyue360;

import com.gexin.fastjson.support.config.FastJsonConfig;
import com.gexin.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.tengyue360.utils.JsonRpcUtils;
import com.tengyue360.web.requestModel.LoginRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

//@ImportResource(locations = { "classpath:quartz/applicationContext.xml" })
@SpringBootApplication(scanBasePackages = "com.tengyue360")
@EnableDiscoveryClient
@MapperScan("com.tengyue360.dao")
@RestController
@ServletComponentScan
public class StudentServiceApplication {


    public static void main(String[] args) throws Exception {

        SpringApplication.run(StudentServiceApplication.class, args);

        //默认访问路径 封装http  缓存11
        String rwesult = JsonRpcUtils.sendPost("http://127.0.0.1:8087/start", null);

        System.out.println("结果："+rwesult);
    }

//
//    /**
//     * 使用fastjson做为json的解析器
//     * @return
//     */
//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        //	fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        HttpMessageConverter<?> converter = fastConverter;
//        return new HttpMessageConverters(converter);
//    }



}
