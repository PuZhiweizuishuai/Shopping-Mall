package com.buguagaoshu.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.buguagaoshu.common.valid.AddGroup;
import com.buguagaoshu.common.valid.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 属性分组
 * 
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 13:07:09
 */
@Data
@TableName("pms_attr_group")
public class AttrGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分组id
	 */
	@TableId
	@NotNull(groups = {UpdateGroup.class})
	private Long attrGroupId;
	/**
	 * 组名
	 */
	@NotEmpty(groups = {AddGroup.class})
	private String attrGroupName;

	/**
	 * 排序
	 */
	@Min(value = 0, message = "必须是一个大于等于0的整数", groups = {AddGroup.class, UpdateGroup.class})
	private Integer sort;
	/**
	 * 描述
	 */
	private String descript;

	/**
	 * 组图标
	 */
	private String icon;

	/**
	 * 所属分类id
	 */
	@NotNull(groups = {AddGroup.class})
	private Long catelogId;


	@TableField(exist = false)
	private Long[] catelogPath;
}
