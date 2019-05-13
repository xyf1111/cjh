package io.chaoshua.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2019/1/9 0009.
 */
@ApiModel("示例Vo")
public class ImgVo {
    @ApiModelProperty("示例名称")
    private  String name;
    @ApiModelProperty("示例图片")
    private String[] imgList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getImgList() {
        return imgList;
    }

    public void setImgList(String[] imgList) {
        this.imgList = imgList;
    }
}
