/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.chaoshua.modules.app.entity;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.chaoshua.modules.app.form.RegisterForm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:06
 */
@TableName("t_user")
public class AppUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 刷手淘宝号
	 */
	private String taobao;
	/**
	 * 刷手手机号
	 */
	private String mobile;
	/**
	 * 刷手密码
	 */
	private String password;
	/**
	 * 微信号
	 */
	private String name;
	/**
	 * 微信openId
	 */
	private String openId;
	/**
	 * 刷手微信号
	 */
	private String wechat;
	/**
	 * 刷手QQ
	 */
	private String qq;
	/**
	 * 刷手银行卡持卡人姓名
	 */
	private String holder;
	/**
	 * 刷手银行卡号
	 */
	private String cardNumber;
	/**
	 * 刷手银行卡所属银行
	 */
	private String bank;
	/**
	 * parentInvitation
	 */
	private String parentInvitation;
	/**
	 * 可提现佣金
	 */
	private BigDecimal freeMoney;
	/**
	 * 不可提现佣金，整数
	 */
	private BigDecimal stockMoney;
	/**
	 * 订单审核状态(1待审核,2已审核,3已拒绝,4已禁用)
	 */
	private Integer status;
	/**
	 * 完成订单数量
	 */
	private Integer finishNumber;
	/**
	 * 上家刷手ID
	 */
	private Long highUserId;

	public Long getHighUserId() {
		return highUserId;
	}

	/**
	 * 一级下家刷手ID
	 */
	private String lowUserId;
	/**
	 * 二级下家刷手ID、下两级刷手ID，数组
	 */
	private String lowerUserId;
	/**
	 * 小号评价截图
	 */
	private String xiaohao;
	/**
	 * 淘宝账号已购买宝贝截图
	 */
	private String buy;
	/**
	 * 淘宝账号体验中心截图
	 */
	private String check;
	/**
	 * 刷手微信二维码
	 */
	private String wechatqr;
	/**
	 * 刷手支付宝信息截图
	 */
	private String ali;
	/**
	 * 性别照片
	 */
	private String sex;
	/**
	 * 刷手备注
	 */
	private String note;
	/**
	 * 是否入黑名单（1：否，2：是）
	 */
	private Integer role;
	/**
	 * 被允许接单的商品最大单价
	 */
	private BigDecimal limit;
	/**
	 * 日接单数量限制
	 */
	private Integer dayLimit;
	/**
	 * 月结单数量限制
	 */
	private Integer monthLimit;
	/**
	 * 提现底线限制
	 */
	private BigDecimal withLimit;
	/**
	 * 刷手淘宝手机号
	 */
	private String mobileForTaobao;
	/**
	 * 管理平台修改刷手的金额
	 */
	private BigDecimal amount;
	/**
	 * 刷手邮箱
	 */
	private String email;
	/**
	 * 刷手最近一次接单订单并审核通过的时间
	 */
	private String lastReceive;
	/**
	 * 上家刷手ID
	 */
	private String highestUserId;
	/**
	 * 身份证正面
	 */
	private String positive;
	/**
	 * 身份证正面
	 */
	private String back;
	/**
	 *
	 */
	private Date createTime;
	/**
	 *
	 */
	private Date updateTime;

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
	 * 设置：刷手密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：刷手密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：刷手微信名
	 */
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	/**
	 * 获取：刷手微信名
	 */
	public String getWechat() {
		return wechat;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * 设置：刷手银行卡持卡人姓名
	 */
	public void setHolder(String holder) {
		this.holder = holder;
	}
	/**
	 * 获取：刷手银行卡持卡人姓名
	 */
	public String getHolder() {
		return holder;
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
	 * 设置：身份证正面照片
	 */
	public void setPositive(String positive) {
		this.positive = positive;
	}
	/**
	 * 获取：身份证正面照片
	 */
	public String getPositive() {
		return positive;
	}
	/**
	 * 设置：身份证反面照片
	 */
	public void setBack(String back) {
		this.back = back;
	}
	/**
	 * 获取：身份证反面照片
	 */
	public String getBack() {
		return back;
	}
	/**
	 * 设置：可提现佣金
	 */
	public void setFreeMoney(BigDecimal freeMoney) {
		this.freeMoney = freeMoney;
	}
	/**
	 * 获取：可提现佣金
	 */
	public BigDecimal getFreeMoney() {
		return freeMoney;
	}
	/**
	 * 设置：不可提现佣金，整数
	 */
	public void setStockMoney(BigDecimal stockMoney) {
		this.stockMoney = stockMoney;
	}
	/**
	 * 获取：不可提现佣金，整数
	 */
	public BigDecimal getStockMoney() {
		return stockMoney;
	}
	/**
	 * 设置：订单审核状态(1待审核,2已审核,3已拒绝,4已禁用)
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：订单审核状态(1待审核,2已审核,3已拒绝,4已禁用)
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：完成订单数量
	 */
	public void setFinishNumber(Integer finishNumber) {
		this.finishNumber = finishNumber;
	}
	/**
	 * 获取：完成订单数量
	 */
	public Integer getFinishNumber() {
		return finishNumber;
	}
	/**
	 * 设置：一级下家刷手ID
	 */
	public void setLowUserId(String lowUserId) {
		this.lowUserId = lowUserId;
	}
	/**
	 * 获取：一级下家刷手ID
	 */
	public String getLowUserId() {
		return lowUserId;
	}
	/**
	 * 设置：二级下家刷手ID、下两级刷手ID，数组
	 */
	public void setLowerUserId(String lowerUserId) {
		this.lowerUserId = lowerUserId;
	}
	/**
	 * 获取：二级下家刷手ID、下两级刷手ID，数组
	 */
	public String getLowerUserId() {
		return lowerUserId;
	}
	/**
	 * 设置：小号评价截图
	 */
	public void setXiaohao(String xiaohao) {
		this.xiaohao = xiaohao;
	}
	/**
	 * 获取：小号评价截图
	 */
	public String getXiaohao() {
		return xiaohao;
	}
	/**
	 * 设置：淘宝账号已购买宝贝截图
	 */
	public void setBuy(String buy) {
		this.buy = buy;
	}
	/**
	 * 获取：淘宝账号已购买宝贝截图
	 */
	public String getBuy() {
		return buy;
	}
	/**
	 * 设置：淘宝账号体验中心截图
	 */
	public void setCheck(String check) {
		this.check = check;
	}
	/**
	 * 获取：淘宝账号体验中心截图
	 */
	public String getCheck() {
		return check;
	}
	/**
	 * 设置：刷手微信二维码
	 */
	public void setWechatqr(String wechatqr) {
		this.wechatqr = wechatqr;
	}
	/**
	 * 获取：刷手微信二维码
	 */
	public String getWechatqr() {
		return wechatqr;
	}

	public void setHighUserId(Long highUserId) {
		this.highUserId = highUserId;
	}

	/**
	 * 设置：刷手支付宝信息截图
	 */
	public void setAli(String ali) {
		this.ali = ali;
	}
	/**
	 * 获取：刷手支付宝信息截图
	 */
	public String getAli() {
		return ali;
	}
	/**
	 * 设置：性别照片
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别照片
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 设置：刷手备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：刷手备注
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置：是否入黑名单（1：否，2：是）
	 */
	public void setRole(Integer role) {
		this.role = role;
	}
	/**
	 * 获取：是否入黑名单（1：否，2：是）
	 */
	public Integer getRole() {
		return role;
	}
	/**
	 * 设置：被允许接单的商品最大单价
	 */
	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}
	/**
	 * 获取：被允许接单的商品最大单价
	 */
	public BigDecimal getLimit() {
		return limit;
	}
	/**
	 * 设置：管理平台修改刷手的金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 获取：管理平台修改刷手的金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 设置：刷手邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：刷手邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：上家刷手ID
	 */
	public void setHighestUserId(String highestUserId) {
		this.highestUserId = highestUserId;
	}
	/**
	 * 获取：上家刷手ID
	 */
	public String getHighestUserId() {
		return highestUserId;
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

	public String getParentInvitation() {
		return parentInvitation;
	}

	public void setParentInvitation(String parentInvitation) {
		this.parentInvitation = parentInvitation;
	}

	public Integer getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}

	public Integer getMonthLimit() {
		return monthLimit;
	}

	public void setMonthLimit(Integer monthLimit) {
		this.monthLimit = monthLimit;
	}

	public BigDecimal getWithLimit() {
		return withLimit;
	}

	public void setWithLimit(BigDecimal withLimit) {
		this.withLimit = withLimit;
	}

	public String getMobileForTaobao() {
		return mobileForTaobao;
	}

	public void setMobileForTaobao(String mobileForTaobao) {
		this.mobileForTaobao = mobileForTaobao;
	}

	public String getLastReceive() {
		return lastReceive;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setLastReceive(String lastReceive) {
		this.lastReceive = lastReceive;
	}

	public static AppUserEntity toEntity(RegisterForm form){
		AppUserEntity appUserEntity = new AppUserEntity();
		appUserEntity.setMobile(form.getMobile());
		appUserEntity.setPassword(BCrypt.hashpw(form.getPassword(),BCrypt.gensalt()));
		appUserEntity.setTaobao(form.getTaobao());
		appUserEntity.setName(form.getName());
		appUserEntity.setEmail(form.getEmail());
		appUserEntity.setWechat(form.getWechat());
		appUserEntity.setQq(form.getQq());
		appUserEntity.setHolder(form.getHolder());
		appUserEntity.setCardNumber(form.getCardNumber());
		appUserEntity.setBank(form.getBank());
		appUserEntity.setPositive(form.getPositive());
		appUserEntity.setBack(form.getBack());
		appUserEntity.setParentInvitation(form.getParentInvitation());
//		appUserEntity.setXiaohao(form.getXiaohao());
//		appUserEntity.setBuy(form.getBuy());
//		appUserEntity.setCheck(form.getCheck());
//		appUserEntity.setAli(form.getAli());
//		appUserEntity.setSex(form.getSex());

		return appUserEntity;
	}



}
