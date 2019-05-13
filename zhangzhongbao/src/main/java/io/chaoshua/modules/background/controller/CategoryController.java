package io.chaoshua.modules.background.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.CategoryEntity;
import io.chaoshua.modules.background.service.CategoryService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;


/**
 * 店铺类目管理
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-11-12 18:39:56
 */
@RestController
@RequestMapping("background/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:category:list")
    public List<CategoryEntity> list() {
        List<CategoryEntity> list = categoryService.selectList(null);
        for (CategoryEntity categoryEntity : list) {
            CategoryEntity categoryEntity1 = categoryService.selectById(categoryEntity.getParentId());
            if (categoryEntity1 != null) {
                if (categoryEntity1.getName() != null) {
                    categoryEntity.setParentName(categoryEntity1.getName());
                }
            }
        }
        return list;
    }

    /**
     * 列表
     */
    @RequestMapping("/getList")
    @RequiresPermissions("background:category:list")
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


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:category:info")
    public R info(@PathVariable("id") Long id) {
        CategoryEntity category = categoryService.selectById(id);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:category:save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.insert(category);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:category:update")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:category:delete")
    public R delete(@RequestBody Long[] ids) {
        categoryService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
