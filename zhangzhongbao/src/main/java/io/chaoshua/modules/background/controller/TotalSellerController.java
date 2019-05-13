package io.chaoshua.modules.background.controller;

import io.chaoshua.common.utils.R;
import io.chaoshua.modules.background.dao.SellerDao;
import io.chaoshua.modules.background.entity.TotalSellerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 获取商家统计
 * Created by dws on 2019/1/23 0023.
 */
@RestController
@RequestMapping("/background/totalSeller")
public class TotalSellerController {
@Autowired
private SellerDao sellerDao;

    @RequestMapping("/getInfo")
    public R getInfo(@RequestParam Map<String,Object> params){
        return R.ok().put("data",sellerDao.getSumTotalBySellerName(params));
    }
}
