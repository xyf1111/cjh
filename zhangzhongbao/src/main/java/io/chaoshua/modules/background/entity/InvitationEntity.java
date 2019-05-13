package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 邀请码表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-18 18:33:20
 */
@TableName("t_invitation")
public class InvitationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 刷手ID
	 */
	private Long userId;
	/**
	 * 持卡人姓名
	 */
	private String holder;
	/**
	 * 刷手手机号
	 */
	private String mobile;
	/**
	 * 邀请码
	 */
	private String code;
	/**
	 * 是否被使用(1:否，2：是)
	 */
	private Integer isUse;
	/**
	 * 是否可用（1:是，2：禁用）
	 */
	private Integer status;
	/**
	 * 使用人
	 */
	private String useMan;
	/**
	 * 使用时间
	 */
	private Date useTime;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;
	/**********************************新增字段开始*****************************************************/
	/**
	 * 邀请码个数
	 */
	@TableField(exist = false)
	private Integer number;
	/**********************************新增字段结束*****************************************************/
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
	 * 设置：刷手ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：刷手ID
	 */
	public Long getUserId() {
		return userId;
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
	 * 设置：刷手手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：刷手手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：邀请码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：邀请码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：是否被使用(1:否，2：是)
	 */
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	/**
	 * 获取：是否被使用(1:否，2：是)
	 */
	public Integer getIsUse() {
		return isUse;
	}
	/**
	 * 设置：是否可用（1:是，2：禁用）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：是否可用（1:是，2：禁用）
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：使用人
	 */
	public void setUseMan(String useMan) {
		this.useMan = useMan;
	}
	/**
	 * 获取：使用人
	 */
	public String getUseMan() {
		return useMan;
	}
	/**
	 * 设置：使用时间
	 */
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}
	/**
	 * 获取：使用时间
	 */
	public Date getUseTime() {
		return useTime;
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
