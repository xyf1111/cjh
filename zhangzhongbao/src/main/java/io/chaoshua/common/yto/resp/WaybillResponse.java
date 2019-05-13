package io.chaoshua.common.yto.resp;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-12 11:53
 */
public class WaybillResponse {
    /**
     * 订单号
     */
    @JSONField(name = "OrderNo")
    private String orderNo;
    /**
     * 快递单号
     */
    @JSONField(name = "WaybillNo")
    private String waybillNo;
    /**
     * 是否获取失败
     */
    @JSONField(name = "IsError")
    private String isError;
    /**
     * 备注，用于记录获取失败原因
     */
    @JSONField(name = "Remark")
    private String remark;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getIsError() {
        return isError;
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
