package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 全局设置表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-19 17:59:48
 */
@TableName("t_invitation_step")
public class InvitationStepEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 是否需要邀请码（1：是，0：否）
	 */
	private Integer open;
	/**
	 * 商家发单本金限制
	 */
	private BigDecimal sellerLimit;
	/**
	 * 店铺接单周期
	 */
	private Integer shopInterval;
	/**
	 * 店铺当天发单限制
	 */
	private Integer shopPublish;
	/**
	 * 接单金额限制
	 */
	private BigDecimal limit;
	/**
	 * 日接单限制
	 */
	private Integer dayLimit;
	/**
	 * 月接单限制
	 */
	private Integer monthLimit;
	/**
	 * 提现最低额度
	 */
	private BigDecimal withLimit;
	/**
	 * 是否需要认证
	 */
	private Integer isAuth;
	/**
	 * 拒绝店铺接单封禁时间
	 */
	private Integer refuseShop;
	/**
	 * 撤销订单限制时间
	 */
	private Integer removeTime;
	/**
	 * 认证失效时间
	 */
	private Integer authTime;
	/**
	 * 收货时间限制
	 */
	private Integer receiveGood;

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
	 * 设置：是否需要邀请码（1：是，0：否）
	 */
	public void setOpen(Integer open) {
		this.open = open;
	}
	/**
	 * 获取：是否需要邀请码（1：是，0：否）
	 */
	public Integer getOpen() {
		return open;
	}
	/**
	 * 设置：商家发单本金限制
	 */
	public void setSellerLimit(BigDecimal sellerLimit) {
		this.sellerLimit = sellerLimit;
	}
	/**
	 * 获取：商家发单本金限制
	 */
	public BigDecimal getSellerLimit() {
		return sellerLimit;
	}
	/**
	 * 设置：店铺接单周期
	 */
	public void setShopInterval(Integer shopInterval) {
		this.shopInterval = shopInterval;
	}
	/**
	 * 获取：店铺接单周期
	 */
	public Integer getShopInterval() {
		return shopInterval;
	}
	/**
	 * 设置：店铺当天发单限制
	 */
	public void setShopPublish(Integer shopPublish) {
		this.shopPublish = shopPublish;
	}
	/**
	 * 获取：店铺当天发单限制
	 */
	public Integer getShopPublish() {
		return shopPublish;
	}
	/**
	 * 设置：接单金额限制
	 */
	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}
	/**
	 * 获取：接单金额限制
	 */
	public BigDecimal getLimit() {
		return limit;
	}
	/**
	 * 设置：日接单限制
	 */
	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}
	/**
	 * 获取：日接单限制
	 */
	public Integer getDayLimit() {
		return dayLimit;
	}
	/**
	 * 设置：月接单限制
	 */
	public void setMonthLimit(Integer monthLimit) {
		this.monthLimit = monthLimit;
	}
	/**
	 * 获取：月接单限制
	 */
	public Integer getMonthLimit() {
		return monthLimit;
	}
	/**
	 * 设置：提现最低额度
	 */
	public void setWithLimit(BigDecimal withLimit) {
		this.withLimit = withLimit;
	}
	/**
	 * 获取：提现最低额度
	 */
	public BigDecimal getWithLimit() {
		return withLimit;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}

	/**
	 * 设置：拒绝店铺接单封禁时间
	 */
	public void setRefuseShop(Integer refuseShop) {
		this.refuseShop = refuseShop;
	}
	/**
	 * 获取：拒绝店铺接单封禁时间
	 */
	public Integer getRefuseShop() {
		return refuseShop;
	}

	public Integer getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Integer authTime) {
		this.authTime = authTime;
	}

	public Integer getRemoveTime() {
		return removeTime;
	}

	public void setRemoveTime(Integer removeTime) {
		this.removeTime = removeTime;
	}

	public Integer getReceiveGood() {
		return receiveGood;
	}

	public void setReceiveGood(Integer receiveGood) {
		this.receiveGood = receiveGood;
	}
}
