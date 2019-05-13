package io.chaoshua.modules.background.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.agent.AgentMoneyDao;
import io.chaoshua.modules.background.entity.agent.AgentMoneyEntity;
import io.chaoshua.modules.background.service.agent.AgentMoneyService;


@Service("agenmoneyService")
public class AgentMoneyServiceImpl extends ServiceImpl<AgentMoneyDao, AgentMoneyEntity> implements AgentMoneyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<AgentMoneyEntity> page = this.selectPage(
                new Query<AgentMoneyEntity>(params).getPage(),
                new EntityWrapper<AgentMoneyEntity>()
        );

        return new PageUtils(page);
    }

}
