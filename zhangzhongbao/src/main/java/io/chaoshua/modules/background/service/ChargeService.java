package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.background.entity.ChargeEntity;

import java.util.Map;

/**
 * 商家充值记录表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-31 09:41:58
 */
public interface ChargeService extends IService<ChargeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 审核
     * @param chargeEntity
     * @return
     */
    R updateStatusById(ChargeEntity chargeEntity);
}

