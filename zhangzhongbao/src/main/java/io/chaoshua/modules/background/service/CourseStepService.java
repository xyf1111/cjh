package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.CourseStepEntity;

import java.util.Map;

/**
 * 打款教程
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-20 15:56:47
 */
public interface CourseStepService extends IService<CourseStepEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

