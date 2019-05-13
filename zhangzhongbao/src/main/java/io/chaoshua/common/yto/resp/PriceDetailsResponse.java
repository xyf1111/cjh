package io.chaoshua.common.yto.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;

/**
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-11 17:36
 */
public class PriceDetailsResponse {
    /**
     * 物流公司类型
     * 可选值。4:圆通
     */
    @JSONField(name = "LogiType")
    private Integer logiType;
    /**
     * 单价
     */
    @JSONField(name = "Price")
    private BigDecimal price;

    public Integer getLogiType() {
        return logiType;
    }

    public void setLogiType(Integer logiType) {
        this.logiType = logiType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PriceDetailsResponse{" +
                "logiType=" + logiType +
                ", price=" + price +
                '}';
    }
}
