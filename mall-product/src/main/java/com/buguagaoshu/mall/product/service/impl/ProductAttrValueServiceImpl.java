package com.buguagaoshu.mall.product.service.impl;

import com.buguagaoshu.mall.product.entity.AttrEntity;
import com.buguagaoshu.mall.product.service.AttrService;
import com.buguagaoshu.mall.product.vo.BaseAttrs;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.common.utils.Query;

import com.buguagaoshu.mall.product.dao.ProductAttrValueDao;
import com.buguagaoshu.mall.product.entity.ProductAttrValueEntity;
import com.buguagaoshu.mall.product.service.ProductAttrValueService;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    private final AttrService attrService;

    public ProductAttrValueServiceImpl(AttrService attrService) {
        this.attrService = attrService;
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveBaseProductAttrValue(Long spuInfoId, List<BaseAttrs> baseAttrs) {
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
            valueEntity.setAttrId(attr.getAttrId());
            AttrEntity id = attrService.getById(attr.getAttrId());
            valueEntity.setAttrName(id.getAttrName());
            valueEntity.setAttrValue(attr.getAttrValues());
            valueEntity.setQuickShow(attr.getShowDesc());
            valueEntity.setSpuId(spuInfoId);

            return valueEntity;
        }).collect(Collectors.toList());

        this.saveBatch(collect);
    }

}