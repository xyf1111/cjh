package io.chaoshua.modules.background.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.alibaba.druid.util.StringUtils;
import io.chaoshua.modules.background.entity.CategoryEntity;
import io.chaoshua.modules.background.service.CategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.ShopEntity;
import io.chaoshua.modules.background.service.ShopService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 店铺表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-24 17:28:54
 */
@RestController
@RequestMapping("background/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:shop:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shopService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:shop:info")
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
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:shop:save")
    public R save(@RequestBody ShopEntity shop){
		shopService.insert(shop);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:shop:update")
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
    @RequiresPermissions("background:shop:update")
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
    @RequiresPermissions("background:shop:delete")
    public R delete(@RequestBody Long[] ids){
			shopService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
