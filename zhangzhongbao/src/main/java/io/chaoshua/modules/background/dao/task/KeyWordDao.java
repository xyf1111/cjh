package io.chaoshua.modules.background.dao.task;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.chaoshua.modules.background.entity.task.KeyWordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 关键词表
 * @author linys
 */
@Mapper
public interface KeyWordDao extends BaseMapper<KeyWordEntity> {

    /**
     * 根据任务Id或者任务模板Id，查询关键词列表
     * @param params
     * @return
     */
	List<KeyWordEntity> getTaskKeyWordById(Map<String, Object> params);

    /**
     * 根据任务Id或者任务模板Id，刪除关键词列表
     * @param params
     * @return
     */
    void deleteByTaskId(Map<String, Object> params);


}
