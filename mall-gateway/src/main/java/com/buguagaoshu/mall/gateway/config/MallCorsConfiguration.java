package com.buguagaoshu.mall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-07 20:41
 * 跨域配置
 */
@Configuration
public class MallCorsConfiguration {
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 配置跨域头
        corsConfiguration.addAllowedHeader("*");

        // 允许跨域请求的方式
        corsConfiguration.addAllowedMethod("*");

        // 允许请求的来源
        corsConfiguration.addAllowedOrigin("*");

        // 允许携带Cookies跨域
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource basedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        basedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(basedCorsConfigurationSource);
    }
}
