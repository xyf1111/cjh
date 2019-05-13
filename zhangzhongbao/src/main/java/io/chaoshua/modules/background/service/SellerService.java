package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.background.entity.DetailEntity;
import io.chaoshua.modules.background.entity.SellerEntity;
import io.chaoshua.modules.seller.vo.BindAgentVO;

import java.util.Map;

/**
 * 商家表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-25 17:22:27
 */
public interface SellerService extends IService<SellerEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 新增商家
     * @param params
     * @return
     */
    R insertByMap(Map<String,Object> params);

    /**
     * 修改余额
     * @param detailEntity
     * @return
     */
    R updateBalance(DetailEntity detailEntity);
    /**
     * 代理商修改商家余额
     * @param detailEntity
     * @return
     */
    R updateBalanceByAgentId(DetailEntity detailEntity,Long agentId);

    /**
     * 根据商家ID获取代理商家
     *
     * @param sellerId 商家ID
     * @return
     */
    String getAgentNameBySellerId(Long sellerId);

    /**
     * 绑定代理商校验
     *
     * @param mobile 商家手机号码
     * @return 是否已存在商家（可以绑定） 1 是 0 否
     */
    Integer bindAgentCheck(String mobile);

    /**
     * 绑定代理商
     *
     * @param params 参数
     */
    void bindAgent(BindAgentVO params);

    /**
     * 根据手机号码获取商家
     *
     * @param mobile 手机号码
     * @return
     */
    SellerEntity queryByMobile(String mobile);
}

