package io.chaoshua.modules.background.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import io.chaoshua.common.utils.R;
import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.validator.Assert;
import io.chaoshua.modules.agent.entity.AgentSellerMoneyEntity;
import io.chaoshua.modules.agent.service.AgentSellerMoneyService;
import io.chaoshua.modules.background.entity.*;
import io.chaoshua.modules.background.entity.agent.AgentEntity;
import io.chaoshua.modules.background.service.*;
import io.chaoshua.modules.background.service.agent.AgentService;
import io.chaoshua.modules.seller.vo.BindAgentVO;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.SellerDao;
import org.springframework.transaction.annotation.Transactional;

@Service("sellerService")
public class SellerServiceImpl extends ServiceImpl<SellerDao, SellerEntity> implements SellerService {
    /**
     * 代理商家名称 （key: sellerId；value: agentName）
     */
    private static Map<Long, String> agentNameMap = new HashedMap();

    @Autowired
    private InvitationStepService invitationStepService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private DetailService detailService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentSellerMoneyService agentSellerMoneyService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SellerEntity> page = new Query<SellerEntity>(params).getPage();
        List<SellerEntity> list = baseMapper.getList(page, params);
        for (SellerEntity seller : list) {
            // 代理商名称
            seller.setAgentName(this.getAgentNameBySellerId(seller.getId()));
        }
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public R insertByMap(Map<String, Object> params) {
        if (params != null) {
            String password = params.get("password").toString();
            String passwordAgain = params.get("passwordAgain").toString();
            String mobile = null;
            Integer type = 0;
            Integer flag = null;
            if (!password.equals(passwordAgain)) {
                return R.error(500, "前后密码不一致");
            }
            SellerEntity sellerEntity = JSON.parseObject(JSON.toJSONString(params), SellerEntity.class);
            if (baseMapper.selectCount(new EntityWrapper<SellerEntity>().eq("mobile", sellerEntity.getMobile())) > 0) {
                return R.error(500, "该手机号已注册!");
            }
            InvitationStepEntity invitationStepEntity = invitationStepService.selectById(1);//添加商家
            if (params.containsKey("type")) {
                if (!StringUtils.isEmpty(params.get("type").toString())) {
                    type = Integer.parseInt(params.get("type").toString());
                }
            }
            if (params.containsKey("pidMobile")) {
                mobile = params.get("pidMobile").toString();
            }
            if (type == 1) {//1:刷手邀请，2：商家邀请，3：代理邀请
                UserEntity userEntity = userService.selectOne(new EntityWrapper<UserEntity>().eq("mobile", mobile));
                if (userEntity != null) {
                    sellerEntity.setPid(userEntity.getId());
                    sellerEntity.setIsSeller(2);
                } else {
                    return R.error(500, "对不起，邀请您的刷手不存在！");
                }
            } else if (type == 2) {
                SellerEntity seller = new SellerEntity();
                seller.setMobile(mobile);
                SellerEntity sellerEntity1 = baseMapper.selectOne(seller);
                if (sellerEntity1 != null) {
                    sellerEntity.setPid(sellerEntity1.getId());
                    sellerEntity.setIsSeller(1);
                } else {
                    return R.error(500, "对不起，邀请您的商家不存在！");
                }
            } else if (type == 3) {
                AgentEntity agentEntity = agentService.selectOne(new EntityWrapper<AgentEntity>().eq("mobile", mobile));
                if (agentEntity != null) {
                    sellerEntity.setPid(agentEntity.getId());
                    sellerEntity.setIsSeller(3);
                } else {
                    return R.error(500, "对不起，没有该代理商");
                }
            }
            if (!StringUtils.isEmpty(params.get("flag").toString())) {//1:平台端，2：商家端
                flag = Integer.parseInt(params.get("flag").toString());
            }
            if (flag == 1) {
                sellerEntity.setStatus(2);
            } else if (flag == 2) {
                sellerEntity.setStatus(1);
            }
            sellerEntity.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            sellerEntity.setCreateTime(new Date());
            sellerEntity.setLimit(invitationStepEntity.getSellerLimit());
            if (baseMapper.insert(sellerEntity) > 0) {
                if (type == 3) {
                    AgentSellerMoneyEntity agentSellerMoneyEntity = new AgentSellerMoneyEntity();
                    agentSellerMoneyEntity.setSellerId(sellerEntity.getId());
                    agentSellerMoneyEntity.setAgentId(sellerEntity.getPid());
                    agentSellerMoneyEntity.setCreateTime(new Date());
                    agentSellerMoneyService.insert(agentSellerMoneyEntity);
                }
                /* 去掉商品注册时候的店铺信息 */
                String categoryIds = params.get("categoryId").toString();
                String[] ids = categoryIds.split(",");
                String id = ids[ids.length - 1];
                CategoryEntity categoryEntity = categoryService.selectById(id);
                ShopEntity shopEntity = JSON.parseObject(JSON.toJSONString(params), ShopEntity.class);//添加店铺
                shopEntity.setInterval(invitationStepEntity.getShopInterval());
                shopEntity.setTotal(invitationStepEntity.getShopPublish());
                shopEntity.setCreateTime(new Date(System.currentTimeMillis()));
                shopEntity.setSellerId(sellerEntity.getId());
                shopEntity.setName(params.get("shopName").toString());
                shopEntity.setCategory(categoryEntity.getName());
                shopEntity.setCategoryId(categoryIds);
                if (flag == 1) {
                    shopEntity.setRole(3);
                    shopEntity.setPassTime(new Date());
                } else if (flag == 2) {
                    shopEntity.setRole(2);
                }
                shopEntity.setIsDefault(1);
                shopService.insert(shopEntity);
            }
        }
        return R.ok();
    }


