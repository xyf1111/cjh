package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信配置表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-20 10:05:56
 */
@TableName("t_wx_step")
public class WxStepEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * APPID
	 */
	private String appId;
	/**
	 * appsecret
	 */
	private String appsecret;
	/**
	 * 消息模板ID
	 */
	private String templateId;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 状态（是否使用1：是，2：否）
	 */
	private Integer status;
	/**
	 * 授权回调地址
	 */
	private String url;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

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
	 * 设置：APPID
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 获取：APPID
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * 设置：appsecret
	 */
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	/**
	 * 获取：appsecret
	 */
	public String getAppsecret() {
		return appsecret;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
