package io.chaoshua.modules.background.dao;

import io.chaoshua.modules.background.entity.UserImgEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 刷手注册认证图片信息表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2019-01-11 18:55:26
 */
@Mapper
public interface UserImgDao extends BaseMapper<UserImgEntity> {
	
}
