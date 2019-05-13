package io.chaoshua.modules.seller.vo;

/**
 *
 *
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-18 18:03
 */
public class BindAgentVO {

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 代理商手机
     */
    private String agentMobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAgentMobile() {
        return agentMobile;
    }

    public void setAgentMobile(String agentMobile) {
        this.agentMobile = agentMobile;
    }
}
