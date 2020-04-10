package com.buguagaoshu.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.mall.product.entity.BrandEntity;
import com.buguagaoshu.mall.product.entity.CategoryBrandRelationEntity;
import com.buguagaoshu.mall.product.entity.CategoryEntity;

import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 13:07:09
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    /**
     * 分页查询
     * @param params 查询参数
     * @return 分页后的结果
     * */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存关联的详细信息
     * @param categoryBrandRelation 关联关系
     * */
    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    /**
     * 更新所有与该品牌关联的名字
     * @param brand 品牌
     * */
    void updateBrandName(BrandEntity brand);


    /**
     * 更新所有与该分类关联的名字
     * @param categoryEntity 分类
     * */
    void updateCatelogName(CategoryEntity categoryEntity);
}

