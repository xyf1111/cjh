package io.chaoshua.modules.app.vo;

import io.chaoshua.modules.background.entity.PictureStepEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2018/10/24 0024.
 */
@ApiModel("二维码设置")
public class PictureStepVo {

    @ApiModelProperty("小号截图二维码")
    private String xiaohaoRegister;

    @ApiModelProperty("小号截图示范")
    private String xiaohaoExample;

    @ApiModelProperty("已购买订单二维码")
    private String buyRegister;

    @ApiModelProperty("已购买订单示范")
    private String buyExample;

    @ApiModelProperty("性别二维码")
    private String sexRegister;

    @ApiModelProperty("支付宝二维码")
    private String aliRegister;

    @ApiModelProperty("性别二维码示范")
    private String sexExample;

    @ApiModelProperty("体验中心示范")
    private String checkExample;

    @ApiModelProperty("体验注册")
    private String checkCode;


    @ApiModelProperty("是否显示二维码")
    private Integer checkStyle;

    @ApiModelProperty("体验中心二维码")
    private String checkRegister;

    public String getXiaohaoRegister() {
        return xiaohaoRegister;
    }

    public void setXiaohaoRegister(String xiaohaoRegister) {
        this.xiaohaoRegister = xiaohaoRegister;
    }

    public String getXiaohaoExample() {
        return xiaohaoExample;
    }

    public void setXiaohaoExample(String xiaohaoExample) {
        this.xiaohaoExample = xiaohaoExample;
    }

    public String getBuyRegister() {
        return buyRegister;
    }

    public void setBuyRegister(String buyRegister) {
        this.buyRegister = buyRegister;
    }

    public String getBuyExample() {
        return buyExample;
    }

    public void setBuyExample(String buyExample) {
        this.buyExample = buyExample;
    }

    public String getSexRegister() {
        return sexRegister;
    }

    public void setSexRegister(String sexRegister) {
        this.sexRegister = sexRegister;
    }

    public String getAliRegister() {
        return aliRegister;
    }

    public void setAliRegister(String aliRegister) {
        this.aliRegister = aliRegister;
    }

    public String getSexExample() {
        return sexExample;
    }

    public void setSexExample(String sexExample) {
        this.sexExample = sexExample;
    }

    public String getCheckExample() {
        return checkExample;
    }

    public void setCheckExample(String checkExample) {
        this.checkExample = checkExample;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public Integer getCheckStyle() {
        return checkStyle;
    }

    public void setCheckStyle(Integer checkStyle) {
        this.checkStyle = checkStyle;
    }

    public String getCheckRegister() {
        return checkRegister;
    }

    public void setCheckRegister(String checkRegister) {
        this.checkRegister = checkRegister;
    }

    public static PictureStepVo toVo(PictureStepEntity pictureStepEntity){
        PictureStepVo pictureStepVo = new PictureStepVo();
        pictureStepVo.setXiaohaoRegister(pictureStepEntity.getXiaohaoRegister());
        pictureStepVo.setXiaohaoExample(pictureStepEntity.getXiaohaoExample());
        pictureStepVo.setBuyRegister(pictureStepEntity.getBuyRegister());
        pictureStepVo.setBuyExample(pictureStepEntity.getBuyExample());
        pictureStepVo.setAliRegister(pictureStepEntity.getAliRegister());
        pictureStepVo.setSexRegister(pictureStepEntity.getSexRegister());
        pictureStepVo.setSexExample(pictureStepEntity.getSexExample());
        pictureStepVo.setCheckRegister(pictureStepEntity.getCheckRegister());
        pictureStepVo.setCheckExample(pictureStepEntity.getCheckExample());
        pictureStepVo.setCheckCode(pictureStepEntity.getCheckCode());
        return pictureStepVo;
    }
}
