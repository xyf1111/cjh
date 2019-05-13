package io.chaoshua.modules.background.dao;

import io.chaoshua.modules.background.entity.ChargeEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 商家充值记录表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-31 09:41:58
 */
@Mapper
public interface ChargeDao extends BaseMapper<ChargeEntity> {

    /**
     * (1:获取平台商家充值总额,2:获取代理充值总额,3:获取代理下商家充值总额)
     * @return
     */
    List<ChargeEntity> getSellerMoneyByIsSeller(Map map);

}
