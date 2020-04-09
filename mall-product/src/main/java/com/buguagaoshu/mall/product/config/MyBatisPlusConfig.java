package com.buguagaoshu.mall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-09 23:00
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.buguagaoshu.mall.product.dao")
public class MyBatisPlusConfig {
    /**
     * 引入分页插件
     * */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setOverflow(true);
        // 每页最大 100 条
        paginationInterceptor.setLimit(100);
        return paginationInterceptor;
    }
}
