package io.chaoshua.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2018/12/8 0008.
 */
@ApiModel("指定追评Vo")
public class AddCommentForm {

    @ApiModelProperty("订单ID")
    private Long missionId;
    @ApiModelProperty("追评截图")
    private String addComImg;

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getAddComImg() {
        return addComImg;
    }

    public void setAddComImg(String addComImg) {
        this.addComImg = addComImg;
    }
}
