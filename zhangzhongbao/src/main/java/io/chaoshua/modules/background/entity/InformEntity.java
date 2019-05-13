package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-27 11:29:58
 */
@TableName("t_inform")
public class InformEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 接收对象（1：商家，2：刷手）
	 */
	private Integer receiverList;
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
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：接收对象（1：商家，2：刷手）
	 */
	public void setReceiverList(Integer receiverList) {
		this.receiverList = receiverList;
	}
	/**
	 * 获取：接收对象（1：商家，2：刷手）
	 */
	public Integer getReceiverList() {
		return receiverList;
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
