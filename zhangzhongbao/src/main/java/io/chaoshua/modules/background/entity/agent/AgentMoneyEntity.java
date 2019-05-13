package io.chaoshua.modules.background.entity.agent;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.chaoshua.modules.agent.entity.AgentSellerMoneyEntity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 代理人定价表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-12-22 10:33:42
 */
@TableName("t_agent_money")
public class AgentMoneyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 代理id,管理t_agent表
	 */
	private Long agentId;
	/**
	 * 商品价格在0-99元时,平台给的底价
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
	 * 商品价格在0-99元时,代理定义订单价格
	 */
	private BigDecimal one1;
	/**
	 * 商品价格在100-199元时，代理定义订单价格
	 */
	private BigDecimal two1;
	/**
	 * 商品价格在200-299元时，代理定义订单价格
	 */
	private BigDecimal three1;
	/**
	 * 商品价格在300-499元时，代理定义订单价格
	 */
	private BigDecimal four1;
	/**
	 * 在500 ~ 999元时，代理定义订单价格
	 */
	private BigDecimal five1;
	/**
	 * 商品价格在1000 ~ 1999元时，代理定义订单价格
	 */
	private BigDecimal six1;
	/**
	 * 商品价格在2000 ~ 2999元时，代理定义订单价格
	 */
	private BigDecimal seven1;
	/**
	 * 单个商品价格在3000 ~ 3999元以上时,代理定义订单价格
	 */
	private BigDecimal eight1;
	/**
	 * 单个商品价格在4000 ~ 4999元以上时，代理定义订单价格
	 */
	private BigDecimal nine1;
	/**
	 * 单个商品价格在5000元以上时，代理定义订单价格
	 */
	private BigDecimal ten1;
	/**
	 * 审核人Id
	 */
	private Long inChangeId;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

	/***************************额外添加字段开始*******************************************/
	/**
	 * 标签二天价格
	 */
	@TableField(exist = false)
	private BigDecimal view1;
	/**
	 * 标签三天价格
	 */
	@TableField(exist = false)
	private BigDecimal view2;
	/**
	 * 指定评价价格
	 */
	@TableField(exist = false)
	private BigDecimal comment;
	/**
	 * 指定追评价格
	 */
	@TableField(exist = false)
	private BigDecimal addComment;
	/**
	 * 商家价格
	 */
	@TableField(exist = false)
	private AgentSellerMoneyEntity agentSellerMoney;
	/***************************额外添加字段结束*******************************************/

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
	 * 设置：代理id,管理t_agent表
	 */
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	/**
	 * 获取：代理id,管理t_agent表
	 */
	public Long getAgentId() {
		return agentId;
	}
	/**
	 * 设置：商品价格在0-99元时,平台给的底价
	 */
	public void setOne(BigDecimal one) {
		this.one = one;
	}
	/**
	 * 获取：商品价格在0-99元时,平台给的底价
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
	 * 设置：商品价格在0-99元时,代理定义订单价格
	 */
	public void setOne1(BigDecimal one1) {
		this.one1 = one1;
	}
	/**
	 * 获取：商品价格在0-99元时,代理定义订单价格
	 */
	public BigDecimal getOne1() {
		return one1;
	}
	/**
	 * 设置：商品价格在100-199元时，代理定义订单价格
	 */
	public void setTwo1(BigDecimal two1) {
		this.two1 = two1;
	}
	/**
	 * 获取：商品价格在100-199元时，代理定义订单价格
	 */
	public BigDecimal getTwo1() {
		return two1;
	}
	/**
	 * 设置：商品价格在200-299元时，代理定义订单价格
	 */
	public void setThree1(BigDecimal three1) {
		this.three1 = three1;
	}
	/**
	 * 获取：商品价格在200-299元时，代理定义订单价格
	 */
	public BigDecimal getThree1() {
		return three1;
	}
	/**
	 * 设置：商品价格在300-499元时，代理定义订单价格
	 */
	public void setFour1(BigDecimal four1) {
		this.four1 = four1;
	}
	/**
	 * 获取：商品价格在300-499元时，代理定义订单价格
	 */
	public BigDecimal getFour1() {
		return four1;
	}
	/**
	 * 设置：在500 ~ 999元时，代理定义订单价格
	 */
	public void setFive1(BigDecimal five1) {
		this.five1 = five1;
	}
	/**
	 * 获取：在500 ~ 999元时，代理定义订单价格
	 */
	public BigDecimal getFive1() {
		return five1;
	}
	/**
	 * 设置：商品价格在1000 ~ 1999元时，代理定义订单价格
	 */
	public void setSix1(BigDecimal six1) {
		this.six1 = six1;
	}
	/**
	 * 获取：商品价格在1000 ~ 1999元时，代理定义订单价格
	 */
	public BigDecimal getSix1() {
		return six1;
	}
	/**
	 * 设置：商品价格在2000 ~ 2999元时，代理定义订单价格
	 */
	public void setSeven1(BigDecimal seven1) {
		this.seven1 = seven1;
	}
	/**
	 * 获取：商品价格在2000 ~ 2999元时，代理定义订单价格
	 */
	public BigDecimal getSeven1() {
		return seven1;
	}
	/**
	 * 设置：单个商品价格在3000 ~ 3999元以上时,代理定义订单价格
	 */
	public void setEight1(BigDecimal eight1) {
		this.eight1 = eight1;
	}
	/**
	 * 获取：单个商品价格在3000 ~ 3999元以上时,代理定义订单价格
	 */
	public BigDecimal getEight1() {
		return eight1;
	}
	/**
	 * 设置：单个商品价格在4000 ~ 4999元以上时，代理定义订单价格
	 */
	public void setNine1(BigDecimal nine1) {
		this.nine1 = nine1;
	}
	/**
	 * 获取：单个商品价格在4000 ~ 4999元以上时，代理定义订单价格
	 */
	public BigDecimal getNine1() {
		return nine1;
	}
	/**
	 * 设置：单个商品价格在5000元以上时，代理定义订单价格
	 */
	public void setTen1(BigDecimal ten1) {
		this.ten1 = ten1;
	}
	/**
	 * 获取：单个商品价格在5000元以上时，代理定义订单价格
	 */
	public BigDecimal getTen1() {
		return ten1;
	}
	/**
	 * 设置：审核人Id
	 */
	public void setInChangeId(Long inChangeId) {
		this.inChangeId = inChangeId;
	}
	/**
	 * 获取：审核人Id
	 */
	public Long getInChangeId() {
		return inChangeId;
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

	public BigDecimal getView1() {
		return view1;
	}

	public void setView1(BigDecimal view1) {
		this.view1 = view1;
	}

	public BigDecimal getView2() {
		return view2;
	}

	public void setView2(BigDecimal view2) {
		this.view2 = view2;
	}

	public BigDecimal getComment() {
		return comment;
	}

	public void setComment(BigDecimal comment) {
		this.comment = comment;
	}

	public BigDecimal getAddComment() {
		return addComment;
	}

	public void setAddComment(BigDecimal addComment) {
		this.addComment = addComment;
	}

	public AgentSellerMoneyEntity getAgentSellerMoney() {
		return agentSellerMoney;
	}

	public void setAgentSellerMoney(AgentSellerMoneyEntity agentSellerMoney) {
		this.agentSellerMoney = agentSellerMoney;
	}
}
