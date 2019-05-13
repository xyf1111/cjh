package io.chaoshua.modules.background.controller;

import java.util.Map;

import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.modules.background.vo.BatchPassTaskVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.task.TaskEntity;
import io.chaoshua.modules.background.service.task.TaskService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 任务表
 *
 * @author dws
 * @date 2018-09-26 14:23:12
 */
@RestController
@RequestMapping("background/task")
public class TaskController {


    @Autowired
    private TaskService taskService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:task:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = taskService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 平台查询列表
     */
    @RequestMapping("/findList")
    public R findList(@RequestBody Map<String, Object> params){
        PageUtils page = taskService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:task:info")
    public R info(@PathVariable("id") Long id){
        try {
            TaskEntity task = taskService.selectById(id);
            if (task != null){
                return R.ok().put("task", task);
            }else {
                return R.error(500,"没有相关任务!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(500,"系统错误,请联系客服!");
        }

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:task:save")
    @LocalLock
    public R save(@RequestBody TaskEntity task){
        taskService.insert(task);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:task:update")
    @LocalLock
    public R update(@RequestBody TaskEntity task){
        taskService.updateByTaskId(task);
        return R.ok();
    }

    /**
     * 批量审核通过
     */
    @RequestMapping("/batchPass")
    @RequiresPermissions("background:task:update")
    public R batchPass(@RequestBody BatchPassTaskVO batchPassTaskVO){
        taskService.batchPass(batchPassTaskVO.getTaskIds(),batchPassTaskVO.getInChargeId(),batchPassTaskVO.getPlatformName());
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update1")
    @RequiresPermissions("background:task:update")
    public R update1(@RequestBody TaskEntity task){
        taskService.updateById(task);
        return R.ok();
    }

    /**
     * 删除(撤销任务)
     */
    @RequestMapping("/delete/{id}")
    @RequiresPermissions("background:task:delete")
    @LocalLock
    public R delete(@PathVariable("id") Long id){
        return taskService.deleteTaskById(id);
    }

}
