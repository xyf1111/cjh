package io.chaoshua.modules.background.entity.mission;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.chaoshua.common.UploadUrl.UploadUrl;
import io.chaoshua.common.utils.GenerateCodeUtils;
import io.chaoshua.modules.background.enums.MissionStatusEnum;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-18 10:51:16
 */
@TableName("t_mission")
public class MissionEntity implements Serializable {
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
     * 订单状态：1订单待认证,2订单待拍下,3订单已拍下,4商家已汇款,5刷手已付款,6商家已发货,7刷手已收获（结单） 8平台已确认
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
     * 刷手付款时间
     */
    private Date payTime;
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
     * 重量
     */
    private BigDecimal weight;

    /**
     * 是否需要物流
     */
    private Integer isLogistics;

    /**
     * 物流公司
     */
    private String logisticsComp;

    /**
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 收货地址
     */
    private String logisticsAddress;

    /**
     * 收货电话
     */
    private String logisticsPhone;


    /**
     * 收货人
     */
    private String logisticsName;

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
     * 店铺类目
     */
    private Date orderTime;
    /******************************添加字段开始*****************************************/

    /**
     * 商家上传指定评价
     */
    @TableField(exist = false)
    private String imgList;
    /**
     * 商家上传小视频
     */
    @TableField(exist = false)
    private String smallVideo;
    /**
     * 产品主图
     */
    @TableField(exist = false)
    private String img;
    /**
    /**
     * 拒绝原因
     */
    @TableField(exist = false)
    private String refuseNote;
    /**
     * 商家上传指定评价
     */
    @TableField(exist = false)
    private Date publishTime;
    /**
     * 订单状态格式化
     */
    @TableField(exist = false)
    private String statusStr;

    /**
     * 标记信息格式化
     */
    @TableField(exist = false)
    private String isMarkStr;
    /**
     * 银行卡号
     */
    @TableField(exist = false)
    private String cardNumber;
    /**
     * 持卡人
     */
    @TableField(exist = false)
    private String holder;

    /**
     * 标记信息格式化
     */
    @TableField(exist = false)
    private String taskStyleStr;

    /**
     * 是否打款格式化
     */
    @TableField(exist = false)
    private String isPayStr;
    /**
     * 任务佣金
     */
    @TableField(exist = false)
    private BigDecimal userPay;

    /**
     * 指定评价
     */
    @TableField(exist = false)
    private String commentImg;
    /**
     * 指定评价内容
     */
    @TableField(exist = false)
    private List<String> imgListStr;

    /**
     * 指定评价内容
     */
    @TableField(exist = false)
    private String comment;
    /**
     * 商家支付佣金
     */
    @TableField(exist = false)
    private BigDecimal commentPay;
    /**
     * 刷手获取不可提现佣金
     */
    @TableField(exist = false)
    private BigDecimal commentUser;
    /**
     * 追评状态
     */
    @TableField(exist = false)
    private Integer isAddCom;
    /**
     * 追评状态
     */
    @TableField(exist = false)
    private String isAddComStr;
    /**
     * 指定评价状态
     */
    @TableField(exist = false)
    private String isCommentStr;
    /**
     * 指定追评商家所支付佣金
     */
    @TableField(exist = false)
    private BigDecimal addComPay;

    /**
     * 指定追评刷手所得佣金
     */
    @TableField(exist = false)
    private BigDecimal addComUser;
    /**
     * 指定追评内容
     */
    @TableField(exist = false)
    private String addCom;
    /**
     * 待付款截图
     */
    @TableField(exist = false)
    private String paying;
    /**
     * 已付款截图
     */
    @TableField(exist = false)
    private String paied;
    /**
     * 刷手指定追评截图
     */
    @TableField(exist = false)
    private String addComImg;
    /**
     * 商家追定追评截图
     */
    @TableField(exist = false)
    private String addImgList;
    /**
     * 商家追定追评截图
     */
    @TableField(exist = false)
    private List<String> addImgListStr;
    /**
     * 商家追评小视频
     */
    @TableField(exist = false)
    private String addSmallVideo;
    /**
     * (1:追评，2：评价)
     */
    @TableField(exist = false)
    private Integer flag;

