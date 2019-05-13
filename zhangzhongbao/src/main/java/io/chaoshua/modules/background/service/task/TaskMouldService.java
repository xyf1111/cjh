package io.chaoshua.modules.background.service.task;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.task.TaskMouldEntity;

import java.util.Map;

/**
 * 任务模板
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-11-12 16:29:45
 */
public interface TaskMouldService extends IService<TaskMouldEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

