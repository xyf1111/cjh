package io.chaoshua.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by dws on 2018/12/21 0021.
 */
@ApiModel("IntervalStepVo")
public class IntervalStepVo {

    @ApiModelProperty("奖励金")
    private BigDecimal sellerMoney;

    public BigDecimal getSellerMoney() {
        return sellerMoney;
    }

    public void setSellerMoney(BigDecimal sellerMoney) {
        this.sellerMoney = sellerMoney;
    }
}
