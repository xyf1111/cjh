package io.chaoshua.modules.background.dao;

import io.chaoshua.modules.background.entity.WxStepEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信配置表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-20 10:05:56
 */
@Mapper
public interface WxStepDao extends BaseMapper<WxStepEntity> {
	
}
