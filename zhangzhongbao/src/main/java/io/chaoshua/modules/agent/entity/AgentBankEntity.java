package io.chaoshua.modules.agent.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 代理商银行卡管理表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-12-27 12:06:18
 */
@TableName("t_agent_bank")
public class AgentBankEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 代理商ID, t_agent表
	 */
	private Long agentId;
	/**
	 * 持卡人姓名
	 */
	private String holder;
	/**
	 * 所属银行
	 */
	private String bank;
	/**
	 * 
	 */
	private String cardNumber;
	/**
	 * 
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
	/**************************新增字段开始********************************************/
	@TableField(exist = false)
	private String statusStr;
	/***************************新增字段结束*******************************************/
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
	 * 设置：代理商ID, t_agent表
	 */
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	/**
	 * 获取：代理商ID, t_agent表
	 */
	public Long getAgentId() {
		return agentId;
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

	public String getStatusStr() {
		if (status != null){
			if (status == 1){
				statusStr = "是";
			}else if(status == 2){
				statusStr = "否";
			}
		}
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
}
