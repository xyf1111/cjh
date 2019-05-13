package io.chaoshua.modules.app.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信验证码
 * 
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2018-04-25 16:07:10
 */
@TableName("t_sms_code")
public class SmsCodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 短信ID
	 */
	@TableId
	private Long id;
	/**
	 * 验证码
	 */
	private String code;
	/**
	 * 短信类型
	 */
	private Integer type;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;


	/**
	 * 设置：短信ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：短信ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：验证码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：验证码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：短信类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：短信类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：手机号码
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号码
	 */
	public String getMobile() {
		return mobile;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
