package io.chaoshua.modules.background.service.impl;

import io.chaoshua.common.utils.R;
import io.chaoshua.common.utils.ShiroUtils;
import io.chaoshua.modules.background.entity.DetailEntity;
import io.chaoshua.modules.background.entity.SellerEntity;
import io.chaoshua.modules.background.entity.agent.AgentEntity;
import io.chaoshua.modules.background.service.DetailService;
import io.chaoshua.modules.background.service.SellerService;
import io.chaoshua.modules.background.service.agent.AgentService;
import io.chaoshua.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.ChargeDao;
import io.chaoshua.modules.background.entity.ChargeEntity;
import io.chaoshua.modules.background.service.ChargeService;
import org.springframework.transaction.annotation.Transactional;


@Service("chargeService")
public class ChargeServiceImpl extends ServiceImpl<ChargeDao, ChargeEntity> implements ChargeService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SellerService sellerService;
    @Autowired
    private DetailService detailService;
    @Autowired
    private AgentService agentService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String contact = null;
        String name = null;
        String status = null;
        String sellerId = null;
        String isSeller = null;
        String chargeId = null;
        String agentId = null;
        if (params.containsKey("name")) {
            name = params.get("name").toString();
        }
        if (params.containsKey("agentId")) {
            agentId = params.get("agentId").toString();
        }
        if (params.containsKey("chargeId")) {
            chargeId = params.get("chargeId").toString();
        }
        if (params.containsKey("status")) {
            status = params.get("status").toString();
        }
        if (params.containsKey("sellerId")) {
            sellerId = params.get("sellerId").toString();
        }
        if (params.containsKey("contact")) {
            contact = params.get("contact").toString();
        }
        if (params.containsKey("isSeller")) {
            isSeller = params.get("isSeller").toString();
        }
        Page<ChargeEntity> page = this.selectPage(
                new Query<ChargeEntity>(params).getPage(),
                new EntityWrapper<ChargeEntity>()
                        .eq(!StringUtils.isEmpty(status),"status",status)
                        .eq(!StringUtils.isEmpty(sellerId),"seller_id",sellerId)
                        .eq(!StringUtils.isEmpty(isSeller),"is_seller",isSeller)
                        .eq(!StringUtils.isEmpty(agentId),"agent_id",agentId)
                        .like(!StringUtils.isEmpty(chargeId),"id",chargeId)
                        .like(!StringUtils.isEmpty(contact), "contact", contact)
                        .like(!StringUtils.isEmpty(name),"name",name)
                        .orderBy("create_time", false)
        );

        for (ChargeEntity charge : page.getRecords()) {
            // 设置代理商名称
            charge.setAgentName(sellerService.getAgentNameBySellerId(charge.getSellerId()));
        }
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public R updateStatusById(ChargeEntity chargeEntity) {
        if (chargeEntity.getFlag() == 1){
            SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();
            chargeEntity.setInChargeId(sysUserEntity.getUserId());
            chargeEntity.setPlatformName(sysUserEntity.getUsername());
        }
        ChargeEntity charge = baseMapper.selectById(chargeEntity.getId());
        chargeEntity.setUpdateTime(new Date(System.currentTimeMillis()));
        chargeEntity.setTime(new Date(System.currentTimeMillis()));
        chargeEntity.setStatus(chargeEntity.getStatus());
        chargeEntity.setMistakeNote(chargeEntity.getMistakeNote());
        //修改充值状态
        if (chargeEntity.getStatus() == 2) {//1为商家，2为代理
            if(chargeEntity.getIsSeller() == 1){
                if (charge.getAgentId() != null){//代理下得商家
                    AgentEntity agentEntity = agentService.selectById(charge.getAgentId());
                    BigDecimal balance = agentEntity.getBalance();
                    if (balance.compareTo(charge.getAmount())< 0){
                        return R.error(500,"余额不足，无法审核通过");
                    }
                    AgentEntity agent = new AgentEntity();
                    agent.setBalance(agentEntity.getBalance().subtract(charge.getAmount()));
                    agent.setId(agentEntity.getId());
                    agentService.updateById(agent);
                }
                baseMapper.updateById(chargeEntity);
                SellerEntity sellerEntity = sellerService.selectById(chargeEntity.getSellerId());
                SellerEntity seller = new SellerEntity();
                seller.setBalance(sellerEntity.getBalance().add(charge.getAmount()));
                seller.setId(sellerEntity.getId());
                if (!sellerService.updateById(seller)) {//修改商家余额
                    return R.error(500, "修改金额失败!");
                } else {//生成流水
                    DetailEntity detailEntity = new DetailEntity();
                    detailEntity.setCode(chargeEntity.getId().toString());
                    detailEntity.setAmount(charge.getAmount());
                    detailEntity.setUserName(sellerEntity.getContact());
                    detailEntity.setMobile(sellerEntity.getMobile());
                    detailEntity.setIsSeller(1);
                    detailEntity.setCreateTime(new Date(System.currentTimeMillis()));
                    detailEntity.setType(1);
                    detailEntity.setUserId(chargeEntity.getSellerId());
                    detailEntity.setBalance(sellerEntity.getBalance().add(charge.getAmount()));
                    detailEntity.setNote("商户充值金额：" + charge.getAmount() + "元");
                    if (detailService.insert(detailEntity)) {
                        return R.ok();
                    } else {
                        return R.error(500, "生成商家充值流水失败!");
                    }
                }
            } else if (chargeEntity.getIsSeller() == 2){
                AgentEntity agentEntity = agentService.selectById(chargeEntity.getAgentId());
                agentEntity.setBalance(agentEntity.getBalance().add(charge.getAmount()));
                if (!agentService.updateById(agentEntity)){
                    return R.error(500, "修改金额失败!");
                } else {
                    baseMapper.updateById(chargeEntity);
                    DetailEntity detailEntity = new DetailEntity();
                    detailEntity.setCode(chargeEntity.getId().toString());
                    detailEntity.setAmount(charge.getAmount());
                    detailEntity.setIsSeller(3);
                    detailEntity.setCreateTime(new Date());
                    detailEntity.setType(11);
                    detailEntity.setUserId(chargeEntity.getSellerId());
                    detailEntity.setBalance(agentEntity.getBalance().add(charge.getAmount()));
                    if (detailService.insert(detailEntity)) {
                        return R.ok();
                    } else {
                        return R.error(500, "生成代理商充值流水失败!");
                    }
                }
            }
        }else {
            baseMapper.updateById(chargeEntity);
        }
        return R.ok();
    }
}
