package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.CategoryEntity;

import java.util.Map;

/**
 * 店铺类目管理
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-11-12 18:39:56
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取类目管理map
     *
     * @return
     */
    Map<Long,String> queryCategoryMap();
}

