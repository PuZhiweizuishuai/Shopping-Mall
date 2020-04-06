package com.buguagaoshu.mall.product;

import com.buguagaoshu.mall.product.entity.BrandEntity;
import com.buguagaoshu.mall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MallProductApplicationTests {
    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(1L);
        brandEntity.setDescript("毛金明牛逼");
        brandService.updateById(brandEntity);

        System.out.println(brandService.getById(1));

    }

}
