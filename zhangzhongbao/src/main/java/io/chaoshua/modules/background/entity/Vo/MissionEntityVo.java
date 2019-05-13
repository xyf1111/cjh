package io.chaoshua.modules.background.entity.Vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-18 10:51:16
 */
@TableName("t_mission")
public class MissionEntityVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 刷手id
     */
    private Long userId;
    /**
     * 商家id
     */
    private Long sellerId;
    /**
     * 店铺id
     */
    private Long shopId;
    /**
     * 订单编号
     */
    private String missionCode;
    /**
     * 关键字
     */
    private String keyWord;
    /**
     * 订单状态：1订单待认证,2订单待拍下,3订单已拍下,4商家已汇款,5刷手已付款,6商家已发货,7刷手已收获（结单）
     */
    private Integer status;
    /**
     * 任务类型（0：精刷单，1：标签二天，2：标签三天）
     */
    private Integer taskStyle;
    /**
     * 执行时间
     */
    private Date missionTime;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 商家名称
     */
    private String sellerName;
    /**
     * 淘宝名称
     */
    private String taobao;
    /**
     * 淘宝订单号
     */
    private String taobaoCode;
    /**
     * 本金
     */
    private BigDecimal price;
    /**
     * 标记信息（1：无标记、未标记，2：已标记）
     */
    private Integer isMark;
    /**
     * 标记信息
     */
    private String mark;
    /**
     * 订单处理人员id
     */
    private Long inChargeId;
    /**
     * 处理客服名称
     */
    private String platformName;
    /**
     * 刷手备注
     */
    private String userNote;
    /**
     * 是否打款（1：否，2是）
     */
    private Integer isPay;
    /**
     * 报错处理（1未报错，2：已报错，3：报错已处理）
     */
    private Integer isBad;
    /**
     * 订单是否撤销（1：否，2：是）
     */
    private Integer isAnnul;
    /**
     * 订单审核状态（1、订单待审核2、审核通过、3：已拒绝）
     */
    private Integer role;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 刷手手机号
     */
    private String mobile;
    /**
     * 刷手名称
     */
    private String userName;
    /**
     * 店铺类目
     */
    private String shopCategory;
    /**
     * 店铺类目
     */
    private Long shopCategoryId;


    /**
     * 设置：id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：任务id
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取：任务id
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * 设置：刷手id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取：刷手id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置：商家id
     */
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * 获取：商家id
     */
    public Long getSellerId() {
        return sellerId;
    }


    /**
     * 设置：店铺id
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取：店铺id
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * 设置：订单编号
     */
    public void setMissionCode(String missionCode) {
        this.missionCode = missionCode;
    }

    /**
     * 获取：订单编号
     */
    public String getMissionCode() {
        return missionCode;
    }

    /**
     * 设置：关键字
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * 获取：关键字
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * 设置：订单状态：1订单待认证,2订单待拍下,3订单已拍下,4商家已汇款,5刷手已付款,6商家已发货,7刷手已收获（结单）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：订单状态：1订单待认证,2订单待拍下,3订单已拍下,4商家已汇款,5刷手已付款,6商家已发货,7刷手已收获（结单）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：任务类型（0：精刷单，1：标签二天，2：标签三天）
     */
    public void setTaskStyle(Integer taskStyle) {
        this.taskStyle = taskStyle;
    }

    /**
     * 获取：任务类型（0：精刷单，1：标签二天，2：标签三天）
     */
    public Integer getTaskStyle() {
        return taskStyle;
    }

    /**
     * 设置：执行时间
     */
    public void setMissionTime(Date missionTime) {
        this.missionTime = missionTime;
    }

    /**
     * 获取：执行时间
     */
    public Date getMissionTime() {
        return missionTime;
    }

    /**
     * 设置：店铺名称
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 获取：店铺名称
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 设置：商家名称
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /**
     * 获取：商家名称
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * 设置：淘宝名称
     */
    public void setTaobao(String taobao) {
        this.taobao = taobao;
    }

    /**
     * 获取：淘宝名称
     */
    public String getTaobao() {
        return taobao;
    }

    /**
     * 设置：淘宝订单号
     */
    public void setTaobaoCode(String taobaoCode) {
        this.taobaoCode = taobaoCode;
    }

    /**
     * 获取：淘宝订单号
     */
    public String getTaobaoCode() {
        return taobaoCode;
    }

    /**
     * 设置：本金
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取：本金
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置：标记信息（1：无标记、未标记，2：已标记）
     */
    public void setIsMark(Integer isMark) {
        this.isMark = isMark;
    }

    /**
     * 获取：标记信息（1：无标记、未标记，2：已标记）
     */
    public Integer getIsMark() {
        return isMark;
    }

    /**
     * 设置：标记信息
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * 获取：标记信息
     */
    public String getMark() {
        return mark;
    }

    /**
     * 设置：订单处理人员id
     */
    public void setInChargeId(Long inChargeId) {
        this.inChargeId = inChargeId;
    }

    /**
     * 获取：订单处理人员id
     */
    public Long getInChargeId() {
        return inChargeId;
    }

    /**
     * 设置：处理客服名称
     */
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    /**
     * 获取：处理客服名称
     */
    public String getPlatformName() {
        return platformName;
    }

    /**
     * 设置：刷手备注
     */
    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    /**
     * 获取：刷手备注
     */
    public String getUserNote() {
        return userNote;
    }

    /**
     * 设置：是否打款（1：否，2是）
     */
    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    /**
     * 获取：是否打款（1：否，2是）
     */
    public Integer getIsPay() {
        return isPay;
    }

    /**
     * 设置：报错处理（1未报错，2：已报错，3：报错已处理）
     */
    public void setIsBad(Integer isBad) {
        this.isBad = isBad;
    }

    /**
     * 获取：报错处理（1未报错，2：已报错，3：报错已处理）
     */
    public Integer getIsBad() {
        return isBad;
    }

    /**
     * 设置：订单是否撤销（1：否，2：是）
     */
    public void setIsAnnul(Integer isAnnul) {
        this.isAnnul = isAnnul;
    }


    /**
     * 获取：订单是否撤销（1：否，2：是）
     */
    public Integer getIsAnnul() {
        return isAnnul;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }


    public Long getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Long shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }


}
