package io.chaoshua.common.yto.resp;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-12 11:51
 */
public class OrderSubmitResponse {

    @JSONField(name = "Code")
    private Integer code;

    @JSONField(name = "Message")
    private String message;

    @JSONField(name = "IsError")
    private Boolean isError;

    /**
     * 扣费金额
     */
    @JSONField(name = "Payment")
    private BigDecimal payment;
    /**
     * 物流单号数据
     */
    @JSONField(name = "Data")
    private List<WaybillResponse> data;

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public List<WaybillResponse> getData() {
        return data;
    }

    public void setData(List<WaybillResponse> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }
}
