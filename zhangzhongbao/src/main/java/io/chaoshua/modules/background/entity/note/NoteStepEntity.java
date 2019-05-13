package io.chaoshua.modules.background.entity.note;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-25 16:41:54
 */
@TableName("t_note_step")
public class NoteStepEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 撤销备注
	 */
	private String removeNote;
	/**
	 * 拒绝备注
	 */
	private String refuseNote;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

	/**
	 * 备注类型
	 */
	private Integer type;
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
	 * 设置：
	 */
	public void setRemoveNote(String removeNote) {
		this.removeNote = removeNote;
	}
	/**
	 * 获取：
	 */
	public String getRemoveNote() {
		return removeNote;
	}
	/**
	 * 设置：
	 */
	public void setRefuseNote(String refuseNote) {
		this.refuseNote = refuseNote;
	}
	/**
	 * 获取：
	 */
	public String getRefuseNote() {
		return refuseNote;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
