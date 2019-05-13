package io.chaoshua.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2018/11/23 0023.
 */
@ApiModel("确认付款")
public class MissionPayForm {
    @ApiModelProperty("订单ID")
    private Long missionId;
    @ApiModelProperty("付款截图")
    private String paied;
    @ApiModelProperty("淘宝订单号")
    private String taobaoCode;

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getPaied() {
        return paied;
    }

    public void setPaied(String paied) {
        this.paied = paied;
    }

    public String getTaobaoCode() {
        return taobaoCode;
    }

    public void setTaobaoCode(String taobaoCode) {
        this.taobaoCode = taobaoCode;
    }
}
