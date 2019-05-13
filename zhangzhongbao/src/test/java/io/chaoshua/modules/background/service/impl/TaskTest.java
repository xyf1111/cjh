package io.chaoshua.modules.background.service.impl;

import cn.hutool.core.util.RandomUtil;
import io.chaoshua.RenrenApplication;
import io.chaoshua.common.utils.AppPage;
import io.chaoshua.common.utils.AppResult;
import io.chaoshua.modules.app.form.TaskForm;
import io.chaoshua.modules.app.vo.TaskVo;
import io.chaoshua.modules.background.dao.task.TaskDao;
import io.chaoshua.modules.background.service.task.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-02-22 14:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RenrenApplication.class})
public class TaskTest {
    @Autowired
    private TaskService taskService;

    @Test
    public void test1() {
        Long userId = 1L;

        TaskForm taskForm = new TaskForm();
        taskForm.setPage("1");
        taskForm.setSize("10");
        taskForm.setTaskStyle("0");
        AppPage<TaskVo> result = taskService.getVoList(taskForm, userId);
        System.out.println("111");
    }
}
