package com.buguagaoshu.mall.product.feign;

import com.buguagaoshu.common.to.SkuReductionTo;
import com.buguagaoshu.common.to.SpuBoundTo;
import com.buguagaoshu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-11 17:40
 * 远程调用优惠券服务
 */
@FeignClient(name = "mall-coupon")
public interface CouponFeignService {
    /**
     * 调用优惠服务保存积分信息
     * @param spuBoundTo 封装好的积分对象
     * @return 调用结果
     * */
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);


    /**
     * 调用远程服务保存商品满减和折扣信息
     * @param skuReductionTo 封装好的积分对象
     * @return 调用结果
     * */
    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
