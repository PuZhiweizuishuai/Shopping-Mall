package com.buguagaoshu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-11 17:47
 */
@Data
public class MemberPrice {
    private Long id;
    private String name;
    private BigDecimal price;
}
