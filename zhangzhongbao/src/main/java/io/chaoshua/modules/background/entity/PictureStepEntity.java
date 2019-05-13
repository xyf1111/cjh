package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 二维码设置表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-20 10:21:48
 */
@TableName("t_picture_step")
public class PictureStepEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 小号截图二维码
	 */
	private String xiaohaoRegister;
	/**
	 * 小号截图示范
	 */
	private String xiaohaoExample;
	/**
	 * 已购买订单二维码
	 */
	private String buyRegister;
	/**
	 * 已购买订单示范
	 */
	private String buyExample;
	/**
	 * 支付宝二维码
	 */
	private String aliRegister;
	/**
	 * 性别二维码
	 */
	private String sexRegister;
	/**
	 * 性别二维码示范
	 */
	private String sexExample;
	/**
	 * 体验中心示范
	 */
	private String checkExample;
	/**
	 * 体验注册
	 */
	private String checkCode;
	/**
	 * 是否显示二维码（1：是，2：否）
	 */
	private Integer checkStyle;
	/**
	 * 体验中心二维码
	 */
	private String checkRegister;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

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
	 * 设置：小号截图二维码
	 */
	public void setXiaohaoRegister(String xiaohaoRegister) {
		this.xiaohaoRegister = xiaohaoRegister;
	}
	/**
	 * 获取：小号截图二维码
	 */
	public String getXiaohaoRegister() {
		return xiaohaoRegister;
	}
	/**
	 * 设置：小号截图示范
	 */
	public void setXiaohaoExample(String xiaohaoExample) {
		this.xiaohaoExample = xiaohaoExample;
	}
	/**
	 * 获取：小号截图示范
	 */
	public String getXiaohaoExample() {
		return xiaohaoExample;
	}
	/**
	 * 设置：已购买订单二维码
	 */
	public void setBuyRegister(String buyRegister) {
		this.buyRegister = buyRegister;
	}
	/**
	 * 获取：已购买订单二维码
	 */
	public String getBuyRegister() {
		return buyRegister;
	}
	/**
	 * 设置：已购买订单示范
	 */
	public void setBuyExample(String buyExample) {
		this.buyExample = buyExample;
	}
	/**
	 * 获取：已购买订单示范
	 */
	public String getBuyExample() {
		return buyExample;
	}
	/**
	 * 设置：支付宝二维码
	 */
	public void setAliRegister(String aliRegister) {
		this.aliRegister = aliRegister;
	}
	/**
	 * 获取：支付宝二维码
	 */
	public String getAliRegister() {
		return aliRegister;
	}
	/**
	 * 设置：性别二维码
	 */
	public void setSexRegister(String sexRegister) {
		this.sexRegister = sexRegister;
	}
	/**
	 * 获取：性别二维码
	 */
	public String getSexRegister() {
		return sexRegister;
	}
	/**
	 * 设置：性别二维码示范
	 */
	public void setSexExample(String sexExample) {
		this.sexExample = sexExample;
	}
	/**
	 * 获取：性别二维码示范
	 */
	public String getSexExample() {
		return sexExample;
	}
	/**
	 * 设置：体验中心示范
	 */
	public void setCheckExample(String checkExample) {
		this.checkExample = checkExample;
	}
	/**
	 * 获取：体验中心示范
	 */
	public String getCheckExample() {
		return checkExample;
	}
	/**
	 * 设置：体验注册
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	/**
	 * 获取：体验注册
	 */
	public String getCheckCode() {
		return checkCode;
	}
	/**
	 * 设置：是否显示二维码（1：是，2：否）
	 */
	public void setCheckStyle(Integer checkStyle) {
		this.checkStyle = checkStyle;
	}
	/**
	 * 获取：是否显示二维码（1：是，2：否）
	 */
	public Integer getCheckStyle() {
		return checkStyle;
	}
	/**
	 * 设置：体验中心二维码
	 */
	public void setCheckRegister(String checkRegister) {
		this.checkRegister = checkRegister;
	}
	/**
	 * 获取：体验中心二维码
	 */
	public String getCheckRegister() {
		return checkRegister;
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
}
