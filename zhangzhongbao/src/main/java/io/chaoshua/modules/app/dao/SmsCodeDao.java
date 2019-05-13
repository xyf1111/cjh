package io.chaoshua.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.chaoshua.modules.app.entity.SmsCodeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信验证码
 * 
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2018-04-25 16:07:10
 */
@Mapper
public interface SmsCodeDao extends BaseMapper<SmsCodeEntity> {
	
}
