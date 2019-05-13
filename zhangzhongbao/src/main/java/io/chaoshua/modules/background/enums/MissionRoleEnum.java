package io.chaoshua.modules.background.enums;

/**
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-26 10:59
 */
public enum  MissionRoleEnum {

    /**
     * 订单审核状态
     * 1、订单待审核
     * 2、审核通过
     * 3、已拒绝
     */
    INIT(0,"初始状态"),AUDIT(1,"待审核"),PASS(2,"审核通过"),REFUSE(3,"已拒绝");


    MissionRoleEnum(Integer value, String name) {
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
}
