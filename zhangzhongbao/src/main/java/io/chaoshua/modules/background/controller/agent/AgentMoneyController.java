package io.chaoshua.modules.background.controller.agent;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.agent.AgentMoneyEntity;
import io.chaoshua.modules.background.service.agent.AgentMoneyService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 代理人定价表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-12-22 10:33:42
 */
@RestController
@RequestMapping("background/agenmoney")
public class AgentMoneyController {
    @Autowired
    private AgentMoneyService agenmoneyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = agenmoneyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long agentId){
			AgentMoneyEntity agentMoney = agenmoneyService.selectOne(new EntityWrapper<AgentMoneyEntity>().eq("agent_id",agentId));

        return R.ok().put("agentMoney", agentMoney);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AgentMoneyEntity agenmoney){
			agenmoneyService.insert(agenmoney);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AgentMoneyEntity agenmoney){
        agenmoney.setUpdateTime(new Date());
        agenmoneyService.updateById(agenmoney);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
			agenmoneyService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
