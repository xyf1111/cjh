package io.chaoshua.modules.app.vo;

import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dws on 2018/11/21 0021.
 */
@ApiModel("订单列表")
public class MissionListVo {
    @ApiModelProperty("订单ID")
    private Long missionId;
    @ApiModelProperty("任务编号")
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

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

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

    public static MissionListVo toVo(MissionEntity missionEntity){
        MissionListVo missionListVo = new MissionListVo();
        missionListVo.setMissionId(missionEntity.getId());
        missionListVo.setPublishTime(missionEntity.getPublishTime());
        missionListVo.setShopCategory(missionEntity.getShopCategory());
        missionListVo.setTaskId(missionEntity.getTaskId());
        if (missionEntity.getShopName() != null){//奇数*替换
            char [] c = missionEntity.getShopName().toCharArray();
            for (int i = 0;i<c.length;i++){
                if (i %2 == 0){
                    c[i] = '*';
                }
            }
            String str = new String(c);
            missionListVo.setShopName(str);
        }
        missionListVo.setShopName(missionEntity.getShopName());
        missionListVo.setUserPay(missionEntity.getUserPay());
        missionListVo.setTaskStyle(missionEntity.getTaskStyleStr());
        return missionListVo;
    }

    public static List<MissionListVo> toVoList(List<MissionEntity> list){
        List<MissionListVo> voList = new ArrayList<>();
        for (MissionEntity missionEntity : list){
            MissionListVo missionListVo = MissionListVo.toVo(missionEntity);
            voList.add(missionListVo);
        }
        return voList;
    }
}
