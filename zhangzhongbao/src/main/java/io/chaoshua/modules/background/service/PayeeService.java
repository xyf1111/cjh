package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.PayeeEntity;

import java.util.Map;

/**
 * 收款人银行账号表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-19 17:11:25
 */
public interface PayeeService extends IService<PayeeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

