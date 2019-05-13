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

import io.chaoshua.modules.background.entity.note.NoteStepEntity;
import io.chaoshua.modules.background.service.note.NoteStepService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 
 *
 * @author dws
 * @date 2018-09-25 16:41:54
 */
@RestController
@RequestMapping("background/notestep")
public class NoteStepController {
    @Autowired
    private NoteStepService noteStepService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:notestep:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = noteStepService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:notestep:info")
    public R info(@PathVariable("id") Long id){
			NoteStepEntity noteStep = noteStepService.selectById(id);

        return R.ok().put("noteStep", noteStep);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:notestep:save")
    public R save(@RequestBody NoteStepEntity noteStep){
        noteStep.setCreateTime(new Date());
        noteStepService.insert(noteStep);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:notestep:update")
    public R update(@RequestBody NoteStepEntity noteStep){
			noteStepService.updateById(noteStep);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:notestep:delete")
    public R delete(@RequestBody Long[] ids){
			noteStepService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
