package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 商家表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-25 17:22:27
 */
@TableName("t_seller")
public class SellerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 上级ID
	 */
	private Long pid;
	/**
	 * 上级类型（1：商家，2：刷手）
	 */
	private Integer isSeller;
	/**
	 * 商家联系人姓名
	 */
	private String contact;
	/**
	 * 商家手机号
	 */
	private String mobile;
	/**
	 * 商家账号密码
	 */
	private String password;
	/**
	 * 微信号
	 */
	private String wechat;
	/**
	 * 商家备注
	 */
	private String note;
	/**
	 * 
	 */
	private String qq;
	/**
	 * 商家余额
	 */
	private BigDecimal balance;
	/**
	 * 商家发布任务的商品单价（本金）限制
	 */
	private BigDecimal limit;
	/**
	 * 是否为vip（1：否，2：是）
	 */
	private Integer isVip;
	/**
	 * 商家发单权限(1:可以，2：不可以)
	 */
	private Integer publishable;
	/**
	 * 佣金优惠
	 */
	private BigDecimal benefit;
	/**
	 * 管理平台修改商家的金额
	 */
	private BigDecimal amount;
	/**
	 * 注册状态（1：未审核，2：已审核，3：被拒绝）
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;
	/*****************************额外添加字段开始**********************************************/
	@TableField(exist = false)
	/**
	 * 店铺类目ID
	 */
	private String categoryId;
	@TableField(exist = false)
	/**
	 * 店铺类目名称
	 */
	private String category;

	/**
	 * 店铺名称
	 */
	@TableField(exist = false)
	private String name;

	/**
	 * 店铺链接
	 */
	@TableField(exist = false)
	private String url;

	/**
	 * 商家发单权限格式化(1:可以，2：不可以)
	 */
	@TableField(exist = false)
	private String publishableStr;
	/**
	 * 密码确认
	 */
	@TableField(exist = false)
	private String passwordAgain;
	/**
	 * id组合字符串
	 */
	@TableField(exist = false)
	private String [] ids;
	/**
	 * isVip格式化
	 */
	@TableField(exist = false)
	private String isVipStr;
	/**
	 * 代理名称
	 */
	@TableField(exist = false)
	private String agentName;
	/*****************************额外添加字段结束**********************************************/
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
	 * 设置：商家联系人姓名
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * 获取：商家联系人姓名
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * 设置：商家手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：商家手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：商家账号密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIsSeller() {
		return isSeller;
	}

	public void setIsSeller(Integer isSeller) {
		this.isSeller = isSeller;
	}

	/**
	 * 获取：商家账号密码
	 */


	public String getPassword() {
		return password;
	}
	/**
	 * 设置：微信号
	 */
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	/**
	 * 获取：微信号
	 */
	public String getWechat() {
		return wechat;
	}
	/**
	 * 设置：商家备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：商家备注
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置：
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * 获取：
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * 设置：商家余额
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	/**
	 * 获取：商家余额
	 */
	public BigDecimal getBalance() {

		return balance;
	}
	/**
	 * 设置：商家发布任务的商品单价（本金）限制
	 */
	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}
	/**
	 * 获取：商家发布任务的商品单价（本金）限制
	 */
	public BigDecimal getLimit() {
		return limit;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	/**
	 * 设置：商家发单权限(1:可以，2：不可以)
	 */
	public void setPublishable(Integer publishable) {
		this.publishable = publishable;
	}
	/**
	 * 获取：商家发单权限(1:可以，2：不可以)
	 */
	public Integer getPublishable() {
		return publishable;
	}
	/**
	 * 设置：佣金优惠
	 */
	public void setBenefit(BigDecimal benefit) {
		this.benefit = benefit;
	}
	/**
	 * 获取：佣金优惠
	 */
	public BigDecimal getBenefit() {
		return benefit;
	}
	/**
	 * 设置：管理平台修改商家的金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 获取：管理平台修改商家的金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 设置：注册状态（1：未审核，2：已审核，3：被拒绝）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：注册状态（1：未审核，2：已审核，3：被拒绝）
	 */
	public Integer getStatus() {
		return status;
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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getPublishableStr() {
		//商家发单权限格式化(1:可以，2：不可以)
		if (publishable != null){
			if (publishable == 1){
				publishableStr = "允许";
			}else {
				publishableStr = "不允许";
			}
		}
		return publishableStr;
	}

	public void setPublishableStr(String publishableStr) {
		this.publishableStr = publishableStr;
	}

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getIsVipStr() {
		if (isVip != null){
			if (isVip ==1){
				isVipStr = "";
			}else {
				isVipStr = "VIP";
			}
		}
		return isVipStr;
	}

	public void setIsVipStr(String isVipStr) {
		this.isVipStr = isVipStr;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
}
