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

import io.chaoshua.modules.background.entity.CourseStepEntity;
import io.chaoshua.modules.background.service.CourseStepService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 打款教程
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-20 15:56:47
 */
@RestController
@RequestMapping("background/coursestep")
public class CourseStepController {
    @Autowired
    private CourseStepService courseStepService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:coursestep:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = courseStepService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:coursestep:info")
    public R info(@PathVariable("id") Long id){
			CourseStepEntity courseStep = courseStepService.selectById(id);

        return R.ok().put("courseStep", courseStep);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:coursestep:save")
    public R save(@RequestBody CourseStepEntity courseStep){
			courseStepService.insert(courseStep);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:coursestep:update")
    public R update(@RequestBody CourseStepEntity courseStep){
			courseStepService.updateById(courseStep);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:coursestep:delete")
    public R delete(@RequestBody Long[] ids){
			courseStepService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
