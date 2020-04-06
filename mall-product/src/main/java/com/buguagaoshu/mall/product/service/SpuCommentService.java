package com.buguagaoshu.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.mall.product.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 13:07:09
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

