package com.buguagaoshu.mall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Pu Zhiwei
 * 不挂高数商城会员服务
 * */
@EnableFeignClients(basePackages = "com.buguagaoshu.mall.member.feign")
@SpringBootApplication
@EnableDiscoveryClient
public class MallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallMemberApplication.class, args);
    }

}
