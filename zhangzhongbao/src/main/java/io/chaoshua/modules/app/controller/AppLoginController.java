package io.chaoshua.modules.app.controller;


import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.druid.util.StringUtils;
import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.utils.AppResult;
import io.chaoshua.common.utils.AppStatus;
import io.chaoshua.common.validator.ValidatorUtils;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.app.entity.AppUserEntity;
import io.chaoshua.modules.app.form.LoginForm;
import io.chaoshua.modules.app.service.AppUserService;
import io.chaoshua.modules.app.utils.JwtUtils;
import io.chaoshua.modules.app.vo.user.UserPasswordVo;
import io.chaoshua.modules.background.entity.UserEntity;
import io.chaoshua.modules.background.service.UserService;
import io.chaoshua.modules.weixin.utils.HttpsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * APP登录授权
 *
 * @author dws
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/app/user")
@Api(value = "AppLoginController",description = "APP刷手登录接口")
public class AppLoginController {

    @Autowired
    private AppUserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService1;
    @Autowired
    private HttpsUtil httpsUtil;

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public AppResult  login(@RequestBody LoginForm form){
        AppResult appResult = null;
        try {
            //表单校验
            ValidatorUtils.validateEntity(form);
            long userId = userService.login(form);
            System.out.println("----------userId(" + userId + ") login--------------");
            System.out.println("code:" + form.getCode());
            if (!StringUtils.isEmpty(form.getCode())){
                String openId = httpsUtil.getOpenid(form.getCode());
                System.out.println("--------------------------------------");
                System.out.println("openId:" + openId);
                if (!StringUtils.isEmpty(openId)){
                    AppUserEntity userEntity = new AppUserEntity();
                    userEntity.setOpenId(openId);
                    userEntity.setId(userId);
                    userService.updateById(userEntity);
                }
            }
            //生成token
            String token = jwtUtils.generateToken(userId);

            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("expire", jwtUtils.getExpire());
            appResult = AppResult.success();
            appResult.setData(map);
        }catch (RRException r){
            appResult = AppResult.error(500,r.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            appResult = AppResult.error(500,"系统错误,请联系客服!");
        }
        return appResult;
    }

    @ApiOperation("用户修改密码")
    @PostMapping("/updatePassword")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public AppStatus updatePassword(@RequestBody UserPasswordVo userPasswordVo,@ApiIgnore @RequestAttribute("userId") Long userId){
        AppStatus appStatus = null;
        try {
            AppUserEntity userEntity = userService.selectById(userId);
            if(BCrypt.checkpw( userPasswordVo.getOldPassword(),userEntity.getPassword())){
                if (userPasswordVo.getNewPassword().equals(userPasswordVo.getConfirmPassword())){
                    UserEntity userEntity1 = new UserEntity();
                    userEntity1.setId(userEntity.getId());
                    userEntity1.setPassword( BCrypt.hashpw(userPasswordVo.getNewPassword(),BCrypt.gensalt()));
                    userService1.updateById(userEntity1);
                    appStatus = AppStatus.success();
                }else {
                    appStatus = AppStatus.error(500,"前后密码不一致!");
                }
            }else {
                appStatus = AppStatus.error(500,"原密码不对!");
            }

        }catch (Exception e){
            e.printStackTrace();
            appStatus = AppStatus.error(500,"系统错误，请联系客服!");
        }
        return appStatus;
    }


}
