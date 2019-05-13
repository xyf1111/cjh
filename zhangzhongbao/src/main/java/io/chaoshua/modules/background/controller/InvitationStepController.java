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

import io.chaoshua.modules.background.entity.InvitationStepEntity;
import io.chaoshua.modules.background.service.InvitationStepService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 全局设置表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-19 17:59:48
 */
@RestController
@RequestMapping("background/invitationstep")
public class InvitationStepController {
    @Autowired
    private InvitationStepService invitationStepService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:invitationstep:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = invitationStepService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:invitationstep:info")
    public R info(@PathVariable("id") Long id){
        InvitationStepEntity invitationStep = invitationStepService.selectById(id);

        return R.ok().put("invitationStep", invitationStep);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:invitationstep:save")
    public R save(@RequestBody InvitationStepEntity invitationStep){
        invitationStepService.insert(invitationStep);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:invitationstep:update")
    public R update(@RequestBody InvitationStepEntity invitationStep){
        invitationStepService.updateById(invitationStep);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:invitationstep:delete")
    public R delete(@RequestBody Long[] ids){
        invitationStepService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
