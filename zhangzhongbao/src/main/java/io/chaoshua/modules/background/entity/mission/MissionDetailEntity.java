package io.chaoshua.modules.background.entity.mission;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.chaoshua.common.UploadUrl.UploadUrl;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-20 17:52:57
 */
@TableName("t_mission_detail")
public class MissionDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long missionId;
	/**
	 * 
	 */
	private String cardNumber;
	/**
	 * 
	 */
	private String bank;
	/**
	 * 
	 */
	private String holder;
	/**
	 * 
	 */
	private String style;
	/**
	 * 
	 */
	private Integer order;
	/**
	 * 
	 */
	private Integer intervalBegin;
	/**
	 * 
	 */
	private Integer intervalEnd;
	/**
	 * 
	 */
	private String note;
	/**
	 * 其他要求
	 */
	private String otherNote;

	private BigDecimal priceFrom;

	private BigDecimal priceTo;

	/**
	 * 
	 */
	private Date publishTime;
	/**
	 * 
	 */
	private String img;
	/**
	 *
	 */
	private String img2;
	/**
	 *
	 */
	private String img3;
	/**
	 * 
	 */
	private Date beginTime;
	/**
	 * 
	 */
	private String url;
	/**
	 * 刷单之后获得的佣金
	 */
	private BigDecimal userPay;
	/**
	 * 代理商所获取的利润
	 */
	private BigDecimal agentMoney;
	/**
	 * 指定评价商家所支付佣金
	 */
	private BigDecimal addComPay;
	/**
	 * 
	 */
	private Integer commentable;
	/**
	 * 
	 */
	private Integer role;
	/**
	 * 
	 */
	private Integer needCheck;
	/**
	 * 
	 */
	private Integer isBack;
	/**
	 * 
	 */
	private Integer isComment;
	/**
	 * 
	 */
	private Integer isAddcom;
	/**
	 * 
	 */
	private String xiaohao;
	/**
	 * 
	 */
	private String buy;
	/**
	 * 
	 */
	private String check;
	/**
	 * 
	 */
	private String compareOne;
	/**
	 * 
	 */
	private String compareTwo;
	/**
	 * 
	 */
	private String compareThree;
	/**
	 * 
	 */
	private String collectionCom;
	/**
	 * 店内宝贝1
	 */
	private String addCart;
	/**
	 * 店内宝贝2
	 */
	private String addCart2;
	/**
	 * 
	 */
	private String shopOne;
	/**
	 * 
	 */
	private String keySearch;
	/**
	 * 
	 */
	private String glanceCom;
	/**
	 * 
	 */
	private String paying;
	/**
	 * 
	 */
	private String sameStyle;
	/**
	 * 
	 */
	private String paied;
	/**
	 * 
	 */
	private String comment;
	/**
	 * 商家上传评价截图
	 */
	private String imgList;
    /**
     * 商家上传小视频
     */
	private String smallVideo;
	/**
	 * 指定评价
	 */
	private String commentImg;
	/**
	 * 
	 */
	private String receive;
	/**
	 * 
	 */
	private String addImglist;
    /**
     * 商家追评小视频
     */
    private String addSmallVideo;
	/**
	 * 商家上传追评内容
	 */
	private String addcom;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 
	 */
	private Date remitTime;
	/**
	 * 
	 */
	private Date commentTime;
	/**
	 * 指定评价商家所支付佣金
	 */
	private BigDecimal commentPay;
	/**
	 * 指定评价刷手所获取佣金
	 */
	private BigDecimal commentUser;
	/**
	 * 
	 */
	private Date finishComTime;
	/**
	 * 
	 */
	private BigDecimal addComUser;
	/**
	 * 
	 */
	private Date finishAddComTime;
	/**
	 * 指定追评
	 */
	private String addComImg;
	/**
	 *
	 */
	private String cancelNote;
	/**
	 * 拒绝备注
	 */
	private String refuseNote;
	/**
	 * 报错信息
	 */
	private String badInfo;
	/**
	 * 刷手备注
	 */
	private String userNote;

    /**
     * 足迹图
     */
    private String footprint;

	/**
	 * 宝贝聊天图
	 */
	private String chat;

    /**
     * 1：指定评价，2：指定追评
     */
	@TableField(exist = false)
	private Integer flag;
	/**用户ID
	 */
	@TableField(exist = false)
	private Long userId;
	/**订单编号
	 */
	@TableField(exist = false)
	private String missionCode;

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

	public String getMissionCode() {
		return missionCode;
	}

	public void setMissionCode(String missionCode) {
		this.missionCode = missionCode;
	}

	public BigDecimal getAgentMoney() {
		return agentMoney;
	}

	public void setAgentMoney(BigDecimal agentMoney) {
		this.agentMoney = agentMoney;
	}

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
	 * 设置：
	 */
	public void setMissionId(Long missionId) {
		this.missionId = missionId;
	}
	/**
	 * 获取：
	 */
	public Long getMissionId() {
		return missionId;
	}
	/**
	 * 设置：
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	/**
	 * 获取：
	 */
	public String getCardNumber() {
		return cardNumber;
	}
	/**
	 * 设置：
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}
	/**
	 * 获取：
	 */
	public String getBank() {
		return bank;
	}
	/**
	 * 设置：
	 */
	public void setHolder(String holder) {
		this.holder = holder;
	}
	/**
	 * 获取：
	 */
	public String getHolder() {
		return holder;
	}
	/**
	 * 设置：
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * 获取：
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * 设置：
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * 获取：
	 */
	public Integer getOrder() {
		return order;
	}

	public BigDecimal getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}

	public BigDecimal getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}

	/**
	 * 设置：
	 */
	public void setIntervalBegin(Integer intervalBegin) {
		this.intervalBegin = intervalBegin;
	}
	/**
	 * 获取：
	 */
	public Integer getIntervalBegin() {
		return intervalBegin;
	}
	/**
	 * 设置：
	 */
	public void setIntervalEnd(Integer intervalEnd) {
		this.intervalEnd = intervalEnd;
	}
	/**
	 * 获取：
	 */
	public Integer getIntervalEnd() {
		return intervalEnd;
	}
	/**
	 * 设置：
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置：
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	/**
	 * 获取：
	 */
	public Date getPublishTime() {
		return publishTime;
	}
	/**
	 * 设置：
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * 获取：
	 */
	public String getImg() {
		if (img != null){
			if(!img.contains("http")){
				img = UploadUrl.getUrl()+img;
			}
		}
		return img;
	}

	public String getImg3() {
		if (img3 != null){
			if(!img3.contains("http")){
				img3 = UploadUrl.getUrl()+img3;
			}
		}
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public String getImg2() {
		if (img2 != null){
			if(!img2.contains("http")){
				img2 = UploadUrl.getUrl()+img2;
			}
		}
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	/**
	 * 设置：
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * 获取：
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	/**
	 * 设置：
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：
	 */
	public void setUserPay(BigDecimal userPay) {
		this.userPay = userPay;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 *
	 * 获取：
	 */
	public BigDecimal getUserPay() {
		return userPay;
	}
	/**
	 * 设置：
	 */
	public void setCommentable(Integer commentable) {
		this.commentable = commentable;
	}
	/**
	 * 获取：
	 */
	public Integer getCommentable() {
		return commentable;
	}
	/**
	 * 设置：
	 */
	public void setRole(Integer role) {
		this.role = role;
	}
	/**
	 * 获取：
	 */
	public Integer getRole() {
		return role;
	}
	/**
	 * 设置：
	 */
	public void setNeedCheck(Integer needCheck) {
		this.needCheck = needCheck;
	}
	/**
	 * 获取：
	 */
	public Integer getNeedCheck() {
		return needCheck;
	}
	/**
	 * 设置：
	 */
	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}
	/**
	 * 获取：
	 */
	public Integer getIsBack() {
		return isBack;
	}
	/**
	 * 设置：
	 */
	public void setIsComment(Integer isComment) {
		this.isComment = isComment;
	}
	/**
	 * 获取：
	 */
	public Integer getIsComment() {
		return isComment;
	}
	/**
	 * 设置：
	 */
	public void setIsAddcom(Integer isAddcom) {
		this.isAddcom = isAddcom;
	}
	/**
	 * 获取：
	 */
	public Integer getIsAddcom() {
		return isAddcom;
	}
	/**
	 * 设置：
	 */
	public void setXiaohao(String xiaohao) {
		this.xiaohao = xiaohao;
	}

	public String getAddCart2() {
		if (!StringUtils.isEmpty(addCart2 ))
		if (!addCart2.contains("http")){
			addCart2 = UploadUrl.getUrl()+addCart2;
		}
		return addCart2;
	}

	public void setAddCart2(String addCart2) {
		this.addCart2 = addCart2;
	}

	/**
	 * 获取：
	 */
	public String getXiaohao() {
		if (xiaohao != null)
			if (!xiaohao.contains("http")){
				xiaohao = UploadUrl.getUrl()+xiaohao;
			}
		return xiaohao;
	}
	/**
	 * 设置：
	 */
	public void setBuy(String buy) {
		this.buy = buy;
	}
	/**
	 * 获取：
	 */
	public String getBuy() {
		return buy;
	}
	/**
	 * 设置：
	 */
	public void setCheck(String check) {
		this.check = check;
	}
	/**
	 * 获取：
	 */
	public String getCheck() {
		if (check != null)
			if (!check.contains("http")){
			check = UploadUrl.getUrl()+check;
			}
		return check;
	}
	/**
	 * 设置：
	 */
	public void setCompareOne(String compareOne) {
		this.compareOne = compareOne;
	}
	/**
	 * 获取：
	 */
	public String getCompareOne() {
		if (compareOne != null){
			if (!compareOne.contains("http")){
				compareOne = UploadUrl.getUrl() + compareOne;
			}
		}
		return compareOne;
	}
	/**
	 * 设置：
	 */
	public void setCompareTwo(String compareTwo) {
		this.compareTwo = compareTwo;
	}
	/**
	 * 获取：
	 */
	public String getCompareTwo() {
		if (compareTwo != null){
			if (!compareTwo.contains("http")){
				compareTwo = UploadUrl.getUrl() + compareTwo;
			}
		}
		return compareTwo;
	}
	/**
	 * 设置：
	 */
	public void setCompareThree(String compareThree) {
		this.compareThree = compareThree;
	}
	/**
	 * 获取：
	 */
	public String getCompareThree() {
		if (compareThree != null){
			if (!compareThree.contains("http")){
				compareThree = UploadUrl.getUrl() + compareThree;
			}
		}
		return compareThree;
	}
	/**
	 * 设置：
	 */
	public void setCollectionCom(String collectionCom) {
		this.collectionCom = collectionCom;
	}
	/**
	 * 获取：
	 */
	public String getCollectionCom() {
		if (collectionCom != null){
			if (!collectionCom.contains("http")){
				collectionCom = UploadUrl.getUrl() + collectionCom;
			}

		}
		return collectionCom;
	}
	/**
	 * 设置：
	 */
	public void setAddCart(String addCart) {
		this.addCart = addCart;
	}
	/**
	 * 获取：
	 */
	public String getAddCart() {
		if (addCart != null){
			if (!addCart.contains("http")){
				addCart = UploadUrl.getUrl()+addCart;
			}
		}
		return addCart;
	}
	/**
	 * 设置：
	 */
	public void setShopOne(String shopOne) {
		this.shopOne = shopOne;
	}
	/**
	 * 获取：
	 */
	public String getShopOne() {
		if (shopOne != null){
			if (!shopOne.contains("http")){
				shopOne = UploadUrl.getUrl() + shopOne;
			}
		}
		return shopOne;
	}
	/**
	 * 设置：
	 */
	public void setKeySearch(String keySearch) {
		this.keySearch = keySearch;
	}
	/**
	 * 获取：
	 */
	public String getKeySearch() {
		if (keySearch != null){
			if (!keySearch.contains("http")){
				keySearch = UploadUrl.getUrl() + keySearch;
			}
		}
		return keySearch;
	}
	/**
	 * 设置：
	 */
	public void setGlanceCom(String glanceCom) {
		this.glanceCom = glanceCom;
	}
	/**
	 * 获取：
	 */
	public String getGlanceCom() {
		if (glanceCom != null){
			if (!glanceCom.contains("http")){
				glanceCom = UploadUrl.getUrl() + glanceCom;
			}
		}
		return glanceCom;
	}
	/**
	 * 设置：
	 */
	public void setPaying(String paying) {
		this.paying = paying;
	}
	/**
	 * 获取：
	 */
	public String getPaying() {
		if (paying != null){
			if (!paying.contains("http")){
				paying = UploadUrl.getUrl() + paying;
			}
		}
		return paying;
	}
	/**
	 * 设置：
	 */
	public void setSameStyle(String sameStyle) {
		this.sameStyle = sameStyle;
	}
	/**
	 * 获取：
	 */
	public String getSameStyle() {
		return sameStyle;
	}
	/**
	 * 设置：
	 */
	public void setPaied(String paied) {
		this.paied = paied;
	}
	/**
	 * 获取：
	 */
	public String getPaied() {
		if (!StringUtils.isEmpty(paied)){
			if (!paied.contains("http")){
				paied = UploadUrl.getUrl() + paied;
			}
		}
		return paied;
	}
	/**
	 * 设置：
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * 获取：
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * 设置：
	 */
	public void setImgList(String imgList) {
		this.imgList = imgList;
	}
	/**
	 * 获取：
	 */
	public String getImgList() {
		return imgList;
	}
	/**
	 * 设置：
	 */
	public void setCommentImg(String commentImg) {
		this.commentImg = commentImg;
	}
	/**
	 * 获取：
	 */
	public String getCommentImg() {
		if (!StringUtils.isEmpty(commentImg )){
			if (!commentImg.contains("http")){
				commentImg = UploadUrl.getUrl()+commentImg;
			}
		}
		return commentImg;
	}
	/**
	 * 设置：
	 */
	public void setReceive(String receive) {
		this.receive = receive;
	}
	/**
	 * 获取：
	 */
	public String getReceive() {
		if (!StringUtils.isEmpty(receive)){
			if (!receive.contains("http")){
				receive = UploadUrl.getUrl() + receive;
			}
		}
		return receive;
	}
	/**
	 * 设置：
	 */
	public void setAddImglist(String addImglist) {
		this.addImglist = addImglist;
	}
	/**
	 * 获取：
	 */
	public String getAddImglist() {
		if (!StringUtils.isEmpty(addImglist)){
			if (!addImglist.contains("http")){
				addImglist = UploadUrl.getUrl()+addImglist;
			}
		}
		return addImglist;
	}
	/**
	 * 设置：
	 */
	public void setAddcom(String addcom) {
		this.addcom = addcom;
	}
	/**
	 * 获取：
	 */
	public String getAddcom() {
		return addcom;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：
	 */
	public void setRemitTime(Date remitTime) {
		this.remitTime = remitTime;
	}
	/**
	 * 获取：
	 */
	public Date getRemitTime() {
		return remitTime;
	}
	/**
	 * 设置：
	 */
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	/**
	 * 获取：
	 */
	public Date getCommentTime() {
		return commentTime;
	}
	/**
	 * 设置：
	 */
	public void setCommentPay(BigDecimal commentPay) {
		this.commentPay = commentPay;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getCommentPay() {
		return commentPay;
	}
	/**
	 * 设置：
	 */
	public void setCommentUser(BigDecimal commentUser) {
		this.commentUser = commentUser;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getCommentUser() {
		return commentUser;
	}
	/**
	 * 设置：
	 */
	public void setFinishComTime(Date finishComTime) {
		this.finishComTime = finishComTime;
	}
	/**
	 * 获取：
	 */
	public Date getFinishComTime() {
		return finishComTime;
	}
	/**
	 * 设置：
	 */
	public void setAddComUser(BigDecimal addComUser) {
		this.addComUser = addComUser;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getAddComUser() {
		return addComUser;
	}
	/**
	 * 设置：
	 */
	public void setFinishAddComTime(Date finishAddComTime) {
		this.finishAddComTime = finishAddComTime;
	}
	/**
	 * 获取：
	 */
	public Date getFinishAddComTime() {
		return finishAddComTime;
	}

	public String getCancelNote() {
		return cancelNote;
	}

	public void setCancelNote(String cancelNote) {
		this.cancelNote = cancelNote;
	}

	public String getAddComImg() {
		return addComImg;
	}

	public void setAddComImg(String addComImg) {
		this.addComImg = addComImg;
	}

	public String getRefuseNote() {
		return refuseNote;
	}

	public void setRefuseNote(String refuseNote) {
		this.refuseNote = refuseNote;
	}

	public String getBadInfo() {
		return badInfo;
	}

	public void setBadInfo(String badInfo) {
		this.badInfo = badInfo;
	}

	public BigDecimal getAddComPay() {
		return addComPay;
	}

	public void setAddComPay(BigDecimal addComPay) {
		this.addComPay = addComPay;
	}

	public String getOtherNote() {
		return otherNote;
	}

	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getIsAddComStr() {
		if (isAddcom != null) {
			//追评状态（1：商家未指定，2:商家已指定追评，3：刷手已完成追评，4：追评被撤销）
			if (isAddcom == 1) {
				isAddComStr = "商家未指定";
			} else if (isAddcom == 2) {
				isAddComStr = "商家已指定追评";
			} else if (isAddcom == 3) {
				isAddComStr = "刷手已完成追评";
			} else if (isAddcom == 4) {
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

	public String getUserNote() {
		return userNote;
	}

	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}

    public String getSmallVideo() {
        return smallVideo;
    }

    public void setSmallVideo(String smallVideo) {
        this.smallVideo = smallVideo;
    }

    public String getAddSmallVideo() {
        return addSmallVideo;
    }

    public void setAddSmallVideo(String addSmallVideo) {
        this.addSmallVideo = addSmallVideo;
    }

    public String getFootprint() {
        return footprint;
    }

    public void setFootprint(String footprint) {
        this.footprint = footprint;
    }

	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}
}
