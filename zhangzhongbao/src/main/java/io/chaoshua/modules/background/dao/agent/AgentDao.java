package io.chaoshua.modules.background.dao.agent;

import io.chaoshua.modules.background.entity.agent.AgentEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 代理平台表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-12-22 10:33:42
 */
@Mapper
public interface AgentDao extends BaseMapper<AgentEntity> {
	
}
