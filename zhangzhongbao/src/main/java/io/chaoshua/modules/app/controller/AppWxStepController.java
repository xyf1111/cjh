package io.chaoshua.modules.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.modules.app.vo.WxStepVo;
import io.chaoshua.modules.background.entity.WxStepEntity;
import io.chaoshua.modules.background.service.WxStepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dws on 2018/12/17 0017.
 * 微信配置
 */
@RestController
@RequestMapping("/app/user/wxStep")
@Api(value = "AppWxStepController",description = "微信配置")
public class AppWxStepController {
@Autowired
private WxStepService wxStepService;

    @GetMapping("/getInfo")
    @ApiOperation("获取微信配置")
    public WxStepVo getInfo(){
        List<WxStepEntity> list = wxStepService.selectList(new EntityWrapper<WxStepEntity>().eq("create_time",false));
        WxStepEntity wxStepEntity = list.get(0);
        WxStepVo wxStepVo = new WxStepVo();
        wxStepVo.setAppId(wxStepEntity.getAppId());
        wxStepVo.setAppsecret(wxStepEntity.getAppsecret());
        return wxStepVo;
    }
}
