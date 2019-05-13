package io.chaoshua.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2019/1/11 0011.
 */
@ApiModel("注册认证图片")
public class UserImgVo {

    @ApiModelProperty("图片名称")
    private String name;
    @ApiModelProperty("图片地址")
    private String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
