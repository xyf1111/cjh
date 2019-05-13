package io.chaoshua.modules.background.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.IntervalStepEntity;
import io.chaoshua.modules.background.service.IntervalStepService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;


/**
 * 全局设置表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-22 14:10:32
 */
@RestController
@RequestMapping("background/intervalstep")
public class IntervalStepController {
    @Autowired
    private IntervalStepService intervalStepService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:intervalstep:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = intervalStepService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:intervalstep:info")
    public R info(@PathVariable("id") Long id) {
        IntervalStepEntity intervalStep = intervalStepService.selectById(id);
        return R.ok().put("intervalStep", intervalStep);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:intervalstep:save")
    public R save(@RequestBody IntervalStepEntity intervalStep) {
        intervalStepService.insert(intervalStep);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:intervalstep:update")
    public R update(@RequestBody IntervalStepEntity intervalStep) {
        intervalStepService.updateById(intervalStep);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:intervalstep:delete")
    public R delete(@RequestBody Long[] ids) {
        intervalStepService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
