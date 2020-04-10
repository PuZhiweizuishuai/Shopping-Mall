package com.buguagaoshu.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.mall.product.entity.AttrEntity;
import com.buguagaoshu.mall.product.vo.AttrResponseVo;
import com.buguagaoshu.mall.product.vo.AttrVo;

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
     * @return 分页结果
     * */
    PageUtils queryBasePage(Long catelogId, Map<String, Object> params);

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
}