    @Override
    @Transactional
    public R updateBalance(DetailEntity detailEntity) {
        Long sellerId = detailEntity.getUserId();
        SellerEntity sellerEntity = baseMapper.selectById(sellerId);
        SellerEntity seller = new SellerEntity();
        if (detailEntity.getFlag() == 1) {
            seller.setBalance(sellerEntity.getBalance().add(detailEntity.getAmount()));
        } else if (detailEntity.getFlag() == 2) {
            if (sellerEntity.getBalance().compareTo(detailEntity.getAmount()) == -1){
                seller.setBalance(new BigDecimal(0));
            } else {
                seller.setBalance(sellerEntity.getBalance().subtract(detailEntity.getAmount()));
            }
        } else {
            throw new RRException("操作类型有误");
        }
        seller.setId(sellerEntity.getId());
        seller.setAmount(detailEntity.getAmount());
        seller.setUpdateTime(new Date(System.currentTimeMillis()));
        if (baseMapper.updateById(seller) > 0) {
            detailEntity.setCreateTime(new Date(System.currentTimeMillis()));
            detailEntity.setUserName(sellerEntity.getContact());
            detailEntity.setMobile(sellerEntity.getMobile());
            detailEntity.setBalance(seller.getBalance());
            detailService.insert(detailEntity);
            return R.ok();
        } else {
            return R.error("修改余额失败!");
        }
    }

    @Override
    @Transactional
    public R updateBalanceByAgentId(DetailEntity detailEntity, Long agentId) {
        AgentEntity agentEntity = agentService.selectById(agentId);
        Long sellerId = detailEntity.getUserId();
        BigDecimal amount = detailEntity.getAmount();
        SellerEntity sellerEntity = baseMapper.selectById(sellerId);//修改商家余额
        if (detailEntity.getFlag() == 2) {
            if ((sellerEntity.getBalance().add(amount)).compareTo(new BigDecimal(0)) < 0) {
                return R.error(500, "修改金额错误!");
            }
        }
        if (agentEntity.getBalance().compareTo(amount) < 0) {
            return R.error(500, "余额不足!");
        }
        AgentEntity agent = new AgentEntity();
        agent.setBalance(agentEntity.getBalance().subtract(amount));
        agent.setId(agentId);
        agentService.updateById(agent);//修改代理商余额
        SellerEntity seller = new SellerEntity();
        seller.setBalance(sellerEntity.getBalance().add(amount));
        seller.setId(sellerEntity.getId());
        seller.setAmount(detailEntity.getAmount());
        seller.setUpdateTime(new Date());
        if (baseMapper.updateById(seller) > 0) {//生成商家充值流水
            detailEntity.setCreateTime(new Date());
            detailEntity.setBalance(sellerEntity.getBalance().add(amount));
            if (detailService.insert(detailEntity)) {//生成代理商充值流水
                DetailEntity detailEntity1 = new DetailEntity();
                detailEntity1.setBalance(agentEntity.getBalance());
                detailEntity1.setCreateTime(new Date());
                detailEntity1.setType(15);
                detailEntity1.setIsSeller(3);
                detailEntity1.setUserId(agentId);
                detailEntity1.setAmount(detailEntity.getAmount());
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("给").append(sellerEntity.getContact()).append("充值扣除").append(amount).append("元");
                detailEntity1.setNote(stringBuffer.toString());
                detailService.insert(detailEntity1);
            }
            return R.ok();
        } else {
            return R.error("修改余额失败!");
        }
    }

