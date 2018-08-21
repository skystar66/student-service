package com.tengyue360.web.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器
 *
 * @author xuliang
 * @date 2018/8/14 上午11:22
 */

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new HandlerIntercepter()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }




}
