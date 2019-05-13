package io.chaoshua.modules.seller.controller;

import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.entity.DetailEntity;
import io.chaoshua.modules.background.service.DetailService;
import io.chaoshua.modules.background.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 流水表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-18 11:54:46
 */
@RestController
@RequestMapping("/app/seller/detail")
public class AppSellerDetailController {
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
    public R list(@RequestParam Map<String, Object> params,@ApiIgnore @RequestAttribute("userId") Long sellerId){
        params.put("userId",sellerId);
        params.put("isSeller",1);
        PageUtils page = detailService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/list1")
    public R list1(@RequestParam Map<String, Object> params){
        PageUtils page = detailService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/getListByPid")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public R getListByPid(@RequestParam Map<String, Object> params,@ApiIgnore @RequestAttribute("userId") Long sellerId){
        params.put("pid",sellerId);
        PageUtils page = detailService.getListByPid(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
			DetailEntity detail = detailService.selectById(id);

        return R.ok().put("detail", detail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DetailEntity detail){
        detail.setCreateTime(new Date());
        detailService.insert(detail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody DetailEntity detail){
			detailService.updateById(detail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
			detailService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