    @Override
    public String getAgentNameBySellerId(Long sellerId) {
        if (agentNameMap.containsKey(sellerId)) {
            return agentNameMap.get(sellerId);
        }
        SellerEntity seller = baseMapper.selectById(sellerId);
        String agentName = "";
        // 3 代理下的商家
        if (seller != null && seller.getPid() != null) {
            AgentEntity agent = agentService.selectById(seller.getPid());
            if (agent != null) {
                agentName = agent.getName();
            }
        }
        agentNameMap.put(sellerId, agentName);
        return agentName;
    }

    @Override
    public Integer bindAgentCheck(String mobile) {
        // 获取商家信息
        SellerEntity sellerEntity = this.queryByMobile(mobile);
        return sellerEntity == null ? 0 : 1;
    }

    @Override
    public void bindAgent(BindAgentVO params) {
        // 获取商家信息
        SellerEntity sellerEntity = this.queryByMobile(params.getMobile());

        // 校验商家是否存在
        Assert.isNull(sellerEntity, "该手机号未注册商家");
        // 校验密码
        if (!BCrypt.checkpw(params.getPassword(), sellerEntity.getPassword())) {
            throw new RRException("密码校验不通过，请输入正确密码");
        }

        // 获取代理商家
        AgentEntity agentEntity = agentService.queryByMobile(params.getAgentMobile());
        if (agentEntity == null) {
            throw new RRException("代理商家不存在");
        }
        // 校验是否绑定同一代理商
        if (sellerEntity.getIsSeller() == 3 && agentEntity.getId().equals(sellerEntity.getPid())) {
            throw new RRException("您已绑定该代理商，请勿重复绑定");
        }

        // 代理商下商家 3
        sellerEntity.setIsSeller(3);
        // 上级（代理商家）ID
        sellerEntity.setPid(agentEntity.getId());
        // 更新时间
        sellerEntity.setUpdateTime(new Date());
        // 更换代理商家名称
        agentNameMap.put(sellerEntity.getId(), agentEntity.getName());
        // 更新绑定信息
        this.updateById(sellerEntity);
        // 初始化代理商家金额
        this.initAgentSellerMoney(agentEntity.getId(),sellerEntity.getId());
    }

    @Override
    public SellerEntity queryByMobile(String mobile) {
        return this.selectOne(new EntityWrapper<SellerEntity>().eq("mobile", mobile));
    }

    /**
     * 初始化代理商家金额
     *
     * @param agentId  代理商ID
     * @param sellerId 商家ID
     */
    private void initAgentSellerMoney(Long agentId, Long sellerId) {
        AgentSellerMoneyEntity agentSellerMoney = agentSellerMoneyService.queryBySellerId(sellerId);
        if (agentSellerMoney == null) {
            // 未绑定 创建金额记录
            AgentSellerMoneyEntity agentSellerMoneyEntity = new AgentSellerMoneyEntity();
            agentSellerMoneyEntity.setAgentId(agentId);
            agentSellerMoneyEntity.setSellerId(sellerId);
            agentSellerMoneyEntity.setCreateTime(new Date());
            agentSellerMoneyEntity.setUpdateTime(new Date());
            agentSellerMoneyService.insert(agentSellerMoneyEntity);
        } else {
            // 已绑定过代理商，更新所属代理商
            AgentSellerMoneyEntity agentSellerMoneyEntity = new AgentSellerMoneyEntity();
            agentSellerMoneyEntity.setAgentId(agentId);
            agentSellerMoneyService.updateById(agentSellerMoneyEntity);
        }
    }
}
