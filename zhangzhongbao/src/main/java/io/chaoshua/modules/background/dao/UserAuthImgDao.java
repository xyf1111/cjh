package io.chaoshua.modules.background.dao;

import io.chaoshua.modules.background.entity.UserAuthImgEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户每日认证上传图片表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2019-01-11 21:09:11
 */
@Mapper
public interface UserAuthImgDao extends BaseMapper<UserAuthImgEntity> {
	
}
