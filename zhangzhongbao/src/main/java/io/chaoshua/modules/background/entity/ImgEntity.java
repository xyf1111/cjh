package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 示例表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2019-01-09 14:08:24
 */
@TableName("t_img")
public class ImgEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 示例名称
	 */
	private String name;
	/**
	 * 图片
	 */
	private String img;
	/**
	 * 示例类型（1：注册示例，2：认证示例）
	 */
	private Integer type;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

	/**
	 * 显示顺序
	 */
	private  Integer sort;
	/**
	 * 显示状态
	 */
	private Integer status;

	/*********************************新增字段开始*********************************************/
	/**
	 * 图片格式化
	 */
	@TableField(exist = false)
	private String [] imgList;
	/*********************************新增字段结束*********************************************/

	public String[] getImgList() {
		return imgList;
	}

	public void setImgList(String[] imgList) {
		this.imgList = imgList;
	}

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
	 * 设置：示例名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：示例名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：图片
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * 获取：图片
	 */
	public String getImg() {
		return img;
	}
	/**
	 * 设置：示例类型（1：注册示例，2：认证示例）
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：示例类型（1：注册示例，2：认证示例）
	 */
	public Integer getType() {
		return type;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getOrder() {
		return sort;
	}

	public void setOrder(Integer order) {
		this.sort = order;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
