package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.InvitationEntity;

import java.util.Map;

/**
 * 邀请码表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-18 18:33:20
 */
public interface InvitationService extends IService<InvitationEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

