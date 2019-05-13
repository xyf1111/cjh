package io.chaoshua.modules.seller.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.common.utils.AppResult;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.entity.InvitationStepEntity;
import io.chaoshua.modules.background.entity.SellerEntity;
import io.chaoshua.modules.background.entity.ShopEntity;
import io.chaoshua.modules.background.service.CategoryService;
import io.chaoshua.modules.background.service.InvitationStepService;
import io.chaoshua.modules.background.service.SellerService;
import io.chaoshua.modules.background.service.ShopService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 店铺表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-24 17:28:54
 */
@RestController
@RequestMapping("app/seller/shop")
public class AppSellerShopController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private InvitationStepService invitationStepService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SellerService sellerService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public AppResult list(@RequestParam Map<String, Object> params,@ApiIgnore @RequestAttribute("userId") Long sellerId){
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            params.put("sellerId",sellerId);
            PageUtils page = shopService.queryPage(params);
            appResult.setData(page);
        }catch (Exception e){
            e.printStackTrace();
            appResult = AppResult.error(500,"系统错误！");
        }

        return appResult;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShopEntity shop = shopService.selectById(id);
        return R.ok().put("shop", shop);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Login
    @ApiImplicitParams(value = {
        @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public R save(@RequestBody ShopEntity shop,@ApiIgnore @RequestAttribute("userId") Long sellerId){
        InvitationStepEntity invitationStepEntity = invitationStepService.selectById(1);
        SellerEntity sellerEntity = sellerService.selectById(sellerId);
        String ids [] = shop.getCategoryIds();
        shop.setCategoryId( StringUtils.join(ids,","));
        Long categoryId = Long.parseLong(ids[ids.length - 1]);
        String categoryName = categoryService.selectById(categoryId).getName();
        String address = shop.getAddress();
        String[] addresses = address.split(",");
        if(addresses.length != 4) {
            return R.error("新增店铺失败！地址格式输入有误！地址请按照：‘省,市,县(区),详细地址’的格式中间以,隔开");
        }
        shop.setCategory(categoryName);
        shop.setTotal(invitationStepEntity.getShopPublish());
        shop.setInterval(invitationStepEntity.getShopInterval());
        shop.setCreateTime(new Date());
        shop.setSellerId(sellerId);
        shop.setRole(2);
        shop.setContact(sellerEntity.getContact());
        shop.setMobile(sellerEntity.getMobile());
        shopService.insert(shop);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ShopEntity shop){
		shopService.updateById(shop);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		shopService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    /**
     * 获取店铺列表
     */
    @GetMapping("/getShopList")
    @Login
    public R getShopList(@ApiIgnore @RequestAttribute("userId") Long sellerId){
        List<ShopEntity> list =  shopService.selectList(new EntityWrapper<ShopEntity>().eq("seller_id",sellerId).eq("status",1).eq("role",3));
        return R.ok().put("list",list);
    }

}
