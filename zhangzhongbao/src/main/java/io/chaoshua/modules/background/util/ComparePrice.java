package io.chaoshua.modules.background.util;

import io.chaoshua.modules.agent.entity.AgentSellerMoneyEntity;

import io.chaoshua.common.exception.RRException;
import io.chaoshua.modules.background.entity.IntervalStepEntity;
import io.chaoshua.modules.background.entity.agent.AgentMoneyEntity;

import java.math.BigDecimal;

/**
 * Created by dws on 2018/12/27 0027.
 */
public class ComparePrice {

    /**
     * 获取代理下商家所支付每单（精刷单）佣金
     *
     * @param price
     * @return
     */
    public static BigDecimal balanceMoney1(BigDecimal price, BigDecimal benefit, AgentSellerMoneyEntity agentSellerMoneyEntity) {
        int money = price.intValue();
        BigDecimal money1 = null;
        if (money >= 0 && money <= 99) {
            money1 = agentSellerMoneyEntity.getOne();
        } else if (money >= 100 && money <= 199) {
            money1 = agentSellerMoneyEntity.getTwo();
        } else if (money >= 200 && money <= 299) {
            money1 = agentSellerMoneyEntity.getThree();
        } else if (money >= 300 && money <= 499) {
            money1 = agentSellerMoneyEntity.getFour();
        } else if (money >= 500 && money <= 999) {
            money1 = agentSellerMoneyEntity.getFive();
        } else if (money >= 1000 && money <= 1999) {
            money1 = agentSellerMoneyEntity.getSix();
        } else if (money >= 2000 && money <= 2999) {
            money1 = agentSellerMoneyEntity.getSeven();
        } else if (money >= 3000 && money <= 3999) {
            money1 = agentSellerMoneyEntity.getEight();
        } else if (money >= 4000 && money <= 4999) {
            money1 = agentSellerMoneyEntity.getNine();
        } else {
            money1 = agentSellerMoneyEntity.getTen();
        }
        money1.add(benefit);
        return money1;
    }

    /**
     * 代理下商家所获取每单（精刷单）利润
     *
     * @param price
     * @return
     */
    public static BigDecimal getAgentMoney(BigDecimal price, AgentSellerMoneyEntity agentSellerMoneyEntity, AgentMoneyEntity agentMoneyEntity) {
        int money = price.intValue();
        BigDecimal money1 = null;
        if (agentSellerMoneyEntity.getCreateTime().compareTo(agentSellerMoneyEntity.getUpdateTime()) == 0) {
            throw new RRException("上级代理商未设置发单金额，请联系上级代理商！");
        }
        if (money >= 0 && money <= 99) {
            money1 = agentSellerMoneyEntity.getOne().subtract(agentMoneyEntity.getOne());
        } else if (money >= 100 && money <= 199) {
            money1 = agentSellerMoneyEntity.getTwo().subtract(agentMoneyEntity.getTwo());
        } else if (money >= 200 && money <= 299) {
            money1 = agentSellerMoneyEntity.getThree().subtract(agentMoneyEntity.getThree());
        } else if (money >= 300 && money <= 499) {
            money1 = agentSellerMoneyEntity.getFour().subtract(agentMoneyEntity.getFour());
        } else if (money >= 500 && money <= 999) {
            money1 = agentSellerMoneyEntity.getFive().subtract(agentMoneyEntity.getFive());
        } else if (money >= 1000 && money <= 1999) {
            money1 = agentSellerMoneyEntity.getSix().subtract(agentMoneyEntity.getSix());
        } else if (money >= 2000 && money <= 2999) {
            money1 = agentSellerMoneyEntity.getSeven().subtract(agentMoneyEntity.getSeven());
        } else if (money >= 3000 && money <= 3999) {
            money1 = agentSellerMoneyEntity.getEight().subtract(agentMoneyEntity.getEight());
        } else if (money >= 4000 && money <= 4999) {
            money1 = agentSellerMoneyEntity.getNine().subtract(agentMoneyEntity.getNine());
        } else {
            money1 = agentSellerMoneyEntity.getTen().subtract(agentMoneyEntity.getTen());
        }
        return money1;
    }

