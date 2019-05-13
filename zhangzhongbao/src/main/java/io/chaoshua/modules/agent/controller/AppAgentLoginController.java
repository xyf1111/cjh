package io.chaoshua.modules.agent.controller;


import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.utils.AppResult;
import io.chaoshua.common.utils.R;
import io.chaoshua.common.validator.ValidatorUtils;
import io.chaoshua.modules.agent.entity.AppAgentEntity;
import io.chaoshua.modules.agent.form.AgentLoginForm;
import io.chaoshua.modules.agent.service.AppAgentService;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.app.utils.JwtUtils;
import io.chaoshua.modules.background.entity.agent.AgentEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 代理商登录授权
 *
 * @author dws
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/app/agent")
public class AppAgentLoginController {
    @Autowired
    private AppAgentService appAgentService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public AppResult login(@RequestBody AgentLoginForm form) {
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            //表单校验
            ValidatorUtils.validateEntity(form);

            //用户登录
            Long agentId = appAgentService.login(form);

            //生成token
            String token = jwtUtils.generateToken(agentId);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("expire", jwtUtils.getExpire());
            appResult.setData(map);
        } catch (RRException rre) {
            appResult = AppResult.error(500, rre.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            appResult = AppResult.error(500, "系统错误请联系管理员!");
        }
        return appResult;
    }

    /**
     * 信息
     */
    @RequestMapping("/getBalance")
    @Login
    public R getBalance(@ApiIgnore @RequestAttribute("userId") Long agentId){
        AppAgentEntity agentEntity = appAgentService.selectById(agentId);
        return R.ok().put("agent", agentEntity);
    }

}
