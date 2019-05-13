package io.chaoshua.modules.agent.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.chaoshua.modules.agent.entity.AgentBankEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 代理商银行卡管理表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-12-27 12:06:18
 */
@Mapper
public interface AgentBankDao extends BaseMapper<AgentBankEntity> {
	
}
