package com.buguagaoshu.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.mall.product.entity.AttrEntity;
import com.buguagaoshu.mall.product.vo.AttrGroupRelationVo;
import com.buguagaoshu.mall.product.vo.AttrResponseVo;
import com.buguagaoshu.mall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 13:07:09
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存商品属性
     * @param attr 商品属性
     * */
    void saveAttr(AttrVo attr);

    /**
     * 依据分类ID查找当前分类ID下的规格参数
     * @param catelogId  分类ID
     * @param params  分页参数
     * @param type
     * @return 分页结果
     * */
    PageUtils queryBasePage(Long catelogId, Map<String, Object> params, String type);

    /**
     * 返回当前属性详细信息
     * @param attrId  属性 ID
     * @return 属性详细信息
     * */
    AttrResponseVo getAttrResponseInfo(Long attrId);

    /**
     * 通过接收到的 AttrResponseVo 更新 AttrEntity
     * @param attr 属性
     * */
    void updateAttrResponseVo(AttrResponseVo attr);


    /**
     * 根据分组 ID 查找关联的所有属性
     * @param attrGroupId 分组ID
     * @return 返回当前属性的关联关系
     * */
    List<AttrEntity> getRelationAttr(Long attrGroupId);

    /**
     * 删除属性与分组之间的关系
     * @param vos 属性与分组 ID
     * */
    void deleteRelation(AttrGroupRelationVo[] vos);

    /**
     * 获取还没有被分组关联的属性列表
     * @param params 分页参数
     * @param attrGroupId  分组 ID
     * @return 分页后的结果
     * */
    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrGroupId);
}

