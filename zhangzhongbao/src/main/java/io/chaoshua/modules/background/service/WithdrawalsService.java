package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.background.entity.WithdrawalsEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 刷手提现
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-31 14:55:16
 */
public interface WithdrawalsService extends IService<WithdrawalsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 修改打款状态
     * @param withdrawalsEntity
     * @return
     */
    R updateStatus(WithdrawalsEntity withdrawalsEntity);

}

