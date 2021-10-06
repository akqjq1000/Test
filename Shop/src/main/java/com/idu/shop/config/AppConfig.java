package com.idu.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 외부 경로 매핑
        registry.addResourceHandler("/upload/**")// 프로젝트 내부에서 사용할 주소
                .addResourceLocations("file:///d:/Shop/files/");// 실제 존재하는 파일 경로
    }
}
