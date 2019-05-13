package io.chaoshua.modules.agent.controller;

import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.service.DetailService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 往来明细
 * Created by dws on 2018/12/28 0028.
 */
@RestController
@RequestMapping("/app/agent/detail")
public class AppAgentDetailController {
    @Autowired
    private DetailService detailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public R list(@RequestParam Map<String, Object> params,@ApiIgnore @RequestAttribute("userId") Long agentId){
        params.put("userId",agentId);
        params.put("isSeller",3);
        PageUtils page = detailService.queryPage(params);

        return R.ok().put("page", page);
    }

}
