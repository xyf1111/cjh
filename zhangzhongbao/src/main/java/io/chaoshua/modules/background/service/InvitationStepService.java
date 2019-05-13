package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.InvitationStepEntity;

import java.util.Map;

/**
 * 全局设置表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-19 17:59:48
 */
public interface InvitationStepService extends IService<InvitationStepEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