    /**
     * 刷手银行信息
     */
    @TableField(exist = false)
    private String bankInfo;
    /**
     * 首单时间
     */
    @TableField(exist = false)
    private Date biginTime;
    /**
     * 首单时间
     */
    @TableField(exist = false)
    private String isBadStr;
    /**
     * 一日浏览
     */
    @TableField(exist = false)
    private String view1;
    /**
     * 二日浏览
     */
    @TableField(exist = false)
    private String view2;

    @TableField(exist = false)
    private List<MissionEntity> missionEntities;
    @TableField(exist = false)
    private Integer isComment;
    /**
     * 报错信息
     */
    @TableField(exist = false)
    private String badInfo;
    /**
     * 撤销备注
     */
    @TableField(exist = false)
    private String cancelNote;

    /**
     * 代理商名称
     */
    @TableField(exist = false)
    private String agentName;

    /********************************************************************************/

    public String getRefuseNote() {
        return refuseNote;
    }

    public String getBadInfo() {
        return badInfo;
    }

    public void setBadInfo(String badInfo) {
        this.badInfo = badInfo;
    }

    public void setRefuseNote(String refuseNote) {
        this.refuseNote = refuseNote;
    }

    public Date getBiginTime() {
        return biginTime;
    }

    public void setBiginTime(Date biginTime) {
        this.biginTime = biginTime;
    }

    public String getIsBadStr() {
        if (isBad != null){
            if (isBad == 1){
                isBadStr = "未报错";
            }else if (isBad == 2){
                isBadStr = "已报错";
            }else if (isBad == 3){
                isBadStr = "报错已处理";
            }
        }
        return isBadStr;
    }

    public void setIsBadStr(String isBadStr) {
        this.isBadStr = isBadStr;
    }

    public String getImg() {
        if (!StringUtils.isEmpty(img)){
            if (!img.contains("http")){
                img = UploadUrl.getUrl() + img;
            }
        }
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getView1() {
        return view1;
    }

    public void setView1(String view1) {
        this.view1 = view1;
    }

    public String getView2() {
        return view2;
    }

    public void setView2(String view2) {
        this.view2 = view2;
    }

    public String getPaying() {
        if (!StringUtils.isEmpty(paying)){
            if (!paying.contains("http")){
                paying = UploadUrl.getUrl() + paying;
            }
        }
        return paying;
    }

    public void setPaying(String paying) {
        this.paying = paying;
    }

    public String getPaied() {
        return paied;
    }

    public void setPaied(String paied) {
        this.paied = paied;
    }

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

    /*******************************添加字段结束****************************************/




    public Integer getIsComment() {
        return isComment;
    }

    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
    }

