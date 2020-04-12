package com.buguagaoshu.mall.warehouse.feign;

import com.buguagaoshu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-12 19:29
 * 远程调用商品服务
 */
@FeignClient("mall-product")
public interface ProductFeignService {

    /**
     * 查找商品信息
     * @param skuId 商品ID
     * @return 远程服务器的运行结果
     * */
    @RequestMapping("/product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);
}
