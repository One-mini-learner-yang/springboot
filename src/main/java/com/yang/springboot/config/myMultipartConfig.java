package com.yang.springboot.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class myMultipartConfig {
    /**
     * 或者在配置文件中配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory multipartConfigFactory=new MultipartConfigFactory();
        multipartConfigFactory.setMaxFileSize(DataSize.parse("50MB"));
        multipartConfigFactory.setMaxRequestSize(DataSize.parse("50MB"));
        return multipartConfigFactory.createMultipartConfig();
    }
}
