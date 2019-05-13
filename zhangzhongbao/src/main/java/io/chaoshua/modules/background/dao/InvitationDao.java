package io.chaoshua.modules.background.dao;

import io.chaoshua.modules.background.entity.InvitationEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邀请码表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-18 18:33:20
 */
@Mapper
public interface InvitationDao extends BaseMapper<InvitationEntity> {
	
}
