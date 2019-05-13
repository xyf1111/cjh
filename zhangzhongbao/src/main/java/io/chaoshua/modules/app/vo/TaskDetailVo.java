package io.chaoshua.modules.app.vo;

import cn.hutool.core.util.RandomUtil;
import io.chaoshua.common.enums.NumberEnum;
import io.chaoshua.modules.background.entity.task.TaskEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

/**
 * Created by dws on 2018/11/22 0022.
 */
@ApiModel("任务详情")
public class TaskDetailVo {
    @ApiModelProperty("任务ID")
    private Long taskId;
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("店铺接单限制")
    private Integer interval;
    @ApiModelProperty("店铺类目")
    private String shopCategory;
    @ApiModelProperty("同类目接单限制")
    private Integer limit;
    @ApiModelProperty("任务要求")
    private String note;
    @ApiModelProperty("其他要求")
    private String otherNote;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOtherNote() {
        return otherNote;
    }

    public void setOtherNote(String otherNote) {
        this.otherNote = otherNote;
    }

    public static TaskDetailVo toVo(TaskEntity taskEntity){
        TaskDetailVo taskDetailVo = new TaskDetailVo();
        taskDetailVo.setTaskId(taskEntity.getId());
        if (StringUtils.isNotBlank(taskEntity.getNote())){
            Integer randomKey = RandomUtil.randomInt(2,5);
            System.out.println("货比一到四家：" + randomKey);
            taskDetailVo.setNote(StringUtils.replace(taskEntity.getNote(),
                    "货比一到四家",String.format("货比一到%s家", NumberEnum.getValueByKey(randomKey))));
        }
        taskDetailVo.setShopCategory(taskEntity.getShopCategory());
        taskDetailVo.setOtherNote(taskEntity.getOtherNote());
        taskDetailVo.setLimit(taskEntity.getLimit());
        if (taskEntity.getShopName() != null) {
            char[] c = taskEntity.getShopName().toCharArray();
            for (int i = 0; i < c.length; i++) {
                // 第偶数个字符替换成*
                if (i % 2 == 1) {
                    c[i] = '*';
                }
            }
            taskDetailVo.setShopName(new String(c));
        }
        taskDetailVo.setInterval(taskEntity.getInterval());
        return taskDetailVo;
    }
}
