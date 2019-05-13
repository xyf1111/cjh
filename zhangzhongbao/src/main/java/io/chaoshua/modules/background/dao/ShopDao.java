package io.chaoshua.modules.background.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.chaoshua.modules.background.entity.ShopEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 店铺表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-24 17:28:54
 */
@Mapper
public interface ShopDao extends BaseMapper<ShopEntity> {

    /**
     * 每日更新发单剩余量
     */
    void updateAll();

    /**
     *根店铺列表
     * @param params
     * @return
     */
    List<ShopEntity> getList(Pagination pagination, Map<String,Object> params);

    /******************************代理商家开始*************************************************/
    /**
     *根据商家上级id获取店铺列表
     * @param params
     * @return
     */
    List<ShopEntity> getShopListByPid(Pagination pagination, Map<String,Object> params);
    /******************************代理商家结束*************************************************/
}
