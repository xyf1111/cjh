package io.chaoshua.modules.agent.dao;

import io.chaoshua.modules.agent.entity.AgentSellerMoneyEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 代理与商家关系表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2019-03-04 17:10:09
 */
@Mapper
public interface AgentSellerMoneyDao extends BaseMapper<AgentSellerMoneyEntity> {
	
}
