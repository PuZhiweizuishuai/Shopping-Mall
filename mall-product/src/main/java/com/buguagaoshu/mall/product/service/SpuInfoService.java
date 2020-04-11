package com.buguagaoshu.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.mall.product.entity.SpuInfoDescEntity;
import com.buguagaoshu.mall.product.entity.SpuInfoEntity;
import com.buguagaoshu.mall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 13:07:09
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存前端提交的完整 Spu 数据
     * @param spuSaveVo 完整数据
     * */
    void spuSaveInfo(SpuSaveVo spuSaveVo);


    /**
     * 保存 Spu 的基本信息
     * @param spuInfoEntity spu 基本信息
     * */
    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);



}

