package io.chaoshua.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2018-04-19 18:00
 */
@ApiModel("返回参数")
public class AppResult<T> extends AppStatus {

    @ApiModelProperty(value = "返回数据")
    private T data;

    public AppResult() {
    }

    private AppResult(int code, String msg) {
        super(code, msg);
    }

    public static AppResult success(){
        return new AppResult(0,"success");
    }

    public static AppResult error(int code, String msg){
        return new AppResult(code,msg);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
