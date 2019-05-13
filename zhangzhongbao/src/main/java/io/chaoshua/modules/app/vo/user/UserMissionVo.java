package io.chaoshua.modules.app.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2018/12/6 0006.
 */
@ApiModel("用户是否需要认证")
public class UserMissionVo {
    @ApiModelProperty("是否认证（true:是，false:否）")
    private boolean isAuth;
    @ApiModelProperty("订单ID")
    private Long missionId;

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }
}
