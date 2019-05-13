package io.chaoshua.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2018/12/17 0017.
 */
@ApiModel("微信配置Vo")
public class WxStepVo {
    @ApiModelProperty("AppID")
    private String appId;
    @ApiModelProperty("Appsecret")
    private String appsecret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }
}
