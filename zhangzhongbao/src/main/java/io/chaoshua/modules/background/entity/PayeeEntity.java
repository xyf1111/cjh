package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 收款人银行账号表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-19 17:11:25
 */
@TableName("t_payee")
public class PayeeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 持卡人姓名
	 */
	private String holder;
	/**
	 * 所属银行
	 */
	private String bank;
	/**
	 * 银行卡卡号
	 */
	private String cardNumber;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 是否显示状态（1：是，2：否）
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
	 * 设置：持卡人姓名
	 */
	public void setHolder(String holder) {
		this.holder = holder;
	}
	/**
	 * 获取：持卡人姓名
	 */
	public String getHolder() {
		return holder;
	}
	/**
	 * 设置：所属银行
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}
	/**
	 * 获取：所属银行
	 */
	public String getBank() {
		return bank;
	}
	/**
	 * 设置：银行卡卡号
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	/**
	 * 获取：银行卡卡号
	 */
	public String getCardNumber() {
		return cardNumber;
	}
	/**
	 * 设置：备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：备注
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置：是否显示状态（1：是，2：否）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：是否显示状态（1：是，2：否）
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
}
