package io.chaoshua.modules.background.service.impl;

import io.chaoshua.modules.background.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.ShopDao;
import io.chaoshua.modules.background.entity.ShopEntity;
import io.chaoshua.modules.background.service.ShopService;


@Service("shopService")
public class ShopServiceImpl extends ServiceImpl<ShopDao, ShopEntity> implements ShopService {

    @Autowired
    private SellerService sellerService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ShopEntity> page = new Query<ShopEntity>(params).getPage();
        List<ShopEntity> list = baseMapper.getList(page,params);
        for (ShopEntity shop : list) {
            shop.setAgentName(sellerService.getAgentNameBySellerId(shop.getSellerId()));
        }
        page.setRecords(list);

        return new PageUtils(page);
    }

    @Override
    public PageUtils getShopListByPid(Map<String, Object> params) {
        Page<ShopEntity> page =  new Query<ShopEntity>(params).getPage();
        page.setRecords(baseMapper.getShopListByPid(page,params));
        return new PageUtils(page);
    }
}
