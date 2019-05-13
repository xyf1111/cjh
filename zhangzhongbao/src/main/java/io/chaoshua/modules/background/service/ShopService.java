package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.ShopEntity;

import java.util.List;
import java.util.Map;

/**
 * 店铺表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-24 17:28:54
 */
public interface ShopService extends IService<ShopEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /******************************代理商家开始*************************************************/
    /**
     *根据商家上级id获取店铺列表
     * @param params
     * @return
     */
    PageUtils getShopListByPid( Map<String,Object> params);
    /******************************代理商家结束*************************************************/
}

