package com.buguagaoshu.mall.warehouse.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-12 19:02
 */
@Data
public class PurchaseDoneVo {
    /**
     * 采购单id
     * */
    @NotNull
    private Long id;

    /**
     * 采购项
     * */
    private List<PurchaseItemDoneVo> items;
}
