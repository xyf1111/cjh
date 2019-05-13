package io.chaoshua.modules.background.service.task;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.*;
import io.chaoshua.modules.app.form.TaskForm;
import io.chaoshua.modules.app.vo.TaskDetailVo;
import io.chaoshua.modules.app.vo.TaskVo;
import io.chaoshua.modules.background.entity.task.TaskEntity;

import java.util.List;
import java.util.Map;

/**
 * 任务表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-26 14:23:12
 */
public interface TaskService extends IService<TaskEntity> {

    PageUtils queryPage( Map<String, Object> params);

    /**
     * 新增任务
     * @param taskEntity
     */
    R insertTask(TaskEntity taskEntity);

    /**
     * 批量新增任务
     * @param list
     */
    R insertBatchTask(List<String[]> list, Long sellerId, String url);

    /**
     * 获取任务列表
     * @param taskForm
     * @param userId
     * @return
     */
    AppPage<TaskVo> getVoList(TaskForm taskForm, Long userId);

    /**
     * 获取任务详情
     * @param taskId
     * @return
     */
    TaskDetailVo getDetail(Long taskId);

    /**
     * 审核任务
     * @param task
     * @return
     */
    R updateByTaskId(TaskEntity task);

    /**
     * 批量审核通过
     *
     * @param taskIds      任务ID列表
     * @param inChargeId   客服ID
     * @param platformName 客服名称
     */
    void batchPass(List<Long> taskIds, Long inChargeId, String platformName);

    /**
     * 撤销任务剩余
     *
     * @param id
     * @return
     */
    R deleteTaskById(Long id);

    /***************************商家端结束************************************************/
    /**
     * 获取商家端任务列表
     * @param params
     * @return
     */
        PageUtils querySellerPage(Map<String, Object> params) ;
    /***************************商家端开始************************************************/

    /*****************************代理端开始**********************************************************/

    /**
     * 获取代理商下的任务列表
     * @param params
     * @return
     */
    PageUtils getListByPid(Map<String,Object> params);
    /*******************************代理端结束********************************************************/
}

