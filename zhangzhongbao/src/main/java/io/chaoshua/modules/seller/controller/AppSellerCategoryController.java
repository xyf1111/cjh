package io.chaoshua.modules.seller.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.modules.background.entity.CategoryEntity;
import io.chaoshua.modules.background.service.CategoryService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取产品类目
 * Created by dws on 2018/11/30 0030.
 */
@RestController
@RequestMapping("/app/seller/category")
public class AppSellerCategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 列表
     */
    @RequestMapping("/getList")
    public List<Map<String, Object>> getList() {
        Map map = new HashedMap();
        map.put("parent_id", 0);
        List<CategoryEntity> list = categoryService.selectByMap(map);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (CategoryEntity categoryEntity : list) {
            Map map1 = new HashedMap();
            map1.put("label", categoryEntity.getName());
            map1.put("value", categoryEntity.getId());
            map1.put("limit", categoryEntity.getLimit());
            List<CategoryEntity> list1 = categoryService.selectList(new EntityWrapper<CategoryEntity>().eq("parent_id", categoryEntity.getId()));
            List<Map<String, Object>> mapList1 = new ArrayList<>();
            if (list1 != null && list1.size() > 0) {
                for (CategoryEntity categoryEntity1 : list1) {
                    Map map2 = new HashedMap();
                    map2.put("label", categoryEntity1.getName());
                    map2.put("value", categoryEntity1.getId());
                    map2.put("limit", categoryEntity1.getLimit());
                    mapList1.add(map2);
                }
                map1.put("children", mapList1);
            }
            mapList.add(map1);
        }
        return mapList;
    }

}
