package com.buguagaoshu.mall.product.controller;

import java.util.Arrays;
import java.util.List;

import com.buguagaoshu.mall.product.cache.CategoryListCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.buguagaoshu.mall.product.entity.CategoryEntity;
import com.buguagaoshu.mall.product.service.CategoryService;
import com.buguagaoshu.common.utils.R;



/**
 * 商品三级分类
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 13:07:09
 */
@RestController
@RequestMapping("/product/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryListCache categoryListCache;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryListCache categoryListCache) {
        this.categoryService = categoryService;
        this.categoryListCache = categoryListCache;
    }

    /**
     * 查出所有分类以及子分类，并且以树形结构组装
     */
    @RequestMapping("/list/tree")
    public R listWithTree(){
        List<CategoryEntity> entities = categoryListCache.getMenusTree();
        return R.ok().put("data", entities);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		if (categoryService.saveAndCheck(category)) {
		    return R.ok();
        }

        return R.error(400,"输入数据有误，请检查后再重新提交");
    }


    /**
     * 修改排序信息
     */
    @PutMapping("/update/sort")
    public R updateSort(@RequestBody CategoryEntity[] category){
        categoryService.updateBatchById(Arrays.asList(category));
        categoryListCache.setMenusTree(categoryService.listWithTree());
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateCascade(category);
        categoryListCache.setMenusTree(categoryService.listWithTree());
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
		// categoryService.removeByIds(Arrays.asList(catIds));
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
