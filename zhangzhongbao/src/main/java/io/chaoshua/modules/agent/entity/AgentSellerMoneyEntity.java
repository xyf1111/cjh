package io.chaoshua.modules.agent.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 代理与商家关系表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2019-03-04 17:10:09
 */
@TableName("t_agent_seller_money")
public class AgentSellerMoneyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 代理平台id，关联t_agent表
	 */
	private Long agentId;
	/**
	 * 商家id，关联t_seller表
	 */
	private Long sellerId;
	/**
	 * 
	 */
	private BigDecimal one;
	/**
	 * 商品价格在100-199元时，平台给的底价
	 */
	private BigDecimal two;
	/**
	 * 商品价格在200-299元时，平台给的底价
	 */
	private BigDecimal three;
	/**
	 * 商品价格在300-499元时，平台给的底价
	 */
	private BigDecimal four;
	/**
	 * 在500 ~ 999元时，平台给的底价
	 */
	private BigDecimal five;
	/**
	 * 商品价格在1000 ~ 1999元时，平台给的底价
	 */
	private BigDecimal six;
	/**
	 * 商品价格在2000 ~ 2999元时，平台给的底价
	 */
	private BigDecimal seven;
	/**
	 * 单个商品价格在3000 ~ 3999元以上时,平台给的底价
	 */
	private BigDecimal eight;
	/**
	 * 单个商品价格在4000 ~ 4999元以上时，平台给的底价
	 */
	private BigDecimal nine;
	/**
	 * 单个商品价格在5000元以上时，平台给的底价
	 */
	private BigDecimal ten;
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
	 * 设置：代理平台id，关联t_agent表
	 */
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	/**
	 * 获取：代理平台id，关联t_agent表
	 */
	public Long getAgentId() {
		return agentId;
	}
	/**
	 * 设置：商家id，关联t_seller表
	 */
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	/**
	 * 获取：商家id，关联t_seller表
	 */
	public Long getSellerId() {
		return sellerId;
	}
	/**
	 * 设置：
	 */
	public void setOne(BigDecimal one) {
		this.one = one;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getOne() {
		return one;
	}
	/**
	 * 设置：商品价格在100-199元时，平台给的底价
	 */
	public void setTwo(BigDecimal two) {
		this.two = two;
	}
	/**
	 * 获取：商品价格在100-199元时，平台给的底价
	 */
	public BigDecimal getTwo() {
		return two;
	}
	/**
	 * 设置：商品价格在200-299元时，平台给的底价
	 */
	public void setThree(BigDecimal three) {
		this.three = three;
	}
	/**
	 * 获取：商品价格在200-299元时，平台给的底价
	 */
	public BigDecimal getThree() {
		return three;
	}
	/**
	 * 设置：商品价格在300-499元时，平台给的底价
	 */
	public void setFour(BigDecimal four) {
		this.four = four;
	}
	/**
	 * 获取：商品价格在300-499元时，平台给的底价
	 */
	public BigDecimal getFour() {
		return four;
	}
	/**
	 * 设置：在500 ~ 999元时，平台给的底价
	 */
	public void setFive(BigDecimal five) {
		this.five = five;
	}
	/**
	 * 获取：在500 ~ 999元时，平台给的底价
	 */
	public BigDecimal getFive() {
		return five;
	}
	/**
	 * 设置：商品价格在1000 ~ 1999元时，平台给的底价
	 */
	public void setSix(BigDecimal six) {
		this.six = six;
	}
	/**
	 * 获取：商品价格在1000 ~ 1999元时，平台给的底价
	 */
	public BigDecimal getSix() {
		return six;
	}
	/**
	 * 设置：商品价格在2000 ~ 2999元时，平台给的底价
	 */
	public void setSeven(BigDecimal seven) {
		this.seven = seven;
	}
	/**
	 * 获取：商品价格在2000 ~ 2999元时，平台给的底价
	 */
	public BigDecimal getSeven() {
		return seven;
	}
	/**
	 * 设置：单个商品价格在3000 ~ 3999元以上时,平台给的底价
	 */
	public void setEight(BigDecimal eight) {
		this.eight = eight;
	}
	/**
	 * 获取：单个商品价格在3000 ~ 3999元以上时,平台给的底价
	 */
	public BigDecimal getEight() {
		return eight;
	}
	/**
	 * 设置：单个商品价格在4000 ~ 4999元以上时，平台给的底价
	 */
	public void setNine(BigDecimal nine) {
		this.nine = nine;
	}
	/**
	 * 获取：单个商品价格在4000 ~ 4999元以上时，平台给的底价
	 */
	public BigDecimal getNine() {
		return nine;
	}
	/**
	 * 设置：单个商品价格在5000元以上时，平台给的底价
	 */
	public void setTen(BigDecimal ten) {
		this.ten = ten;
	}
	/**
	 * 获取：单个商品价格在5000元以上时，平台给的底价
	 */
	public BigDecimal getTen() {
		return ten;
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
