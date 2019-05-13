package io.chaoshua.modules.background.enums;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * 订单状态（本佣模式3，5合并）
 *
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-25 10:41
 */
public enum MissionStatusEnum {
    /**
     * 状态值，名称
     * 1订单待认证,2订单待拍下,3订单已拍下,
     * 4商家已汇款,5刷手已付款,6商家已发货,
     * 7刷手已收货（结单）,8平台已确认
     */
    WAIT_AUTH(1,"订单待认证"),WAIT_TAKE(2,"订单待拍下"),TAKEN(3,"订单已拍下"),SELLER_PAID(4,"平台已汇款"),
    USER_PAID(5,"刷手已付款"),SELLER_SEND(6,"商家已发货"),USER_RECEIVE(7,"刷手已收货"),PLATFORM_CHECK(8,"平台已确认");

    MissionStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 状态值
     */
    private Integer value;
    /**
     * 状态名称
     */
    private String name;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 根据value值获取name
     *
     * @param value 状态值
     * @return
     */
    public static String getNameByValue(Integer value) {
        MissionStatusEnum[] missionStatusEnums = MissionStatusEnum.values();
        for (MissionStatusEnum status : missionStatusEnums){
            if (status.getValue().equals(value)){
                return status.getName();
            }
        }
        return "";
    }
}
