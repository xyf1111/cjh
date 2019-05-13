package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 刷手提现
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-31 14:55:16
 */
@TableName("t_withdrawals")
public class WithdrawalsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 刷手ID,关联user表
	 */
	private Long userId;
	/**
	 * 刷手手机号
	 */
	private String mobile;
	/**
	 * 刷手微信号
	 */
	private String wechat;
	/**
	 * 刷手银行卡所属银行
	 */
	private String bank;
	/**
	 * 刷手银行卡号
	 */
	private String cardNumber;
	/**
	 * 提现金额
	 */
	private BigDecimal amount;
	/**
	 * 银行卡持卡人姓名
	 */
	private String holder;
	/**
	 * 微信个人二维码
	 */
	private String wechatqr;
	/**
	 * 刷手淘宝号
	 */
	private String taobao;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 提现类型(1:银行卡，2：微信)
	 */
	private Integer type;
	/**
	 * 提现审核状态(1:已提交，2：已提现，3：已拒绝)
	 */
	private Integer role;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

	@TableField(exist = false)
	private List<WithdrawalsEntity> list;
	/******************************************添加字段开始************************************/

	/**
	 * 提现状态格式化
	 */
	@TableField(exist = false)
	private String roleStr;

	/**
	 * 导出报表格式
	 */
	@TableField(exist = false)
	private Integer excelType;
	/**
	 * 提现总金额（1：待审核，2：已提现）
	 */
	@TableField(exist = false)
	private String sumMoney;
	/******************************************添加字段结束************************************/
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
	 * 设置：刷手ID,关联user表
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：刷手ID,关联user表
	 */
	public Long getUserId() {
		return userId;
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
	 * 设置：刷手微信号
	 */
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	/**
	 * 获取：刷手微信号
	 */
	public String getWechat() {
		return wechat;
	}
	/**
	 * 设置：刷手银行卡所属银行
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}
	/**
	 * 获取：刷手银行卡所属银行
	 */
	public String getBank() {
		return bank;
	}
	/**
	 * 设置：刷手银行卡号
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	/**
	 * 获取：刷手银行卡号
	 */
	public String getCardNumber() {
		return cardNumber;
	}
	/**
	 * 设置：提现金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 获取：提现金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 设置：银行卡持卡人姓名
	 */
	public void setHolder(String holder) {
		this.holder = holder;
	}
	/**
	 * 获取：银行卡持卡人姓名
	 */
	public String getHolder() {
		return holder;
	}
	/**
	 * 设置：微信个人二维码
	 */
	public void setWechatqr(String wechatqr) {
		this.wechatqr = wechatqr;
	}
	/**
	 * 获取：微信个人二维码
	 */
	public String getWechatqr() {
		return wechatqr;
	}
	/**
	 * 设置：刷手淘宝号
	 */
	public void setTaobao(String taobao) {
		this.taobao = taobao;
	}
	/**
	 * 获取：刷手淘宝号
	 */
	public String getTaobao() {
		return taobao;
	}
	/**
	 * 设置：提现类型(1:银行卡，2：微信)
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：提现类型(1:银行卡，2：微信)
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：提现审核状态(1:已提交，2：已提现，3：已拒绝)
	 */
	public void setRole(Integer role) {
		this.role = role;
	}
	/**
	 * 获取：提现审核状态(1:已提交，2：已提现，3：已拒绝)
	 */
	public Integer getRole() {
		return role;
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

	public String getRoleStr() {
		if (role != null){
			if (role == 1){
				roleStr ="未处理";
			}else if (role == 2){
				roleStr = "已提现";
			}else if (role == 3){
				roleStr = "已拒绝";
			}
		}
		return roleStr;
	}

	public void setRoleStr(String roleStr) {
		this.roleStr = roleStr;
	}

	public List<WithdrawalsEntity> getList() {
		return list;
	}

	public void setList(List<WithdrawalsEntity> list) {
		this.list = list;
	}

	public Integer getExcelType() {
		return excelType;
	}

	public void setExcelType(Integer excelType) {
		this.excelType = excelType;
	}

	public String getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(String sumMoney) {
		this.sumMoney = sumMoney;
	}
}
