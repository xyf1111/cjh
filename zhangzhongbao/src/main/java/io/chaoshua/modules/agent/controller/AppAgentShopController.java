package io.chaoshua.modules.agent.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.entity.CategoryEntity;
import io.chaoshua.modules.background.entity.ShopEntity;
import io.chaoshua.modules.background.service.CategoryService;
import io.chaoshua.modules.background.service.ShopService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * 店铺管理
 * Created by dws on 2018/12/26 0026.
 */
@RestController
@RequestMapping("/app/agent/shop")
public class AppAgentShopController {
@Autowired
private ShopService shopService;
@Autowired
private CategoryService categoryService;
    /**
     * 获取店铺列表
     * @return
     */
    @RequestMapping("/getList")
    @Login
    public R getList(@RequestParam Map<String,Object> params, @ApiIgnore @RequestAttribute("userId")Long agentId){
        params.put("pid",agentId);
        params.put("isSeller",3);
        PageUtils page = shopService.getShopListByPid(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShopEntity shop = shopService.selectById(id);
        if (shop.getCategoryId() != null){
            String[] ids = shop.getCategoryId().split(",");
            int in[] = new int[ids.length];
            for (int i = 0; i < ids.length; i++) {
                in[i] = Integer.parseInt(ids[i]);
            }
            shop.setCategoryIds1(in);
        }
        return R.ok().put("shop", shop);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ShopEntity shop){
        if (shop.getRole() != null){
            if (shop.getRole() == 3){
                shop.setPassTime(new Date());
            }
        }
        if (shop.getCategoryId() != null){
            String [] str = shop.getCategoryId().split(",");
            CategoryEntity categoryEntity = categoryService.selectById(Long.parseLong(str[str.length-1]));
            shop.setCategory(categoryEntity.getName());
        }
        shop.setUpdateTime(new Date());
        shopService.updateById(shop);
        return R.ok();
    }

    /**
     * 批量修改
     */
    @RequestMapping("/updateBatch")
    public R update(@RequestBody Map<String,Object> params){
        String  str = params.get("ids").toString();
        String [] ids = null;
        if (str != null && str.length()>0) {
            str = str.replace('[', ' ').replace(']', ' ').trim();
            ids = str.split(",");
        }
        String total = null;
        String interval = null;
        String categoryId = null;
        ShopEntity shopEntity = new ShopEntity();
        if (params.containsKey("total")){
            if (!StringUtils.isEmpty(params.get("total").toString())){
                total = params.get("total").toString();
                shopEntity.setTotal(Integer.parseInt(total));
            }
        }
        if (params.containsKey("interval")){
            if (!StringUtils.isEmpty(params.get("interval").toString())){
                interval = params.get("interval").toString();
                shopEntity.setInterval(Integer.parseInt(interval));
            }
        }
        if (params.containsKey("categoryId")){
            if (!StringUtils.isEmpty(params.get("categoryId").toString())){
                categoryId = params.get("categoryId").toString();
                shopEntity.setCategoryId(categoryId);
                String [] strings = categoryId.split(",");
                CategoryEntity categoryEntity = categoryService.selectById(strings[strings.length-1]);
                shopEntity.setCategory(categoryEntity.getName());
            }
        }
        for (int i = 0;i<ids.length;i++){
            shopEntity.setId(Long.parseLong(ids[i].toString().trim()));
            shopService.updateById(shopEntity);
        }
        return R.ok();
    }



    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        shopService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/category/getList")
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
