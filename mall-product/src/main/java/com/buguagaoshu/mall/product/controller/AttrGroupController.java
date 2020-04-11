package com.buguagaoshu.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.buguagaoshu.common.valid.AddGroup;
import com.buguagaoshu.common.valid.UpdateGroup;
import com.buguagaoshu.mall.product.entity.AttrEntity;
import com.buguagaoshu.mall.product.service.AttrAttrgroupRelationService;
import com.buguagaoshu.mall.product.service.AttrService;
import com.buguagaoshu.mall.product.service.CategoryService;
import com.buguagaoshu.mall.product.vo.AttrGroupRelationVo;
import com.buguagaoshu.mall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.buguagaoshu.mall.product.entity.AttrGroupEntity;
import com.buguagaoshu.mall.product.service.AttrGroupService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.common.utils.R;


/**
 * 属性分组
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 13:07:09
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {

    private final AttrGroupService attrGroupService;

    private final CategoryService categoryService;

    private final AttrService attrService;

    private final AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    public AttrGroupController(AttrGroupService attrGroupService, CategoryService categoryService, AttrService attrService, AttrAttrgroupRelationService attrAttrgroupRelationService) {
        this.attrGroupService = attrGroupService;
        this.categoryService = categoryService;
        this.attrService = attrService;
        this.attrAttrgroupRelationService = attrAttrgroupRelationService;
    }

    // /product/attrgroup/{catelogId}/withattr
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttr(@PathVariable("catelogId") Long catelogId) {
        List<AttrGroupWithAttrsVo> list = attrGroupService.getAttrGroupWithAttr(catelogId);
        return R.ok().put("data", list);
    }


    /**
     * 属性关联
     * */
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelationList(@PathVariable("attrgroupId") Long attrGroupId) {
        List<AttrEntity> attrEntityList = attrService.getRelationAttr(attrGroupId);
        return R.ok().put("data", attrEntityList);
    }

    /**
     * 没有关联的属性
     * */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelationList(@PathVariable("attrgroupId") Long attrGroupId,
                                @RequestParam Map<String, Object> params) {
        PageUtils page = attrService.getNoRelationAttr(params, attrGroupId);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @GetMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId") Long catelogId) {
        PageUtils page = attrGroupService.queryPage(params, catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        long catId = attrGroup.getCatelogId();
        attrGroup.setCatelogPath(categoryService.findCatelogPath(catId));
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 添加新的关联关系
     * */
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos){

        attrAttrgroupRelationService.saveBatch(vos);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated(AddGroup.class) @RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@Validated(UpdateGroup.class) @RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }


    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos) {
        attrService.deleteRelation(vos);
        return R.ok();
    }

}
