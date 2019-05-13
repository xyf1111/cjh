package io.chaoshua.modules.background.service.impl;

import io.chaoshua.common.utils.*;
import io.chaoshua.modules.background.entity.DetailEntity;
import io.chaoshua.modules.background.entity.UserEntity;
import io.chaoshua.modules.background.service.DetailService;
import io.chaoshua.modules.background.service.UserService;
import io.chaoshua.modules.weixin.service.SendInfoController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.chaoshua.modules.background.dao.WithdrawalsDao;
import io.chaoshua.modules.background.entity.WithdrawalsEntity;
import io.chaoshua.modules.background.service.WithdrawalsService;
import org.springframework.transaction.annotation.Transactional;

@Service("withdrawalsService")
public class WithdrawalsServiceImpl extends ServiceImpl<WithdrawalsDao, WithdrawalsEntity> implements WithdrawalsService {
@Autowired
private DetailService detailService;
@Autowired
private UserService userService;
@Autowired
private WithdrawalsService withdrawalsService;
@Autowired
private SendInfoController sendInfoController;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String holder = null;
        String mobile = null;
        String startTime = null;
        String endTime = null;
        if (params.containsKey("holder")){
            holder = params.get("holder").toString();
        }
        if (params.containsKey("mobile")){
            mobile = params.get("mobile").toString();
        }
        if (params.containsKey("startTime")){
            startTime = params.get("startTime").toString();
        }
        if (params.containsKey("endTime")){
            endTime = params.get("endTime").toString();
        }
        Page<WithdrawalsEntity> page = this.selectPage(
                new Query<WithdrawalsEntity>(params).getPage(),
                new EntityWrapper<WithdrawalsEntity>()
                .eq("role",params.get("role"))
                .like(!StringUtils.isEmpty(holder),"holder",holder)
                .like(!StringUtils.isEmpty(mobile),"mobile",mobile)
                .gt(!StringUtils.isEmpty(startTime),"create_time",startTime)
                .le(!StringUtils.isEmpty(endTime),"create_time",endTime)
                .orderBy("create_time",false)
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public R updateStatus(WithdrawalsEntity withdrawalsEntity) {
        List<WithdrawalsEntity> list = withdrawalsEntity.getList();
        for (WithdrawalsEntity withdrawalsEntity1: list) {
            withdrawalsEntity1.setRole(withdrawalsEntity.getRole());
            withdrawalsEntity1.setUpdateTime(new Date());
            baseMapper.updateById(withdrawalsEntity1);//修改提现状态
            UserEntity userEntity = userService.selectById(withdrawalsEntity1.getUserId());
            if (withdrawalsEntity.getRole() == 2) { //2：已提现
                DetailEntity detailEntity = new DetailEntity();
                detailEntity.setAmount(new BigDecimal(0).subtract(withdrawalsEntity1.getAmount()));
                detailEntity.setCreateTime(new Date(System.currentTimeMillis()));
                detailEntity.setUserId(withdrawalsEntity1.getUserId());
                detailEntity.setUserName(withdrawalsEntity1.getHolder());
                detailEntity.setCode(withdrawalsEntity1.getId().toString());
                detailEntity.setType(6);
                detailEntity.setMobile(userEntity.getMobile());
                detailEntity.setFree(userEntity.getFreeMoney());
                detailEntity.setBeforeFree(userEntity.getFreeMoney().add(withdrawalsEntity1.getAmount()));
                detailEntity.setStock(userEntity.getStockMoney());
                detailEntity.setBeforeStock(userEntity.getStockMoney());
                detailEntity.setIsSeller(2);
                detailEntity.setNote("提现金额为" + withdrawalsEntity1.getAmount());
                detailService.insert(detailEntity);//生成刷手流水
                if (userEntity.getOpenId() != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(withdrawalsEntity1.getId()).append("(").append(withdrawalsEntity1.getAmount()).append("元").append(")");
                    sendInfoController.sendInfo(userEntity.getOpenId(), "您好，您的提款已转至银行卡上", stringBuilder.toString(),
                            DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN), "预计1-2天到账,请注意查收");
                }
            } else if (withdrawalsEntity.getRole() == 3) { //3：已拒绝
                UserEntity user = new UserEntity();
                user.setId(userEntity.getId());
                user.setFreeMoney(userEntity.getFreeMoney().add(withdrawalsEntity1.getAmount()));
                userService.updateById(user);//修改刷手余额
                DetailEntity detailEntity = new DetailEntity();
                detailEntity.setAmount(withdrawalsEntity1.getAmount());
                detailEntity.setCreateTime(new Date(System.currentTimeMillis()));
                detailEntity.setUserId(withdrawalsEntity1.getUserId());
                detailEntity.setUserName(withdrawalsEntity1.getHolder());
                detailEntity.setCode(withdrawalsEntity1.getId().toString());
                detailEntity.setMobile(userEntity.getMobile());
                detailEntity.setType(6);
                detailEntity.setFree(user.getFreeMoney());
                detailEntity.setBeforeFree(user.getFreeMoney().subtract(withdrawalsEntity1.getAmount()));
                detailEntity.setStock(userEntity.getStockMoney());
                detailEntity.setBeforeStock(userEntity.getStockMoney());
                detailEntity.setIsSeller(2);
                detailEntity.setNote("提现被拒绝返还金额为" + withdrawalsEntity1.getAmount());
                detailService.insert(detailEntity);//生成刷手流水
                if (userEntity.getOpenId() != null) {
                    sendInfoController.sendInfo(userEntity.getOpenId(), "后台已拒绝您的提现申请", withdrawalsEntity1.getId().toString(),
                            DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN), "请确认提现金额后，重新提交申请！");
                }
            }
        }
        return R.ok();
    }

}
