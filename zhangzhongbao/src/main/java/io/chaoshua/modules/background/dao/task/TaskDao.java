package io.chaoshua.modules.background.dao.task;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.chaoshua.modules.background.entity.task.TaskEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.chaoshua.modules.background.entity.task.TaskVo1;
import io.chaoshua.modules.background.entity.task.TaskVo2;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 任务表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-26 14:23:12
 */
@Mapper
public interface TaskDao extends BaseMapper<TaskEntity> {

    /**
     * 获取任务列表
     * @param params
     * @return
     */
	List<TaskEntity> getList(Pagination pagination,Map<String, Object> params);

    /**
     * 获取app展示列表
     * @param pagination
     * @param params
     * @return
     */
	List<TaskEntity> getVoList(Pagination pagination ,Map<String,Object> params);

    /**
     * 获取任务详情
     * @param taskId
     * @return
     */
	TaskEntity getDetail(Long taskId);

    /**
     * 根据用户ID获取列表
     * @param userId
     * @return
     */
	List<TaskVo1> getListByUserId(Long userId);
    /**
     * 根据用户ID获取店铺拒绝限制列表列表
     * @param userId
     * @return
     */
	List<TaskVo2> getListByRole(Long userId);
    /**
     * 根据用户ID获取店铺拒绝限制列表列表
     * @param userId
     * @return
     */
	List<TaskVo2> getListByIsAnnul(Long userId);
    /**
     * 根据用户ID获取同店铺列表
     * @param userId
     * @return
     */
	List<TaskVo2> getListShopTimeByUserId(Long userId);

    /**
     * 获取任务列表（刷手端）
     *
     * @param pagination 分页参数
     * @param params
     * @return
     */
    List<TaskEntity> getTaskListFromApp(Pagination pagination, Map<String, Object> params);
}
