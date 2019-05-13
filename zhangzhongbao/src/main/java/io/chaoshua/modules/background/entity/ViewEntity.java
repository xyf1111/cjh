package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 标签单浏览表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-12-07 17:26:10
 */
@TableName("t_view")
public class ViewEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 刷手ID，关联t_user表
	 */
	private Long userId;
	/**
	 * 订单ID,关联t_mission表
	 */
	private Long missionId;
	/**
	 * 标签单天数
	 */
	private Integer day;
	/**
	 * 标签任务，浏览图片
	 */
	private String imglist;
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
	 * 设置：刷手ID，关联t_user表
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：刷手ID，关联t_user表
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：订单ID,关联t_mission表
	 */
	public void setMissionId(Long missionId) {
		this.missionId = missionId;
	}
	/**
	 * 获取：订单ID,关联t_mission表
	 */
	public Long getMissionId() {
		return missionId;
	}
	/**
	 * 设置：标签单天数
	 */
	public void setDay(Integer day) {
		this.day = day;
	}
	/**
	 * 获取：标签单天数
	 */
	public Integer getDay() {
		return day;
	}
	/**
	 * 设置：标签任务，浏览图片
	 */
	public void setImglist(String imglist) {
		this.imglist = imglist;
	}
	/**
	 * 获取：标签任务，浏览图片
	 */
	public String getImglist() {
		return imglist;
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
