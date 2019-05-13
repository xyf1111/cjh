package io.chaoshua.common.yto.req;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

/**
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-11 17:54
 */
public class OrderSubmitRequest extends CommonRequest{

    /**
     * 物流公司类型 可选值。4:圆通
     */
    private Integer logiType;
    /**
     * 1.要提交的订单集合（Order[]）的Json格式的字符串。
     * 2.支持多个订单批量提交。
     */
    private List<OrderRequest> orders;

    public Integer getLogiType() {
        return logiType;
    }

    public void setLogiType(Integer logiType) {
        this.logiType = logiType;
    }

    public List<OrderRequest> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderRequest> orders) {
        this.orders = orders;
    }

    public static Map<String, String> toMap(OrderSubmitRequest request) {
        Map<String, String> map = CommonRequest.toMap(request);
        map.put("LogiType", String.valueOf(request.getLogiType()));
        map.put("Orders", JSON.toJSONString(request.getOrders()));

        return map;
    }
}
