package io.chaoshua.modules.app.vo.user;

import io.chaoshua.modules.background.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by dws on 2018/11/20 0020.
 */
@ApiModel("我的资料")
public class UserInfoVo {
    @ApiModelProperty("刷手ID")
    private Long userId;
    @ApiModelProperty("淘宝号")
    private String taobao;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("微信号")
    private String weChat;
    @ApiModelProperty("开户行")
    private String bank;
    @ApiModelProperty("开户人")
    private String holder;
    @ApiModelProperty("银行卡号")
    private String cardNumber;
    @ApiModelProperty("可提现佣金")
    private BigDecimal freeMoney;
    @ApiModelProperty("不可提现佣金")
    private BigDecimal stockMoney;
    @ApiModelProperty("用户余额")
    private BigDecimal balance;
    @ApiModelProperty("收货地址")
    private String address;
    @ApiModelProperty("收件人姓名")
    private String receiveName;
    @ApiModelProperty("收件人联系方式")
    private String receiveMobile;
    @ApiModelProperty("提现底线限制")
    private Integer withLimit;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTaobao() {
        return taobao;
    }

    public void setTaobao(String taobao) {
        this.taobao = taobao;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getStockMoney() {
        return stockMoney;
    }

    public void setStockMoney(BigDecimal stockMoney) {
        this.stockMoney = stockMoney;
    }

    public BigDecimal getFreeMoney() {
        return freeMoney;
    }

    public void setFreeMoney(BigDecimal freeMoney) {
        this.freeMoney = freeMoney;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public Integer getWithLimit() {
        return withLimit;
    }

    public void setWithLimit(Integer withLimit) {
        this.withLimit = withLimit;
    }

    public static UserInfoVo toUserInfoVo(UserEntity userEntity){
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setUserId(userEntity.getId());
        userInfoVo.setMobile(userEntity.getMobile());
        userInfoVo.setName(userEntity.getName());
        userInfoVo.setWeChat(userEntity.getWechat());
        userInfoVo.setBank(userEntity.getBank());
        userInfoVo.setHolder(userEntity.getHolder());
        userInfoVo.setCardNumber(userEntity.getCardNumber());
        userInfoVo.setFreeMoney(userEntity.getFreeMoney());
        userInfoVo.setStockMoney(userEntity.getStockMoney());
        userInfoVo.setTaobao(userEntity.getTaobao());
        userInfoVo.setBalance(userEntity.getFreeMoney().add(userEntity.getStockMoney()));
        userInfoVo.setWithLimit(userEntity.getWithLimit());
        return userInfoVo;
    }
}
