package io.chaoshua.modules.agent.controller;

import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.service.mission.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 订单管理
 * Created by dws on 2018/12/26 0026.
 */
@RestController
@RequestMapping("/app/agent/mission")
public class AppAgentMissionController {
    @Autowired
    private MissionService missionService;

    /**
     * 获取订单列表
     * @param params
     * @param agentId
     * @return
     */
    @Login
    @RequestMapping("/getList")
    public R getList(@RequestParam Map<String,Object> params, @ApiIgnore @RequestAttribute("userId") Long agentId){
        params.put("agentId",agentId);
        PageUtils page = missionService.queryPage(params);
        return R.ok().put("page",page);
    }
}
