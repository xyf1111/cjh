package io.chaoshua.modules.app.controller;

import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.service.SmsCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信接口
 *
 * @author dws
 * @date 2018-04-25 15:50
 */
@RestController
@RequestMapping("/app")
@Api(value = "AppSmsCodeController", description = "短信验证码接口")
public class AppSmsCodeController {
    @Autowired
    private SmsCodeService smsCodeService;

    /**
     * 获取短信验证码
     *
     * @param mobile
     * @return
     */
    @GetMapping(value = "smsCode/get/{mobile}")
    @ApiOperation(value = "获取短信验证码")
    public R get(@PathVariable("mobile") String mobile) {
        smsCodeService.get(mobile);
        return R.ok();
    }
}
