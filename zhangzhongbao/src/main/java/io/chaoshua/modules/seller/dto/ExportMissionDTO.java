package io.chaoshua.modules.seller.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-27 14:58
 */
public class ExportMissionDTO {
    /**
     * 收款账户
     */
    private String cardNumber;
    /**
     * 收款户名
     */
    private String holder;
    /**
     * 转账金额
     */
    private BigDecimal price;
    /**
     * 收款银行
     */
    private String bank;

    /**
     * 任务类型（0：精刷单，1：标签二天，2：标签三天）
     */
    private Integer taskStyle;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 淘宝名称
     */
    private String taobao;
    /**
     * 刷手名称
     */
    private String userName;
    /**
     * 淘宝订单号
     */
    private String taobaoCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 执行时间
     */
    private Date missionTime;
    /**
     * 订单状态：1订单待认证,2订单待拍下,3订单已拍下,4商家已汇款,5刷手已付款,6商家已发货,7刷手已收获（结单）
     */
    private Integer status;
    /**
     * 是否指定评价
     */
    private Integer isComment;
    /**
     * 是否指定追评
     */
    private Integer isAddcom;
    /**
     * 对于预约任务（标签二、三天），商家所需要支付的总浏览佣金（佣金规则中单价对应的浏览佣金*总单数）
     */
    private BigDecimal viewPay;
    /**
     * 商家发任务所需支付的总佣金（佣金规则单价对应支付佣金*总单数）
     */
    private BigDecimal sellerPay;
    /**
     * 任务总单数
     */
    private Integer amount;
    /**
     * 任务ID
     */
    private Long taskId;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Integer getTaskStyle() {
        return taskStyle;
    }

    public void setTaskStyle(Integer taskStyle) {
        this.taskStyle = taskStyle;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTaobao() {
        return taobao;
    }

    public void setTaobao(String taobao) {
        this.taobao = taobao;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTaobaoCode() {
        return taobaoCode;
    }

    public void setTaobaoCode(String taobaoCode) {
        this.taobaoCode = taobaoCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getMissionTime() {
        return missionTime;
    }

    public void setMissionTime(Date missionTime) {
        this.missionTime = missionTime;
    }

    public Integer getIsComment() {
        return isComment;
    }

    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
    }

    public Integer getIsAddcom() {
        return isAddcom;
    }

    public void setIsAddcom(Integer isAddcom) {
        this.isAddcom = isAddcom;
    }

    public BigDecimal getViewPay() {
        return viewPay;
    }

    public void setViewPay(BigDecimal viewPay) {
        this.viewPay = viewPay;
    }

    public BigDecimal getSellerPay() {
        return sellerPay;
    }

    public void setSellerPay(BigDecimal sellerPay) {
        this.sellerPay = sellerPay;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIsCommentStr() {
        if (isComment != null) {
            if (isComment == 1) {
                return "商家未指定";
            } else if (isComment == 2) {
                return "商家已指定评价";
            } else if (isComment == 3) {
                return "刷手已完成评价";
            } else if (isComment == 4) {
                return "评价被撤销";
            }
        }
        return "";
    }

    public String getIsAddComStr() {
        if (isAddcom != null) {
            //追评状态（1：商家未指定，2:商家已指定追评，3：刷手已完成追评，4：追评被撤销）
            if (isAddcom == 1) {
                return "商家未指定";
            } else if (isAddcom == 2) {
                return "商家已指定追评";
            } else if (isAddcom == 3) {
                return "刷手已完成追评";
            } else if (isAddcom == 4) {
                return "追评被撤销";
            }
        }
        return "";
    }
}
