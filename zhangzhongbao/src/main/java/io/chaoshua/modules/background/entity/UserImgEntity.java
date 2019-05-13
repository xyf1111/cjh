package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.chaoshua.common.UploadUrl.UploadUrl;

import java.io.Serializable;
import java.util.Date;

/**
 * 刷手注册认证图片信息表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2019-01-11 18:55:26
 */
@TableName("t_user_img")
public class UserImgEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户ID,关联t_user表
	 */
	private Long userId;
	/**
	 * 图片名称
	 */
	private String name;
	/**
	 * 图片
	 */
	private String img;

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
	 * 设置：用户ID,关联t_user表
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID,关联t_user表
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：图片名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：图片名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：图片
	 */
	public void setImg(String img) {
		if (!img.contains("http")){
			img = UploadUrl.getUrl()+ img;
		}
		this.img = img;
	}
	/**
	 * 获取：图片
	 */
	public String getImg() {
		return img;
	}
}
