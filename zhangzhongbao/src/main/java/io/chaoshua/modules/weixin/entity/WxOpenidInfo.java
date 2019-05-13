package io.chaoshua.modules.weixin.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/12/13 0013.
 */
public class WxOpenidInfo implements Serializable{
    private List<String> openid;

    public List<String> getOpenid() {
        return openid;
    }

    public void setOpenid(List<String> openid) {
        this.openid = openid;
    }
}
