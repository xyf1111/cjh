package io.chaoshua.modules.app.vo.user;

import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dws on 2018/11/20 0020.
 */
@ApiModel("用户不可用金额列表")
public class StateMoneyVo {
    @ApiModelProperty("任务ID")
    private Long missionId;
    @ApiModelProperty("任务编号")
    private Long taskId;
    @ApiModelProperty("关键词")
    private String keyWord;
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("单笔佣金")
    private BigDecimal userPay;
    @ApiModelProperty("日期")
    private Date createTime;
    @ApiModelProperty("订单状态")
    private String status;

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

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public BigDecimal getUserPay() {
        return userPay;
    }

    public void setUserPay(BigDecimal userPay) {
        this.userPay = userPay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static StateMoneyVo toVO(MissionEntity missionEntity){
        StateMoneyVo stateMoneyVo = new StateMoneyVo();
        stateMoneyVo.setMissionId(missionEntity.getId());
        stateMoneyVo.setTaskId(missionEntity.getTaskId());
        stateMoneyVo.setKeyWord(missionEntity.getKeyWord());
        if (missionEntity.getShopName() != null){//奇数*替换
            char [] c = missionEntity.getShopName().toCharArray();
            for (int i = 0;i<c.length;i++){
                if (i %2 == 0){
                    c[i] = '*';
                }
            }
            String str = new String(c);
            stateMoneyVo.setShopName(str);
        }
        stateMoneyVo.setUserPay(missionEntity.getUserPay());
        stateMoneyVo.setCreateTime(missionEntity.getCreateTime());
        Integer status = missionEntity.getStatus();
        if (status != null){//订单状态：1订单待认证,2订单待拍下,3订单已拍下,4商家已汇款,5刷手已付款,6商家已发货,7刷手已收获（结单）
            if (status == 1){
                stateMoneyVo.setStatus("订单待认证");
            }else if (status == 2){
                stateMoneyVo.setStatus("订单待拍下");
            }else if (status == 3){
                stateMoneyVo.setStatus("订单已拍下");
            }else if (status == 4){
                stateMoneyVo.setStatus("商家已汇款");
            }else if (status == 5){
                stateMoneyVo.setStatus("刷手已付款");
            }else if (status == 6){
                stateMoneyVo.setStatus("刷手已付款");
            }else if (status == 7){
                stateMoneyVo.setStatus("刷手已收获");
            }
        }
        return stateMoneyVo;
    }
}
