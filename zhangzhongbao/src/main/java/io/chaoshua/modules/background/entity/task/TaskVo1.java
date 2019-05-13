package io.chaoshua.modules.background.entity.task;

import java.util.Date;

/**
 * Created by dws on 2019/1/3 0003.
 */
public class TaskVo1 {
    /**
     * 任务ID
     */
    private Long id;
    /**
     * 店铺类目
     */
    private String shopCategory;
    /**
     * 类目ID
     */
    private Long shopCategoryId;
    /**
     * 店铺ID
     */
    private Long shopId;
    /**
     * 类目限制时间
     */
    private Date categoryTime;
    /**
     * d店铺限制时间
     */
    private Date shopTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Long shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Date getCategoryTime() {
        return categoryTime;
    }

    public void setCategoryTime(Date categoryTime) {
        this.categoryTime = categoryTime;
    }

    public Date getShopTime() {
        return shopTime;
    }

    public void setShopTime(Date shopTime) {
        this.shopTime = shopTime;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }
}
