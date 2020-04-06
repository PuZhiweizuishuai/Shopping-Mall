package com.buguagaoshu.mall.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Pu Zhiwei
 * 不挂高数商城仓储服务
 * */
@SpringBootApplication
@EnableDiscoveryClient
public class MallWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallWarehouseApplication.class, args);
    }

}
