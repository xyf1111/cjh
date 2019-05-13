package io.chaoshua.modules.background.service.impl;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.CategoryDao;
import io.chaoshua.modules.background.entity.CategoryEntity;
import io.chaoshua.modules.background.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CategoryEntity> page = this.selectPage(
                new Query<CategoryEntity>(params).getPage(),
                new EntityWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Map<Long, String> queryCategoryMap() {
        List<CategoryEntity> categoryList = this.selectList(null);
        Map<Long,String> categoryMap = new HashedMap();
        for (CategoryEntity category : categoryList) {
            categoryMap.put(category.getId(),category.getName());
        }

        return categoryMap;
    }
}
