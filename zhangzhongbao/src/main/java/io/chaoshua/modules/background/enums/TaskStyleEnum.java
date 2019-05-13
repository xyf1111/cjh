package io.chaoshua.modules.background.enums;

/**
 * 任务类型
 *
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-27 15:07
 */
public enum TaskStyleEnum {
    /**
     * 任务类型
     */
    FINE(0, "精刷单"), LABEL_TWO(1, "标签2天"), LABEL_THREE(2, "标签3天");

    TaskStyleEnum(Integer value, String name) {
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
        TaskStyleEnum[] taskStyleEnums = TaskStyleEnum.values();
        for (TaskStyleEnum style : taskStyleEnums) {
            if (style.getValue().equals(value)) {
                return style.getName();
            }
        }
        return "";
    }

    /**
     * 根据name值获取value
     *
     * @param name 名称
     * @return
     */
    public static Integer getValueByName(String name) {
        if (name == null) {
            return null;
        }
        TaskStyleEnum[] taskStyleEnums = TaskStyleEnum.values();
        for (TaskStyleEnum style : taskStyleEnums) {
            if (style.getName().equals(name.trim())) {
                return style.getValue();
            }
        }
        return null;
    }
}
