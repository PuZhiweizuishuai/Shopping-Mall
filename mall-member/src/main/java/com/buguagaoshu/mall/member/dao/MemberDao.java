package com.buguagaoshu.mall.member.dao;

import com.buguagaoshu.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 14:47:56
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
