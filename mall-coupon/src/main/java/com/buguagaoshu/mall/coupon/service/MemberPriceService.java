package com.buguagaoshu.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.mall.coupon.entity.MemberPriceEntity;

import java.util.Map;

/**
 * 商品会员价格
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 14:34:56
 */
public interface MemberPriceService extends IService<MemberPriceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

