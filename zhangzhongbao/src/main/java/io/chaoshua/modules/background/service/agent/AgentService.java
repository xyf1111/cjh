package io.chaoshua.modules.background.service.agent;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.background.entity.agent.AgentEntity;

import java.util.Map;

/**
 * 代理平台表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-12-22 10:33:42
 */
public interface AgentService extends IService<AgentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 新增代理
     * @return
     */
    R insertAgent(AgentEntity agentEntity);

    /**
     * 修改密码
     * @param map
     * @return
     */
    R updatePw(Map map,Long agentId);

    /**
     * 根据手机号码获取代理商
     *
     * @param mobile
     * @return
     */
    AgentEntity queryByMobile(String mobile);
}

