package io.chaoshua.modules.agent.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.agent.entity.AgentSellerMoneyEntity;
import io.chaoshua.modules.agent.service.AgentSellerMoneyService;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.entity.IntervalStepEntity;
import io.chaoshua.modules.background.entity.agent.AgentMoneyEntity;
import io.chaoshua.modules.background.service.IntervalStepService;
import io.chaoshua.modules.background.service.agent.AgentMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

/**
 * 平台给与代理价格
 * Created by dws on 2018/12/26 0026.
 */
@RestController
@RequestMapping("/app/agent/money")
public class AppAgentMoneyController {
@Autowired
private AgentMoneyService agentMoneyService;
@Autowired
private IntervalStepService intervalStepService;
@Autowired
private AgentSellerMoneyService agentSellerMoneyService;
    /**
     * 获取代理价格
     * @return
     */
    @RequestMapping("/getInfo/{sellerId}")
    @Login
    public R getInfo(@ApiIgnore @RequestAttribute("userId") Long agentId, @PathVariable("sellerId") String sellerId){
        AgentMoneyEntity agentMoneyEntity = agentMoneyService.selectOne(new EntityWrapper<AgentMoneyEntity>().eq("agent_id",agentId));
        AgentSellerMoneyEntity agentSellerMoneyEntity = agentSellerMoneyService.selectOne(new EntityWrapper<AgentSellerMoneyEntity>().eq("seller_id",sellerId));
        IntervalStepEntity intervalStepEntity = intervalStepService.selectById(1);
        agentMoneyEntity.setView1(intervalStepEntity.getAppointOneSeller());
        agentMoneyEntity.setView2(intervalStepEntity.getAppointTwoSeller());
        agentMoneyEntity.setComment(intervalStepEntity.getCommentSeller());
        agentMoneyEntity.setAddComment(intervalStepEntity.getAddCommentSeller());
        agentMoneyEntity.setAgentSellerMoney(agentSellerMoneyEntity);
        return R.ok().put("money",agentMoneyEntity);
    }

    /**
     * 修改价格
     * @param agentMoneyEntity
     * @return
     */
    @RequestMapping("/updateMoney")
    public R updateMoney(@RequestBody AgentMoneyEntity agentMoneyEntity){
        agentMoneyEntity.setUpdateTime(new Date());
        agentMoneyService.updateById(agentMoneyEntity);
        return R.ok();
    }

}
