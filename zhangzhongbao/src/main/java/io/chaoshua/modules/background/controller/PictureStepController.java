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

import io.chaoshua.modules.background.entity.PictureStepEntity;
import io.chaoshua.modules.background.service.PictureStepService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 二维码设置表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-20 10:21:48
 */
@RestController
@RequestMapping("background/picturestep")
public class PictureStepController {
    @Autowired
    private PictureStepService pictureStepService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:picturestep:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pictureStepService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:picturestep:info")
    public R info(@PathVariable("id") Long id){
			PictureStepEntity pictureStep = pictureStepService.selectById(id);

        return R.ok().put("pictureStep", pictureStep);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:picturestep:save")
    public R save(@RequestBody PictureStepEntity pictureStep){
            pictureStep.setCreateTime(new Date());
			pictureStepService.insert(pictureStep);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:picturestep:update")
    public R update(@RequestBody PictureStepEntity pictureStep){
        pictureStep.setUpdateTime(new Date());
			pictureStepService.updateById(pictureStep);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:picturestep:delete")
    public R delete(@RequestBody Long[] ids){
			pictureStepService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
