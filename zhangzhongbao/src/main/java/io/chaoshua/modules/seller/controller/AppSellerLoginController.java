package io.chaoshua.modules.seller.controller;


import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.utils.AppResult;
import io.chaoshua.common.utils.DateUtils;
import io.chaoshua.common.validator.ValidatorUtils;
import io.chaoshua.modules.app.form.LoginForm;
import io.chaoshua.modules.app.utils.JwtUtils;
import io.chaoshua.modules.background.entity.SellerEntity;
import io.chaoshua.modules.seller.entity.AppSellerEntity;
import io.chaoshua.modules.seller.service.AppSellerService;
import io.chaoshua.modules.sys.service.SysUserTokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 商家登录授权
 *
 * @author dws
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/app/seller")
public class AppSellerLoginController {
    @Autowired
    private AppSellerService sellerService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public AppResult login(@RequestBody LoginForm form) {
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            //表单校验
            ValidatorUtils.validateEntity(form);

            //用户登录
            Long sellerId = sellerService.login(form);
            AppSellerEntity sellerEntity = sellerService.selectById(sellerId);
            //生成token
            String token = jwtUtils.generateToken(sellerId);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("expire", jwtUtils.getExpire());
            map.put("isSeller",sellerEntity.getIsSeller());
            map.put("pid",sellerEntity.getPid());
            map.put("sellerId",sellerEntity.getId());
            map.put("mobile",sellerEntity.getMobile());
            appResult.setData(map);
        } catch (RRException rre) {
            appResult = AppResult.error(500, rre.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            appResult = AppResult.error(500, "系统错误请联系管理员!");
        }
        return appResult;
    }

}
