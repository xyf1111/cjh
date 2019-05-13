package io.chaoshua.modules.background.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.common.utils.ShiroUtils;
import io.chaoshua.modules.background.service.SellerService;
import io.chaoshua.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.ChargeEntity;
import io.chaoshua.modules.background.service.ChargeService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 商家充值记录表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-31 09:41:58
 */
@RestController
@RequestMapping("background/charge")
public class ChargeController {
    @Autowired
    private ChargeService chargeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:charge:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = chargeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:charge:info")
    public R info(@PathVariable("id") Long id){
        ChargeEntity charge = chargeService.selectById(id);
        return R.ok().put("charge", charge);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:charge:save")
    public R save(@RequestBody ChargeEntity charge){
        chargeService.insert(charge);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:charge:update")
    @LocalLock
    public R update(@RequestBody ChargeEntity charge){
        return chargeService.updateStatusById(charge);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:charge:delete")
    public R delete(@RequestBody Long[] ids){
        chargeService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
