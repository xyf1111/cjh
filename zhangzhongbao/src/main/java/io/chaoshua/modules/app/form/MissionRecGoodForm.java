package io.chaoshua.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2018/11/23 0023.
 */
@ApiModel("确认收获")
public class MissionRecGoodForm {
    @ApiModelProperty("任务ID")
    private Long missionId;
    @ApiModelProperty("确认收货截图")
    private String receive;
    @ApiModelProperty("评价截图")
    private String commentImg;

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public String getCommentImg() {
        return commentImg;
    }

    public void setCommentImg(String commentImg) {
        this.commentImg = commentImg;
    }

}
