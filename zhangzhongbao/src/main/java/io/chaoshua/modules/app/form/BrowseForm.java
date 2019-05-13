package io.chaoshua.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2018/11/23 0023.
 */
@ApiModel("浏览导入1")
public class BrowseForm {
    @ApiModelProperty("任务ID")
    private Long missionId;
    @ApiModelProperty("浏览图片列表")
    private String imgList;
    @ApiModelProperty("任务类型（0：精刷单，1：标签二天，2：标签三天）")
    private Integer taskStyle;

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getImgList() {
        return imgList;
    }

    public void setImgList(String imgList) {
        this.imgList = imgList;
    }

    public Integer getTaskStyle() {
        return taskStyle;
    }

    public void setTaskStyle(Integer taskStyle) {
        this.taskStyle = taskStyle;
    }
}
