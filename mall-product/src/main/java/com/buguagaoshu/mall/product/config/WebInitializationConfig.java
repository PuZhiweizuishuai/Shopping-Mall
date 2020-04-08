package com.buguagaoshu.mall.product.config;

import com.buguagaoshu.mall.product.cache.CategoryListCache;
import com.buguagaoshu.mall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-08 18:30
 */
@Configuration
@Slf4j
public class WebInitializationConfig {
    @Bean
    public CommandLineRunner dataLoader(CategoryListCache categoryListCache, CategoryService categoryService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // 初始化三级分类树
                categoryListCache.setMenusTree(categoryService.listWithTree());
                log.info("初始化商品三级分类树");
            }
        };
    }
}
