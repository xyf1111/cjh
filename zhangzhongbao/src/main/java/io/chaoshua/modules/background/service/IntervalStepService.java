package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.IntervalStepEntity;

import java.util.Map;

/**
 * 全局设置表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-22 14:10:32
 */
public interface IntervalStepService extends IService<IntervalStepEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

