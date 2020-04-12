package com.buguagaoshu.mall.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.mall.warehouse.entity.PurchaseEntity;
import com.buguagaoshu.mall.warehouse.vo.MergeVo;
import com.buguagaoshu.mall.warehouse.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 20:03:29
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查找没有被领取的采购单
     * @param params 请求的分页参数
     * @return 分页后的查询结果
     * */
    PageUtils queryPageUnReceivePurchase(Map<String, Object> params);

    /**
     * 合并采购请求
     * @param mergeVo 合并的请求
     * */
    void mergePurchase(MergeVo mergeVo);

    /**
     * 采购人员领取采购单
     * @param ids 领取的采购单 ID
     * */
    void receivedPurchase(List<Long> ids);


    /**
     * 采购结果报告
     * @param doneVo 结果参数
     * */
    void done(PurchaseDoneVo doneVo);
}

