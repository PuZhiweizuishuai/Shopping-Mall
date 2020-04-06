package com.buguagaoshu.mall.member.feign;

import com.buguagaoshu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-06 21:09
 * 调用 mall-coupon 的服务
 */
@FeignClient("mall-coupon")
public interface CouponFeignService {
    /**
     * 先去注册中心找到 mall-coupon 服务
     * 然后调用远程服务的 memberCoupons 方法
     * */
    @GetMapping("/coupon/coupon/member/list")
    R memberCoupons();
}
