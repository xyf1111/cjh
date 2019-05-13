package io.chaoshua.modules.agent.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.modules.agent.entity.AgentBankEntity;
import io.chaoshua.modules.agent.service.AgentBankService;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.entity.PayeeEntity;
import io.chaoshua.modules.background.service.BankService;
import io.chaoshua.modules.background.service.PayeeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 代理商银行卡管理表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-12-27 12:06:18
 */
@RestController
@RequestMapping("app/agent/agentBank")
public class AgnebankController {
    @Autowired
    private AgentBankService agentBankService;
    @Autowired
    private BankService bankService;
    @Autowired
    private PayeeService payeeService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = agentBankService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        AgentBankEntity agentBankEntity = agentBankService.selectById(id);

        return R.ok().put("agentBank", agentBankEntity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @Login
    public R save(@RequestBody AgentBankEntity agent, @ApiIgnore @RequestAttribute("userId") Long agentId){
        agent.setCreateTime(new Date());
        agent.setAgentId(agentId);
        agentBankService.insert(agent);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AgentBankEntity agent){
        agentBankService.updateById(agent);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        agentBankService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 获取银行列表
     * @return
     */
    @RequestMapping("/getBankList")
    public R getBankList(){
       return R.ok().put("bank",bankService.selectList(null));
    }

    /**
     * 获取平台银行卡列表
     * @return
     */
    @RequestMapping("/getPayBank")
    public R getPayBank(){
        List<PayeeEntity> list = payeeService.selectList(new EntityWrapper<PayeeEntity>().eq("status",1));
       return R.ok().put("pay",list);
    }


}
