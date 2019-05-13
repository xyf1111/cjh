package io.chaoshua.modules.background.entity.task;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务关键字表
 * @author linys
 */
@TableName("t_key_word")
public class KeyWordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;

	private Long taskId;

	private Long taskMouldId;

	private String keyWord;

	private Integer amount;

	private Integer sort;

	private Date createTime;

	@TableField(exist = false)
	private Boolean isUsed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getTaskMouldId() {
		return taskMouldId;
	}

	public void setTaskMouldId(Long taskMouldId) {
		this.taskMouldId = taskMouldId;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getUsed() {
		return isUsed;
	}

	public void setUsed(Boolean used) {
		isUsed = used;
	}
}
