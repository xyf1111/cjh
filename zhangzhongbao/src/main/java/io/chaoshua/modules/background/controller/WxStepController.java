package io.chaoshua.modules.background.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.WxStepEntity;
import io.chaoshua.modules.background.service.WxStepService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 微信配置表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-20 10:05:56
 */
@RestController
@RequestMapping("background/wxstep")
public class WxStepController {
    @Autowired
    private WxStepService wxStepService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:wxstep:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wxStepService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:wxstep:info")
    public R info(@PathVariable("id") Long id){
			WxStepEntity wxStep = wxStepService.selectById(id);

        return R.ok().put("wxStep", wxStep);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:wxstep:save")
    public R save(@RequestBody WxStepEntity wxStep){
            wxStep.setCreateTime(new Date());
			wxStepService.insert(wxStep);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:wxstep:update")
    public R update(@RequestBody WxStepEntity wxStep){
            wxStep.setUpdateTime(new Date());
			wxStepService.updateById(wxStep);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:wxstep:delete")
    public R delete(@RequestBody Long[] ids){
			wxStepService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
