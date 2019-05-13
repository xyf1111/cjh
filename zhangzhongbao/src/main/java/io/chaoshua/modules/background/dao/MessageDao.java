package io.chaoshua.modules.background.dao;

import io.chaoshua.modules.background.entity.MessageEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-11-20 15:51:03
 */
@Mapper
public interface MessageDao extends BaseMapper<MessageEntity> {
	
}
