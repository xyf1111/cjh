package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.WxStepEntity;

import java.util.Map;

/**
 * 微信配置表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-20 10:05:56
 */
public interface WxStepService extends IService<WxStepEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

