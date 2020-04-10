package com.buguagaoshu.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.mall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 13:07:09
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 更新数据的同时更新冗余表的细节
     * 保证冗余字段的一致性
     * @param brand 品牌
     * */
    void updateDetail(BrandEntity brand);
}

