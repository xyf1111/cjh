package io.chaoshua.modules.background.dao;

import io.chaoshua.modules.background.entity.BankEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 银行表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-19 16:52:51
 */
@Mapper
public interface BankDao extends BaseMapper<BankEntity> {
	
}
