package com.buguagaoshu.mall.warehouse.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-12 18:12
 * 接收采购单的合并请求
 */
@Data
public class MergeVo {
    /**
     * 整单id
     * */
    private Long purchaseId;

    /**
     * [1,2,3,4] 合并项集合
     * */
    private List<Long> items;
}
