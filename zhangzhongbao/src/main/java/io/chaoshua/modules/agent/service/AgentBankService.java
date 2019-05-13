package io.chaoshua.modules.agent.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.agent.entity.AgentBankEntity;

import java.util.Map;

/**
 * 代理商银行卡管理表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-12-27 12:06:18
 */
public interface AgentBankService extends IService<AgentBankEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

