package io.chaoshua.modules.seller.controller;

import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.entity.ChargeEntity;
import io.chaoshua.modules.background.service.ChargeService;
import io.chaoshua.modules.background.service.SellerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 商家充值记录表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-31 09:41:58
 */
@RestController
@RequestMapping("/app/seller/charge")
public class AppSellerChargeController {
    @Autowired
    private ChargeService chargeService;
    @Autowired
    private SellerService sellerService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public R list(@RequestParam Map<String, Object> params,@ApiIgnore @RequestAttribute("userId") Long sellerId) {
        params.put("sellerId",sellerId);
        PageUtils page = chargeService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        ChargeEntity charge = chargeService.selectById(id);
        return R.ok().put("charge", charge);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    @LocalLock
    public R save(@RequestBody ChargeEntity charge,@ApiIgnore @RequestAttribute("userId") Long sellerId) {
        charge.setCreateTime(new Date());
        charge.setSellerId(sellerId);
        charge.setContact(sellerService.selectById(sellerId).getContact());
        chargeService.insert(charge);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ChargeEntity charge) {
        return chargeService.updateStatusById(charge);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        chargeService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
