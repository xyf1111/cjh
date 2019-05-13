package io.chaoshua.modules.agent.service.impl;

import io.chaoshua.modules.agent.dao.AgentSellerMoneyDao;
import io.chaoshua.modules.agent.entity.AgentSellerMoneyEntity;
import io.chaoshua.modules.agent.service.AgentSellerMoneyService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;



@Service("agensellerMoneyService")
public class AgentSellerMoneyServiceImpl extends ServiceImpl<AgentSellerMoneyDao, AgentSellerMoneyEntity> implements AgentSellerMoneyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<AgentSellerMoneyEntity> page = this.selectPage(
                new Query<AgentSellerMoneyEntity>(params).getPage(),
                new EntityWrapper<AgentSellerMoneyEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public AgentSellerMoneyEntity queryBySellerId(Long sellerId) {
        return this.selectOne(new EntityWrapper<AgentSellerMoneyEntity>().eq("seller_id",sellerId));
    }
}
