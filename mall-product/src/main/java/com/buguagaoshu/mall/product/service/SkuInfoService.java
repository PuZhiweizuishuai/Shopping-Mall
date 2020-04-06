package com.buguagaoshu.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.mall.product.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku信息
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 13:07:09
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

