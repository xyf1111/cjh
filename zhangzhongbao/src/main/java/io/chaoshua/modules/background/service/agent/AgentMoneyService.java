package io.chaoshua.modules.background.service.agent;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.agent.AgentMoneyEntity;

import java.util.Map;

/**
 * 代理人定价表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-12-22 10:33:42
 */
public interface AgentMoneyService extends IService<AgentMoneyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

