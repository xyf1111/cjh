package io.chaoshua.modules.background.controller;

import io.chaoshua.common.utils.R;
import io.chaoshua.modules.background.dao.*;
import io.chaoshua.modules.background.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据统计
 * Created by dws on 2019/1/22 0022.
 */
@RestController
@RequestMapping("/background/sum")
public class SumController {
@Autowired
private ChargeDao chargeDao;
@Autowired
private WithdrawalsDao withdrawalsDao;
@Autowired
private DetailDao detailDao;
@Autowired
private SellerDao sellerDao;
@Autowired
private UserDao userDao;

    @RequestMapping("/getInfo")
    public R getInfo(@RequestParam  Map params){
        String sellerMoney1 = null;//平台下商家充值总额
        String sellerMoney2 = null;//代理充值总额
        String sellerMoney3 = null;//代理下商家充值总额
        String sellerMoney4 = null;//刷手提现待审核总金额
        String sellerMoney5 = null;//刷手提现已审核总金额
        String sellerMoney6 = null;//平台修改刷手总金额
        String sellerMoney7 = null;//平台修改商家总金额
        String sellerMoney8 = null;//平台修改代理总金额
        String sellerMoney9 = null;//平台下商家总余额
        String sellerMoney10 = null;//代理下商家总余额
        String sellerMoney11 = null;//刷手总余额
        List<ChargeEntity> list = chargeDao.getSellerMoneyByIsSeller(params);
        if (list != null && list.size()>0){
            for (ChargeEntity chargeEntity :list){
                if (chargeEntity.getIsSeller() == 1){
                    sellerMoney1 = chargeEntity.getSellerMoney();
                }else if(chargeEntity.getIsSeller() == 2){
                    sellerMoney2 = chargeEntity.getSellerMoney();
                }else if (chargeEntity.getIsSeller() == 3){
                    sellerMoney3 = chargeEntity.getSellerMoney();
                }
            }
        }
        List<WithdrawalsEntity> list1 = withdrawalsDao.getSumMoney(params);
        if (list1.size()>0 && list1 != null){
            for (WithdrawalsEntity withdrawalsEntity :list1){
                if (withdrawalsEntity.getRole() == 1){
                    sellerMoney4 = withdrawalsEntity.getSumMoney();
                }
                if (withdrawalsEntity.getRole() == 2){
                    sellerMoney5 = withdrawalsEntity.getSumMoney();
                }
            }
        }

        List<DetailEntity> list2 = detailDao.getSumMoney(params);
        if (list2.size()>0 && list2 != null){
            for (DetailEntity detailEntity :list2){
                if (detailEntity.getIsSeller() == 1){
                    sellerMoney6 = detailEntity.getSumMoney();
                }
                if (detailEntity.getIsSeller() == 2){
                    sellerMoney7 = detailEntity.getSumMoney();
                }
                if (detailEntity.getIsSeller() == 3){
                    sellerMoney8 = detailEntity.getSumMoney();
                }
            }
        }
        //上级类型（0：平台，1：商家，2：刷手,3:代理商）
        List<TotalSellerEntity> list3 = sellerDao.getSumBalance(params);
        if (list3.size()>0 && list3 != null){
            BigDecimal total = new BigDecimal(0);
            for (TotalSellerEntity totalSellerEntity :list3){
                if (totalSellerEntity.getIsSeller() == 3){
                    sellerMoney10 = totalSellerEntity.getSumBalance();
                }else {
                    BigDecimal total1 = new BigDecimal(totalSellerEntity.getSumBalance());
                    total = total.add(total1);
                }
            }
            sellerMoney9 = total.toString();
        }
        UserEntity userEntity = userDao.getUserBalance(params);
        sellerMoney11 = userEntity.getSumBalance();
        Map map = new HashMap();
        map.put("sellerMoney1",sellerMoney1);
        map.put("sellerMoney2",sellerMoney2);
        map.put("sellerMoney3",sellerMoney3);
        map.put("sellerMoney4",sellerMoney4);
        map.put("sellerMoney5",sellerMoney5);
        map.put("sellerMoney6",sellerMoney6);
        map.put("sellerMoney7",sellerMoney7);
        map.put("sellerMoney8",sellerMoney8);
        map.put("sellerMoney9",sellerMoney9);
        map.put("sellerMoney10",sellerMoney10);
        map.put("sellerMoney11",sellerMoney11);
        return R.ok().put("data",map);
    }
}
