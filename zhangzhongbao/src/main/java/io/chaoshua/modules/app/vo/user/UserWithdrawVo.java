package io.chaoshua.modules.app.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by dws on 2018/11/20 0020.
 */
@ApiModel("用户提现")
public class UserWithdrawVo {
    @ApiModelProperty("银行卡号")
    private String cardNumber;
    @ApiModelProperty("提现金额")
    private BigDecimal money;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
