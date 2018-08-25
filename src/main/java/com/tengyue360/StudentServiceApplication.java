package com.tengyue360;

import com.tengyue360.utils.JsonRpcUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RestController;

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
