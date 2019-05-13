package io.chaoshua.modules.app.controller;

import io.chaoshua.common.utils.AppResult;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.app.vo.IntervalStepVo;
import io.chaoshua.modules.app.vo.SellerVo;
import io.chaoshua.modules.background.dao.SellerDao;
import io.chaoshua.modules.background.service.IntervalStepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Created by dws on 2018/12/21 0021.
 */
@Api(value = "AppLowerSellerController",description = "刷手推荐商家接口")
@RestController
@RequestMapping("/app/user/lowerSeller")
public class AppLowerSellerController {
@Autowired
private SellerDao sellerDao;
@Autowired
private IntervalStepService intervalStepService;

    @ApiOperation("获取下级商家列表")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    @GetMapping("/getList")
    public AppResult<List<SellerVo>> getList(@ApiIgnore @RequestAttribute("userId") Long userId){
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            List<SellerVo> list = sellerDao.getLowerSellerListByPid(userId);
            appResult.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            appResult = AppResult.error(500,"系统错误，请联系客服!");
        }
        return appResult;
    }

    @ApiOperation("获取奖励金")
    @GetMapping("/getIntervalStepInfo")
    public AppResult<IntervalStepVo> getIntervalStepInfo(){
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            IntervalStepVo intervalStepVo = new IntervalStepVo();
            intervalStepVo.setSellerMoney(intervalStepService.selectById(1).getSellerMoney());
            appResult.setData(intervalStepVo);
        }catch (Exception e){
            e.printStackTrace();
            appResult = AppResult.error(500,"系统错误，请联系客服!");
        }
        return appResult;
    }
}
