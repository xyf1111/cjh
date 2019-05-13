package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-24 17:28:54
 */
@TableName("t_shop")
public class ShopEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商家,id关联t_seller表
	 */
	private Long sellerId;
	/**
	 * 商家姓名
	 */
	private String name;
	/**
	 * 店铺联系人
	 */
	private String contact;
	/**
	 * 商家手机号
	 */
	private String mobile;
	/**
	 * 店铺类目ID
	 */
	private String categoryId;
	/**
	 * 备注
	 */
	private String category;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 店铺链接
	 */
	private String url;
	/**
	 * 店铺发货地址
	 */
	private String address;
	/**
	 * 店铺所属平台(1:淘宝，2：天猫，3：京东)
	 */
	private Integer platform;
	/**
	 * 店铺状态(1店铺未提交审核,2待审核,3已审核,4已驳回)
	 */
	private Integer role;
	/**
	 * 一天内店铺能发单的上限
	 */
	private Integer total;
	/**
	 * 同一刷手接同一店铺的单周期，默认60天
	 */
	private Integer interval;
	/**
	 * 一天之内本店铺剩余发单数量
	 */
	private Integer isPublish;
	/**
	 * 店铺的最后发单时间
	 */
	private Date lastPublish;
	/**
	 * 是否默认店铺（1：是，0：否）
	 */
	private Integer isDefault;
	/**
	 * 店铺状态(1:正常，2：被禁用)
	 */
	private Integer status;
	/**
	 * 店铺审核通过时间
	 */
	private Date passTime;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;
	/********************************新增字段开始***************************/
	/**
	 * 平台格式化
	 */
	@TableField(exist = false)
	private String platFormStr;

	/**
	 * 店铺状态格式化
	 */
	@TableField(exist = false)
	private String roleStr;

	/**
	 * 店铺禁用状态格式化
	 */
	@TableField(exist = false)
	private String statusStr;
	/**
	 * 代理名称
	 */
	@TableField(exist = false)
	private String agentName;
	/**
	 * 店铺类目数组
	 */
	@TableField(exist = false)
	private String [] categoryIds;
	/**
	 * 店铺类目数组
	 */
	@TableField(exist = false)
	private int [] categoryIds1;
	/********************************新增字段结束***************************/
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
	 * 设置：商家,id关联t_seller表
	 */
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	/**
	 * 获取：商家,id关联t_seller表
	 */
	public Long getSellerId() {
		return sellerId;
	}
	/**
	 * 设置：商家姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：商家姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：店铺联系人
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * 获取：店铺联系人
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
	 * 设置：店铺类目
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * 获取：店铺类目
	 */
	public String getCategory() {
		return category;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 设置：店铺所属平台(1:淘宝，2：天猫，3：京东)
	 */
	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	/**
	 * 获取：店铺所属平台(1:淘宝，2：天猫，3：京东)
	 */
	public Integer getPlatform() {
		return platform;
	}
	/**
	 * 设置：店铺状态(1店铺未提交审核,2待审核,3已审核,4已驳回)
	 */
	public void setRole(Integer role) {
		this.role = role;
	}
	/**
	 * 获取：店铺状态(1店铺未提交审核,2待审核,3已审核,4已驳回)
	 */
	public Integer getRole() {
		return role;
	}
	/**
	 * 设置：一天内店铺能发单的上限
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	/**
	 * 获取：一天内店铺能发单的上限
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置：同一刷手接同一店铺的单周期，默认60天
	 */
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	/**
	 * 获取：同一刷手接同一店铺的单周期，默认60天
	 */
	public Integer getInterval() {
		return interval;
	}
	/**
	 * 设置：一天之内本店铺剩余发单数量
	 */
	public void setIsPublish(Integer isPublish) {
		this.isPublish = isPublish;
	}
	/**
	 * 获取：一天之内本店铺剩余发单数量
	 */
	public Integer getIsPublish() {
		return isPublish;
	}
	/**
	 * 设置：店铺的最后发单时间
	 */
	public void setLastPublish(Date lastPublish) {
		this.lastPublish = lastPublish;
	}
	/**
	 * 获取：店铺的最后发单时间
	 */
	public Date getLastPublish() {
		return lastPublish;
	}
	/**
	 * 设置：是否默认店铺（1：是，0：否）
	 */
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	/**
	 * 获取：是否默认店铺（1：是，0：否）
	 */
	public Integer getIsDefault() {
		return isDefault;
	}
	/**
	 * 设置：店铺状态(1:正常，2：被禁用)
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：店铺状态(1:正常，2：被禁用)
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：店铺审核通过时间
	 */
	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}
	/**
	 * 获取：店铺审核通过时间
	 */
	public Date getPassTime() {
		return passTime;
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

	public String getPlatFormStr() {
		//店铺所属平台(1:淘宝，2：天猫，3：京东)
		if (platform != null){
			if (platform == 1){
				platFormStr = "淘宝";
			}else if (platform == 2){
				platFormStr = "天猫";
			}else if (platform == 3){
				platFormStr = "京东";
			}
		}
		return platFormStr;
	}

	public void setPlatFormStr(String platFormStr) {
		this.platFormStr = platFormStr;
	}

	public String getRoleStr() {
		if (role != null){
			//店铺状态(1店铺未提交审核,2待审核,3已审核,4已驳回)
			if (role == 1){
				roleStr = "未提交审核";
			}else if (role == 2){
				roleStr = "待审核";
			}else if (role == 3){
				roleStr = "已审核";
			}else if (role == 4){
				roleStr = "已驳回";
			}
		}
		return roleStr;
	}

	public void setRoleStr(String roleStr) {
		this.roleStr = roleStr;
	}

	public String getStatusStr() {
		//店铺禁用状态(1:正常，2：被禁用)
		if (status != null){
			if (status == 1){
				statusStr = "正常";
			}else if (status == 2){
				statusStr = "被禁用";
			}
		}
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String[] getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String[] categoryIds) {
		this.categoryIds = categoryIds;
	}

	public int[] getCategoryIds1() {
		return categoryIds1;
	}

	public void setCategoryIds1(int[] categoryIds1) {
		this.categoryIds1 = categoryIds1;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
}
