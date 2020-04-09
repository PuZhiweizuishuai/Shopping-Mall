package com.buguagaoshu.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.mall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 13:07:09
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 返回首页商品分类的三级结构
     * @return 返回首页商品分类的三级结构
     * */
    List<CategoryEntity> listWithTree();

    /**
     * 删除没有子节点的商品分类
     * @param asList 传入商品分类的 Id 集合
     * */
    void removeMenuByIds(List<Long> asList);

    /**
     * 保存前检查数据是否合法
     * @param category 分类
     * @return 合法性
     * */
    boolean saveAndCheck(CategoryEntity category);


    /**
     * 查找当前 catId 的所属路径
     * @param catId 分类ID
     * @return 完整路径
     * */
    Long[] findCatelogPath(long catId);
}

