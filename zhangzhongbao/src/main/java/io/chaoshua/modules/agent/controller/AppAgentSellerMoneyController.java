package io.chaoshua.modules.agent.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.modules.agent.entity.AgentSellerMoneyEntity;
import io.chaoshua.modules.agent.service.AgentSellerMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 代理下商家的金额
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2019-03-04 17:10:09
 */
@RestController
@RequestMapping("/app/agent/seller/money")
public class AppAgentSellerMoneyController {
    @Autowired
    private AgentSellerMoneyService agensellerMoneyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = agensellerMoneyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
			AgentSellerMoneyEntity agensellerMoney = agensellerMoneyService.selectById(id);

        return R.ok().put("agentSellerMoney", agensellerMoney);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AgentSellerMoneyEntity agensellerMoney){
        agensellerMoney.setCreateTime(new Date());
        agensellerMoneyService.insert(agensellerMoney);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AgentSellerMoneyEntity agensellerMoney){
        agensellerMoney.setUpdateTime(new Date());
        agensellerMoneyService.update(agensellerMoney,new EntityWrapper<AgentSellerMoneyEntity>().eq("seller_id",agensellerMoney.getSellerId()));
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
			agensellerMoneyService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
