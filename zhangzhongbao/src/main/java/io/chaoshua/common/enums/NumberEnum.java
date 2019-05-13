package io.chaoshua.common.enums;

/**
 * 数字枚举类
 *
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-02-25 14:56
 */
public enum NumberEnum {
    /**
     * 阿拉伯数字，中文数字
     */
    ONE(1, "一"), TWO(2, "二"), THREE(3, "三"), FOUR(4, "四"), FIVE(5, "五");

    private Integer key;
    private String value;

    NumberEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 根据阿拉伯数字获取中文数字
     *
     * @param key 阿拉伯数字
     * @return
     */
    public static String getValueByKey(Integer key) {
        for (NumberEnum number : NumberEnum.values()) {
            if (number.getKey().equals(key)) {
                return number.getValue();
            }
        }
        // 默认返回三
        return NumberEnum.THREE.getValue();
    }
}
