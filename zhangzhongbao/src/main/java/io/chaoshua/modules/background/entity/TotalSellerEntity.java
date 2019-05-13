package io.chaoshua.modules.background.entity;

/**
 * 商家统计
 * Created by dws on 2019/1/23 0023.
 */
public class TotalSellerEntity {
    /**
     * 联系人
     */
    private String contact;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 完成总单数
     */
    private String sumRemain;
    /**
     * 报错总单数
     */
    private String sumBad;
    /**
     * 充值总金额
     */
    private String sumMoney;
    /**
     * 商家发布总单数
     */
    private String sumAmount;
    /**
     * 平台下商家总余额
     */
    private String sumBalance;
    /**
     * 代理下商家总余额
     */
    private String sumBalance1;

    /**
     * 上级类型
     */
    private Integer isSeller;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSumRemain() {
        return sumRemain;
    }

    public void setSumRemain(String sumRemain) {
        this.sumRemain = sumRemain;
    }

    public String getSumBad() {
        return sumBad;
    }

    public void setSumBad(String sumBad) {
        this.sumBad = sumBad;
    }

    public String getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(String sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(String sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getSumBalance() {
        return sumBalance;
    }

    public void setSumBalance(String sumBalance) {
        this.sumBalance = sumBalance;
    }

    public String getSumBalance1() {
        return sumBalance1;
    }

    public void setSumBalance1(String sumBalance1) {
        this.sumBalance1 = sumBalance1;
    }

    public Integer getIsSeller() {
        return isSeller;
    }

    public void setIsSeller(Integer isSeller) {
        this.isSeller = isSeller;
    }
}
