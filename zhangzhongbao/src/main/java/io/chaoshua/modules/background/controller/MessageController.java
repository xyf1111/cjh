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

import io.chaoshua.modules.background.entity.MessageEntity;
import io.chaoshua.modules.background.service.MessageService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 消息表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-11-20 15:51:03
 */
@RestController
@RequestMapping("background/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:message:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = messageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:message:info")
    public R info(@PathVariable("id") Long id){
			MessageEntity message = messageService.selectById(id);

        return R.ok().put("message", message);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:message:save")
    public R save(@RequestBody MessageEntity message){
			messageService.insert(message);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:message:update")
    public R update(@RequestBody MessageEntity message){
			messageService.updateById(message);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:message:delete")
    public R delete(@RequestBody Long[] ids){
			messageService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
