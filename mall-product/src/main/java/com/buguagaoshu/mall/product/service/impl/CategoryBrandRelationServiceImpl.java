package com.buguagaoshu.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.buguagaoshu.mall.product.dao.BrandDao;
import com.buguagaoshu.mall.product.dao.CategoryDao;
import com.buguagaoshu.mall.product.entity.BrandEntity;
import com.buguagaoshu.mall.product.entity.CategoryEntity;
import com.buguagaoshu.mall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.common.utils.Query;

import com.buguagaoshu.mall.product.dao.CategoryBrandRelationDao;
import com.buguagaoshu.mall.product.entity.CategoryBrandRelationEntity;
import com.buguagaoshu.mall.product.service.CategoryBrandRelationService;


/**
 * @author puzhiwei
 */
@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    private final BrandDao brandDao;

    private final CategoryDao categoryDao;

    private final CategoryBrandRelationDao categoryBrandRelationDao;


    @Autowired
    public CategoryBrandRelationServiceImpl(BrandDao brandDao, CategoryDao categoryDao, CategoryBrandRelationDao categoryBrandRelationDao) {
        this.brandDao = brandDao;
        this.categoryDao = categoryDao;
        this.categoryBrandRelationDao = categoryBrandRelationDao;

    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        String brandName = brandDao.selectById(categoryBrandRelation.getBrandId()).getName();
        String catelogName = categoryDao.selectById(categoryBrandRelation.getCatelogId()).getName();
        categoryBrandRelation.setBrandName(brandName);
        categoryBrandRelation.setCatelogName(catelogName);
        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrandName(BrandEntity brand) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setBrandId(brand.getBrandId());
        entity.setBrandName(brand.getName());

        this.update(entity,
                new UpdateWrapper<CategoryBrandRelationEntity>()
                        .eq("brand_id", brand.getBrandId()));
    }


    @Override
    public void updateCatelogName(CategoryEntity categoryEntity) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setCatelogId(categoryEntity.getCatId());
        entity.setCatelogName(categoryEntity.getName());
        this.update(entity,
                new UpdateWrapper<CategoryBrandRelationEntity>()
                .eq("catelog_id", categoryEntity.getCatId())
        );
    }

    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {
        List<CategoryBrandRelationEntity> catelogId = categoryBrandRelationDao.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        List<BrandEntity> collect = catelogId.stream().map(item -> {
            Long brandId = item.getBrandId();
            BrandEntity byId = brandDao.selectById(brandId);
            return byId;
        }).collect(Collectors.toList());
        return collect;
    }

}