package com.buguagaoshu.mall.product.service.impl;

import com.buguagaoshu.mall.product.cache.CategoryListCache;
import com.buguagaoshu.mall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.common.utils.Query;

import com.buguagaoshu.mall.product.dao.CategoryDao;
import com.buguagaoshu.mall.product.entity.CategoryEntity;
import com.buguagaoshu.mall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


/**
 * @author puzhiwei
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    private final CategoryListCache categoryListCache;

    private final CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    public CategoryServiceImpl(CategoryListCache categoryListCache, CategoryBrandRelationService categoryBrandRelationService) {
        this.categoryListCache = categoryListCache;
        this.categoryBrandRelationService = categoryBrandRelationService;
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

    @Override
    public Long[] findCatelogPath(long catId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catId, paths);
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    @Override
    @Transactional
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (!StringUtils.isEmpty(category.getName())) {
            categoryBrandRelationService.updateCatelogName(category);
            // TODO 更新其它关联
        }
    }

    private List<Long> findParentPath(Long catelogId,List<Long> paths){
        //1、收集当前节点id
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if(byId.getParentCid()!=0){
            findParentPath(byId.getParentCid(),paths);
        }
        return paths;
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