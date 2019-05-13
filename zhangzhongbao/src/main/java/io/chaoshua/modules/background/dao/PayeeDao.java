package io.chaoshua.modules.background.dao;

import io.chaoshua.modules.background.entity.PayeeEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 收款人银行账号表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-19 17:11:25
 */
@Mapper
public interface PayeeDao extends BaseMapper<PayeeEntity> {
	
}
