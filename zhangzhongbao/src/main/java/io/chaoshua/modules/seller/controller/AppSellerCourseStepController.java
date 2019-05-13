package io.chaoshua.modules.seller.controller;

import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.background.entity.CourseStepEntity;
import io.chaoshua.modules.background.service.CourseStepService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 打款教程
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-20 15:56:47
 */
@RestController
@RequestMapping("/app/seller/coursestep")
public class AppSellerCourseStepController {
    @Autowired
    private CourseStepService courseStepService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = courseStepService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
			CourseStepEntity courseStep = courseStepService.selectById(id);

        return R.ok().put("courseStep", courseStep);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CourseStepEntity courseStep){
			courseStepService.insert(courseStep);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CourseStepEntity courseStep){
			courseStepService.updateById(courseStep);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
			courseStepService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
