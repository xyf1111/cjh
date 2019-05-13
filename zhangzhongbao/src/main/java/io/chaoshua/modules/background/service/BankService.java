package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.BankEntity;

import java.util.Map;

/**
 * 银行表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-19 16:52:51
 */
public interface BankService extends IService<BankEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

