package io.chaoshua.modules.background.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.modules.background.entity.DetailEntity;
import io.chaoshua.modules.background.entity.ShopEntity;
import io.chaoshua.modules.background.service.ShopService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.SellerEntity;
import io.chaoshua.modules.background.service.SellerService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 商家
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-25 17:22:27
 */
@RestController
@RequestMapping("background/seller")
public class SellerController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SellerService sellerService;
    @Autowired
    private ShopService shopService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:seller:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sellerService.queryPage(params);
        return R.ok().put("page", page);
    }
    /**
     * 获取所有商家列表
     */
    @RequestMapping("/getList")
    public R getList(@RequestParam Map<String, Object> params){
        List<SellerEntity> list = sellerService.selectList(new EntityWrapper<SellerEntity>().eq("status",2));
        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:seller:info")
    public R info(@PathVariable("id") Long id){
        SellerEntity seller = sellerService.selectById(id);
        ShopEntity shopEntity = shopService.selectOne(new EntityWrapper<ShopEntity>().eq("seller_id",id));
        seller.setCategoryId(shopEntity.getCategory());
        seller.setName(shopEntity.getName());
        seller.setUrl(shopEntity.getUrl());
        return R.ok().put("seller", seller);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:seller:save")
    public R save(@RequestBody Map<String,Object> params){
        return sellerService.insertByMap(params);
    }

    /**
     * 修改(审核)
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:seller:update")
    @Transactional
    public R update(@RequestBody SellerEntity seller){
        if (!StringUtils.isEmpty(seller.getPassword()) && !StringUtils.isEmpty(seller.getPasswordAgain())){
            if (!seller.getPassword().equals(seller.getPasswordAgain())){
                return R.error(500,"前后密码不一致！");
            }else {
                seller.setPassword(BCrypt.hashpw(seller.getPassword(),BCrypt.gensalt()));
            }
        }
        seller.setUpdateTime(new Date());
        sellerService.updateById(seller);
        if (seller.getStatus() != null && seller.getStatus() == 2){
            // 注册商家通过审核，他注册连带的店铺也通过了审核
            //2--通过审核 ,3--不通过审核
            ShopEntity shopEntity =  shopService.selectOne(new EntityWrapper<ShopEntity>().eq("seller_id",seller.getId()));
            ShopEntity shop = new ShopEntity();
            shop.setRole(3);
            shop.setId(shopEntity.getId());
            shop.setPassTime(new Date(System.currentTimeMillis()));
            shop.setUpdateTime(new Date(System.currentTimeMillis()));
            shopService.updateById(shop);
        }
        return R.ok();
    }

    /**
     * 批量修改
     */
    @RequestMapping("/updateBatch")
    @RequiresPermissions("background:seller:update")
    public R updateBatch(@RequestBody SellerEntity seller){
        String [] ids = seller.getIds();
        for (int i = 0;i<ids.length;i++){
            SellerEntity sellerEntity = new SellerEntity();
            sellerEntity.setId(Long.parseLong(ids[i].trim()));
            sellerEntity.setUpdateTime(new Date());
            sellerEntity.setIsVip(seller.getIsVip());
            sellerEntity.setLimit(seller.getLimit());
            sellerEntity.setBenefit(seller.getBenefit());
            sellerService.updateById(sellerEntity);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:seller:delete")
    public R delete(@RequestBody Long[] ids){
			sellerService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 修改余额
     * @return
     */
    @RequestMapping("/updateBalance")
    public R updateBalance(@RequestBody DetailEntity detailEntity){
        return  sellerService.updateBalance(detailEntity);
    }

}
