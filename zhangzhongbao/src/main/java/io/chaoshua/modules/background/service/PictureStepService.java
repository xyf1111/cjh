package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.PictureStepEntity;

import java.util.Map;

/**
 * 二维码设置表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-20 10:21:48
 */
public interface PictureStepService extends IService<PictureStepEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

