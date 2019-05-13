package io.chaoshua.modules.seller.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.entity.ShopEntity;
import io.chaoshua.modules.background.service.ShopService;
import io.chaoshua.modules.background.service.task.KeyWordService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.chaoshua.modules.background.entity.task.TaskMouldEntity;
import io.chaoshua.modules.background.service.task.TaskMouldService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 任务模板
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-11-12 16:29:45
 */
@RestController
@RequestMapping("/app/seller/taskMould")
public class AppSellerTaskMouldController {
    @Autowired
    private TaskMouldService taskMouldService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private KeyWordService keyWordService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public R list(@RequestParam Map<String, Object> params, @ApiIgnore @RequestAttribute("userId") Long sellerId) {
        params.put("sellerId", sellerId);
        PageUtils page = taskMouldService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        TaskMouldEntity taskMould = taskMouldService.selectById(id);
        return R.ok().put("taskMould", taskMould);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public R save(@RequestBody TaskMouldEntity taskMould, @ApiIgnore @RequestAttribute("userId") Long sellerId) {
        ShopEntity shopEntity = shopService.selectById(taskMould.getShopId());
        taskMould.setShopName(shopEntity.getName());
        taskMould.setSellerId(sellerId);
        taskMould.setCreateTime(new Date(System.currentTimeMillis()));
        taskMouldService.insert(taskMould);
        return R.ok();
    }

    /**
     * 修改( todo 有某些自动没法修改的问题！)
     */
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    @RequestMapping("/update")
    public R update(@RequestBody TaskMouldEntity taskMould, @ApiIgnore @RequestAttribute("userId") Long sellerId) {
        taskMould.setSellerId(sellerId);
        String keywords = taskMould.getKeyword();
        Long taskMouldIId = taskMould.getId();
        keyWordService.saveByTaskMouldIId(taskMouldIId, keywords);
        taskMould.setUpdateTime(new Date(System.currentTimeMillis()));
        taskMouldService.updateById(taskMould);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        taskMouldService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
