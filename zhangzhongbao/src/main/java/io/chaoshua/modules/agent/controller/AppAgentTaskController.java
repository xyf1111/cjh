package io.chaoshua.modules.agent.controller;

import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.chaoshua.modules.background.entity.task.TaskEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 订单管理
 * Created by dws on 2018/12/26 0026.
 */
@RestController
@RequestMapping("/app/agent/task")
public class AppAgentTaskController {
    @Autowired
    private TaskService taskService;

    /**
     * 获取任务列表
     * @param params
     * @param agentId
     * @return
     */
    @Login
    @RequestMapping("/getList")
    public R getList(@RequestParam Map<String,Object> params, @ApiIgnore @RequestAttribute("userId") Long agentId){
        params.put("agentId",agentId);
        PageUtils page = taskService.getListByPid(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
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
}
