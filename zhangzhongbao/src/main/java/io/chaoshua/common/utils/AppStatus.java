package io.chaoshua.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2018-04-19 18:14
 */
@ApiModel(value = "AppStatus",description = "返回参数")
public class AppStatus {
    @ApiModelProperty(value = "状态码,0表示成功，错误会返回相应状态码")
    private int code;
    @ApiModelProperty(value = "提示信息")
    private String msg;

    public AppStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public AppStatus() {
    }

    public static AppStatus success(){
        return new AppStatus(0,"success");
    }

    public static AppStatus error(int code, String msg){
        return new AppStatus(code,msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
