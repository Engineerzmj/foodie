package com.zmj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    public CorsConfig() {
    }

    @Bean
    public CorsFilter corsFilter() {
        // 1. 配置cors信息
        CorsConfiguration config = new CorsConfiguration();
        // 添加允许的跨域路径
        config.addAllowedOrigin("http://localhost:8080");
        // 添加允许的请求方法
        config.addAllowedMethod("*");
        // 添加允许发送的Header信息
        config.addAllowedHeader("*");
        // 设置是否发送cookie信息
        config.setAllowCredentials(true);

        // 2. 为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", config);

        // 3. 返回重新定义好的corsSource
        return new CorsFilter(corsSource);
    }
}
