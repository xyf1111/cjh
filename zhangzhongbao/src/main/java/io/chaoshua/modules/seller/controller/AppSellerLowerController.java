package io.chaoshua.modules.seller.controller;

import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.entity.SellerEntity;
import io.chaoshua.modules.background.entity.ShopEntity;
import io.chaoshua.modules.background.service.SellerService;
import io.chaoshua.modules.background.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 商家下级Controller
 * Created by dws on 2018/12/20 0020.
 */
@RestController
@RequestMapping("/app/seller/sellerLower")
public class AppSellerLowerController {
@Autowired
private SellerService sellerService;
@Autowired
private ShopService shopService;

        /**
         * 列表
         */
        @RequestMapping("/list")
        @Login
        public R list(@RequestParam Map<String, Object> params, @ApiIgnore @RequestAttribute("userId") Long sellerId){
            params.put("pid",sellerId);
            PageUtils page = sellerService.queryPage(params);

            return R.ok().put("page", page);
        }


        /**
         * 信息
         */
        @RequestMapping("/info/{id}")
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
        public R save(@RequestBody Map<String,Object> params){
            return sellerService.insertByMap(params);
        }

        /**
         * 修改
         */
        @RequestMapping("/update")
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
            if (seller.getStatus() == 2){
                ShopEntity shopEntity =  shopService.selectOne(new EntityWrapper<ShopEntity>().eq("seller_id",seller.getId()));
                ShopEntity shop = new ShopEntity();
                shop.setRole(3);
                shop.setId(shopEntity.getId());
                shop.setUpdateTime(new Date());
                shopService.updateById(shop);
            }
            return R.ok();
        }
        /**
         * 批量修改
         */
        @RequestMapping("/updateBatch")
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
        public R delete(@RequestBody Long[] ids){
            sellerService.deleteBatchIds(Arrays.asList(ids));

            return R.ok();
        }

}
