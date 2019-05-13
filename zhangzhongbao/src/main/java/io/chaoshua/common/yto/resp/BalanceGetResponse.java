package io.chaoshua.common.yto.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Joiner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-11 17:35
 */
public class BalanceGetResponse {
    @JSONField(name = "Amount")
    private BigDecimal amount;
    @JSONField(name = "PriceDetails")
    private List<PriceDetailsResponse> priceDetails;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<PriceDetailsResponse> getPriceDetails() {
        return priceDetails;
    }

    public void setPriceDetails(List<PriceDetailsResponse> priceDetails) {
        this.priceDetails = priceDetails;
    }

    public String toString(){
        Joiner j = Joiner.on("|");
        return amount.toString() + "===" + j.join(priceDetails);
    }

}
