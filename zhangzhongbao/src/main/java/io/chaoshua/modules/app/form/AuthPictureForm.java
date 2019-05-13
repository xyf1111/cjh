package io.chaoshua.modules.app.form;

import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by dws on 2018/11/23 0023.
 */
@ApiModel("认证信息")
public class AuthPictureForm {

    @ApiModelProperty("订单Id")
    private Long missionId;
    @ApiModelProperty("小号评价截图")
    private String xiaohao;
    @ApiModelProperty("已买宝贝截图")
    private String buy;
    @ApiModelProperty("评价截图")
    private String check;

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getXiaohao() {
        return xiaohao;
    }

    public void setXiaohao(String xiaohao) {
        this.xiaohao = xiaohao;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public static MissionDetailEntity toEntity(AuthPictureForm authPictureForm){
        MissionDetailEntity missionDetailEntity = new MissionDetailEntity();
        missionDetailEntity.setMissionId(authPictureForm.getMissionId());
        missionDetailEntity.setXiaohao(authPictureForm.getXiaohao());
        missionDetailEntity.setBuy(authPictureForm.getBuy());
        missionDetailEntity.setCheck(authPictureForm.getCheck());

        return missionDetailEntity;
    }
}
