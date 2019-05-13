package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-19 16:52:51
 */
@TableName("t_bank")
public class BankEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 银行名称
	 */
	private String name;
	/**
	 * 银行备注
	 */
	private String note;
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
	 * 设置：银行名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：银行名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：银行备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：银行备注
	 */
	public String getNote() {
		return note;
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
