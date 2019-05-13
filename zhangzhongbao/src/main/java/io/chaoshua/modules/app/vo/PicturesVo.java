package io.chaoshua.modules.app.vo;

import io.chaoshua.modules.background.entity.PictureStepEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2018/11/23 0023.
 */
@ApiModel("认证示例图")
public class PicturesVo {
    @ApiModelProperty("小号截图二维码")
    private String xiaohaoRegister;

    @ApiModelProperty("小号截图示范")
    private String xiaohaoExample;

    @ApiModelProperty("已购买订单二维码")
    private String buyRegister;

    @ApiModelProperty("已购买订单示范")
    private String buyExample;

    @ApiModelProperty("体验中心二维码")
    private String checkRegister;

    @ApiModelProperty("体验中心示范")
    private String checkExample;

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

    public String getCheckExample() {
        return checkExample;
    }

    public void setCheckExample(String checkExample) {
        this.checkExample = checkExample;
    }

    public String getCheckRegister() {
        return checkRegister;
    }

    public void setCheckRegister(String checkRegister) {
        this.checkRegister = checkRegister;
    }

    public static PicturesVo toVo(PictureStepEntity pictureStepEntity) {
        PicturesVo picturesVo = new PicturesVo();
        picturesVo.setXiaohaoRegister(pictureStepEntity.getXiaohaoRegister());
        picturesVo.setXiaohaoExample(pictureStepEntity.getXiaohaoExample());
        picturesVo.setBuyRegister(pictureStepEntity.getBuyRegister());
        picturesVo.setBuyExample(pictureStepEntity.getBuyExample());
        picturesVo.setCheckRegister(pictureStepEntity.getCheckRegister());
        picturesVo.setCheckExample(pictureStepEntity.getCheckExample());
        return picturesVo;
    }
}
