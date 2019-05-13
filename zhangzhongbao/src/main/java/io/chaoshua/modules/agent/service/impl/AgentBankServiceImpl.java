package io.chaoshua.modules.agent.service.impl;

import io.chaoshua.modules.agent.dao.AgentBankDao;
import io.chaoshua.modules.agent.entity.AgentBankEntity;
import io.chaoshua.modules.agent.service.AgentBankService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;



@Service("agentBankService")
public class AgentBankServiceImpl extends ServiceImpl<AgentBankDao, AgentBankEntity> implements AgentBankService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<AgentBankEntity> page = this.selectPage(
                new Query<AgentBankEntity>(params).getPage(),
                new EntityWrapper<AgentBankEntity>()
                .eq("status",1)
        );

        return new PageUtils(page);
    }

}
