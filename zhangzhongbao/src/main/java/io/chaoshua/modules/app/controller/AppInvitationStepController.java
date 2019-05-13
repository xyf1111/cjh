package io.chaoshua.modules.app.controller;

import io.chaoshua.modules.background.service.InvitationStepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dws on 2019/1/15 0015.
 */
@Api(value = "AppInvitationStepController",description = "邀请状态(1:是，2：否)")
@RestController
@RequestMapping("/app/invitationStep")
public class AppInvitationStepController {
    @Autowired
    private InvitationStepService invitationStepService;

    @ApiOperation("邀请状态(1:是，2：否)")
    @GetMapping("/getInfo")
    public Integer getInfo(){

        return invitationStepService.selectById(1).getOpen();
    }
}
