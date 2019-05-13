package io.chaoshua.modules.background.dao;

import io.chaoshua.modules.background.entity.WithdrawalsEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 刷手提现
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-31 14:55:16
 */
@Mapper
public interface WithdrawalsDao extends BaseMapper<WithdrawalsEntity> {

    /**
     * 根据状态获取刷手提现总金额
     * @param params
     * @return
     */
    List<WithdrawalsEntity> getSumMoney(Map<String,Object> params);
}
