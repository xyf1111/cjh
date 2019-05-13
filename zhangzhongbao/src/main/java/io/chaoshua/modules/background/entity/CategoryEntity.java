package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺类目管理
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-11-12 18:39:56
 */
@TableName("t_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 类目名称
	 */
	private String name;
	/**
	 * 父级ID
	 */
	private Long parentId;
	/**
	 * 类目接单周期，单位（天）
	 */
	private Integer limit;

	/********************|********额外添加字段开始************************************/
	@TableField(exist = false)
	/**
	 * 父级名称
	 */
	private String parentName;
	/****************************额外添加字段结束************************************/
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
	 * 设置：类目名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：类目名称
	 */
	public String getName() {
		return name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 设置：类目接单周期，单位（天）
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	/**
	 * 获取：类目接单周期，单位（天）
	 */
	public Integer getLimit() {
		return limit;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
