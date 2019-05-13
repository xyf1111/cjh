package io.chaoshua.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by dws on 2019/1/11 0011.
 */
@ApiModel(value = "每日认证图片")
public class UserAuthImgVo {
    @ApiModelProperty("认证图片")
    private List<UserImgVo> authImgs;
    @ApiModelProperty("订单ID")
    private Long missionId;

    public List<UserImgVo> getAuthImgs() {
        return authImgs;
    }

    public void setAuthImgs(List<UserImgVo> authImgs) {
        this.authImgs = authImgs;
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }
}
