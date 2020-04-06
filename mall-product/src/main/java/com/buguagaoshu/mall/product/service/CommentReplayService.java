package com.buguagaoshu.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.mall.product.entity.CommentReplayEntity;

import java.util.Map;

/**
 * 商品评价回复关系
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 13:07:09
 */
public interface CommentReplayService extends IService<CommentReplayEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