    /**
     * 获取平台下商家所支付每单（精刷单）佣金
     */
    public static BigDecimal getSellerPayCommissionMoney(BigDecimal price, BigDecimal benefit, IntervalStepEntity intervalStepEntity) {
        int money = price.intValue();
        BigDecimal money1 = null;
        /*if (money >= 1 && money <= 100) {
            money1 = intervalStepEntity.getOne();
        } else if (money >= 100 && money <= 199) {
            money1 = intervalStepEntity.getTwo();
        } else if (money >= 200 && money <= 299) {
            money1 = intervalStepEntity.getThree();
        } else if (money >= 300 && money <= 499) {
            money1 = intervalStepEntity.getFour();
        } else if (money >= 500 && money <= 999) {
            money1 = intervalStepEntity.getFive();
        } else if (money >= 1000 && money <= 1999) {
            money1 = intervalStepEntity.getSix();
        } else if (money >= 2000 && money <= 2999) {
            money1 = intervalStepEntity.getSeven();
        } else if (money >= 3000 && money <= 3999) {
            money1 = intervalStepEntity.getEight();
        } else if (money >= 4000 && money <= 4999) {
            money1 = intervalStepEntity.getNine();
        } else {
            money1 = intervalStepEntity.getTen();
        }*/
        if (money > 0 && money <= 50) {
            money1 = intervalStepEntity.getSellerCommission1();
        } else if (money > 50 && money <= 200) {
            money1 = intervalStepEntity.getSellerCommission2();
        } else if (money > 200 && money <= 300) {
            money1 = intervalStepEntity.getSellerCommission3();
        } else if (money > 300 && money <= 400) {
            money1 = intervalStepEntity.getSellerCommission4();
        } else if (money > 400 && money <= 550) {
            money1 = intervalStepEntity.getSellerCommission5();
        } else if (money > 550 && money <= 900) {
            money1 = intervalStepEntity.getSellerCommission6();
        } else if (money > 900 && money <= 1200) {
            money1 = intervalStepEntity.getSellerCommission7();
        } else if (money > 1200 && money <= 1500) {
            money1 = intervalStepEntity.getSellerCommission8();
        } else if (money > 1500 && money <= 2000) {
            money1 = intervalStepEntity.getSellerCommission9();
        } else if (money > 2000 && money <= 2500) {
            money1 = intervalStepEntity.getSellerCommission10();
        } else if (money > 2500 && money <= 3000) {
            money1 = intervalStepEntity.getSellerCommission11();
        } else if (money > 3000 && money <= 4000) {
            money1 = intervalStepEntity.getSellerCommission12();
        } else if (money > 4000 && money <= 5000) {
            money1 = intervalStepEntity.getSellerCommission13();
        } else if (money > 5000 && money <= 6000) {
            money1 = intervalStepEntity.getSellerCommission14();
        } else if (money > 6000 && money <= 7000) {
            money1 = intervalStepEntity.getSellerCommission15();
        } else if (money > 7000 && money <= 8000) {
            money1 = intervalStepEntity.getSellerCommission16();
        } else if (money > 8000 && money <= 9000) {
            money1 = intervalStepEntity.getSellerCommission17();
        } else if (money > 9000 && money <= 10000) {
            money1 = intervalStepEntity.getSellerCommission18();
        } else if (money > 10000)  {
            money1 = intervalStepEntity.getSellerCommission19();
        }
        money1 = money1.add(benefit);
        return money1;
    }

    /**
     * 获取刷手所得每单佣金
     */
    public static BigDecimal getUserPayCommissionMoney(BigDecimal price, Integer taskStyle, IntervalStepEntity intervalStepEntity) {
        int money = price.intValue();
        BigDecimal money1 = null;
        /*if (money >= 0 && money <= 300) {
            money1 = intervalStepEntity.getFir();
        } else if (money >= 301 && money <= 600) {
            money1 = intervalStepEntity.getSec();
        } else if (money >= 601 && money <= 1000) {
            money1 = intervalStepEntity.getThi();
        } else if (money >= 1001 && money <= 2000) {
            money1 = intervalStepEntity.getFourth();
        } else {
            money1 = intervalStepEntity.getFifth();
        }*/
        if (money > 0 && money <= 300) {
            money1 = intervalStepEntity.getUserCommission1();
        } else if (money > 300 && money <= 550) {
            money1 = intervalStepEntity.getUserCommission2();
        } else if (money > 550 && money <= 900) {
            money1 = intervalStepEntity.getUserCommission3();
        } else if (money > 900 && money <= 1500) {
            money1 = intervalStepEntity.getUserCommission4();
        } else if (money > 1500 && money <= 2000) {
            money1 = intervalStepEntity.getUserCommission5();
        } else if (money > 2000 && money <= 2500) {
            money1 = intervalStepEntity.getUserCommission6();
        } else if (money > 2500 && money <= 3000) {
            money1 = intervalStepEntity.getUserCommission7();
        } else if (money > 3000 && money <= 4000) {
            money1 = intervalStepEntity.getUserCommission8();
        } else if (money > 4000 && money <= 5000) {
            money1 = intervalStepEntity.getUserCommission9();
        } else if (money > 5000 && money <= 6000) {
            money1 = intervalStepEntity.getUserCommission10();
        } else if (money > 6000 && money <= 7000) {
            money1 = intervalStepEntity.getUserCommission11();
        } else if (money > 7000 && money <= 8000) {
            money1 = intervalStepEntity.getUserCommission12();
        } else if (money > 8000){
            money1 = intervalStepEntity.getUserCommission13();
        }

        if (taskStyle == 1) {//,1:标签二天，2：标签三天
            money1 = money1.add(intervalStepEntity.getAppointOneUser());
        } else if (taskStyle == 2) {
            money1 = money1.add(intervalStepEntity.getAppointTwoUser());
        }
        return money1;
    }

    /**
     * 获取标签任务商家所支付总金额
     *
     * @param taskStyle 0:精刷单,1:标签二天，2：标签三天，3：流量单
     * @return
     */
    public static BigDecimal getViewPay(Integer taskStyle, Integer amount, IntervalStepEntity intervalStepEntity) {
        BigDecimal money = null;
        if (taskStyle == 1) {
            money = intervalStepEntity.getAppointOneSeller().multiply(new BigDecimal(amount));
        } else if (taskStyle == 2) {
            money = intervalStepEntity.getAppointTwoSeller().multiply(new BigDecimal(amount));
        }
        return money;
    }

}
