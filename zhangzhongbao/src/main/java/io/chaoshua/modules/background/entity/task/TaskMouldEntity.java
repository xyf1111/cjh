package io.chaoshua.modules.background.entity.task;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 任务模板
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-11-12 16:29:45
 */
@TableName("t_task_mould")
public class TaskMouldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商家id,关联t_seller
	 */
	private Long sellerId;
	/**
	 * 店铺id，关联t_shop
	 */
	private Long shopId;
	/**
	 * 产品主图1
	 */
	private String img;
	/**
	 * 产品主图2
	 */
	private String img2;
	/**
	 * 产品主图3
	 */
	private String img3;
	/**
	 * 店铺名称
	 */
	private String shopName;
	/**
	 * 商品链接
	 */
	private String url;
	/**
	 * 商品款号
	 */
	private String style;
	/**
	 * 商品价格
	 */
	private BigDecimal price;
	/**
	 * 价格区间小
	 */
	private BigDecimal priceFrom;
	/**
	 * 价格区间大
	 */
	private BigDecimal priceTo;
	/**
	 * 商品货重
	 */
	private BigDecimal weight;
	/**
	 * 商品关键字
	 */
	private String keyword;
	/**
	 * 商品排列顺序
	 */
	private Integer order;
	/**
	 * 商品排序方式起始排名
	 */
	private Integer intervalBegin;
	/**
	 * 商品排列方式终止排名
	 */
	private Long intervalEnd;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 其他备注
	 */
	private String otherNote;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

	/***********************额外添加字段开始***************************/
	/**
	 * 排序方式格式化 商品排序方式（1：综合排序，2：销量排序，3：价格排序）
	 */
	@TableField(exist = false)
	private String orderStr;
	/************************额外添加字段结束**************************/
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
	 * 设置：商家id,关联t_seller
	 */
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	/**
	 * 获取：商家id,关联t_seller
	 */
	public Long getSellerId() {
		return sellerId;
	}
	/**
	 * 设置：店铺id，关联t_shop
	 */
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	/**
	 * 获取：店铺id，关联t_shop
	 */
	public Long getShopId() {
		return shopId;
	}
	/**
	 * 设置：产品主图1
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * 获取：产品主图1
	 */
	public String getImg() {
		return img;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	/**
	 * 设置：店铺名称
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 * 获取：店铺名称
	 */
	public String getShopName() {
		return shopName;
	}
	/**
	 * 设置：商品链接
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：商品链接
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：商品款号
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * 获取：商品款号
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * 设置：商品价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：商品价格
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：商品关键字
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * 获取：商品关键字
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * 设置：商品排列顺序
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * 获取：商品排列顺序
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * 设置：商品排序方式起始排名
	 */
	public void setIntervalBegin(Integer intervalBegin) {
		this.intervalBegin = intervalBegin;
	}
	/**
	 * 获取：商品排序方式起始排名
	 */
	public Integer getIntervalBegin() {
		return intervalBegin;
	}
	/**
	 * 设置：商品排列方式终止排名
	 */
	public void setIntervalEnd(Long intervalEnd) {
		this.intervalEnd = intervalEnd;
	}
	/**
	 * 获取：商品排列方式终止排名
	 */
	public Long getIntervalEnd() {
		return intervalEnd;
	}
	/**
	 * 设置：备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：备注
	 */
	public String getNote() {
		return note;
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

	public String getOtherNote() {
		return otherNote;
	}

	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * 获取：
	 */


	public Date getUpdateTime() {
		return updateTime;
	}

	public String getOrderStr() {
		//商品排序方式（1：综合排序，2：销量排序，3：价格排序）
		if (order != null){
			if (order == 1){
				orderStr = "综合排序";
			}else if (order == 2){
				orderStr = "销量排序";
			}else if (order == 3){
				orderStr = "价格排序";
			}
		}
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public BigDecimal getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}

	public BigDecimal getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}
}
