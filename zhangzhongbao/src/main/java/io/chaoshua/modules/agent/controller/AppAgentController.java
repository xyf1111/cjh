package io.chaoshua.modules.agent.controller;

import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.entity.ChargeEntity;
import io.chaoshua.modules.background.entity.agent.AgentEntity;
import io.chaoshua.modules.background.service.ChargeService;
import io.chaoshua.modules.background.service.agent.AgentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 代理商
 * Created by dws on 2018/12/26 0026.
 */
@RestController
@RequestMapping("/app/agent")
public class AppAgentController {
    @Autowired
    private AgentService agentService;
    @Autowired
    private ChargeService chargeService;

    /**
     * 修改密码
     * @param params
     * @return
     */
    @RequestMapping("/updatePw")
    @Login
    public R upadtePw(@RequestBody Map params,@ApiIgnore @RequestAttribute("userId") Long agentId){
        return agentService.updatePw(params,agentId);
    }


    /**
     * 列表
     */
    @RequestMapping("/charge/list")
    @Login
    public R list(@RequestParam Map<String, Object> params,@ApiIgnore @RequestAttribute("userId") Long agentId){
        params.put("seller_id",agentId);
        params.put("isSeller",2);
        PageUtils page = chargeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/charge/info/{id}")
    public R info(@PathVariable("id") Long id){
        ChargeEntity charge = chargeService.selectById(id);

        return R.ok().put("charge", charge);
    }


    /**
     * 保存
     */
    @RequestMapping("/charge/save")
    @Login
    @LocalLock
    public R save(@RequestBody ChargeEntity charge,@ApiIgnore @RequestAttribute("userId") Long agentId){
        charge.setCreateTime(new Date());
        charge.setAgentId(agentId);
        chargeService.insert(charge);

        return R.ok();
    }


}
