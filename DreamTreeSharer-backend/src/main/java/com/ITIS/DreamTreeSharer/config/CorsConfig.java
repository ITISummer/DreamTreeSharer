package com.ITIS.DreamTreeSharer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CorsConfig
 * @Author LCX
 * @Date 2021 2021-05-17 4:17 p.m.
 * @Version 1.0
 **/
public class CorsConfig {


    /**
     * 跨域配置
     * [Springboot 2.4.0跨域配置无效及接口访问报错（解决方法）allowedOrigins cannot contain the special value "*"]
     * (https://www.cnblogs.com/technicist/p/14466665.html)
     *
     * [SpringBoot 优雅解决 ajax 跨域请求](https://juejin.cn/post/6844903954015322126)
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 是否允许请求带有验证信息
        config.setAllowCredentials(true);

        // 允许访问的客户端域名
        // (springboot2.4以上的加入这一段可解决 allowedOrigins cannot contain the special value "*"问题)
        List<String> allowedOriginPatterns = new ArrayList<>();
        allowedOriginPatterns.add("*");
        config.setAllowedOriginPatterns(allowedOriginPatterns);

        // 设置访问源地址
        // config.addAllowedOrigin("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
