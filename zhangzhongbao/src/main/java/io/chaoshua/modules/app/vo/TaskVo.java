package io.chaoshua.modules.app.vo;

import io.chaoshua.modules.background.entity.task.TaskEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dws on 2018/11/22 0022.
 */
@ApiModel("任务Vo")
public class TaskVo {
    @ApiModelProperty("任务ID")
    private Long taskId;
    @ApiModelProperty("发布时间")
    private Date publishTime;
    @ApiModelProperty("任务类型")
    private String taskStyle;
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("店铺类目")
    private String shopCategory;
    @ApiModelProperty("单笔佣金")
    private BigDecimal userPay;
    @ApiModelProperty("关键字")
    private String keyWord;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getTaskStyle() {
        return taskStyle;
    }

    public void setTaskStyle(String taskStyle) {
        this.taskStyle = taskStyle;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public BigDecimal getUserPay() {
        return userPay;
    }

    public void setUserPay(BigDecimal userPay) {
        this.userPay = userPay;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public static TaskVo toVo(TaskEntity taskEntity){
        TaskVo taskVo = new TaskVo();
        taskVo.setTaskId(taskEntity.getId());
        taskVo.setPublishTime(taskEntity.getPublishTime());
        taskVo.setTaskStyle(taskEntity.getTaskStyleStr());
        taskVo.setKeyWord(taskEntity.getKeyword());
        if (taskEntity.getShopName() != null) {
            char[] c = taskEntity.getShopName().toCharArray();
            for (int i = 0; i < c.length; i++) {
                // 第偶数个字符替换成*
                if (i % 2 != 1) {
                    c[i] = '*';
                }
                taskVo.setShopName(new String(c));
            }
        }
        taskVo.setShopCategory(taskEntity.getShopCategory());
        taskVo.setUserPay(taskEntity.getUserPay());
        return taskVo;
    }

    public static List<TaskVo> toList(List<TaskEntity> list){
        List<TaskVo> list1 = new ArrayList<>();
        for (TaskEntity taskEntity : list){
            TaskVo taskVo = TaskVo.toVo(taskEntity);
            list1.add(taskVo);
        }
        return list1;
    }
}
