package com.buguagaoshu.mall.product.vo;

import lombok.Data;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-10 20:23
 * 接收删除关联关系的请求
 */
@Data
public class AttrGroupRelationVo {
    /**
     * 属性 ID
     * */
    private Long attrId;

    /**
     * 分组 ID
     * */
    private Long attrGroupId;
}
