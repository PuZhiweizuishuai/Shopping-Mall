package com.buguagaoshu.mall.product.vo;

import lombok.Data;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-10 18:13
 */
@Data
public class AttrResponseVo extends AttrVo {
    /**
     * 所属分类名字
     * */
    private String catelogName;

    /**
     * 所属分组名字
     * */
    private String groupName;

    /**
     * 完整分类路径
     * */
    private Long[] catelogPath;
}
