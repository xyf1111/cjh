package io.chaoshua.modules.agent.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.agent.entity.AgentSellerMoneyEntity;

import java.util.Map;

/**
 * 代理与商家关系表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2019-03-04 17:10:09
 */
public interface AgentSellerMoneyService extends IService<AgentSellerMoneyEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 根据商家ID获取代理商家发单金额
     *
     * @param sellerId 商家ID
     * @return
     */
    AgentSellerMoneyEntity queryBySellerId(Long sellerId);
}

