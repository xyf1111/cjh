package io.chaoshua.modules.background.service.task;


import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.modules.background.entity.task.KeyWordEntity;

import java.util.List;

/**
 * 关键字表
 *
 * @author linys
 */
public interface KeyWordService extends IService<KeyWordEntity> {


    void saveByTaskMouldIId(Long taskMouldIId, String keywords);

    void saveByTaskId(Long taskId, String keywords, String counts);

    public List<KeyWordEntity> findByTaskId(Long taskId);


}

