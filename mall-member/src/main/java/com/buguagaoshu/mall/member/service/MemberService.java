package com.buguagaoshu.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.mall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 14:47:56
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

