package io.chaoshua.modules.background.entity.task;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.chaoshua.common.UploadUrl.UploadUrl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-26 14:23:12
 */
@TableName("t_task")
public class TaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long sellerId;
	/**
	 * 
	 */
	private Long shopId;
	/**
	 * 商家名称
	 */
	private String sellerName;
	/**
	 * 产品主图1
	 */
	private String img;
	/**
	 * 产品主图2
	 */
	private String img2;
	/**
	 * 产品主图3
	 */
	private String img3;
	/**
	 * 店铺名称
	 */
	private String shopName;
	/**
	 * 店铺类目ID
	 */
	private Long shopCategoryId;
	/**
	 * 店铺类目
	 */
	private String shopCategory;
	/**
	 * 店铺链接
	 */
	private String url;
	/**
	 * 产品款号
	 */
	private String style;
	/**
	 * 单价，每单订单的商品价格
	 */
	private BigDecimal price;
	/**
	 * 价格区间小
	 */
	private BigDecimal priceFrom;
	/**
	 * 价格区间大
	 */
	private BigDecimal priceTo;
	/**
	 * 单价，每单订单的商品重量
	 */
	private BigDecimal weight;
	/**
	 * 
	 */
	private String keyword;
	/**
	 * 排序方式(1综合排序,2销量排序,3价格排序)
	 */
	private Integer order;
	/**
	 * 排名区间起点
	 */
	private Integer intervalBegin;
	/**
	 * 排名区间终点
	 */
	private Integer intervalEnd;
	/**
	 * 其他要求
	 */
	private String note;
	/**
	 * 其他要求
	 */
	private String otherNote;
	/**
	 * 任务审核状态(1任务未审核,2任务已审核,3任务未通过、已拒绝)
	 */
	private Integer role;
	/**
	 * 任务审核通过的时间
	 */
	private Date publishTime;
	/**
	 * 平台管理对任务审核的意见（包括通过和拒绝）
	 */
	private String feedback;
	/**
	 * 任务总单数
	 */
	private Integer amount;
	/**
	 * 每单任务的间隔时间
	 */
	private Integer interval;
	/**
	 * 任务类型(1精刷单,2标签刷单、预约刷单)
	 */
	private Integer taskStyle;
	/**
	 * 预约刷单天数
	 */
	private Integer appoint;
	/**
	 * 任务审核客服的id
	 */
	private Long inChargeId;
	/**
	 * 任务已接数量（不包含被撤销的任务）
	 */
	private Integer accept;
	/**
	 * 任务剩余数量
	 */
	private Integer remain;
	/**
	 * 第一个任务订单能够被刷手接的时间，即任务的起始执行时间
	 */
	private Date beginTime;
	/**
	 * 已接过这个任务的刷手列表（？包括被撤销的刷手吗）
	 */
	private String userIdList;
	/**
	 * 任务客服名称还是审核管理员名字
	 */
	private String platformName;
	/**
	 * 某个任务被撤销的订单数量
	 */
	private Integer remove;
	/**
	 * 对于预约任务（标签二、三天），商家所需要支付的总浏览佣金（佣金规则中单价对应的浏览佣金*总单数）
	 */
	private BigDecimal viewPay;
	/**
	 * 商家发任务所需支付的总佣金（佣金规则单价对应支付佣金*总单数）
	 */
	private BigDecimal sellerPay;
	/**
	 * 刷手接单之后获得的不可提现佣金
	 */
	private BigDecimal userPay;
	/**
	 * 代理商获取的精刷单金额
	 */
	private BigDecimal agentMoney;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;
	/**********************************添加开始**********************************************/
	/**
	 * 任务类型格式化
	 */
	@TableField(exist = false)
	private String taskStyleStr;

	/**
	 * 审核权限标识（1：有审核拒绝权限，2：无）
	 */
	@TableField(exist = false)
	private Integer sysMenuRole;
	/**
	 * 任务状态格式化
	 */
	@TableField(exist = false)
	private String roleStr;

	/**
	 * 排序方式
	 */
	@TableField(exist = false)
	private String orderStr;
	/**
	 * 完成状态
	 */
	@TableField(exist = false)
	private Integer status;
	/**
	 *同类目接单限制
	 */
	@TableField(exist = false)
	private Integer limit;

    /**
     * 代理商名称
     */
	@TableField(exist = false)
	private String agentName;

	/**
	 * 关键词数量数组，分号分割
	 */
	@TableField(exist = false)
	private String keywordCounts;
	/**********************************添加结束**********************************************/

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	/**
	 * 获取：
	 */
	public Long getSellerId() {
		return sellerId;
	}
	/**
	 * 设置：
	 */
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	/**
	 * 获取：
	 */
	public Long getShopId() {
		return shopId;
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
	 * 设置：产品主图
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * 获取：产品主图
	 */
	public String getImg() {
		if (img != null){
			if (!img.contains("http")){
				img = UploadUrl.getUrl()+img;
			}
		}
		return img;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
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
	 * 设置：店铺类目
	 */
	public void setShopCategory(String shopCategory) {
		this.shopCategory = shopCategory;
	}
	/**
	 * 获取：店铺类目
	 */
	public String getShopCategory() {
		return shopCategory;
	}
	/**
	 * 设置：店铺链接
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：店铺链接
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：产品款号
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * 获取：产品款号
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * 设置：单价，每单订单的商品价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：单价，每单订单的商品价格
	 */
	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * 设置：
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * 获取：
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * 设置：排序方式(1综合排序,2销量排序,3价格排序)
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * 获取：排序方式(1综合排序,2销量排序,3价格排序)
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * 设置：排名区间起点
	 */
	public void setIntervalBegin(Integer intervalBegin) {
		this.intervalBegin = intervalBegin;
	}
	/**
	 * 获取：排名区间起点
	 */
	public Integer getIntervalBegin() {
		return intervalBegin;
	}
	/**
	 * 设置：排名区间终点
	 */
	public void setIntervalEnd(Integer intervalEnd) {
		this.intervalEnd = intervalEnd;
	}
	/**
	 * 获取：排名区间终点
	 */
	public Integer getIntervalEnd() {
		return intervalEnd;
	}
	/**
	 * 设置：其他要求
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：其他要求
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置：任务审核状态(1任务未审核,2任务已审核,3任务未通过、已拒绝)
	 */
	public void setRole(Integer role) {
		this.role = role;
	}
	/**
	 * 获取：任务审核状态(1任务未审核,2任务已审核,3任务未通过、已拒绝)
	 */
	public Integer getRole() {
		return role;
	}
	/**
	 * 设置：任务审核通过的时间
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	/**
	 * 获取：任务审核通过的时间
	 */
	public Date getPublishTime() {
		return publishTime;
	}
	/**
	 * 设置：平台管理对任务审核的意见（包括通过和拒绝）
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	/**
	 * 获取：平台管理对任务审核的意见（包括通过和拒绝）
	 */
	public String getFeedback() {
		return feedback;
	}
	/**
	 * 设置：任务总单数
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	/**
	 * 获取：任务总单数
	 */
	public Integer getAmount() {
		return amount;
	}
	/**
	 * 设置：每单任务的间隔时间
	 */
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	/**
	 * 获取：每单任务的间隔时间
	 */
	public Integer getInterval() {
		return interval;
	}
	/**
	 * 设置：任务类型(1精刷单,2标签刷单、预约刷单)
	 */
	public void setTaskStyle(Integer taskStyle) {
		this.taskStyle = taskStyle;
	}
	/**
	 * 获取：任务类型(1精刷单,2标签刷单、预约刷单)
	 */
	public Integer getTaskStyle() {
		return taskStyle;
	}
	/**
	 * 设置：预约刷单天数
	 */
	public void setAppoint(Integer appoint) {
		this.appoint = appoint;
	}
	/**
	 * 获取：预约刷单天数
	 */
	public Integer getAppoint() {
		return appoint;
	}
	/**
	 * 设置：任务审核客服的id
	 */
	public void setInChargeId(Long inChargeId) {
		this.inChargeId = inChargeId;
	}
	/**
	 * 获取：任务审核客服的id
	 */
	public Long getInChargeId() {
		return inChargeId;
	}
	/**
	 * 设置：任务已接数量（不包含被撤销的任务）
	 */
	public void setAccept(Integer accept) {
		this.accept = accept;
	}
	/**
	 * 获取：任务已接数量（不包含被撤销的任务）
	 */
	public Integer getAccept() {
		return accept;
	}
	/**
	 * 设置：任务剩余数量
	 */
	public void setRemain(Integer remain) {
		this.remain = remain;
	}
	/**
	 * 获取：任务剩余数量
	 */
	public Integer getRemain() {
		return remain;
	}
	/**
	 * 设置：第一个任务订单能够被刷手接的时间，即任务的起始执行时间
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * 获取：第一个任务订单能够被刷手接的时间，即任务的起始执行时间
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	/**
	 * 设置：已接过这个任务的刷手列表（？包括被撤销的刷手吗）
	 */
	public void setUserIdList(String userIdList) {
		this.userIdList = userIdList;
	}
	/**
	 * 获取：已接过这个任务的刷手列表（？包括被撤销的刷手吗）
	 */
	public String getUserIdList() {
		return userIdList;
	}
	/**
	 * 设置：任务客服名称还是审核管理员名字
	 */
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	/**
	 * 获取：任务客服名称还是审核管理员名字
	 */
	public String getPlatformName() {
		return platformName;
	}
	/**
	 * 设置：某个任务被撤销的订单数量
	 */
	public void setRemove(Integer remove) {
		this.remove = remove;
	}
	/**
	 * 获取：某个任务被撤销的订单数量
	 */
	public Integer getRemove() {
		return remove;
	}
	/**
	 * 设置：对于预约任务（标签二、三天），商家所需要支付的总浏览佣金（佣金规则中单价对应的浏览佣金*总单数）
	 */
	public void setViewPay(BigDecimal viewPay) {
		this.viewPay = viewPay;
	}
	/**
	 * 获取：对于预约任务（标签二、三天），商家所需要支付的总浏览佣金（佣金规则中单价对应的浏览佣金*总单数）
	 */
	public BigDecimal getViewPay() {
		return viewPay;
	}
	/**
	 * 设置：商家发任务所需支付的总佣金（佣金规则单价对应支付佣金*总单数）
	 */
	public void setSellerPay(BigDecimal sellerPay) {
		this.sellerPay = sellerPay;
	}
	/**
	 * 获取：商家发任务所需支付的总佣金（佣金规则单价对应支付佣金*总单数）
	 */
	public BigDecimal getSellerPay() {
		return sellerPay;
	}
	/**
	 * 设置：刷手接单之后获得的不可提现佣金
	 */
	public void setUserPay(BigDecimal userPay) {
		this.userPay = userPay;
	}


	/**
	 * 获取：刷手接单之后获得的不可提现佣金
	 */
	public BigDecimal getUserPay() {
		return userPay;
	}

	public BigDecimal getAgentMoney() {
		return agentMoney;
	}

	public void setAgentMoney(BigDecimal agentMoney) {
		this.agentMoney = agentMoney;
	}

	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
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

	public String getTaskStyleStr() {
		if(taskStyle != null ){
			if (taskStyle == 0){
				taskStyleStr = "精刷单";
			}else if (taskStyle == 1){
				taskStyleStr = "标签二天";
			}else if (taskStyle == 2){
				taskStyleStr = "标签三天";
			}else if (taskStyle == 3){
				taskStyleStr = "流量单";
			}
		}
		return taskStyleStr;
	}

	public void setTaskStyleStr(String taskStyleStr) {
		this.taskStyleStr = taskStyleStr;
	}

	public String getRoleStr() {
		if(role != null){
			if(role == 1){
				roleStr ="任务未审核";
			}else if (role == 2){
				roleStr ="任务已审核";
			}else if (role == 3){
				roleStr ="任务未通过";
			}
		}
		return roleStr;
	}

	public void setRoleStr(String roleStr) {
		this.roleStr = roleStr;
	}

	public String getOrderStr() {
		if (order != null){
			if(order == 1){
				orderStr = "综合排序";
			}else if(order == 2){
				orderStr = "销量排序";
			}else if (order == 3){
				orderStr = "价格排序";
			}
		}
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public Long getShopCategoryId() {
		return shopCategoryId;
	}

	public void setShopCategoryId(Long shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}

	public Integer getSysMenuRole() {
		return sysMenuRole;
	}

	public void setSysMenuRole(Integer sysMenuRole) {
		this.sysMenuRole = sysMenuRole;
	}

	public String getOtherNote() {
		return otherNote;
	}

	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getKeywordCounts() {
		return keywordCounts;
	}

	public void setKeywordCounts(String keywordCounts) {
		this.keywordCounts = keywordCounts;
	}
}
