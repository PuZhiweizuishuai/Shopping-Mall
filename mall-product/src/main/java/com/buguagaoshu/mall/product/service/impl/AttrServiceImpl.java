package com.buguagaoshu.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.buguagaoshu.mall.product.dao.AttrAttrgroupRelationDao;
import com.buguagaoshu.mall.product.dao.AttrGroupDao;
import com.buguagaoshu.mall.product.dao.CategoryDao;
import com.buguagaoshu.mall.product.entity.AttrAttrgroupRelationEntity;
import com.buguagaoshu.mall.product.entity.AttrGroupEntity;
import com.buguagaoshu.mall.product.entity.CategoryEntity;
import com.buguagaoshu.mall.product.service.CategoryService;
import com.buguagaoshu.mall.product.vo.AttrResponseVo;
import com.buguagaoshu.mall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
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

import com.buguagaoshu.mall.product.dao.AttrDao;
import com.buguagaoshu.mall.product.entity.AttrEntity;
import com.buguagaoshu.mall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    private final AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    private final AttrGroupDao attrGroupDao;

    private final CategoryDao categoryDao;

    private final CategoryService categoryService;


    @Autowired
    public AttrServiceImpl(AttrAttrgroupRelationDao attrAttrgroupRelationDao, AttrGroupDao attrGroupDao, CategoryDao categoryDao, CategoryService categoryService) {
        this.attrAttrgroupRelationDao = attrAttrgroupRelationDao;
        this.attrGroupDao = attrGroupDao;
        this.categoryDao = categoryDao;
        this.categoryService = categoryService;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        // 保存基本数据
        this.save(attrEntity);
        // 保存关联关系
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attr.getAttrGroupId());
        relationEntity.setAttrId(attrEntity.getAttrId());

        attrAttrgroupRelationDao.insert(relationEntity);
    }

    @Override
    public PageUtils queryBasePage(Long catelogId, Map<String, Object> params) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper();
        String key = (String) params.get("key");

        if (catelogId != 0) {
            wrapper.eq("catelog_id", catelogId);
        }
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((queryWrapper) -> {
                queryWrapper.eq("attr_id", key).like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrResponseVo> respVos = records.stream().map((attrEntity) -> {
            AttrResponseVo attrRespVo = new AttrResponseVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
//            if("base".equalsIgnoreCase(type)){
            AttrAttrgroupRelationEntity attrId = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            if (attrId != null && attrId.getAttrGroupId() != null) {
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }

//            }
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;
    }

    @Override
    public AttrResponseVo getAttrResponseInfo(Long attrId) {
        AttrResponseVo attrResponseVo = new AttrResponseVo();
        AttrEntity attrEntity = this.getById(attrId);
        if (attrEntity != null) {
            BeanUtils.copyProperties(attrEntity, attrResponseVo);
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity
                    = attrAttrgroupRelationDao.selectOne(
                    new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId)
            );
            if (attrAttrgroupRelationEntity != null) {
                attrResponseVo.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                if (attrGroupEntity != null) {
                    attrResponseVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            attrResponseVo.setCatelogPath(categoryService.findCatelogPath(attrEntity.getCatelogId()));
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrResponseVo.setCatelogName(categoryEntity.getName());
            }
            return attrResponseVo;
        } else {
            return null;
        }

    }

    @Override
    public void updateAttrResponseVo(AttrResponseVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);


        //1、修改分组关联
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attr.getAttrGroupId());
        relationEntity.setAttrId(attr.getAttrId());
        Integer count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
        if (count > 0) {
            attrAttrgroupRelationDao.update(relationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
        } else {
            attrAttrgroupRelationDao.insert(relationEntity);
        }
    }

}