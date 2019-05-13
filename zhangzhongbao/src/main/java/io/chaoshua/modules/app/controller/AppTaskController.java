package io.chaoshua.modules.app.controller;

import io.chaoshua.common.utils.AppPage;
import io.chaoshua.common.utils.AppResult;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.app.form.TaskForm;
import io.chaoshua.modules.app.vo.TaskDetailVo;
import io.chaoshua.modules.app.vo.TaskVo;
import io.chaoshua.modules.background.service.task.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Created by dws on 2018/11/22 0022.
 */
@RestController
@RequestMapping("/app/user/task")
@Api(value = "AppTaskController",description = "任务管理")
public class AppTaskController {

    @Autowired
    private TaskService taskService;

    @ApiOperation("获取任务列表")
    @PostMapping("/getList")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public AppPage<TaskVo> getList(@RequestBody TaskForm taskForm, @ApiIgnore @RequestAttribute("userId") Long userId){
        return taskService.getVoList(taskForm,userId);
    }

    @GetMapping("/getTaskDetail/{taskId}")
    @ApiOperation("任务详情")
    public AppResult<TaskDetailVo> getTaskDetail(@PathVariable("taskId") Long taskId){
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            appResult.setData(taskService.getDetail(taskId));
        }catch (Exception e){
            e.printStackTrace();
            appResult = AppResult.error(500,"系统错误,请联系客服!");
        }
       return appResult;
    }
}
