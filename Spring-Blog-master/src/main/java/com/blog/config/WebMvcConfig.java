package com.blog.config;

import com.blog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author shoji
 */
@Configuration
@ComponentScan(basePackages = "com.blog")
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * カスタマイズしたintecepterを登録する
     *
     * @param registry intercepter
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }


}

