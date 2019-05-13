package io.chaoshua.modules.agent.controller;

import java.util.Arrays;
import java.util.Map;

import io.chaoshua.modules.app.annotation.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.chaoshua.modules.background.entity.ChargeEntity;
import io.chaoshua.modules.background.service.ChargeService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 商家充值记录表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-31 09:41:58
 */
@RestController
@RequestMapping("app/agent/seller/charge")
public class AppAgentRecordController {
    @Autowired
    private ChargeService chargeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @Login
    public R list(@RequestParam Map<String, Object> params, @ApiIgnore @RequestAttribute("userId") Long agentId){
        params.put("agentId",agentId);
        params.put("isSeller",1);
        PageUtils page = chargeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
			ChargeEntity charge = chargeService.selectById(id);

        return R.ok().put("charge", charge);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ChargeEntity charge){
        chargeService.insert(charge);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ChargeEntity charge){
        return chargeService.updateStatusById(charge);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        chargeService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
