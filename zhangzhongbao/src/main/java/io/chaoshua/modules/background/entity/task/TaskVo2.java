package io.chaoshua.modules.background.entity.task;

import java.util.Date;

/**
 * 店铺拒绝限制实体
 * Created by dws on 2019/1/4 0004.
 */
public class TaskVo2 {
    /**
     * 店铺ID
     */
    private Long shopId;
    /**
     * 拒绝店铺最新限制时间
     */
    private Date createTime;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
