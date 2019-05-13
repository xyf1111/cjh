package io.chaoshua.modules.app.controller;

import io.chaoshua.common.utils.AppResult;
import io.chaoshua.modules.app.vo.BankVo;
import io.chaoshua.modules.background.entity.BankEntity;
import io.chaoshua.modules.background.service.BankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dws on 2018/10/23 0023.
 */
@Api(value = "AppBankController",description = "获取银行列表")
@RestController
@RequestMapping("/app/bank")
public class AppBankController {
@Autowired
    private BankService bankService;

    @ApiOperation("获取列表")
    @GetMapping("/getList")
    public AppResult<List<BankVo>> getList(){
        AppResult appResult = null;
        Map<String,Object> params = new HashedMap();
        List<BankEntity> list  =  bankService.selectByMap(params);
        List<BankVo> voList = new ArrayList<>();
        for (BankEntity bankEntity :list){
            BankVo vo = new BankVo();
            vo.setBankId(bankEntity.getId());
            vo.setName(bankEntity.getName());
            voList.add(vo);
        }
        appResult = AppResult.success();
        appResult.setData(voList);
        return appResult;
    }
}
