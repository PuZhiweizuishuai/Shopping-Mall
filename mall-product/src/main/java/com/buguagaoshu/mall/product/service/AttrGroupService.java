package com.buguagaoshu.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.mall.product.entity.AttrGroupEntity;
import com.buguagaoshu.mall.product.vo.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 13:07:09
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取分类属性分组
     * @param params 分页参数
     * @param catelogId  分类的 ID
     * @return 分页后获取的数据
     * */
    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    /**
     * 根据分类id查出所有的分组以及这些组里面的属性
     * @param catelogId 分类 ID
     * @return 结果列表
     * */
    List<AttrGroupWithAttrsVo> getAttrGroupWithAttr(Long catelogId);
}

