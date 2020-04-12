package com.buguagaoshu.mall.warehouse.vo;

import com.buguagaoshu.common.constant.WareConstant;
import com.buguagaoshu.common.valid.ListValue;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-12 19:03
 */
@Data
public class PurchaseItemDoneVo {
    /**
     * 采购需求 ID
     * */
    @NotNull(message = "采购需求 ID 不能为空")
    private Long itemId;

    /**
     * 采购状态
     * */
    @ListValue(value = {3, 4})
    private Integer status;

    /**
     * 原因
     * */
    private String reason;
}