    /**
     * 设置：id
     */
    public void setId(Long id) {
        this.id = id;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
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
        return price = price;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
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

    public String getStatusStr() {
        //1订单待认证,2订单待拍下,3订单已拍下,4商家已汇款,5刷手已付款,6商家已发货,7刷手已收获
        if (status != null) {
            int code = status;
            switch (code) {
                case 1:
                    statusStr = "订单待认证";
                    break;
                case 2:
                    statusStr = "订单待拍下";
                    break;
                case 3:
                    statusStr = "订单已拍下";
                    break;
                case 4:
                    statusStr = "商家已汇款";
                    break;
                case 5:
                    statusStr = "刷手已付款";
                    break;
                case 6:
                    statusStr = "商家已发货";
                    break;
                case 7:
                    statusStr = "刷手已收货";
                    break;
                case 8:
                    statusStr = "平台已确认";
                    break;
            }
        }
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getIsMarkStr() {
        //标记信息（1：无标记、未标记，2：已标记）
        if (isMark != null) {
            int code = isMark;
            switch (code) {
                case 1:
                    isMarkStr = "无标记";
                case 2:
                    isMarkStr = "已标记";
            }
        }
        return isMarkStr;
    }

    public void setIsMarkStr(String isMarkStr) {
        this.isMarkStr = isMarkStr;
    }

    public String getTaskStyleStr() {
        //（0：精刷单，1：标签二天，2：标签三天）
        if (taskStyle != null) {
            if (taskStyle == 0) {
                taskStyleStr = "精刷单";
            } else if (taskStyle == 1) {
                taskStyleStr = "标签二天";
            } else if (taskStyle == 2) {
                taskStyleStr = "标签三天";
            }
        }
        return taskStyleStr;
    }

    public void setTaskStyleStr(String taskStyleStr) {
        this.taskStyleStr = taskStyleStr;
    }

    public String getIsPayStr() {
        if (isPay != null) {
            if (isPay == 1) {
                isPayStr = "否";
            } else if (isPay == 2) {
                isPayStr = "是";
            }
        }
        return isPayStr;
    }

    public void setIsPayStr(String isPayStr) {
        this.isPayStr = isPayStr;
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

    public BigDecimal getUserPay() {
        return userPay;
    }

    public void setUserPay(BigDecimal userPay) {
        this.userPay = userPay;
    }

    public String getCommentImg() {
        if (!StringUtils.isEmpty(commentImg)) {
            if (!commentImg.contains("http")) {
                commentImg = UploadUrl.getUrl() + commentImg;
            }
        }
        return commentImg;
    }

    public void setCommentImg(String commentImg) {
        this.commentImg = commentImg;
    }

    public String getImgList() {
        return imgList;
    }

    public void setImgList(String imgList) {
        this.imgList = imgList;
    }

    public String getSmallVideo() {
        return smallVideo;
    }

    public void setSmallVideo(String smallVideo) {
        this.smallVideo = smallVideo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getImgListStr() {
        List<String> list = new ArrayList<>();
        if (imgList != null) {
            String[] str = imgList.split(",");
            for (int i = 0; i < str.length; i++) {
                if (!str[i].contains("http")) {
                    String str1 = UploadUrl.getUrl() + str[i];
                    list.add(str1);
                } else {
                    list.add(str[i]);
                }
            }
        }
        return list;
    }

    public void setImgListStr(List<String> imgListStr) {
        this.imgListStr = imgListStr;
    }

    public BigDecimal getCommentPay() {
        return commentPay;
    }

    public void setCommentPay(BigDecimal commentPay) {
        this.commentPay = commentPay;
    }

    public BigDecimal getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(BigDecimal commentUser) {
        this.commentUser = commentUser;
    }

    public Integer getIsAddCom() {
        return isAddCom;
    }

    public void setIsAddCom(Integer isAddCom) {
        this.isAddCom = isAddCom;
    }

    public String getIsAddComStr() {
        if (isAddCom != null) {
            //追评状态（1：商家未指定，2:商家已指定追评，3：刷手已完成追评，4：追评被撤销）
            if (isAddCom == 1) {
                isAddComStr = "商家未指定";
            } else if (isAddCom == 2) {
                isAddComStr = "商家已指定追评";
            } else if (isAddCom == 3) {
                isAddComStr = "刷手已完成追评";
            } else if (isAddCom == 4) {
                isAddComStr = "追评被撤销";
            }
        }
        return isAddComStr;
    }

    public String getIsCommentStr() {
        if (isComment != null){
            if (isComment == 1) {
                isCommentStr = "商家未指定";
            } else if (isComment == 2) {
                isCommentStr = "商家已指定评价";
            } else if (isComment == 3) {
                isCommentStr = "刷手已完成评价";
            } else if (isComment == 4) {
                isCommentStr = "评价被撤销";
            }
        }
        return isCommentStr;
    }

    public void setIsCommentStr(String isCommentStr) {
        this.isCommentStr = isCommentStr;
    }

    public void setIsAddComStr(String isAddComStr) {
        this.isAddComStr = isAddComStr;
    }

    public BigDecimal getAddComPay() {
        return addComPay;
    }

    public void setAddComPay(BigDecimal addComPay) {
        this.addComPay = addComPay;
    }

    public BigDecimal getAddComUser() {
        return addComUser;
    }

    public void setAddComUser(BigDecimal addComUser) {
        this.addComUser = addComUser;
    }

    public String getAddCom() {
        return addCom;
    }

    public void setAddCom(String addCom) {
        this.addCom = addCom;
    }

    public String getAddComImg() {
        if (!StringUtils.isEmpty(addComImg)){
            if (!addComImg.contains("http")){
                addComImg = UploadUrl.getUrl() + addComImg;
            }
        }
        return addComImg;
    }

    public void setAddComImg(String addComImg) {
        this.addComImg = addComImg;
    }

    public String getAddImgList() {
        return addImgList;
    }

    public void setAddImgList(String addImgList) {
        this.addImgList = addImgList;
    }

    public List<String> getAddImgListStr() {
        List<String> list = new ArrayList<>();
        if (addImgList != null) {
            String[] str = addImgList.split(",");
            for (int i = 0; i < str.length; i++) {
                if (!str[i].contains("http")) {
                    String str1 = UploadUrl.getUrl() + str[i];
                    list.add(str1);
                } else {
                    list.add(str[i]);
                }
            }
        }
        return list;
    }

    public void setAddImgListStr(List<String> addImgListStr) {
        this.addImgListStr = addImgListStr;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public List<MissionEntity> getMissionEntities() {
        return missionEntities;
    }

    public void setMissionEntities(List<MissionEntity> missionEntities) {
        this.missionEntities = missionEntities;
    }

    public Long getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Long shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getCancelNote() {
        return cancelNote;
    }

    public void setCancelNote(String cancelNote) {
        this.cancelNote = cancelNote;
    }

    public String getAddSmallVideo() {
        return addSmallVideo;
    }

    public void setAddSmallVideo(String addSmallVideo) {
        this.addSmallVideo = addSmallVideo;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getLogisticsComp() {
        return logisticsComp;
    }

    public void setLogisticsComp(String logisticsComp) {
        this.logisticsComp = logisticsComp;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getLogisticsAddress() {
        return logisticsAddress;
    }

    public void setLogisticsAddress(String logisticsAddress) {
        this.logisticsAddress = logisticsAddress;
    }

    public String getLogisticsPhone() {
        return logisticsPhone;
    }

    public void setLogisticsPhone(String logisticsPhone) {
        this.logisticsPhone = logisticsPhone;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public Integer getIsLogistics() {
        return isLogistics;
    }

    public void setIsLogistics(Integer isLogistics) {
        this.isLogistics = isLogistics;
    }

    /**
     * 初始化订单
     *
     * @return
     */
    public MissionEntity missionInit(){
        MissionEntity mission = new MissionEntity();
        mission.setTaskId(taskId);
        mission.setSellerId(sellerId);
        mission.setShopId(shopId);
        mission.setMissionCode(GenerateCodeUtils.getMissionCode(taskId));
        mission.setKeyWord(keyWord);
        mission.setStatus(MissionStatusEnum.WAIT_AUTH.getValue());
        mission.setTaskStyle(taskStyle);
        mission.setMissionTime(missionTime);
        mission.setShopName(shopName);
        mission.setSellerName(sellerName);
        mission.setPrice(price);
        mission.setWeight(weight);
        mission.setIsMark(1);
        mission.setInChargeId(inChargeId);
        mission.setPlatformName(platformName);
        mission.setCreateTime(new Date());
        mission.setShopCategory(shopCategory);
        mission.setShopCategoryId(shopCategoryId);
        return mission;
    }
}
