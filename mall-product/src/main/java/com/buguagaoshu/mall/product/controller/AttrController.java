package com.buguagaoshu.mall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.buguagaoshu.mall.product.vo.AttrResponseVo;
import com.buguagaoshu.mall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.buguagaoshu.mall.product.entity.AttrEntity;
import com.buguagaoshu.mall.product.service.AttrService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.common.utils.R;


/**
 * 商品属性
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 13:07:09
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {

    private final AttrService attrService;

    @Autowired
    public AttrController(AttrService attrService) {
        this.attrService = attrService;
    }


    @GetMapping("/{type}/list/{catelogId}")
    public R baseList(@PathVariable("catelogId") Long catelogId,
                      @RequestParam Map<String, Object> params,
                      @PathVariable("type") String type) {
        //PageUtils page = attrService.queryPage(params);
        PageUtils page = attrService.queryBasePage(catelogId, params, type);
        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId) {
        AttrResponseVo attr = attrService.getAttrResponseInfo(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AttrVo attr) {
        attrService.saveAttr(attr);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody AttrResponseVo attr) {
        attrService.updateAttrResponseVo(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
