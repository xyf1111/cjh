package io.chaoshua.modules.app.form;

import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2018/11/23 0023.
 */
@ApiModel("任务导入")
public class ImportTaskForm {
    @ApiModelProperty("任务ID")
    private Long missionId;
    @ApiModelProperty("淘宝订单编号")
    private String taobaoCode;
    @ApiModelProperty("足迹图")
    private String footprint;
    @ApiModelProperty("宝贝聊天图")
    private String chat;
    @ApiModelProperty("收藏图")
    private String collectionCom;
    @ApiModelProperty("已付款截图")
    private String paied;

    public String getTaobaoCode() {
        return taobaoCode;
    }

    public void setTaobaoCode(String taobaoCode) {
        this.taobaoCode = taobaoCode;
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getFootprint() {
        return footprint;
    }

    public void setFootprint(String footprint) {
        this.footprint = footprint;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getCollectionCom() {
        return collectionCom;
    }

    public void setCollectionCom(String collectionCom) {
        this.collectionCom = collectionCom;
    }

    public String getPaied() {
        return paied;
    }

    public void setPaied(String paied) {
        this.paied = paied;
    }

    public static MissionDetailEntity toEntity(ImportTaskForm form){
        MissionDetailEntity missionDetailEntity = new MissionDetailEntity();
        missionDetailEntity.setMissionId(form.getMissionId());
        missionDetailEntity.setFootprint(form.getFootprint());
        missionDetailEntity.setCollectionCom(form.getCollectionCom());
        missionDetailEntity.setChat(form.getChat());
        missionDetailEntity.setPaied(form.getPaied());
        return missionDetailEntity;
    }
}
