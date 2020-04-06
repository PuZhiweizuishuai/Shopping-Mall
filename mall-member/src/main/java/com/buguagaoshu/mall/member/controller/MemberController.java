package com.buguagaoshu.mall.member.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import com.buguagaoshu.mall.member.feign.CouponFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.buguagaoshu.mall.member.entity.MemberEntity;
import com.buguagaoshu.mall.member.service.MemberService;
import com.buguagaoshu.common.utils.PageUtils;
import com.buguagaoshu.common.utils.R;



/**
 * 会员
 *
 * @author Pu Zhiwei
 * @email puzhiweipuzhiwei@foxmail.com
 * @date 2020-04-06 14:47:56
 */
@RestController
@RequestMapping("member/member")
public class MemberController {

    private final MemberService memberService;

    private final CouponFeignService couponFeignService;

    @Autowired
    public MemberController(MemberService memberService, CouponFeignService couponFeignService) {
        this.memberService = memberService;
        this.couponFeignService = couponFeignService;
    }


    @GetMapping("/test")
    public R test() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("kkk");
        R r = couponFeignService.memberCoupons();

        return Objects.requireNonNull(R.ok().put("member", memberEntity)).put("coupon", r.get("coupon"));

    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
