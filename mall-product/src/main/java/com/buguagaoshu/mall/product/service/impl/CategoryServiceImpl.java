package com.buguagaoshu.mall.product.service.impl;

import com.buguagaoshu.mall.product.cache.CategoryListCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.common.utils.Query;

import com.buguagaoshu.mall.product.dao.CategoryDao;
import com.buguagaoshu.mall.product.entity.CategoryEntity;
import com.buguagaoshu.mall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    private final CategoryListCache categoryListCache;

    @Autowired
    public CategoryServiceImpl(CategoryListCache categoryListCache) {
        this.categoryListCache = categoryListCache;
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        // 组装成父子的树形结构
        List<CategoryEntity> level1Menus =
                entities.stream().filter((categoryEntity -> categoryEntity.getParentCid() == 0))
                        .peek((menu) -> menu.setChildren(getChildren(menu, entities)))
                        .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                        .collect(Collectors.toList());

        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        // TODO 检查删除的菜单是否被别的地方引用
        // 批量删除
        baseMapper.deleteBatchIds(asList);
        // 更新缓存
        categoryListCache.setMenusTree(listWithTree());
    }

    @Override
    public boolean saveAndCheck(CategoryEntity category) {
        if (category == null) {
            return false;
        }
        if (category.getName() == null || category.getName().equals("")) {
            return false;
        }
        if (category.getParentCid() == null || category.getParentCid() < 0) {
            return false;
        }
        if (category.getCatLevel() == null || category.getCatLevel() < 1 || category.getCatLevel() > 3) {
            return false;
        }
        baseMapper.insert(category);
        // 更新缓存
        categoryListCache.setMenusTree(listWithTree());
        return true;
    }


    /**
     * 获取某一菜单的子菜单
     */
    private List<CategoryEntity> getChildren(CategoryEntity root,
                                             List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream().filter((categoryEntity) -> categoryEntity.getParentCid().equals(root.getCatId()))
                .peek((categoryEntity) -> {
                    // 查找子菜单
                    categoryEntity.setChildren(getChildren(categoryEntity, all));
                })
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
        return children;
    }

}