package com.buguagaoshu.mall.product.vo;

import lombok.Data;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-11 16:35
 */
@Data
public class Images {
    private String imgUrl;

    /**
     * 是否默认图片
     * */
    private int defaultImg;
}
