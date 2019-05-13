package io.chaoshua.modules.app.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by dws on 2018/11/26 0026.
 */
@ApiModel("我的下级")
public class LowUserVo {
    @ApiModelProperty("注册时间")
    private Date createTime;
    @ApiModelProperty("注册用户")
    private String userName;
    @ApiModelProperty("完成任务数")
    private Integer count;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName()
    {
        userName = userName.replace("*",userName.substring(1));
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
