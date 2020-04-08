package com.buguagaoshu.mall.product.cache;

import com.buguagaoshu.mall.product.entity.CategoryEntity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-08 18:09
 * 缓存生成的分类树
 */
@Component
@Data
@Slf4j
public class CategoryListCache {
    private List<CategoryEntity> menusTree;
}
