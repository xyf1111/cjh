package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-11-20 15:51:03
 */
@TableName("t_message")
public class MessageEntity implements Serializable {
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
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 订单编号
	 */
	private String code;
	/**
	 * 管理员ID
	 */
	private Long platformId;
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
	 * 设置：消息标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：消息标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：消息内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：消息内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：订单编号
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：订单编号
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：管理员ID
	 */
	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
	/**
	 * 获取：管理员ID
	 */
	public Long getPlatformId() {
		return platformId;
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
