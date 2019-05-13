package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 流水表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-18 11:54:46
 */
@TableName("t_detail")
public class DetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联t_user表
	 */
	private Long userId;
	/**
	 * 刷手姓名
	 */
	private String userName;
	/**
	 * 刷手手机号
	 */
	private String mobile;
	/**
	 * 流水对象类型(1:商家，2：刷手)
	 */
	private Integer isSeller;
	/**
	 * 是否报错（1：是，0：否）
	 */
	private Integer isWrong;
	/**
	 * 佣金流动类型（1商家充值，2刷手接单获得不可提现佣金，3商家发布任务被拒绝返回的佣金，
     * 4指定评价的佣金，5刷手订单被审核通过获得的佣金，6刷手提现的佣金，7订单被撤销，返回给商家的佣金或是扣除刷手的佣金，8下家完成任务的佣金）
	 */
	private Integer type;
	/**
	 * 佣金变动金额总值
	 */
	private BigDecimal amount;
	/**
	 * 刷手在佣金变化之前的不可提现佣金
	 */
	private BigDecimal beforeStock;
	/**
	 * 刷手在佣金变化之前的可提现佣金
	 */
	private BigDecimal beforeFree;
	/**
	 * 刷手在佣金变化之后的不可提现佣金
	 */
	private BigDecimal stock;
	/**
	 * 刷手在佣金变化之后的可提现佣金
	 */
	private BigDecimal free;
	/**
	 * 商家余额
	 */
	private BigDecimal balance;
	/**
	 * 流水备注
	 */
	private String note;
	/**
	 * 订单编号
	 */
	private String code;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

	/*********************************额外添加字段开始********************************************/

	/**
	 * 操作类型
	 */
	@TableField(exist = false)
	private String typeStr;
	/**
	 * 总额
	 */
	@TableField(exist = false)
	private String sumMoney;
	/**
	 * 计算类型（1：加，2：减）
	 */
	@TableField(exist = false)
	private Integer flag;
	/*********************************额外添加字段结束********************************************/
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
	 * 设置：关联t_user表
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：关联t_user表
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：刷手姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：刷手姓名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：刷手手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：刷手手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：流水对象类型(1:商家，2：刷手)
	 */
	public void setIsSeller(Integer isSeller) {
		this.isSeller = isSeller;
	}
	/**
	 * 获取：流水对象类型(1:商家，2：刷手)
	 */
	public Integer getIsSeller() {
		return isSeller;
	}
	/**
	 * 设置：是否报错（1：是，0：否）
	 */
	public void setIsWrong(Integer isWrong) {
		this.isWrong = isWrong;
	}
	/**
	 * 获取：是否报错（1：是，0：否）
	 */
	public Integer getIsWrong() {
		return isWrong;
	}
	/**
	 * 设置：佣金流动类型（1商家充值，2刷手接单获得不可提现佣金，3商家发布任务被拒绝返回的佣金，
4指定评价的佣金，5刷手订单被审核通过获得的佣金，6刷手提现的佣金，7订单被撤销，返回给商家的佣金或是扣除刷手的佣金，8下家完成任务的佣金）
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：佣金流动类型（1商家充值，2刷手接单获得不可提现佣金，3商家发布任务被拒绝返回的佣金，
4指定评价的佣金，5刷手订单被审核通过获得的佣金，6刷手提现的佣金，7订单被撤销，返回给商家的佣金或是扣除刷手的佣金，8下家完成任务的佣金）
	 */
	public Integer getType() {
		return type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBeforeStock() {
		return beforeStock;
	}

	public void setBeforeStock(BigDecimal beforeStock) {
		this.beforeStock = beforeStock;
	}

	public BigDecimal getBeforeFree() {
		return beforeFree;
	}

	public void setBeforeFree(BigDecimal beforeFree) {
		this.beforeFree = beforeFree;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public BigDecimal getFree() {
		return free;
	}

	public void setFree(BigDecimal free) {
		this.free = free;
	}

	/**
	 * 设置：流水备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：流水备注
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置：订单编号
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：订单编号
	 */
	public String getCode() {
		return code;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getTypeStr() {
		if(type != null){
			//佣金流动类型（1商家充值，2刷手接单获得不可提现佣金，3商家发布任务被拒绝返回的佣金，
			//4指定评价的佣金，5刷手订单被审核通过获得的佣金，6刷手提现的佣金，7订单被撤销，返回给商家的佣金或是扣除刷手的佣金，8下家完成任务的佣金,9后台修改）
			if (type == 1){
				typeStr = "商户充值";
			}else if(type == 2){
				typeStr = "订单付给刷手佣金";
			}else if(type == 3){
				typeStr = "发单拒绝返还佣金";
			}else if(type == 4){
				typeStr = "指定评价";
			}else if(type == 5){
				typeStr = "接受订单";
			}else if(type == 6){
				typeStr = "提现明细";
			}else if(type == 7){
				typeStr = "撤销任务返还";
			}else if(type == 8){
				typeStr = "下家完成任务的佣金";
			}else if(type == 9){
				typeStr = "后台修改";
			}
		}
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(String sumMoney) {
		this.sumMoney = sumMoney;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
