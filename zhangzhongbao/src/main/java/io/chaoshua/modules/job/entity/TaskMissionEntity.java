package io.chaoshua.modules.job.entity;

import java.util.Date;

/**
 * Created by dws on 2018/12/18 0018.
 */
public class TaskMissionEntity {
    /**
     * 订单ID
     */
    private Long id;
    /**
     * 任务ID
     */
    private Long taskId;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 订单编号
     */
    private String missionCode;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 类目名称
     */
    private String shopCategory;
    /**
     * 执行时间
     */
    private Date missionTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMissionCode() {
        return missionCode;
    }

    public void setMissionCode(String missionCode) {
        this.missionCode = missionCode;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public Date getMissionTime() {
        return missionTime;
    }

    public void setMissionTime(Date missionTime) {
        this.missionTime = missionTime;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
