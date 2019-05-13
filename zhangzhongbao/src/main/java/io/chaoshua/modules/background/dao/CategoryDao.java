package io.chaoshua.modules.background.dao;

import io.chaoshua.modules.background.entity.CategoryEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 店铺类目管理
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-11-12 18:39:56
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
